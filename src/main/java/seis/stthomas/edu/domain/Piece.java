/**
 * Piece - Abstract base class from which all chess pieces are derived.
 */

package seis.stthomas.edu.domain;

import java.util.List;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.log4j.Logger;

import seis.stthomas.edu.utility.Utilities;

public abstract class Piece {
	boolean isWhite;
	Board board;
	boolean hasMoved;
	int value;

	private static final Logger LOG = Logger.getLogger(Piece.class.getName());

	public Piece(boolean isWhite, Board boardRef, int value) {
		this.isWhite = isWhite;
		this.board = boardRef;
		this.hasMoved = false;
		this.value = value;
	}
	
	public Piece(boolean isWhite, int value) {
		this.isWhite = isWhite;
		this.hasMoved = false;
		this.value = value;
	}

	/**
	 * getPiecesInRange - Abstract method to be defined by each piece type. This
	 * method determines which spaces are valid as a destination for the
	 * selected piece. The Board object notifies the Piece of its current row
	 * and column position with the input parameters. This method calls
	 * setSquareInRange for each board position that is in the valid range.
	 */
	
	//TODO Document these abstract methods
	public abstract void updateSquaresInRange(int currentRow, int currentCol);

	public abstract String getMoveStrategy();
	
	public abstract List<ImmutablePair<Integer, Integer>> getAvailableMoves();
	/**
	 * setSquaresOnVector - Step along a vector from a starting position, and
	 * set all squares in range that are empty, until encountering another
	 * piece. If that piece is not the same color, also add that square to the
	 * valid range.
	 */
	protected void setSquaresOnVector(int startRow, int startCol, int rowDelta,
			int colDelta) {
		LOG.debug(" >> entering setSquaresOnVector");
		boolean done = false;
		int row = startRow;
		int col = startCol;
		Piece piece;

		while (!done) {
			// apply the delta to move along the vector
			row += rowDelta;
			col += colDelta;

			// first check if the square is within the board limits
			if (Utilities.validSquare(row, col)) {
				piece = board.getPiece(row, col);

				// if there is no piece on the square, it is a valid square
				if (piece == null) {
					board.setSquareInRange(row, col);
				}

				/*
				 * If there is a piece on the square, the search is done. Add
				 * the square if the color does not match the selected piece's
				 * color.
				 */
				else {
					done = true;
					if (piece.isWhite() != isWhite()) {
						board.setSquareInRange(row, col);
					}
				}
			}

			// If square is not valid, end the search
			else {
				done = true;
			}
		}
	}

	/**
	 * setSingleSquareUnlessColorMatch - Set a single square as being in range
	 * if the square is empty or if it has a piece of the opposite color
	 */
	protected void setSingleSquareUnlessColorMatch(int row, int col) {
		LOG.debug(" >> entering setSingleSquareUnlessColorMatch");

		Piece piece;

		// first check if the square is within the board limits
		if (Utilities.validSquare(row, col)) {
			piece = board.getPiece(row, col);

			// if there is no piece on the square, it is a valid square
			if (piece == null) {
				board.setSquareInRange(row, col);
			}

			/*
			 * If there is a piece on the square, add the square if the color
			 * does not match the selected piece's color.
			 */
			else if (piece.isWhite() != isWhite()) {
				board.setSquareInRange(row, col);
			}
		}
	}

	/**
	 * setSingleSquareIfColorMismatch - Set a single square as being in range if
	 * the square is occupied and has the opposite color
	 */
	protected void setSingleSquareIfColorMismatch(int row, int col) {
		Piece piece;

		// first check if the square is within the board limits
		if (Utilities.validSquare(row, col)) {
			piece = board.getPiece(row, col);

			// if there is no piece on the square, it is a valid square
			if (piece != null) {
				/*
				 * If there is a piece on the square, add the square if the
				 * color does not match the selected piece's color.
				 */
				if (piece.isWhite() != isWhite()) {
					board.setSquareInRange(row, col);
				}
			}
		}
	}

	public void setHasMoved(boolean moved) {
		hasMoved = moved;
	}

	public boolean getHasMoved() {
		return hasMoved;
	}

	public boolean isWhite() {
		return isWhite;
	}

	public Class getType() {
		return this.getClass();
	}

	public int getValue() {
		return value;
	}

}
