package seis.stthomas.edu.utility;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.util.StopWatch;
import org.springframework.web.client.RestTemplate;

import seis.stthomas.edu.domain.Game;


public class TesterMain {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		
		int processors = Runtime.getRuntime().availableProcessors();

		System.out.println(processors);
	    StopWatch stopWatch = new StopWatch();  


		
//		RestTemplate rest = new RestTemplate();
//		Game game = rest.getForObject(
//		      "http://localhost:8080/ChessRoo/games" +
//		      "/?id={id}",
//		      Game.class, "1");
//		
//		Game game = rest.getForObject(
//			      "http://localhost:8080/ChessRoo/games/{id}=1",
//			      Game.class);

	}

}
