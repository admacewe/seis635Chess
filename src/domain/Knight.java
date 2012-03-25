/**
 * Knight - extends the Piece class, and defines the valid move for the knight
 *  chess piece.
 */

package domain;

public class Knight extends Piece
{
    public Knight(ChessColor newColor, Board boardRef)
    {
        super(newColor, boardRef);
        type = PieceType.knight;
    }
    
    /**
     * updateSquaresInRange - see description of abstract method in Piece class
     */
    public void updateSquaresInRange(int currentRow, int currentCol)
    {
        /* Check each of the eight possible ways a knight can move.
         * Add it to the selectable range if it is not occupied by
         * a piece of the same color.
         */
        setSingleSquareUnlessColorMatch(currentRow-2, currentCol-1);
        setSingleSquareUnlessColorMatch(currentRow-2, currentCol+1);
        setSingleSquareUnlessColorMatch(currentRow-1, currentCol-2);
        setSingleSquareUnlessColorMatch(currentRow-1, currentCol+2);
        setSingleSquareUnlessColorMatch(currentRow+1, currentCol-2);
        setSingleSquareUnlessColorMatch(currentRow+1, currentCol+2);
        setSingleSquareUnlessColorMatch(currentRow+2, currentCol-1);
        setSingleSquareUnlessColorMatch(currentRow+2, currentCol+1);
    }
}
