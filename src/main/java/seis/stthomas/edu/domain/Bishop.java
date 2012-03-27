/**
 * Bishop - extends the Piece class, and defines the valid move for the bishop
 *  chess piece.
 */

package seis.stthomas.edu.domain;

public class Bishop extends Piece
{
    public Bishop(ChessColor newColor, Board boardRef)
    {
        super(newColor, boardRef);
        type = PieceType.bishop;
    }
    
    /**
     * updateSquaresInRange - see description of abstract method in Piece class
     */
    public void updateSquaresInRange(int currentRow, int currentCol)
    {   
        // bishop may move along 4 vectors (each diagonal)
        setSquaresOnVector(currentRow, currentCol, -1, -1);
        setSquaresOnVector(currentRow, currentCol, -1,  1);
        setSquaresOnVector(currentRow, currentCol,  1, -1);
        setSquaresOnVector(currentRow, currentCol,  1,  1);
    }
}
