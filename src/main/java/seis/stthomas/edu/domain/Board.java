/**
 * Board - This class keeps track of which Pieces are at each location on the
 *  chess board, and checks for check and checkmate.  It is also responsible for
 *  for trying moves before they are made permanent, to verify that the move
 *  does not place the current player in check.
 */

package seis.stthomas.edu.domain;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.log4j.Logger;

import seis.stthomas.edu.utility.Utilities;

public class Board {
	Piece[][] pieces;
	boolean[][] squareInRange;

	private static final Logger LOG = Logger.getLogger(Board.class.getName());
	
	private static final Class[] parameterTypes = {Integer.TYPE, Integer.TYPE,Piece.class,List.class};

	

	//TODO this is has changes to the pieces etc.
	public Board(int x) {
		int row;
		int col;

		pieces = new Piece[8][8];

		// Create black pieces
		pieces[0][0] = new Rook(false);
		pieces[0][1] = new Knight(false);
		pieces[0][2] = new Bishop(false);
		pieces[0][3] = new Queen(false);
		pieces[0][4] = new King(false);
		pieces[0][5] = new Bishop(false);
		pieces[0][6] = new Knight(false);
		pieces[0][7] = new Rook(false);
		for (col = 0; col < 8; col++) {
			pieces[1][col] = new Pawn(false);
		}

		// Explicitely set middle rows to null
		for (row = 2; row < 6; row++) {
			for (col = 0; col < 8; col++) {
				pieces[row][col] = null;
			}
		}

		// Create white pieces
		for (col = 0; col < 8; col++) {
			pieces[6][col] = new Pawn(true);
		}
		pieces[7][0] = new Rook(true);
		pieces[7][1] = new Knight(true);
		pieces[7][2] = new Bishop(true);
		pieces[7][3] = new Queen(true);
		pieces[7][4] = new King(true);
		pieces[7][5] = new Bishop(true);
		pieces[7][6] = new Knight(true);
		pieces[7][7] = new Rook(true);

		// Initialize the squareInRange array
		squareInRange = new boolean[8][8];
		clearSquaresInRange();
	}
	
	public Board() {
		int row;
		int col;

		pieces = new Piece[8][8];

		// Create black pieces
		pieces[0][0] = new Rook(false, this);
		pieces[0][1] = new Knight(false, this);
		pieces[0][2] = new Bishop(false, this);
		pieces[0][3] = new Queen(false, this);
		pieces[0][4] = new King(false, this);
		pieces[0][5] = new Bishop(false, this);
		pieces[0][6] = new Knight(false, this);
		pieces[0][7] = new Rook(false, this);
		for (col = 0; col < 8; col++) {
			pieces[1][col] = new Pawn(false, this);
		}

		// Explicitely set middle rows to null
		for (row = 2; row < 6; row++) {
			for (col = 0; col < 8; col++) {
				pieces[row][col] = null;
			}
		}

		// Create white pieces
		for (col = 0; col < 8; col++) {
			pieces[6][col] = new Pawn(true, this);
		}
		pieces[7][0] = new Rook(true, this);
		pieces[7][1] = new Knight(true, this);
		pieces[7][2] = new Bishop(true, this);
		pieces[7][3] = new Queen(true, this);
		pieces[7][4] = new King(true, this);
		pieces[7][5] = new Bishop(true, this);
		pieces[7][6] = new Knight(true, this);
		pieces[7][7] = new Rook(true, this);

		// Initialize the squareInRange array
		squareInRange = new boolean[8][8];
		clearSquaresInRange();
	}

	/**
	 * tryMove - This method temporarily updates the state of the board by
	 * moving the piece at (curRow, curCol) to (destRow, destCol). It then
	 * checks to see if the move places the currently active player in check. If
	 * the move does not place the current player in check, it is OK, the board
	 * update is retained, and true is returned. If the move does place the
	 * current player in check, the move is undone, and false is returned.
	 */
	public boolean tryMove(int curRow, int curCol, int destRow, int destCol,
			boolean updateState, boolean updateHasMoved) {
		boolean valid = true;

		// Save references to the current source/destination in case these
		// need to be restored.
		Piece tempSourceRef = pieces[curRow][curCol];
		Piece tempDestRef = pieces[destRow][destCol];

		// Try moving the piece
		pieces[destRow][destCol] = pieces[curRow][curCol];
		pieces[curRow][curCol] = null;

		// If a pawn has moved into row 0 or row 7, exchange it for a queen
		if ((pieces[destRow][destCol].getType() == Pawn.class)
				&& ((destRow == 0) || (destRow == 7))) {
			pieces[destRow][destCol] = new Queen(
					pieces[destRow][destCol].isWhite(), this);
		}

		// Check if the move places the current player in check. The current
		// player can be found from the source piece
		if (playerInCheck(tempSourceRef.isWhite())) {
			valid = false;
		}

		// If the move is invalid or the state is not to be preserved, restore
		// the saved state
		if ((!valid) || (!updateState)) {
			pieces[destRow][destCol] = tempDestRef;
			pieces[curRow][curCol] = tempSourceRef;
		}

		// If the move is valid and the state is to be updated, set the hasMoved
		// flag
		if (valid && updateState && updateHasMoved) {
			pieces[destRow][destCol].setHasMoved(true);
		}

		return valid;
	}

	/**
	 * playerInCheck - Evaluate the board to see if the indicated player is in
	 * check.
	 */
	public boolean playerInCheck(boolean activePlayerIsWhite) {
		int kingRow = -1; // row containing the king
		int kingCol = -1; // column containing the king
		int row; // row index
		int col; // column index
		Piece piece; // temporary piece reference
		boolean check = false;

		// First, identify the location of the king. Stop looping when
		// the king is found (kingRow is updated).
		for (row = 0; (row < 8) && (kingRow < 0); row++) {
			for (col = 0; (col < 8) && (kingRow < 0); col++) {
				piece = getPiece(row, col);
				if (piece != null) {
					if ((piece.getType() == King.class)
							&& (piece.isWhite() == activePlayerIsWhite)) {
						kingRow = row;
						kingCol = col;
					}
				}
			}
		}

		// Next, loop over all pieces of the opposite color, and check if
		// the king location is in the selectable range.
		for (row = 0; (row < 8) && (!check); row++) {
			for (col = 0; (col < 8) && (!check); col++) {
				// find all pieces of the opposite color
				piece = getPiece(row, col);
				if (piece != null) {
					if (piece.isWhite() != activePlayerIsWhite) {
						// set the squares in range for that piece
						clearSquaresInRange();
						piece.updateSquaresInRange(row, col);

						// if the king is in range, declare check
						if (getSquareInRange(kingRow, kingCol)) {
							check = true;
						}
					}
				}
			}
		}

		return check;
	}

	/**
	 * playerInCheckMate - Evaluate the board to see if the indicated player is
	 * in checkmate.
	 */
	public boolean playerHasValidMove(boolean activePlayerIsWhite) {
		int row;
		int col;
		Piece piece;
		boolean validMove = false;

		// Find each of the player's pieces
		for (row = 0; (row < 8) && (!validMove); row++) {
			for (col = 0; (col < 8) && (!validMove); col++) {
				piece = getPiece(row, col);
				if (piece != null) {
					if (piece.isWhite() == activePlayerIsWhite) {
						validMove = pieceHasValidMove(piece, row, col);
					}
				}
			}
		}

		return validMove;
	}

	/**
	 * clearSquaresInRange - Initialize all squares as being not in range.
	 */
	public void clearSquaresInRange() {
		for (int row = 0; row < 8; row++) {
			for (int col = 0; col < 8; col++) {
				squareInRange[row][col] = false;
			}
		}
	}

	/**
	 * setSquareInRange - Sets the square indicated by the indices as being in
	 * range.
	 */
	public void setSquareInRange(int row, int col) {


		squareInRange[row][col] = Utilities.validSquare(row, col);

	}

	/**
	 * getSquareInRange - Gets the in-range status of a square indicated by the
	 * indices.
	 */
	public boolean getSquareInRange(int row, int col) {
		boolean inRange = false;

		if (Utilities.validSquare(row, col)) {
			inRange = squareInRange[row][col];
		}

		return inRange;
	}

	/**
	 * getPiece - Retrieve the piece at the specified row and column number. If
	 * there is no piece, or the indices are invalid, null is returned.
	 */
	public Piece getPiece(int row, int col) {
		Piece piece = null;

		if (Utilities.validSquare(row, col)) {
			piece = pieces[row][col];
		}

		return piece;
	}

	public void setPiece(int row, int col, Piece pieceSetting) {
		pieces[row][col] = pieceSetting;
	}

	private boolean pieceHasValidMove(Piece piece, int pieceRow, int pieceCol) {
		boolean[][] tryDestRange = new boolean[8][8];
		int row;
		int col;
		boolean validMove = false;

		// find which squares are in the valid range, and copy to tryDestRange
		clearSquaresInRange();
		piece.updateSquaresInRange(pieceRow, pieceCol);
		for (row = 0; row < 8; row++) {
			for (col = 0; col < 8; col++) {
				tryDestRange[row][col] = squareInRange[row][col];
			}
		}

		// try each of the possible moves, and see if any are valid
		for (row = 0; (row < 8) && (!validMove); row++) {
			for (col = 0; (col < 8) && (!validMove); col++) {
				if (tryDestRange[row][col]) {
					validMove = tryMove(pieceRow, pieceCol, row, col, false,
							false);
				}
			}
		}

		return validMove;
	}

	// BELOW ARE METHODS FROM PIECE!!!

	/**
	 * setSquaresOnVector - Step along a vector from a starting position, and
	 * set all squares in range that are empty, until encountering another
	 * piece. If that piece is not the same color, also add that square to the
	 * valid range.
	 * 
	 * 
	 */
	
	protected void setSquaresOnVector(int startRow, int startCol, Piece incomingPiece, List<ImmutablePair<Integer, Integer>> movesList) {
		
		
		Iterator<ImmutablePair<Integer, Integer>> iterator = movesList
				.iterator();
		ImmutablePair<Integer, Integer> move;
		while (iterator.hasNext()) {
			move = iterator.next();
			setSquaresOnVectorInternal(startRow, startCol, move.getLeft()
					.intValue(), move.getRight().intValue(), incomingPiece);
		}
		
		
		
		
	}
	protected void setSquaresOnVectorInternal(int startRow, int startCol, int rowDelta,
			int colDelta, Piece incomingPiece) {
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
				piece = this.getPiece(row, col);

				// if there is no piece on the square, it is a valid square
				if (piece == null) {
					this.setSquareInRange(row, col);
				}

				/*
				 * If there is a piece on the square, the search is done. Add
				 * the square if the color does not match the selected piece's
				 * color.
				 */
				else {
					done = true;
					if (piece.isWhite() != incomingPiece.isWhite()) {
						this.setSquareInRange(row, col);
					}
				}
			}

			// If square is not valid, end the search
			else {
				done = true;
			}
		}
	}
	
	protected void setSingleSquareIfColorMismatch(int row, int col,
			Piece incomingPiece, List<ImmutablePair<Integer, Integer>> movesList) {
//		Iterator<ImmutablePair<Integer, Integer>> iterator = movesList
//				.iterator();
//		ImmutablePair<Integer, Integer> move;
//		while (iterator.hasNext()) {
//			move = iterator.next();
//			setSingleSquareIfColorMismatch(move.getLeft()
//					.intValue(), move.getRight().intValue(), incomingPiece);
//		}
//		
		setSingleSquareIfColorMismatch(row, col, incomingPiece);
		
		
	}

	/**
	 * setSingleSquareIfColorMismatch - Set a single square as being in range if
	 * the square is occupied and has the opposite color
	 */
	protected void setSingleSquareIfColorMismatchWithColor(int row, int col,
			boolean isWhite) {

		// first check if the square is within the board limits
		if (Utilities.validSquare(row, col)) {
			Piece piece = this.getPiece(row, col);

			// if there is no piece on the square, it is a valid square
			if (piece != null) {
				/*
				 * If there is a piece on the square, add the square if the
				 * color does not match the selected piece's color.
				 */
				if (piece.isWhite() != isWhite) {
					this.setSquareInRange(row, col);
				}
			}
		}
	}
	
    /**
     * updateSquaresInRange - see description of abstract method in Piece class
     */
    public void setSingleSquareIfColorMismatch(int currentRow, int currentCol, Piece incomingPiece){
    	LOG.debug("current Row is : " + currentRow);
		LOG.debug("current Col is : " + currentCol);

        // direction based on the current player's color (pawn may only
        // move up or down based on color)
        int dir = (incomingPiece.isWhite() == false) ? 1 : -1;
        LOG.debug("Is White : " + incomingPiece.isWhite());

        LOG.debug("direction is : " + dir);
        
        LOG.debug("currentRow+dir" +  + (currentRow+dir));

        // Check if pawn can move forward one space
        if(this.getPiece(currentRow+dir, currentCol) == null){
            this.setSquareInRange(currentRow+dir, currentCol);
            LOG.debug("Can move forward one square");

            
            // If pawn hasn't moved, check if it can move forward 2 spaces
            if((!incomingPiece.getHasMoved()) &&
               (this.getPiece(currentRow+(2*dir), currentCol) == null)){
                this.setSquareInRange(currentRow+(2*dir), currentCol);
            }
        }
        
        // Check if there is a piece the pawn can capture
        setSingleSquareIfColorMismatchWithColor(currentRow+dir, currentCol-1, incomingPiece.isWhite());
        setSingleSquareIfColorMismatchWithColor(currentRow+dir, currentCol+1, incomingPiece.isWhite());
    }

	
	protected void setSingleSquareUnlessColorMatch(int startRow, int startCol, 
			Piece incomingPiece, List<ImmutablePair<Integer, Integer>> movesList) {
		
		
		Iterator<ImmutablePair<Integer, Integer>> iterator = movesList
				.iterator();
		ImmutablePair<Integer, Integer> move;
		while (iterator.hasNext()) {
			move = iterator.next();
			setSingleSquareUnlessColorMatchInternal(startRow
					+ move.getLeft().intValue(), startCol
					+ move.getRight().intValue(), incomingPiece);
		}
	}

	/**
	 * setSingleSquareUnlessColorMatch - Set a single square as being in range
	 * if the square is empty or if it has a piece of the opposite color
	 */
	protected void setSingleSquareUnlessColorMatchInternal(int row, int col,
			Piece incomingPiece) {
		Piece piece;

		// first check if the square is within the board limits
		if (Utilities.validSquare(row, col)) {
			piece = this.getPiece(row, col);

			// if there is no piece on the square, it is a valid square
			if (piece == null) {
				this.setSquareInRange(row, col);
			}

			/*
			 * If there is a piece on the square, add the square if the color
			 * does not match the selected piece's color.
			 */
			else if (piece.isWhite() != incomingPiece.isWhite()) {
				this.setSquareInRange(row, col);
			}
		}
	}
	/*
	 * @return void
	 * 
	 * This method uses reflection to look into the piece type to determine what it's usage contract is.
	 * Then the method executes that method with the context of the board.
	 * @exception SecurityException
	 * @exception NoSuchMethodException
	 * @exception IllegalArgumentException
	 * @exception IllegalAccessException
	 * @exception InvocationTargetException



	 */
	
	public void updateSquaresInRange(int currentRow, int currentCol, Piece incomingPiece) 
			throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException{
		
		List<ImmutablePair<Integer, Integer>> moveList = incomingPiece.getAvailableMoves();
		LOG.debug("method type for this peice is : " + incomingPiece.getMoveStrategy());
				
		this.setPrivateMethodsAccessiableForReflectionOnly(incomingPiece.getMoveStrategy());

		Method method = this.getClass().getDeclaredMethod(incomingPiece.getMoveStrategy(), parameterTypes);
		Object[] args = {currentRow, currentCol, incomingPiece, moveList};

		method.invoke(this, args);
	
	}
	
	private void setPrivateMethodsAccessiableForReflectionOnly(String methodName) throws SecurityException, NoSuchMethodException{
		Method method = Board.class.
		        getDeclaredMethod(methodName, parameterTypes);
		method.setAccessible(true);
	}
	

	public static void main(String[] args) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		
		Piece rook = new Rook(true);

		Object[] parameters = new Object[4];
		parameters[0] = 1;
		parameters[1] = 2;
		parameters[2] = rook;
		parameters[3] = rook.getAvailableMoves() ;

		


		
		Board board = new Board();
		
		board.setPrivateMethodsAccessiableForReflectionOnly(rook.getMoveStrategy());
		Method method3 = board.getClass().getDeclaredMethod(rook.getMoveStrategy(), 
				parameterTypes);
		method3.invoke(board, parameters);

            

       
    

	      
	      

	}
	
	
}
