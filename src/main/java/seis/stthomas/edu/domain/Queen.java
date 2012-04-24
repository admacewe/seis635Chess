/**
 * Queen - extends the Piece class, and defines the valid move for the queen
 *  chess piece.
 */

package seis.stthomas.edu.domain;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.tuple.ImmutablePair;

public class Queen extends Piece {

	@SuppressWarnings("unchecked")
	private final List<ImmutablePair<Integer, Integer>> availableMoves = Arrays
			.asList(new ImmutablePair<Integer, Integer>(0, -1),
					new ImmutablePair<Integer, Integer>(0, 1),
					new ImmutablePair<Integer, Integer>(1, 0),
					new ImmutablePair<Integer, Integer>(1, 1),
					new ImmutablePair<Integer, Integer>(1, -1),

					new ImmutablePair<Integer, Integer>(-1, 0),
					new ImmutablePair<Integer, Integer>(-1, 1),
					new ImmutablePair<Integer, Integer>(-1, -1)
					);

	private final String moveStrategy = "setSquaresOnVector";

	public Queen(boolean isWhite, Board boardRef) {
		super(isWhite, boardRef, 9);
	}

	public Queen(boolean isWhite) {
		super(isWhite, 9);
	}

	/**
	 * updateSquaresInRange - see description of abstract method in Piece class
	 */
	public void updateSquaresInRange(int currentRow, int currentCol) {

		Iterator<ImmutablePair<Integer, Integer>> iterator = getAvailableMoves()
				.iterator();
		ImmutablePair<Integer, Integer> move;
		while (iterator.hasNext()) {
			move = iterator.next();
			setSquaresOnVector(currentRow, currentCol, move.getLeft()
					.intValue(), move.getRight().intValue());
		}

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
