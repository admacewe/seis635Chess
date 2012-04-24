/**
 * Pawn - extends the Piece class, and defines the valid move for the pawn
 *  chess piece.
 */

package seis.stthomas.edu.domain;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.log4j.Logger;

public class Pawn extends Piece
{
	
	
	@SuppressWarnings("unchecked")
	private final List<ImmutablePair<Integer, Integer>> availableMoves =  Arrays.asList(

			new  ImmutablePair<Integer, Integer>(0, 1)
		);
	
	private final String moveStrategy = "setSingleSquareIfColorMismatch";
	private static final Logger LOG = Logger.getLogger(Pawn.class.getName());
	
	
    public Pawn(boolean isWhite, Board boardRef)
    {
        super(isWhite, boardRef, 1);
    }
    
    public Pawn(boolean isWhite)
    {
        super(isWhite, 1);
    }
    
    /**
     * updateSquaresInRange - see description of abstract method in Piece class
     */
    public void updateSquaresInRange(int currentRow, int currentCol){
    	LOG.debug("current Row is : " + currentRow);
		LOG.debug("current Col is : " + currentCol);

        // direction based on the current player's color (pawn may only
        // move up or down based on color)
        int dir = (isWhite == false) ? 1 : -1;
        
        // Check if pawn can move forward one space
        if(board.getPiece(currentRow+dir, currentCol) == null){
            board.setSquareInRange(currentRow+dir, currentCol);
            
            // If pawn hasn't moved, check if it can move forward 2 spaces
            if((!hasMoved) &&
               (board.getPiece(currentRow+(2*dir), currentCol) == null)){
                board.setSquareInRange(currentRow+(2*dir), currentCol);
            }
        }
        
        // Check if there is a piece the pawn can capture
        setSingleSquareIfColorMismatch(currentRow+dir, currentCol-1);
        setSingleSquareIfColorMismatch(currentRow+dir, currentCol+1);
    }

	/**
	 * @return the availableMoves
	 */
	public List<ImmutablePair<Integer, Integer>> getAvailableMoves() {
		return availableMoves;
	}

	/**
	 * @return the moveStrategy
	 */
	public String getMoveStrategy() {
		return moveStrategy;
	}
	
	
}
