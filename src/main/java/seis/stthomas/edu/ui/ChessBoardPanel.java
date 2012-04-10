/**
 * ChessBoardPanel - Controls display of chess board, and notifies game
 *  controller of user control events.
 */
package seis.stthomas.edu.ui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import seis.stthomas.edu.domain.*;

public class ChessBoardPanel extends JPanel
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private ChessController controller;
    
    // panels used in the display
    private JPanel squarePanel;     // contains the squares making up the tic-tac-toe board
    private JPanel startPanel;      // contains the start button
    private JPanel playerMsgPanel;  // indicates the active player, or winner
    private JPanel statusMsgPanel;  // gives status messages

    // components contained in squarePanel
    private JButton[][] squareButtons;
    
    // components contained in startPanel
    private JButton startVsHumanButton;
    private JButton startVsCPUButton;
    
    // components contained in messagePanel
    private JLabel activePlayerMsg;
    private JLabel statusMsg;
    
    // most recent CPU move
    private PieceMove cpuMove;
    
    /**
     * Constructor - initialize reference to game controller, instantiate
     * the UI components, and set them to the initial state.
     * 
     * @param chessControl
     */
    public ChessBoardPanel(ChessController chessControl)
    {        
        controller = chessControl;
        
        // Initialize squarePanel
        squareButtons = new JButton[8][8];
        squarePanel = new JPanel();       
        for(int row = 0; row < 8; row++)
        {
            for(int col = 0; col < 8; col++)
            {
                squareButtons[row][col] = new JButton();
                squareButtons[row][col].addActionListener(new SquareButtonListener());
                squareButtons[row][col].setPreferredSize(new Dimension(60, 60));
                squareButtons[row][col].setEnabled(true);
                squarePanel.add(squareButtons[row][col]);                
            }
        }
        
        squarePanel.setBackground(Color.orange);
        squarePanel.setPreferredSize(new Dimension(530, 530));

        // Initialize start panel
        startVsHumanButton = new JButton("Start Vs Human");
        startVsHumanButton.addActionListener(new StartButtonListener());
        startVsCPUButton = new JButton("Start Vs CPU");
        startVsCPUButton.addActionListener(new StartButtonListener());
        startPanel = new JPanel();
        startPanel.add(startVsHumanButton);
        startPanel.add(startVsCPUButton);
        startPanel.setBackground(Color.orange);
        startPanel.setPreferredSize(new Dimension(600, 40));
        
        // Initialize playerMsgPanel
        activePlayerMsg = new JLabel();
        playerMsgPanel = new JPanel();
        playerMsgPanel.add(activePlayerMsg);
        playerMsgPanel.setBackground(Color.orange);
        playerMsgPanel.setPreferredSize(new Dimension(600, 20));

        // Initialize statusMsgPanel
        statusMsg = new JLabel();
        statusMsgPanel = new JPanel();
        statusMsgPanel.add(statusMsg);
        statusMsgPanel.setBackground(Color.orange);
        statusMsgPanel.setPreferredSize(new Dimension(600, 20));
        
        // set the position of the sub-panels within the main panel
        setBackground(Color.orange);
        setPreferredSize(new Dimension(650, 650));
        add(squarePanel);
        add(startPanel);
        add(playerMsgPanel);
        add(statusMsgPanel);
        
        // Initially use the destValidNoCheck state, because that normally
        // causes the UI display to update for piece selection, which is
        // what we want at the start of a game.
        updateButtons(SelectionStatus.destValidNoCheck, false);
        updateMessages(SelectionStatus.destValidNoCheck);
    }
    
    /**
     * updateButtons - Updates the display of pieces on the board.
     */
    private void updateButtons(SelectionStatus status, boolean cpuMoved)
    {
        Piece piece;
        ChessColor color = ChessColor.white;
        PieceType type = PieceType.pawn;
        
        for(int row = 0; row < 8; row++)
        {
            for(int col = 0; col < 8; col++)
            {   
                // set the color of the square, to highlight the square
                // if a piece has been selected, and this square is in
                // range.
                if((status == SelectionStatus.pieceValid) &&
                   (controller.getSquareInRange(row, col)))
                {
                    squareButtons[row][col].setBackground(Color.yellow);
                }
                // Otherwise, use the square's default color
                else
                {
                    if((row + col) % 2 == 0)
                    {
                        squareButtons[row][col].setBackground(Color.red);
                    }
                    else
                    {
                        squareButtons[row][col].setBackground(Color.black);
                    }
                }
                
                // if the computer player moved, highlight the starting and ending squares
                if(cpuMoved)
                {
                    squareButtons[cpuMove.startRow][cpuMove.startCol].setBackground(Color.yellow);
                    squareButtons[cpuMove.destRow][cpuMove.destCol].setBackground(Color.yellow);
                }
                
                // set the piece icon in the square
                piece = controller.getPiece(row, col);
                if(piece != null)
                {
                    type = piece.getType();
                    color = piece.getColor();
                }

                // If there is no piece, set the icon to null
                if(piece == null)
                    squareButtons[row][col].setIcon(null);

                // Handle white pieces
                else if(type == PieceType.pawn && color == ChessColor.white)
                    squareButtons[row][col].setIcon(new ImageIcon("images/wPawn.png"));
                else if(type == PieceType.rook && color == ChessColor.white)
                    squareButtons[row][col].setIcon(new ImageIcon("images/wRook.png"));
                else if(type == PieceType.knight && color == ChessColor.white)
                    squareButtons[row][col].setIcon(new ImageIcon("images/wKnight.png"));
                else if(type == PieceType.bishop && color == ChessColor.white)
                    squareButtons[row][col].setIcon(new ImageIcon("images/wBishop.png"));
                else if(type == PieceType.queen && color == ChessColor.white)
                    squareButtons[row][col].setIcon(new ImageIcon("images/wQueen.png"));
                else if(type == PieceType.king && color == ChessColor.white)
                    squareButtons[row][col].setIcon(new ImageIcon("images/wKing.png"));

                // Handle black pieces...
                else if(type == PieceType.pawn && color == ChessColor.black)
                    squareButtons[row][col].setIcon(new ImageIcon("images/bPawn.png"));
                else if(type == PieceType.rook && color == ChessColor.black)
                    squareButtons[row][col].setIcon(new ImageIcon("images/bRook.png"));
                else if(type == PieceType.knight && color == ChessColor.black)
                    squareButtons[row][col].setIcon(new ImageIcon("images/bKnight.png"));
                else if(type == PieceType.bishop && color == ChessColor.black)
                    squareButtons[row][col].setIcon(new ImageIcon("images/bBishop.png"));
                else if(type == PieceType.queen && color == ChessColor.black)
                    squareButtons[row][col].setIcon(new ImageIcon("images/bQueen.png"));
                else if(type == PieceType.king && color == ChessColor.black)
                    squareButtons[row][col].setIcon(new ImageIcon("images/bKing.png"));

                // This branch is unexpected.  Set icon to null
                else
                    squareButtons[row][col].setIcon(null);
            }
        }
    }
    
    private void updateMessages(SelectionStatus status)
    {
        ChessColor activePlayer = controller.getActivePlayer();
        
        // Update the player message
        if(status == SelectionStatus.destValidCheckMate)
        {
            if(activePlayer == ChessColor.white)
                activePlayerMsg.setText("White player wins!");
            else
                activePlayerMsg.setText("Black player wins!");
        }
        else if(status != SelectionStatus.invalidState)
        {
            if(activePlayer == ChessColor.white)
                activePlayerMsg.setText("White player's turn");
            else
                activePlayerMsg.setText("Black player's turn");            
        }
        
        // Update the status message
        if(status == SelectionStatus.destValidNoCheck)
            statusMsg.setText("Select a piece to move");
        else if(status == SelectionStatus.pieceNull)
            statusMsg.setText("That square is empty.  Select a piece to move.");
        else if(status == SelectionStatus.pieceWrongColor)
            statusMsg.setText("That's not your piece.  Select a piece to move.");
        else if(status == SelectionStatus.pieceValid)
            statusMsg.setText("Select a destination.");
        else if(status == SelectionStatus.destValidCheck)
            statusMsg.setText("CHECK!  Select a piece to move.");
        else if(status == SelectionStatus.destValidCheckMate)
            statusMsg.setText("CHECKMATE!");
        else if(status == SelectionStatus.destValidDraw)
            statusMsg.setText("No valid moves.  Game is a draw.");
        else if(status == SelectionStatus.destOutOfRange)
            statusMsg.setText("That square is not in range.  Select a piece to move.");
        else if(status == SelectionStatus.destSelfInCheck)
            statusMsg.setText("That move would place you in check.  Select a piece to move.");
        else
            statusMsg.setText("Start a game to play again.");        
    }
    
    private class StartButtonListener implements ActionListener
    {
        public void actionPerformed(ActionEvent event)
        {
            if(event.getSource() == startVsHumanButton)
            {
                // start game against human opponent
                controller.newGame(false, 0);

                // Initially use the destValidNoCheck state, because that normally
                // causes the UI display to update for piece selection, which is
                // what we want at the start of a game.
                updateButtons(SelectionStatus.destValidNoCheck, false);
                updateMessages(SelectionStatus.destValidNoCheck);
            }
            
            if(event.getSource() == startVsCPUButton)
            {
                // start game against computer opponent
                controller.newGame(true, 4);

                // Initially use the destValidNoCheck state, because that normally
                // causes the UI display to update for piece selection, which is
                // what we want at the start of a game.
                updateButtons(SelectionStatus.destValidNoCheck, false);
                updateMessages(SelectionStatus.destValidNoCheck);
            }
        }
    }
    
    private class SquareButtonListener implements ActionListener
    {
        SelectionStatus status;
        
        public void actionPerformed(ActionEvent event)
        {
            for(int row = 0; row < 8; row++)
            {
                for(int col = 0; col < 8; col++)
                {
                    if(event.getSource() == squareButtons[row][col])
                    {
                        status = controller.squareSelected(row, col);

                        // Always update the display after each button press
                        updateButtons(status, false);
                        updateMessages(status);
                        
                        if(controller.getState() == GameState.cpuTurn)
                        {
                            status = controller.determineCPUMove();
                            cpuMove = controller.getCPUMove();
                            
                            // Always update the display after each button press
                            updateButtons(status, true);
                            updateMessages(status);
                        }                        
                        
                        break;
                    }                    
                }
            }
        }     
    }    
}
