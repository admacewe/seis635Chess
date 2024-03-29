/**
 * ChessController - This class is the interface from the UI to the domain.  It
 *  also keeps track of game state, and calls other methods in the domain based
 *  on user actions and the current game state.
 */

package seis.stthomas.edu.ui;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import seis.stthomas.edu.ai.CPUPlayer;
import seis.stthomas.edu.domain.Game;
import seis.stthomas.edu.service.GameServiceImpl;

public class ChessController {

	private static final Logger LOG = Logger.getLogger(ChessController.class
			.getName());

	Game game;
	ChessWindow ui; // user interface
	GameServiceImpl gameService = new GameServiceImpl();

	ApplicationContext ac = new ClassPathXmlApplicationContext(
			"classpath*:META-INF/spring/applicationContext*.xml");

	/**
	 * Initialize for a new game. Create the board and set inital states.
	 */

	public void newTwoPlayerGame() {
		LOG.info("starting a new game");
		this.game = new Game();
		this.game.initGame();
//		 game.newGame(playVsCPU, difficulty);
		// game.newGame();

	}
	
	public void newOnePlayerGameGame(boolean playVsCPU, int difficulty) {
		LOG.info("starting a new game");
		this.game = new Game(playVsCPU, difficulty);
		this.game.initGame();

		// game.newGame();

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
		controller.newTwoPlayerGame();
		controller.createUI();
	}

	public Game getGame() {
		return this.game;
	}


	public void saveGame() {

		LOG.info("Game id is set to ::: " + game.getId());
		if (game.getId()==null){
			Date date = new Date();
			game.setName(date.toString());
			gameService.saveGame(game);
		} else {
			gameService.updateGame(game);
		}

			

	}

	public void loadGameFromId(Long selectedGameId) {
		LOG.info("selectedGameId : " + selectedGameId);
		this.game = gameService.findGame(selectedGameId);
		LOG.info("piece map size : " + this.game.getBoard().getPieces().size());
		LOG.info("Squares map size : " + this.game.getBoard().getSquareInRange().size());

		LOG.info("board id is : " +this.game.getBoard().getId());
		LOG.info(this.game.toJson());
		if (this.game.isIsOpponentCPU()){
			this.game.setCpuPlayer(new CPUPlayer());
			//TODO need to fix the difficulty.
			this.game.getCpuPlayer().setDifficulty(1);
		}
		
	}
	
	public void loadGameFromName(String selectedGameName) {
		LOG.debug("selectedGameName : " + selectedGameName);
		List<Game> games = gameService.findAllGames();
		Iterator<Game> iterator = games.iterator();
		while(iterator.hasNext()){
			Game tempGame = (Game) iterator.next();
			if (tempGame.getName().equalsIgnoreCase(selectedGameName)){
				this.game = tempGame;
			}
			
		}
		
		

		LOG.debug("board id is : " +this.game.getBoard().getId());
		LOG.debug(this.game.toJson());
		if (this.game.isIsOpponentCPU()){
			this.game.setCpuPlayer(new CPUPlayer());
			//TODO need to fix the difficulty.
			this.game.getCpuPlayer().setDifficulty(1);
		}
		
	}

	public Long[] getGameIds() {
		List<Long> gameIds = new ArrayList<Long>();
		List<Game> listOfAllGames = gameService.findAllGames();
		LOG.info("Found this many games : " +listOfAllGames.size());
		Iterator<Game> iterator = listOfAllGames.iterator();
		while (iterator.hasNext()) {
			Game gameFromList = iterator.next();
			LOG.info("Found this game : " +gameFromList.getId());

				Long gameFromListName = gameFromList.getId();

				gameIds.add(gameFromListName);
			LOG.info("added this id to the games list :: " + gameFromListName);

		}
		Long [] emptyStringArray = new Long[0];
		Long[] gameIdsArray = (Long[]) gameIds.toArray(emptyStringArray);
		return gameIdsArray;
	}
	
	public String[] getGameNames() {
		List<String> gameNames = new ArrayList<String>();
		List<Game> listOfAllGames = gameService.findAllGames();
		LOG.info("Found this many games : " +listOfAllGames.size());
		Iterator<Game> iterator = listOfAllGames.iterator();
		while (iterator.hasNext()) {
			Game gameFromList = iterator.next();
			LOG.info("Found this game : " +gameFromList.getId());

				String gameFromListName = gameFromList.getName();

			gameNames.add(gameFromListName);
			LOG.info("added this id to the games list :: " + gameFromListName);

		}
		String[] emptyStringArray = new String[0];
		String[] gameNamesArray = (String[])gameNames.toArray(emptyStringArray);
		return gameNamesArray;
	}

}