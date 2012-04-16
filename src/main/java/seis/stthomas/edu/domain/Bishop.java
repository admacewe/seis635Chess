/**
 * Bishop - extends the Piece class, and defines the valid move for the bishop
 *  chess piece.
 */

package seis.stthomas.edu.domain;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.tuple.ImmutablePair;

public class Bishop extends Piece


{
	
	@SuppressWarnings("unchecked")
	private final List<ImmutablePair<Integer, Integer>> availableMoves =  Arrays.asList(
			new  ImmutablePair<Integer, Integer>(-1, -1),
			new  ImmutablePair<Integer, Integer>(-1, 1),
			new  ImmutablePair<Integer, Integer>(-1, -1),
			new  ImmutablePair<Integer, Integer>(1, 1)
		);
    public Bishop(boolean isWhite, Board boardRef)
    {
        super(isWhite, boardRef, 3);
    }
    
    /**
     * updateSquaresInRange - see description of abstract method in Piece class
     */
    public void updateSquaresInRange(int currentRow, int currentCol)
    {   
    	
    	Iterator<ImmutablePair<Integer, Integer>> iterator = getAvailableMoves().iterator();
    	ImmutablePair<Integer, Integer> move;
    	while (iterator.hasNext()) {
    		move = iterator.next();
    		setSquaresOnVector(currentRow,currentCol, move.getLeft().intValue(), move.getRight().intValue() );
    	}
        // bishop may move along 4 vectors (each diagonal)
//        setSquaresOnVector(currentRow, currentCol, -1, -1);
//        setSquaresOnVector(currentRow, currentCol, -1,  1);
//        setSquaresOnVector(currentRow, currentCol,  1, -1);
//        setSquaresOnVector(currentRow, currentCol,  1,  1);
    }

	/**
	 * @return the availableMoves
	 */
	public List<ImmutablePair<Integer, Integer>> getAvailableMoves() {
		return availableMoves;
	}
    
    
}
