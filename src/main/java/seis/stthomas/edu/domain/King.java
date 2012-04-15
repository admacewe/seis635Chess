/**
 * King - extends the Piece class, and defines the valid move for the king
 *  chess piece.
 */

package seis.stthomas.edu.domain;

public class King extends Piece
{
    public King(ChessColor newColor, Board boardRef)
    {
        super(newColor, boardRef, 1000);
        type = PieceType.king;
    }
    
    /**
     * updateSquaresInRange - see description of abstract method in Piece class
     */
    public void updateSquaresInRange(int currentRow, int currentCol)
    {
        /* Set all squares adjacent to king in range if they are not
         * occupied by a piece of the same color.
         */
        setSingleSquareUnlessColorMatch(currentRow-1, currentCol-1);
        setSingleSquareUnlessColorMatch(currentRow-1, currentCol  );
        setSingleSquareUnlessColorMatch(currentRow-1, currentCol+1);
        setSingleSquareUnlessColorMatch(currentRow,   currentCol-1);
        setSingleSquareUnlessColorMatch(currentRow,   currentCol+1);
        setSingleSquareUnlessColorMatch(currentRow+1, currentCol-1);
        setSingleSquareUnlessColorMatch(currentRow+1, currentCol  );
        setSingleSquareUnlessColorMatch(currentRow+1, currentCol+1);
    }
}
