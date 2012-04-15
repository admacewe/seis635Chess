/**
 * Piece - Abstract base class from which all chess pieces are derived.
 */

package seis.stthomas.edu.domain;

public abstract class Piece
{
    PieceType type;
    ChessColor color;
    Board board;
    boolean hasMoved;
    int value;
    
    public Piece(ChessColor newColor, Board boardRef, int valueSetting)
    {
        color = newColor;
        board = boardRef;
        value = valueSetting;
        hasMoved = false;
    }
    
    /**
     * getPiecesInRange - Abstract method to be defined by each piece type.
     *  This method determines which spaces are valid as a destination for the
     *  selected piece.  The Board object notifies the Piece of its current
     *  row and column position with the input parameters.  This method calls
     *  setSquareInRange for each board position that is in the valid range.
     */
    public abstract void updateSquaresInRange(int currentRow, int currentCol);
    
    /**
     * setSquaresOnVector - Step along a vector from a starting position, and
     *  set all squares in range that are empty, until encountering another
     *  piece.  If that piece is not the same color, also add that square to
     *  the valid range.
     */
    protected void setSquaresOnVector(int startRow, int startCol, int rowDelta, int colDelta)
    {
        boolean done = false;
        int row = startRow;
        int col = startCol;
        Piece piece;
        
        while(!done)
        {
            // apply the delta to move along the vector
            row += rowDelta;
            col += colDelta;
            
            // first check if the square is within the board limits
            if(Utilities.validSquare(row, col))
            {
                piece = board.getPiece(row, col);
                
                // if there is no piece on the square, it is a valid square
                if(piece == null)
                {
                    board.setSquareInRange(row, col);
                }
                
                /* If there is a piece on the square, the search is done.
                 * Add the square if the color does not match the selected
                 * piece's color.
                 */
                else
                {
                    done = true;
                    if(piece.getColor() != color)
                    {
                        board.setSquareInRange(row, col);
                    }
                }
            }
            
            // If square is not valid, end the search
            else
            {
                done = true;
            }
        }
    }

    /**
     * setSingleSquareUnlessColorMatch - Set a single square as being in range
     *  if the square is empty or if it has a piece of the opposite color
     */
    protected void setSingleSquareUnlessColorMatch(int row, int col)
    {
        Piece piece;
        
        // first check if the square is within the board limits
        if(Utilities.validSquare(row, col))
        {
            piece = board.getPiece(row, col);
            
            // if there is no piece on the square, it is a valid square
            if(piece == null)
            {
                board.setSquareInRange(row, col);
            }
            
            /* If there is a piece on the square, add the square if the color
             * does not match the selected piece's color.
             */
            else if(piece.getColor() != color)
            {
                board.setSquareInRange(row, col);
            }
        }        
    }

    /**
     * setSingleSquareIfColorMismatch - Set a single square as being in range
     *  if the square is occupied and has the opposite color
     */
    protected void setSingleSquareIfColorMismatch(int row, int col)
    {
        Piece piece;
        
        // first check if the square is within the board limits
        if(Utilities.validSquare(row, col))
        {
            piece = board.getPiece(row, col);
            
            // if there is no piece on the square, it is a valid square
            if(piece != null)
            {
                /* If there is a piece on the square, add the square if the color
                 * does not match the selected piece's color.
                 */
                if(piece.getColor() != color)
                {
                    board.setSquareInRange(row, col);
                }
            }
        }        
    }
    
    public void setHasMoved(boolean moved)
    {
        hasMoved = moved;
    }
    
    public boolean getHasMoved()
    {
        return hasMoved;
    }
    
    public ChessColor getColor()
    {
        return color;
    }
    
    public PieceType getType()
    {
        return type;
    }
    
    public int getValue()
    {
        return value;
    }
}
