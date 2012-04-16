/**
 * King - extends the Piece class, and defines the valid move for the king
 *  chess piece.
 */

package seis.stthomas.edu.domain;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.tuple.ImmutablePair;

public class King extends Piece
{
	
	@SuppressWarnings("unchecked")
	private final List<ImmutablePair<Integer, Integer>> availableMoves =  Arrays.asList(
			new  ImmutablePair<Integer, Integer>(0, -1),
			new  ImmutablePair<Integer, Integer>(0, 1),
			new  ImmutablePair<Integer, Integer>(1, 0),
			new  ImmutablePair<Integer, Integer>(-1, 0),
			new  ImmutablePair<Integer, Integer>(-1, -1),
			new  ImmutablePair<Integer, Integer>(-1, 1),
			new  ImmutablePair<Integer, Integer>(-1, -1),
			new  ImmutablePair<Integer, Integer>(1, 1)
		);
	
	
    public King(boolean isWhite, Board boardRef)
    {
        super(isWhite, boardRef, 1000);
    }
    
    /**
     * updateSquaresInRange - see description of abstract method in Piece class
     */
    public void updateSquaresInRange(int currentRow, int currentCol)
    {
        /* Set all squares adjacent to king in range if they are not
         * occupied by a piece of the same color.
         */

    	Iterator<ImmutablePair<Integer, Integer>> iterator = getAvailableMoves().iterator();
    	ImmutablePair<Integer, Integer> move;
    	while (iterator.hasNext()) {
    		move = iterator.next();
    		setSingleSquareUnlessColorMatch(currentRow + move.getLeft().intValue(),currentCol + move.getRight().intValue() );
    	}
    	
    	
//        setSingleSquareUnlessColorMatch(currentRow-1, currentCol-1);
//        setSingleSquareUnlessColorMatch(currentRow-1, currentCol  );
//        setSingleSquareUnlessColorMatch(currentRow-1, currentCol+1);
//        setSingleSquareUnlessColorMatch(currentRow,   currentCol-1);
//        setSingleSquareUnlessColorMatch(currentRow,   currentCol+1);
//        setSingleSquareUnlessColorMatch(currentRow+1, currentCol-1);
//        setSingleSquareUnlessColorMatch(currentRow+1, currentCol  );
//        setSingleSquareUnlessColorMatch(currentRow+1, currentCol+1);
    }

	/**
	 * @return the availableMoves
	 */
	public List<ImmutablePair<Integer, Integer>> getAvailableMoves() {
		return availableMoves;
	}
    
    
}
