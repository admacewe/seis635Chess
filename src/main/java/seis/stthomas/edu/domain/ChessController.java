/**
 * ChessController - This class is the interface from the UI to the domain.  It
 *  also keeps track of game state, and calls other methods in the domain based
 *  on user actions and the current game state.
 */

package seis.stthomas.edu.domain;

import seis.stthomas.edu.ui.ChessWindow;

public class ChessController
{
    Board board;           // reference to the chess board
    GameState state;       // current state of the chess game
    ChessColor activePlayer;    // color of the active player
    int pieceRow;          // row number of the currently selected piece
    int pieceCol;          // column number of the currently selected piece
    ChessWindow ui;        // user interface
    CPUPlayer cpuPlayer;   // reference to a CPU player in case a game is played vs CPU
    boolean isOpponentCPU; // indicates if opponent is a CPU player or not
    PieceMove cpuMove;     // most recent CPU move
    
    public ChessController()
    {
        cpuPlayer = new CPUPlayer();
        cpuPlayer.setDifficulty(4);
    }
    
    /**
     * Initialize for a new game.  Create the board and set inital states.
     */
    public void newGame(boolean playVsCPU, int difficulty)
    {
        board = new Board();
        state = GameState.choosePiece;
        activePlayer = ChessColor.white;
        isOpponentCPU = playVsCPU;
        cpuPlayer.setDifficulty(difficulty);
    }
    
    /**
     * squareSelected - This method is called by the UI to notify the game
     *  controller that a square at coordinates (row, col) has been selected.
     *  This method checks if the selection was valid, updates the game state
     *  as needed, and gives a return status indication to the UI.
     */
    public SelectionStatus squareSelected(int row, int col)
    {
        SelectionStatus status;
        
        // call the appropriate selection handler based on the game state.
        if(state == GameState.choosePiece)
        {
            status = pieceSelected(row, col);
        }
        else if(state == GameState.chooseDestination)
        {
            status = destinationSelected(row, col);
        }
        else
        {
            // Only expect a square to be pressed in one of the above
            // states.  Otherwise, let the UI know this was unexpected.
            status = SelectionStatus.invalidState;
        }

        return status;
    }
    
    /**
     * pieceSelected - This sub-method of squareSelected handles a selection
     *  when the state is GameState.choosePiece.  The square selected must
     *  have a piece matching the color of the active player.  Otherwise, an
     *  error status will be returned.
     */
    private SelectionStatus pieceSelected(int row, int col)
    {
        Piece piece;
        SelectionStatus status;
        
        piece = board.getPiece(row, col);
        
        // Verify the space has a piece of the same color as
        // the active player
        if(piece == null)
        {
            status = SelectionStatus.pieceNull;
        }
        else if(piece.getColor() != activePlayer)
        {
            status = SelectionStatus.pieceWrongColor;
        }
        else
        {
            status = SelectionStatus.pieceValid;
            pieceRow = row;
            pieceCol = col;
            
            // Clear the squares in range on the board, then request that the
            // piece identify which squares are in range.  The piece will
            // update the board.
            board.clearSquaresInRange();
            piece.updateSquaresInRange(row, col);
            
            // The next step is to wait for a destination square
            state = GameState.chooseDestination;
        }
        
        return status;
    }
    
    /**
     * destinationSelection - This sub-method of squareSelected handles a
     *  selection when the state is GameState.chooseDestination.  The square
     *  selected must be in range of the piece previously selected, and the
     *  move must not place the active player in check.
     */
    private SelectionStatus destinationSelected(int destRow, int destCol)
    {
        SelectionStatus status;
        ChessColor opponent;
        
        // Initially assume state is switching to waiting for a piece
        // selection.  This may be overwritten in the case of checkmate
        // or a CPU player turn.
        state = GameState.choosePiece;
        
        // If the square is not in range, update the return status
        if(board.getSquareInRange(destRow, destCol) == false)
        {
            status = SelectionStatus.destOutOfRange;
        }
        
        // If the square is in range, and it does not place the current
        // player in check, the move is valid.
        else if(board.tryMove(pieceRow, pieceCol, destRow, destCol, true, true) == false)
        {
            status = SelectionStatus.destSelfInCheck;
        }   
        
        // Otherwise, the move is valid
        else
        {           
            // Evaluate whether the move places the opposing player in check
            opponent = (activePlayer == ChessColor.white) ? 
                    ChessColor.black : ChessColor.white;
            
            // If opponent has a valid move, update status to check or no-check
            if(board.playerHasValidMove(opponent))
            {
                if(board.playerInCheck(opponent))
                {
                    status = SelectionStatus.destValidCheck;                    
                }
                else
                {
                    status = SelectionStatus.destValidNoCheck;                    
                }
                
                // If the game is not over, update the player turn
                activePlayer = opponent;
                
                // If it is the CPU player's turn
                if((activePlayer == ChessColor.black) && isOpponentCPU)
                {
                    state = GameState.cpuTurn;
                }
            }
            
            // if the opponent has no valid move, declare checkmate or draw
            else
            {
                state = GameState.idle;
                
                if(board.playerInCheck(opponent))
                {
                    status = SelectionStatus.destValidCheckMate;
                }
                else
                {
                    status = SelectionStatus.destValidDraw;
                }
            }                        
        }
        
        return status;
    }
    
    public SelectionStatus determineCPUMove()
    {
        SelectionStatus status;
        
        cpuMove = cpuPlayer.selectMove(board);
                
        // If opponent has a valid move, update status to check or no-check
        if(board.playerHasValidMove(ChessColor.white))
        {
            if(board.playerInCheck(ChessColor.white))
            {
                status = SelectionStatus.destValidCheck;                    
            }
            else
            {
                status = SelectionStatus.destValidNoCheck;                    
            }
            
            // If the game is not over, update the player turn
            activePlayer = ChessColor.white;
            state = GameState.choosePiece;
        }
        
        // if the opponent has no valid move, declare checkmate or draw
        else
        {
            state = GameState.idle;
            
            if(board.playerInCheck(ChessColor.white))
            {
                status = SelectionStatus.destValidCheckMate;
            }
            else
            {
                status = SelectionStatus.destValidDraw;
            }
        }                        
        
        return status;
    }
    
    public PieceMove getCPUMove()
    {
        return cpuMove;
    }
    
    /**
     * getPiece - Pass-through message to the Board object, to retrieve the
     *  piece at a specified location.
     */
    public Piece getPiece(int row, int col)
    {
        return board.getPiece(row, col);
    }
    
    /**
     * getSquareInRange - Pass-through message to the Board object, to retrieve
     *  the in-range status at a specified location.
     */
    public boolean getSquareInRange(int row, int col)
    {
        return board.getSquareInRange(row, col);
    }
    
    public ChessColor getActivePlayer()
    {
        return activePlayer;
    }
    
    public GameState getState()
    {
        return state;
    }
    
    /**
     * createUI - Instantiate a ChessWindow object to create and initialize
     *  the user interface.
     */
    private void createUI()
    {
        ui = new ChessWindow(this);
    }
    
    /**
     * main - Main method.  Instantiate a ChessController, initialize the game,
     *  and create the user interface.
     */
    public static void main(String[] args)
    {
        ChessController controller = new ChessController();

        // Initialize the game and create the UI.  This must be done
        // in this order or the UI will not properly display the board.
        controller.newGame(false, 0);
        controller.createUI();
    }
}