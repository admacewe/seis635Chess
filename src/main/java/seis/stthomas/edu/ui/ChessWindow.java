package seis.stthomas.edu.ui;

import java.awt.BorderLayout;

import javax.swing.JFrame;

import org.apache.log4j.Logger;



public class ChessWindow
{
    ChessBoardPanel boardPanel;
    ChessController gameController;
	private static final Logger LOG = Logger.getLogger(ChessWindow.class
			.getName());
    
    public ChessWindow(ChessController chessControl)
    {
        boardPanel = new ChessBoardPanel(chessControl);

        JFrame frame = new JFrame("Chess UI Demo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(boardPanel, BorderLayout.CENTER);
        frame.pack();
        frame.setVisible(true);     
        LOG.info("Done rendering window");
    }
}
