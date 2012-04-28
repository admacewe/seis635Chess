package seis.stthomas.edu.ai;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

import org.apache.log4j.Logger;

import seis.stthomas.edu.domain.Board;
import seis.stthomas.edu.domain.Piece;

public class BestMoveThread extends Thread implements Runnable, Comparable<BestMoveThread> {
	
	private int startCol;
	private int startRow;
	private int destCol;
	private int destRow;
	private Board board;
	private CPUPlayer cpuPlayer;
	private boolean isWhite;
	private int currentScore;
	private int movesRemaining;
	private HashMap<Integer, BestMoveThread> globalMovesList;
	
	public int score;
	
	
	
	private static final Logger LOG = Logger.getLogger(BestMoveThread.class
			.getName());
	
	
	public BestMoveThread(int startCol, int startRow, int destCol, int destRow,
			Board board, CPUPlayer cpuPlayer, boolean isWhite,
			int currentScore, int movesRemaining, HashMap<?, ?> movesList) {
		super();
		this.startCol = startCol;
		this.startRow = startRow;
		this.destCol = destCol;
		this.destRow = destRow;
		this.board = board;
		this.cpuPlayer = cpuPlayer;
		this.isWhite = isWhite;
		this.currentScore = currentScore;
		this.movesRemaining = movesRemaining;
		this.globalMovesList = (HashMap<Integer, BestMoveThread>) movesList;
	}
	
	
	@Override
	public void run() {
		Piece startPiece;
		Piece destPiece;
		boolean opponentColorIsWhite = (isWhite == true) ? false : true;
		PieceMove move = null;
		
		LOG.debug("Entering :: getMoveScore ");

		// set references to the pieces at the start and destination squares, so
		// these
		// can be restored later
		startPiece = board.getPiece(startRow, startCol);
		destPiece = board.getPiece(destRow, destCol);

		// get the value of the piece that is captured, if any
		if (destPiece != null) {
			currentScore += destPiece.getPieceValue();
		}

		// update the board with the new move
		try {
			if (board.tryMove(startRow, startCol, destRow, destCol, true, false)) {
				// if the move was valid, check if there are more moves to evaluate
				if (movesRemaining <= 0) {
					this.score = currentScore;

				} else {
					// Request the opponent to pick their best move. The score is
					// inverted when
					// passed to the opponent, and when returned, because a high
					// score indicates
					// a favorable outcome for the active player.
					try {
						move = cpuPlayer.pickBestMove(board, opponentColorIsWhite, -1
								* currentScore, movesRemaining - 1);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (SecurityException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IllegalArgumentException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (NoSuchMethodException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					this.score = -1 * move.getScore();
				}

				// After finding the best score, restore the state of the board
				board.setPiece(startRow, startCol, startPiece);
				board.setPiece(destRow, destCol, destPiece);
			} else {
				// move placed self in check - return the invalid move score
				this.score = cpuPlayer.INVALID_SCORE;

				// no need to undo the move here, because tryMove already undid it
			}
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		LOG.debug("score :: "+ score);
		LOG.debug("Exiting :: getMoveScore ");
		globalMovesList.put(this.hashCode(),this);
//		return score;
	}
	
	


	/**
	 * @return the score
	 */
	public int getScore() {
		return score;
	}
	
	


	/**
	 * @return the startCol
	 */
	public int getStartCol() {
		return startCol;
	}


	/**
	 * @return the startRow
	 */
	public int getStartRow() {
		return startRow;
	}


	/**
	 * @return the destCol
	 */
	public int getDestCol() {
		return destCol;
	}


	/**
	 * @return the destRow
	 */
	public int getDestRow() {
		return destRow;
	}


	/**
	 * @return the isWhite
	 */
	public boolean isWhite() {
		return isWhite;
	}


	/**
	 * @return the currentScore
	 */
	public int getCurrentScore() {
		return currentScore;
	}


	/**
	 * @return the movesRemaining
	 */
	public int getMovesRemaining() {
		return movesRemaining;
	}


	/**
	 * @return the globalMovesList
	 */
	public HashMap<?, ?> getGlobalMovesList() {
		return globalMovesList;
	}


	@Override
	public int compareTo(BestMoveThread otherThread) {
		int compareQuantity = ((BestMoveThread) otherThread).getScore(); 
		 
		//ascending order
		return this.score - compareQuantity;
 
		//descending order
		//return compareQuantity - this.score;
	}

}
