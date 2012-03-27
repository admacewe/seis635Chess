/**
 * Rook - extends the Piece class, and defines the valid move for the rook
 *  chess piece.
 */

package seis.stthomas.edu.domain;

public class Rook extends Piece
{
    public Rook(ChessColor newColor, Board boardRef)
    {
        super(newColor, boardRef);
        type = PieceType.rook;
    }
    
    /**
     * updateSquaresInRange - see description of abstract method in Piece class
     */
    public void updateSquaresInRange(int currentRow, int currentCol)
    {
        // rook may move along 4 vectors (left, right, up, down)
        setSquaresOnVector(currentRow, currentCol,  0, -1);
        setSquaresOnVector(currentRow, currentCol,  0,  1);
        setSquaresOnVector(currentRow, currentCol, -1,  0);
        setSquaresOnVector(currentRow, currentCol,  1,  0);
    }
}
