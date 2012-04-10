/**
 * Pawn - extends the Piece class, and defines the valid move for the pawn
 *  chess piece.
 */

package seis.stthomas.edu.domain;

public class Pawn extends Piece
{
    public Pawn(ChessColor newColor, Board boardRef)
    {
        super(newColor, boardRef, 1);
        type = PieceType.pawn;
    }
    
    /**
     * updateSquaresInRange - see description of abstract method in Piece class
     */
    public void updateSquaresInRange(int currentRow, int currentCol)
    {
        // direction based on the current player's color (pawn may only
        // move up or down based on color)
        int dir = (color == ChessColor.black) ? 1 : -1;
        
        // Check if pawn can move forward one space
        if(board.getPiece(currentRow+dir, currentCol) == null)
        {
            board.setSquareInRange(currentRow+dir, currentCol);
            
            // If pawn hasn't moved, check if it can move forward 2 spaces
            if((!hasMoved) &&
               (board.getPiece(currentRow+(2*dir), currentCol) == null))
            {
                board.setSquareInRange(currentRow+(2*dir), currentCol);
            }
        }
        
        // Check if there is a piece the pawn can capture
        setSingleSquareIfColorMismatch(currentRow+dir, currentCol-1);
        setSingleSquareIfColorMismatch(currentRow+dir, currentCol+1);
    }
}
