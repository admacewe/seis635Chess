package seis.stthomas.edu.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import org.apache.log4j.Logger;

import seis.stthomas.edu.utility.BestMoveThread;

public class CPUPlayer {
	private Random rand;
	private int difficulty;
	public static final int INVALID_SCORE = -1000000;
	private static final int MAX_DIFFICULTY = 10;
	private HashMap<?, ?> movesList;

	private static final Logger LOG = Logger.getLogger(CPUPlayer.class
			.getName());

	public CPUPlayer() {
		rand = new Random();
	}

	public void setDifficulty(int difficultySetting) {
		if (difficultySetting > MAX_DIFFICULTY) {
			difficulty = MAX_DIFFICULTY;
		} else {
			difficulty = difficultySetting;
		}
	}

	public PieceMove selectMove(Board board) throws InterruptedException {
		PieceMove move = pickBestMove(board, false, 0, difficulty - 1);

		if (move != null) {
			board.tryMove(move.startRow, move.startCol, move.destRow,
					move.destCol, true, true);
		}

		LOG.info("move from " + move.startRow + ", " + move.startCol + " to "
				+ move.destRow + ", " + move.destCol + " with score of : "
				+ move.score);

		return move;
	}

	/**
	 * Select the best move for a player based on the current score and moves
	 * remaining.
	 * 
	 * @throws InterruptedException
	 */
	public PieceMove pickBestMove(Board board, boolean isWhite,
			int currentScore, int movesRemaining) throws InterruptedException {
		long timeNow = new Date().getTime();

		LOG.debug("Entering :: PickBestMove ");

		if (movesList == null) {
			movesList = new HashMap();
		}

		int startRow;
		int startCol;
		int destRow;
		int destCol;
		Piece piece;
		ArrayList<PieceMove> moveCandidates = new ArrayList<PieceMove>();
		PieceMove move;
		int score;
		int bestScore = INVALID_SCORE;
		boolean copySquaresInRange[][] = new boolean[8][8];

		// loop over all the squares on the board to find available pieces to
		// move
		for (startRow = 0; startRow < 8; startRow++) {
			for (startCol = 0; startCol < 8; startCol++) {
				// if there is a piece of the player's color, check where it can
				// move
				piece = board.getPiece(startRow, startCol);
				if ((piece != null) && (piece.isWhite() == isWhite)) {
					board.clearSquaresInRange();
					piece.updateSquaresInRange(startRow, startCol);

					// create a copy of the squaresInRange array
					for (destRow = 0; destRow < 8; destRow++) {
						for (destCol = 0; destCol < 8; destCol++) {
							copySquaresInRange[destRow][destCol] = board
									.getSquareInRange(destRow, destCol);
						}
					}

					// for each square in range, get the score of that move
					for (destRow = 0; destRow < 8; destRow++) {
						for (destCol = 0; destCol < 8; destCol++) {
							if (copySquaresInRange[destRow][destCol]) {
								score = getMoveScore(board, startRow, startCol,
										destRow, destCol, isWhite,
										movesRemaining, currentScore);
								// System.out.printf("pickBestMove: trying a move from %d, %d to %d, %d: score=%d\n",
								// startRow, startCol, destRow, destCol, score);
								if (score >= bestScore) {
									// if this is the highest score, update the
									// high score
									// and create a new list of move candidates
									if (score > bestScore) {
										bestScore = score;
										moveCandidates = new ArrayList<PieceMove>();
									}

									// add this move to the candidate list
									move = new PieceMove();
									move.startRow = startRow;
									move.startCol = startCol;
									move.destCol = destCol;
									move.destRow = destRow;
									move.score = score;
									moveCandidates.add(move);
								}
							}
						}
					}
				}
			}
		}

		// if there is a valid move, return a random selection from the
		// candidates
		if (moveCandidates.size() > 0) {
			move = moveCandidates.get(rand.nextInt(moveCandidates.size()));
		}

		// otherwise, return null to indicate no valid moves
		else {
			move = null;
		}

		long timeTaken = new Date().getTime() - timeNow;
		LOG.debug("Exiting  :: PickBestMove :: after this many milliSeconds ::  "
				+ timeTaken);
		return move;
	}

	/**
	 * getMoveScore - This method attempts a move, and asks the opponent to try
	 * all possible moves and return the best possible score assuming the given
	 * move is made.
	 * 
	 * @throws InterruptedException
	 */
	private int getMoveScore(Board board, int startRow, int startCol,
			int destRow, int destCol, boolean isWhite, int movesRemaining,
			int currentScore) throws InterruptedException {
		int score;
		Piece startPiece;
		Piece destPiece;
		boolean opponentColorIsWhite = (isWhite == true) ? false : true;
		PieceMove move;

		LOG.debug("Entering :: getMoveScore ");

		// set references to the pieces at the start and destination squares, so
		// these
		// can be restored later
		startPiece = board.getPiece(startRow, startCol);
		destPiece = board.getPiece(destRow, destCol);

		// get the value of the piece that is captured, if any
		if (destPiece != null) {
			currentScore += destPiece.getValue();
		}

		// update the board with the new move
		if (board.tryMove(startRow, startCol, destRow, destCol, true, false)) {
			// if the move was valid, check if there are more moves to evaluate
			if (movesRemaining <= 0) {
				score = currentScore;
			} else {
				// Request the opponent to pick their best move. The score is
				// inverted when
				// passed to the opponent, and when returned, because a high
				// score indicates
				// a favorable outcome for the active player.
				move = pickBestMove(board, opponentColorIsWhite, -1
						* currentScore, movesRemaining - 1);
				score = -1 * move.score;
			}

			// After finding the best score, restore the state of the board
			board.setPiece(startRow, startCol, startPiece);
			board.setPiece(destRow, destCol, destPiece);
		} else {
			// move placed self in check - return the invalid move score
			score = INVALID_SCORE;

			// no need to undo the move here, because tryMove already undid it
		}
		LOG.debug("Exiting :: getMoveScore ");
		return score;
	}

	public PieceMove pickBestMoveThreaded(Board board, boolean isWhite,
			int currentScore, int movesRemaining) throws InterruptedException {
		long timeNow = new Date().getTime();

		LOG.debug("Entering :: PickBestMove ");

		if (movesList == null) {
			movesList = new HashMap();
		}

		int startRow;
		int startCol;
		int destRow;
		int destCol;
		Piece piece;
		ArrayList<PieceMove> moveCandidates = new ArrayList<PieceMove>();
		PieceMove move;
		int bestScore = INVALID_SCORE;
		boolean copySquaresInRange[][] = new boolean[8][8];

		// loop over all the squares on the board to find available pieces to
		// move
		for (startRow = 0; startRow < 8; startRow++) {
			for (startCol = 0; startCol < 8; startCol++) {
				// if there is a piece of the player's color, check where it can
				// move
				piece = board.getPiece(startRow, startCol);
				if ((piece != null) && (piece.isWhite() == isWhite)) {
					board.clearSquaresInRange();
					piece.updateSquaresInRange(startRow, startCol);

					// create a copy of the squaresInRange array
					for (destRow = 0; destRow < 8; destRow++) {
						for (destCol = 0; destCol < 8; destCol++) {
							copySquaresInRange[destRow][destCol] = board
									.getSquareInRange(destRow, destCol);
						}
					}
					// END // create a copy of the squaresInRange array

					// for each square in range, get the score of that move
					List<BestMoveThread> threads = new ArrayList<BestMoveThread>();

					for (destRow = 0; destRow < 8; destRow++) {
						for (destCol = 0; destCol < 8; destCol++) {
							
							if (copySquaresInRange[destRow][destCol]) {
								LOG.debug("inside :: copySquaresInRange");

								BestMoveThread thread = new BestMoveThread(
										startCol, startRow, destCol, destRow,
										board, this, isWhite, currentScore,
										movesRemaining, movesList);
								thread.run();
								threads.add(thread);
								LOG.debug("add thread ! :" + thread.getId());

							}

						}

					}// done spawning threads now

					// join all threads together again.
					for (int i = 0; i < threads.size(); i++) {
						((Thread) threads.get(i)).join(1000000);
					}
					
					BestMoveThread[] valuesArray = (BestMoveThread[])movesList.values().toArray(new BestMoveThread[movesList.values().size()]);
					
					


					Arrays.sort(valuesArray);



					// check for best score and stuff...

					// if this is the highest score, update the
					// high score
					// and create a new list of move candidates
					if (valuesArray != null && valuesArray.length>0){

						if (valuesArray[0].getScore() > bestScore) {
							bestScore = valuesArray[0].getScore();
							if (bestScore > 0)
								LOG.info("setting best score! " + bestScore);

							moveCandidates = new ArrayList<PieceMove>();
						}
						// add this move to the candidate list
						move = new PieceMove();
						move.startRow = valuesArray[0].getStartRow();
						move.startCol = valuesArray[0].getStartCol();;
						move.destCol = valuesArray[0].getDestCol();
						move.destRow = valuesArray[0].getDestRow();
						move.score = 0;
						moveCandidates.add(move);
					}




					// for each square in range, get the score of that move

				}
			}
		}

		// if there is a valid move, return a random selection from the
		// candidates
		if (moveCandidates.size() > 0) {
			move = moveCandidates.get(rand.nextInt(moveCandidates.size()));
		}

		// otherwise, return null to indicate no valid moves
		else {
			move = null;
		}

		long timeTaken = new Date().getTime() - timeNow;
		LOG.debug("Exiting  :: PickBestMove :: after this many milliSeconds ::  "
				+ timeTaken);
		return move;
	}
}
