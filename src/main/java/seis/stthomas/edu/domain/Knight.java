/**
 * Knight - extends the Piece class, and defines the valid move for the knight
 *  chess piece.
 */

package seis.stthomas.edu.domain;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.tuple.ImmutablePair;

public class Knight extends Piece {

	@SuppressWarnings("unchecked")
	private final List<ImmutablePair<Integer, Integer>> availableMoves = Arrays
			.asList(new ImmutablePair<Integer, Integer>(-2, -1),
					new ImmutablePair<Integer, Integer>(-2, 1),
					new ImmutablePair<Integer, Integer>(-1, -2),
					new ImmutablePair<Integer, Integer>(-1, 2),
					new ImmutablePair<Integer, Integer>(1, -2),
					new ImmutablePair<Integer, Integer>(1, 2),
					new ImmutablePair<Integer, Integer>(2, -1),
					new ImmutablePair<Integer, Integer>(2, 1));

	private final String moveStrategy = "setSingleSquareUnlessColorMatch";

	public Knight(boolean isWhite, Board boardRef) {
		super(isWhite, boardRef, 3);
	}
	
	public Knight(boolean isWhite) {
		super(isWhite, 3);
	}

	/**
	 * updateSquaresInRange - see description of abstract method in Piece class
	 */
	public void updateSquaresInRange(int currentRow, int currentCol) {
		/*
		 * Check each of the eight possible ways a knight can move. Add it to
		 * the selectable range if it is not occupied by a piece of the same
		 * color.
		 */

		Iterator<ImmutablePair<Integer, Integer>> iterator = getAvailableMoves()
				.iterator();
		ImmutablePair<Integer, Integer> move;
		while (iterator.hasNext()) {
			move = iterator.next();
			setSingleSquareUnlessColorMatch(currentRow
					+ move.getLeft().intValue(), currentCol
					+ move.getRight().intValue());
		}

	}

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
