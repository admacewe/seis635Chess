/**
 * ChessController - This class is the interface from the UI to the domain.  It
 *  also keeps track of game state, and calls other methods in the domain based
 *  on user actions and the current game state.
 */

package seis.stthomas.edu.ui;

import org.apache.log4j.Logger;

import seis.stthomas.edu.domain.Game;

public class ChessController {
	
	private static final Logger LOG = Logger.getLogger(ChessController.class
			.getName());
	
	Game game;
	ChessWindow ui; // user interface

	/**
	 * Initialize for a new game. Create the board and set inital states.
	 */
	
	public void newGame(boolean playVsCPU, int difficulty) {
		LOG.info("starting a new game");
		this.game = new Game();
		game.newGame(playVsCPU, difficulty);
	}
	
	/**
	 * createUI - Instantiate a ChessWindow object to create and initialize the
	 * user interface.
	 */
	private void createUI() {
		ui = new ChessWindow(this);
	}

	/**
	 * main - Main method. Instantiate a ChessController, initialize the game,
	 * and create the user interface.
	 */
	public static void main(String[] args) {
		ChessController controller = new ChessController();

		// Initialize the game and create the UI. This must be done
		// in this order or the UI will not properly display the board.
		controller.newGame(false, 0);
		controller.createUI();
	}

	public Game getGame() {
		return this.game;
	}
	
	


}