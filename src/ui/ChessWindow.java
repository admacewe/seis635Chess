package ui;

import domain.ChessController;
import java.awt.*;
import javax.swing.*;

public class ChessWindow
{
    ChessBoardPanel boardPanel;
    ChessController gameController;
    
    public ChessWindow(ChessController chessControl)
    {
        boardPanel = new ChessBoardPanel(chessControl);

        JFrame frame = new JFrame("Chess UI Demo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(boardPanel, BorderLayout.CENTER);
        frame.pack();
        frame.setVisible(true);        
    }
}
