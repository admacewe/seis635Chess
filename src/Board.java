package seis.stthomas.edu.domain;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.OneToMany;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.log4j.Logger;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

import seis.stthomas.edu.utility.Utilities;

@RooJavaBean
@RooToString
@RooJpaActiveRecord
public class Board implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = -4695614365620956689L;

	@OneToMany
	Set <Piece> pieces  = new HashSet<Piece>(64);
	@OneToMany
	Set <Boolean> squareInRange  = new HashSet<Boolean>(64);
	
	String name;

	private static final Logger LOG = Logger.getLogger(Board.class.getName());
	
	private static final Class[] parameterTypes = {Integer.TYPE, Integer.TYPE,Piece.class,List.class};

	//TODO this is has changes to the pieces etc.
		public Board() {
			int row;
			int col;
			LOG.info("entering the constructor!");

//			pieces = new Piece[64];

			// Create black pieces
			
			pieces.toArray()[0] = new Rook(false);
			pieces.toArray()[1] = new Knight(false);
			pieces.toArray()[2] = new Bishop(false);
			pieces.toArray()[3] = new Queen(false);
			pieces.toArray()[4] = new King(false);
			pieces.toArray()[5] = new Bishop(false);
			pieces.toArray()[6] = new Knight(false);
			pieces.toArray()[7] = new Rook(false);
			for (col = 0; col < 8; col++) {
				pieces.toArray()[1*8+col] = new Pawn(false);
			}

			// Explicitely set middle rows to null
			for (row = 2; row < 6; row++) {
				for (col = 0; col < 8; col++) {
					pieces.toArray()[row*8+col] = null;
				}
			}

			// Create white pieces
			for (col = 0; col < 8; col++) {
				pieces.toArray()[6*8+col] = new Pawn(true);
			}
			pieces.toArray()[7*8+0] = new Rook(true);
			pieces.toArray()[7*8+1] = new Knight(true);
			pieces.toArray()[7*8+2] = new Bishop(true);
			pieces.toArray()[7*8+3] = new Queen(true);
			pieces.toArray()[7*8+4] = new King(true);
			pieces.toArray()[7*8+5] = new Bishop(true);
			pieces.toArray()[7*8+6] = new Knight(true);
			pieces.toArray()[7*8+7] = new Rook(true);

			// Initialize the squareInRange array
//			squareInRange = new boolean[64];
			clearSquaresInRange();
			LOG.info("leaving the constructor!");
		}
		


		/**
		 * tryMove - This method temporarily updates the state of the board by
		 * moving the piece at (curRow, curCol) to (destRow, destCol). It then
		 * checks to see if the move places the currently active player in check. If
		 * the move does not place the current player in check, it is OK, the board
		 * update is retained, and true is returned. If the move does place the
		 * current player in check, the move is undone, and false is returned.
		 * @throws InvocationTargetException 
		 * @throws IllegalAccessException 
		 * @throws NoSuchMethodException 
		 * @throws IllegalArgumentException 
		 * @throws SecurityException 
		 */
		public boolean tryMove(int curRow, int curCol, int destRow, int destCol,
				boolean updateState, boolean updateHasMoved) throws SecurityException, IllegalArgumentException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
			boolean valid = true;

			// Save references to the current source/destination in case these
			// need to be restored.
			Piece tempSourceRef = (Piece) pieces.toArray()[curRow*8+curCol];
			Piece tempDestRef = (Piece) pieces.toArray()[destRow*8+destCol];

			// Try moving the piece
			pieces.toArray()[destRow*8+destCol] = pieces.toArray()[curRow*8+curCol];
			pieces.toArray()[curRow*8+curCol] = null;

			// If a pawn has moved into row 0 or row 7, exchange it for a queen
			if ((pieces.toArray()[destRow*8+destCol].getClass() == Pawn.class)
					&& ((destRow == 0) || (destRow == 7))) {
				pieces.toArray()[destRow*8+destCol] = new Queen(
						((Piece) pieces.toArray()[destRow*8+destCol]).isIsWhite());
			}

			// Check if the move places the current player in check. The current
			// player can be found from the source piece
			if (playerInCheck(tempSourceRef.isIsWhite())) {
				valid = false;
			}

			// If the move is invalid or the state is not to be preserved, restore
			// the saved state
			if ((!valid) || (!updateState)) {
				pieces.toArray()[destRow*8+destCol] = tempDestRef;
				pieces.toArray()[curRow*8+curCol] = tempSourceRef;
			}

			// If the move is valid and the state is to be updated, set the hasMoved
			// flag
			if (valid && updateState && updateHasMoved) {
				((Piece) pieces.toArray()[destRow*8+destCol]).setHasMoved(true);
			}

			return valid;
		}

		/**
		 * playerInCheck - Evaluate the board to see if the indicated player is in
		 * check.
		 * @throws InvocationTargetException 
		 * @throws IllegalAccessException 
		 * @throws NoSuchMethodException 
		 * @throws IllegalArgumentException 
		 * @throws SecurityException 
		 */
		public boolean playerInCheck(boolean activePlayerIsWhite) throws SecurityException, IllegalArgumentException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
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
						if ((piece.getClass() == King.class)
								&& (piece.isIsWhite() == activePlayerIsWhite)) {
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
						if (piece.isIsWhite() != activePlayerIsWhite) {
							// set the squares in range for that piece
							clearSquaresInRange();
							this.updateSquaresInRange(row, col, piece);

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
		 * @throws InvocationTargetException 
		 * @throws IllegalAccessException 
		 * @throws NoSuchMethodException 
		 * @throws IllegalArgumentException 
		 * @throws SecurityException 
		 */
		public boolean playerHasValidMove(boolean activePlayerIsWhite) throws SecurityException, IllegalArgumentException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
			int row;
			int col;
			Piece piece;
			boolean validMove = false;

			// Find each of the player's pieces
			for (row = 0; (row < 8) && (!validMove); row++) {
				for (col = 0; (col < 8) && (!validMove); col++) {
					piece = getPiece(row, col);
					if (piece != null) {
						if (piece.isIsWhite() == activePlayerIsWhite) {
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
					squareInRange.toArray()[row*8+col] = Boolean.FALSE;
				}
			}
		}

		/**
		 * setSquareInRange - Sets the square indicated by the indices as being in
		 * range.
		 */
		public void setSquareInRange(int row, int col) {


			squareInRange.toArray()[row*8+col] = Utilities.validSquare(row, col);

		}

		/**
		 * getSquareInRange - Gets the in-range status of a square indicated by the
		 * indices.
		 */
		public boolean getSquareInRange(int row, int col) {
			Boolean inRange = false;

			if (Utilities.validSquare(row, col)) {
				inRange = (Boolean) squareInRange.toArray()[row*8+col];
			}

			return inRange.booleanValue();
		}

		/**
		 * getPiece - Retrieve the piece at the specified row and column number. If
		 * there is no piece, or the indices are invalid, null is returned.
		 */
		public Piece getPiece(int row, int col) {
			Piece piece = null;

			if (Utilities.validSquare(row, col)) {
				piece = (Piece) pieces.toArray()[row*8+col];
			}

			return piece;
		}

		public void setPiece(int row, int col, Piece pieceSetting) {
			pieces.toArray()[row*8+col] = pieceSetting;
		}

		private boolean pieceHasValidMove(Piece piece, int pieceRow, int pieceCol) throws SecurityException, IllegalArgumentException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
			boolean[] tryDestRange = new boolean[64];
			int row;
			int col;
			boolean validMove = false;

			// find which squares are in the valid range, and copy to tryDestRange
			clearSquaresInRange();
			this.updateSquaresInRange(pieceRow, pieceCol, piece);
			for (row = 0; row < 8; row++) {
				for (col = 0; col < 8; col++) {
					tryDestRange[row*8+col] = (Boolean) squareInRange.toArray()[row*8+col];
				}
			}

			// try each of the possible moves, and see if any are valid
			for (row = 0; (row < 8) && (!validMove); row++) {
				for (col = 0; (col < 8) && (!validMove); col++) {
					if (tryDestRange[row*8+col]) {
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
						if (piece.isIsWhite() != incomingPiece.isIsWhite()) {
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
//			Iterator<ImmutablePair<Integer, Integer>> iterator = movesList
//					.iterator();
//			ImmutablePair<Integer, Integer> move;
//			while (iterator.hasNext()) {
//				move = iterator.next();
//				setSingleSquareIfColorMismatch(move.getLeft()
//						.intValue(), move.getRight().intValue(), incomingPiece);
//			}
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
					if (piece.isIsWhite() != isWhite) {
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
	        int dir = (incomingPiece.isIsWhite() == false) ? 1 : -1;
	        LOG.debug("Is White : " + incomingPiece.isIsWhite());

	        LOG.debug("direction is : " + dir);
	        
	        LOG.debug("currentRow+dir" +  + (currentRow+dir));

	        // Check if pawn can move forward one space
	        if(this.getPiece(currentRow+dir, currentCol) == null){
	            this.setSquareInRange(currentRow+dir, currentCol);
	            LOG.debug("Can move forward one square");

	            
	            // If pawn hasn't moved, check if it can move forward 2 spaces
	            if((!incomingPiece.isHasMoved()) &&
	               (this.getPiece(currentRow+(2*dir), currentCol) == null)){
	                this.setSquareInRange(currentRow+(2*dir), currentCol);
	            }
	        }
	        
	        // Check if there is a piece the pawn can capture
	        setSingleSquareIfColorMismatchWithColor(currentRow+dir, currentCol-1, incomingPiece.isIsWhite());
	        setSingleSquareIfColorMismatchWithColor(currentRow+dir, currentCol+1, incomingPiece.isIsWhite());
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
				else if (piece.isIsWhite() != incomingPiece.isIsWhite()) {
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
		
    
}
