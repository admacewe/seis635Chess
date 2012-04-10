package seis.stthomas.edu.ui;

import java.awt.BorderLayout;

import javax.swing.JFrame;

import seis.stthomas.edu.domain.ChessController;


public class ChessWindow
{
    ChessBoardPanel boardPanel;
    ChessController gameController;
    
    public ChessWindow(ChessController chessControl)
    {
        boardPanel = new ChessBoardPanel(chessControl);

        JFrame frame = new JFrame("Chess");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(boardPanel, BorderLayout.CENTER);
        frame.pack();
        frame.setVisible(true);        
    }
}
