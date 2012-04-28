/**
 * ChessBoardPanel - Controls display of chess board, and notifies game
 *  controller of user control events.
 */
package seis.stthomas.edu.ui;

import java.awt.*;
import java.awt.event.*;
import java.lang.reflect.InvocationTargetException;

import javax.swing.*;

import org.apache.log4j.Logger;

import seis.stthomas.edu.ai.PieceMove;
import seis.stthomas.edu.domain.Bishop;
import seis.stthomas.edu.domain.Game;
import seis.stthomas.edu.domain.GameState;
import seis.stthomas.edu.domain.King;
import seis.stthomas.edu.domain.Knight;
import seis.stthomas.edu.domain.Pawn;
import seis.stthomas.edu.domain.Piece;
import seis.stthomas.edu.domain.Queen;
import seis.stthomas.edu.domain.Rook;
import seis.stthomas.edu.domain.SelectionStatus;

public class ChessBoardPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final Logger LOG = Logger.getLogger(ChessBoardPanel.class
			.getName());

	private ChessController controller;
	Long selectedGameId = null;

	// panels used in the display
	private JPanel squarePanel; // contains the squares making up the
								// tic-tac-toe board
	private JPanel startPanel; // contains the start button
	private JPanel playerMsgPanel; // indicates the active player, or winner
	private JPanel statusMsgPanel; // gives status messages

	// components contained in squarePanel
	private JButton[][] squareButtons;

	// components contained in startPanel
	private JButton startVsHumanButton;
	private JButton startVsCPUButton;
	private JButton saveGameButton;
	private JButton loadGameButton;
	private JComboBox gameList = new JComboBox();
	private JComboBox cpuDifficulty = new JComboBox();


	// components contained in messagePanel
	private JLabel activePlayerMsg;
	private JLabel statusMsg;
	// most recent CPU move
	private PieceMove cpuMove;
	private Integer selectedDifficulty = 2;


	/**
	 * Constructor - initialize reference to game controller, instantiate the UI
	 * components, and set them to the initial state.
	 * 
	 * @param chessControl
	 */
	public ChessBoardPanel(ChessController controller) {
		this.controller = controller;

		// Initialize squarePanel
		squareButtons = new JButton[8][8];
		squarePanel = new JPanel();
		for (int row = 0; row < 8; row++) {
			for (int col = 0; col < 8; col++) {
				squareButtons[row][col] = new JButton();
				squareButtons[row][col]
						.addActionListener(new SquareButtonListener());
				squareButtons[row][col].setPreferredSize(new Dimension(60, 60));
				squareButtons[row][col].setEnabled(true);
				squarePanel.add(squareButtons[row][col]);
			}
		}
		LOG.debug("Created the array");

		squarePanel.setBackground(Color.orange);
		squarePanel.setPreferredSize(new Dimension(530, 530));

		// Initialize start panel
		// Initialize start panel
		startVsHumanButton = new JButton("Start Vs Human");
		startVsHumanButton.addActionListener(new StartButtonListener());
		startVsCPUButton = new JButton("Start Vs CPU");
		startVsCPUButton.addActionListener(new StartButtonListener());
		saveGameButton = new JButton("Save Game");
		saveGameButton.addActionListener(new StartButtonListener());
		loadGameButton = new JButton("Load Game");
		loadGameButton.addActionListener(new StartButtonListener());
		gameList = new JComboBox(controller.getGameIds());
		gameList.addActionListener(new StartButtonListener());
		String[] difficultyOptions = {"1","2","3","4","5","6"};

		cpuDifficulty = new JComboBox(difficultyOptions);
		cpuDifficulty.setSelectedIndex(2);
		cpuDifficulty.addActionListener(new StartButtonListener());

		startPanel = new JPanel();
		startPanel.add(startVsHumanButton);
		startPanel.add(startVsCPUButton);
		startPanel.add(cpuDifficulty);
		startPanel.add(saveGameButton);
		startPanel.add(loadGameButton);
		startPanel.add(gameList);
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
	private void updateButtons(SelectionStatus status, boolean cpuMoved) {
		LOG.debug(">> updateButtons");
		Piece piece;
		boolean isWhite = true;
		Class<?> pieceType = Pawn.class;

		for (int row = 0; row < 8; row++) {
			LOG.debug("row : " + row);

			for (int col = 0; col < 8; col++) {
				LOG.debug("col : " + col);

				// set the color of the square, to highlight the square
				// if a piece has been selected, and this square is in
				// range.
				if ((status == SelectionStatus.pieceValid)
						&& (controller.getGame().getSquareInRange(row, col))) {
					squareButtons[row][col].setBackground(Color.yellow);
				}
				// Otherwise, use the square's default color
				else {
					if ((row + col) % 2 == 0) {
						squareButtons[row][col].setBackground(Color.red);
					} else {
						squareButtons[row][col].setBackground(Color.black);
					}
				}

				// if the computer player moved, highlight the starting and
				// ending squares
				if (cpuMoved) {
					LOG.debug("String info:");
					LOG.debug(cpuMove.toString());

					squareButtons[cpuMove.getStartRow()][cpuMove.getStartCol()]
							.setBackground(Color.yellow);
					squareButtons[cpuMove.getDestRow()][cpuMove.getDestCol()]
							.setBackground(Color.yellow);
				}

				// set the piece icon in the square
				piece = controller.getGame().getPiece(row, col);
				if (piece != null) {
					pieceType = piece.getClass();
					isWhite = piece.isIsWhite();
				}
				LOG.debug("assigning image for piecetype : "
						+ pieceType.getSimpleName());

				// If there is no piece, set the icon to null
				if (piece == null)
					squareButtons[row][col].setIcon(null);

				// Handle white pieces
				else if (pieceType == Pawn.class && isWhite == true) {
					LOG.debug("pieceType == Pawn.class && isWhite == true");

					squareButtons[row][col].setIcon(new ImageIcon(
							"images/wPawn.png"));

				} else if (pieceType == Rook.class && isWhite == true)
					squareButtons[row][col].setIcon(new ImageIcon(
							"images/wRook.png"));
				else if (pieceType == Knight.class && isWhite == true)
					squareButtons[row][col].setIcon(new ImageIcon(
							"images/wKnight.png"));
				else if (pieceType == Bishop.class && isWhite == true)
					squareButtons[row][col].setIcon(new ImageIcon(
							"images/wBishop.png"));
				else if (pieceType == Queen.class && isWhite == true)
					squareButtons[row][col].setIcon(new ImageIcon(
							"images/wQueen.png"));
				else if (pieceType == King.class && isWhite == true)
					squareButtons[row][col].setIcon(new ImageIcon(
							"images/wKing.png"));

				// Handle black pieces...
				else if (pieceType == Pawn.class && isWhite == false)
					squareButtons[row][col].setIcon(new ImageIcon(
							"images/bPawn.png"));
				else if (pieceType == Rook.class && isWhite == false)
					squareButtons[row][col].setIcon(new ImageIcon(
							"images/bRook.png"));
				else if (pieceType == Knight.class && isWhite == false)
					squareButtons[row][col].setIcon(new ImageIcon(
							"images/bKnight.png"));
				else if (pieceType == Bishop.class && isWhite == false)
					squareButtons[row][col].setIcon(new ImageIcon(
							"images/bBishop.png"));
				else if (pieceType == Queen.class && isWhite == false)
					squareButtons[row][col].setIcon(new ImageIcon(
							"images/bQueen.png"));
				else if (pieceType == King.class && isWhite == false)
					squareButtons[row][col].setIcon(new ImageIcon(
							"images/bKing.png"));

				// This branch is unexpected. Set icon to null
				else
					squareButtons[row][col].setIcon(null);
			}
		}
	}

	private void updateMessages(SelectionStatus status) {
		boolean activePlayerIsWhite = controller.getGame()
				.isActivePlayerIsWhite();

		// Update the player message
		if (status == SelectionStatus.destValidCheckMate) {
			if (activePlayerIsWhite == true)
				activePlayerMsg.setText("White player wins!");
			else
				activePlayerMsg.setText("Black player wins!");
		} else if (status != SelectionStatus.invalidState) {
			if (activePlayerIsWhite == true)
				activePlayerMsg.setText("White player's turn");
			else
				activePlayerMsg.setText("Black player's turn");
		}

		// Update the status message
		if (status == SelectionStatus.destValidNoCheck)
			statusMsg.setText("Select a piece to move");
		else if (status == SelectionStatus.pieceNull)
			statusMsg.setText("That square is empty.  Select a piece to move.");
		else if (status == SelectionStatus.pieceWrongColor)
			statusMsg
					.setText("That's not your piece.  Select a piece to move.");
		else if (status == SelectionStatus.pieceValid)
			statusMsg.setText("Select a destination.");
		else if (status == SelectionStatus.destValidCheck)
			statusMsg.setText("CHECK!  Select a piece to move.");
		else if (status == SelectionStatus.destValidCheckMate)
			statusMsg.setText("CHECKMATE!");
		else if (status == SelectionStatus.destValidDraw)
			statusMsg.setText("No valid moves.  Game is a draw.");
		else if (status == SelectionStatus.destOutOfRange)
			statusMsg
					.setText("That square is not in range.  Select a piece to move.");
		else if (status == SelectionStatus.destSelfInCheck)
			statusMsg
					.setText("That move would place you in check.  Select a piece to move.");
		else
			statusMsg.setText("Start a game to play again.");
	}

	private class StartButtonListener implements ActionListener {

		public void actionPerformed(ActionEvent event) {
			if (event.getSource() == startVsHumanButton) {
				// start game against human opponent
				controller.newTwoPlayerGame();

				// Initially use the destValidNoCheck state, because that
				// normally
				// causes the UI display to update for piece selection, which is
				// what we want at the start of a game.
				updateButtons(SelectionStatus.destValidNoCheck, false);
				updateMessages(SelectionStatus.destValidNoCheck);
			}

			if (event.getSource() == startVsCPUButton) {
				// start game against computer opponent
				controller.newOnePlayerGameGame(true, selectedDifficulty.intValue());

				// Initially use the destValidNoCheck state, because that
				// normally
				// causes the UI display to update for piece selection, which is
				// what we want at the start of a game.
				updateButtons(SelectionStatus.destValidNoCheck, false);
				updateMessages(SelectionStatus.destValidNoCheck);
			}

			if (event.getSource() == saveGameButton) {
				controller.saveGame();

				gameList.removeAllItems();
				for (Long s : controller.getGameIds()) {
					gameList.addItem(s);
				}

			}

			if (event.getSource() == gameList) {
				JComboBox cb = (JComboBox) event.getSource();
				selectedGameId = (Long) cb.getSelectedItem();
				gameList = new JComboBox(controller.getGameIds());
				startPanel.repaint();
			}
			
			if (event.getSource() == cpuDifficulty) {
				JComboBox cb = (JComboBox) event.getSource();
				String StringSelectedDifficulty = (String) cb.getSelectedItem();
				selectedDifficulty = new Integer(StringSelectedDifficulty);
				LOG.info("difficulty selected was : "  + selectedDifficulty);
			}

			if (event.getSource() == loadGameButton) {

				controller.loadGame(selectedGameId);
				updateButtons(SelectionStatus.destValidNoCheck, false);
				updateMessages(SelectionStatus.destValidNoCheck);
				LOG.info("Just loaded this game : " +controller.game.getId());
				updateButtons(SelectionStatus.destValidNoCheck, false);
				updateMessages(SelectionStatus.destValidNoCheck);
			}
		}
	}

	private class SquareButtonListener implements ActionListener {
		SelectionStatus status;

		public void actionPerformed(ActionEvent event) {
			for (int row = 0; row < 8; row++) {
				for (int col = 0; col < 8; col++) {
					if (event.getSource() == squareButtons[row][col]) {
						try {
							status = controller.getGame().squareSelected(row,
									col);
						} catch (SecurityException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (IllegalArgumentException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (NoSuchMethodException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (IllegalAccessException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (InvocationTargetException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}

						// Always update the display after each button press
						updateButtons(status, false);
						updateMessages(status);

						if (controller.getGame().getState() == GameState.cpuTurn) {
							try {
								status = controller.getGame()
										.determineCPUMove();
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (SecurityException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (IllegalArgumentException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (NoSuchMethodException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (IllegalAccessException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (InvocationTargetException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

							// Always update the display after each button press
							LOG.info(status);
                            cpuMove = controller.getGame().getCpuMove();

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
