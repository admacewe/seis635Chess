package seis.stthomas.edu.service;

import org.springframework.roo.addon.json.RooJson;

import seis.stthomas.edu.domain.Board;
import seis.stthomas.edu.domain.Game;
import seis.stthomas.edu.domain.Piece;

@RooJson(deepSerialize = true)
public class GameServiceImpl implements GameService {
	
//	  public static Game findGameFull(Long id) {
//	        if (id == null) return null;
//	        Game game = Game.entityManager()
//	        		.find(Game.class, id);
//	        game.getBoard().getPieces();
//	        Board board = Board.entityManager()
//	        		.find(Board.class, game.getBoard().getId());
////	        System.out.println("This many pieces : " + Piece.findAllPieces().size());
//	        System.out.println("This many pieces : " + board.getPieces().size());
////	        Piece.findAllPieces();
//	        return game;
//	  }
	
    
}
