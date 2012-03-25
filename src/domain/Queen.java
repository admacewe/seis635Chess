/**
 * Queen - extends the Piece class, and defines the valid move for the queen
 *  chess piece.
 */

package domain;

public class Queen extends Piece
{
    public Queen(ChessColor newColor, Board boardRef)
    {
        super(newColor, boardRef);
        type = PieceType.queen;
    }
    
    /**
     * updateSquaresInRange - see description of abstract method in Piece class
     */
    public void updateSquaresInRange(int currentRow, int currentCol)
    {
        // queen may move along any of 8 possible vectors
        setSquaresOnVector(currentRow, currentCol, -1, -1);
        setSquaresOnVector(currentRow, currentCol, -1,  0);
        setSquaresOnVector(currentRow, currentCol, -1,  1);
        setSquaresOnVector(currentRow, currentCol,  0, -1);
        setSquaresOnVector(currentRow, currentCol,  0,  1);
        setSquaresOnVector(currentRow, currentCol,  1, -1);
        setSquaresOnVector(currentRow, currentCol,  1,  0);
        setSquaresOnVector(currentRow, currentCol,  1,  1);
    }
}
