package controllers;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import poker.video_poker.*;
import models.PokerStats;
import database.PokerDatabase;

@CrossOrigin
@RestController
public class PokerController {
	
	@GetMapping("/poker")
	public String index() {
		return "future home of video poker!";
	}
	
	@GetMapping("/poker/shuffle")
	public String shuffle() throws Exception {
    	Deck newDeck = new Deck();
    	newDeck.createDeck();
    	newDeck.shuffle();
    	
    	String obj = newDeck.deckToJSON().toString();
		return obj;
	} 
}
