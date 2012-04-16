package seis.stthomas.edu.domain;

import org.apache.log4j.Logger;

public class Game {
	public Board board;
	public GameState state;
	public boolean activePlayerIsWhite;
	public int pieceRow;
	public int pieceCol;
	boolean isOpponentCPU; // indicates if opponent is a CPU player or not
	PieceMove cpuMove; // most recent CPU move
	CPUPlayer cpuPlayer; // reference to a CPU player in case a game is played
							// vs CPU

	private static final Logger LOG = Logger.getLogger(Game.class.getName());

	/**
	 * Initialize for a new game. Create the board and set inital states.
	 */
	public void newGame(boolean playVsCPU, int difficulty) {
		board = new Board();
		state = GameState.choosePiece;
		isOpponentCPU = playVsCPU;
		cpuPlayer.setDifficulty(difficulty);
	}

	public Game() {
		board = new Board();
		state = GameState.choosePiece;
		activePlayerIsWhite = true;
		LOG.info("Leaving the game constructor!");
		cpuPlayer = new CPUPlayer();
		cpuPlayer.setDifficulty(1);

	}

	/**
	 * squareSelected - This method is called by the UI to notify the game
	 * controller that a square at coordinates (row, col) has been selected.
	 * This method checks if the selection was valid, updates the game state as
	 * needed, and gives a return status indication to the UI.
	 */
	public SelectionStatus squareSelected(int row, int col) {
		SelectionStatus status;

		// call the appropriate selection handler based on the game state.
		if (this.state == GameState.choosePiece) {
			status = pieceSelected(row, col);
		} else if (this.state == GameState.chooseDestination) {
			status = destinationSelected(row, col);
		} else {
			// Only expect a square to be pressed in one of the above
			// states. Otherwise, let the UI know this was unexpected.
			status = SelectionStatus.invalidState;
			LOG.warn("Returning an invalid state for some reason!");

		}

		return status;
	}

	/**
	 * pieceSelected - This sub-method of squareSelected handles a selection
	 * when the state is GameState.choosePiece. The square selected must have a
	 * piece matching the color of the active player. Otherwise, an error status
	 * will be returned.
	 */
	private SelectionStatus pieceSelected(int row, int col) {
		Piece piece;
		SelectionStatus status;

		piece = this.board.getPiece(row, col);
		LOG.info("Row selected was : " + row);
		LOG.info("Column selected was : " + col);

		// Verify the space has a piece of the same color as
		// the active player
		if (piece == null) {
			LOG.warn("The piece selected is null!");

			status = SelectionStatus.pieceNull;
		} else if (piece.isWhite() != this.activePlayerIsWhite) {
			status = SelectionStatus.pieceWrongColor;
		} else {
			status = SelectionStatus.pieceValid;
			this.pieceRow = row;
			this.pieceCol = col;

			// Clear the squares in range on the board, then request that the
			// piece identify which squares are in range. The piece will
			// update the board.
			this.board.clearSquaresInRange();
			piece.updateSquaresInRange(row, col);

			// The next step is to wait for a destination square
			this.state = GameState.chooseDestination;
		}

		return status;
	}

	/**
	 * destinationSelection - This sub-method of squareSelected handles a
	 * selection when the state is GameState.chooseDestination. The square
	 * selected must be in range of the piece previously selected, and the move
	 * must not place the active player in check.
	 */
	private SelectionStatus destinationSelected(int destRow, int destCol) {
		SelectionStatus status;
		boolean opponent;

		// Initially assume state is switching to waiting for a piece
		// selection. This may be overwritten with checkmate below.
		this.state = GameState.choosePiece;

		// If the square is not in range, update the return status
		if (this.board.getSquareInRange(destRow, destCol) == false) {
			status = SelectionStatus.destOutOfRange;
		}

		// If the square is in range, and it does not place the current
		// player in check, the move is valid.

		else if (this.board.tryMove(this.pieceRow, this.pieceCol, destRow,
				destCol, true, true) == false) {
			status = SelectionStatus.destSelfInCheck;
		}

		// Otherwise, the move is valid
		else {
			// Evaluate whether the move places the opposing player in check
			opponent = (this.activePlayerIsWhite == true) ? false : true;

			// If opponent has a valid move, update status to check or no-check
			if (this.board.playerHasValidMove(opponent)) {
				if (this.board.playerInCheck(opponent)) {
					status = SelectionStatus.destValidCheck;
				} else {
					status = SelectionStatus.destValidNoCheck;
				}

				// If the game is not over, update the player turn
				this.activePlayerIsWhite = opponent;

				// If it is the CPU player's turn
				if ((this.activePlayerIsWhite == false) && isOpponentCPU) {
					state = GameState.cpuTurn;
				}
			}

			// if the opponent has no valid move, declare checkmate or draw
			else {
				this.state = GameState.idle;

				if (this.board.playerInCheck(opponent)) {
					status = SelectionStatus.destValidCheckMate;
				} else {
					status = SelectionStatus.destValidDraw;
				}
			}
		}

		return status;
	}

	public SelectionStatus determineCPUMove() throws InterruptedException {
		SelectionStatus status;

		cpuMove = cpuPlayer.selectMove(board);

		// If opponent has a valid move, update status to check or no-check
		if (board.playerHasValidMove(true)) {
			if (board.playerInCheck(true)) {
				status = SelectionStatus.destValidCheck;
			} else {
				status = SelectionStatus.destValidNoCheck;

			}

			// If the game is not over, update the player turn
			activePlayerIsWhite = true;
			state = GameState.choosePiece;
		}

		// if the opponent has no valid move, declare checkmate or draw
		else {
			state = GameState.idle;

			if (board.playerInCheck(true)) {
				status = SelectionStatus.destValidCheckMate;

			} else {
				status = SelectionStatus.destValidDraw;
			}
		}

		return status;
	}

	/**
	 * getPiece - Pass-through message to the Board object, to retrieve the
	 * piece at a specified location.
	 */
	public Piece getPiece(int row, int col) {
		return this.board.getPiece(row, col);
	}

	/**
	 * getSquareInRange - Pass-through message to the Board object, to retrieve
	 * the in-range status at a specified location.
	 */
	public boolean getSquareInRange(int row, int col) {
		return this.board.getSquareInRange(row, col);
	}

	public boolean getActivePlayerIsWhite() {
		return this.activePlayerIsWhite;
	}

	/**
	 * @return the cpuMove
	 */
	public PieceMove getCpuMove() {
		return cpuMove;
	}

	/**
	 * @return the state
	 */
	public GameState getState() {
		return state;
	}

}