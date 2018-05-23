package controllers;

import org.json.JSONObject;
import org.json.JSONArray;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import blackjack.*;
import models.BlackjackStats;
import database.BlackjackDatabase;


@CrossOrigin
@RestController
public class BlackjackController {

    @GetMapping("/")
    public String index() {
    	String houseRules = " House Rules: 1. Blackjack pays 3/2. 2. Dealer hits on 16, stays on all 17s. 3. Maximum of three splits per hand. 4. When splitting aces, you only get one additional card. If that card is an ace, you can split them again.";
    	String message = "Welcome to Blackjack!" + houseRules;
    	
        return message;
    }
    
    @GetMapping("/game") 
    	public String game() {
    		return "Greetings, Professor Falcon. Would you like to play a game?";
    	}
    
    @GetMapping("/blackjack/shuffle")
	public String shuffle() throws Exception {
    	Deck newDeck = new Deck();
    	newDeck.createFullDeck();
    	newDeck.shuffle();
    	
    	String obj = newDeck.deckToJSON().toString();
		return obj;
	}
    
    @GetMapping("/blackjack/stats/{id}")
    public String getStats(@PathVariable(name = "id") int id) throws Exception {
    	try {
    		BlackjackDatabase.createBlackjackStatsTable();
    	} catch (Exception e) {
    		System.out.println(e);
    	}
    	
    	JSONObject obj = new JSONObject();
    	try {
	    	obj = BlackjackDatabase.getStats(id);
    	} catch (Exception e) {
    		System.out.println(e);
    	}
    	return obj.toString();
//    	return obj;
    }
    
    @GetMapping("/blackjack/leaderboard")
    public String getLeaderboard() throws Exception {
    	JSONArray array = new JSONArray();
    	array = BlackjackDatabase.getTopTen();
    	return array.toString();
    }
       
    @PostMapping("/blackjack/stats/{id}")
    public String postStats(@PathVariable(name = "id") int id, @RequestBody BlackjackStats stats) throws Exception {
    	try {
    		BlackjackDatabase.createBlackjackStatsTable();
    	} catch (Exception e) {
    		System.out.println(e);
    	}
    	
    	JSONObject obj = new JSONObject();
    	
    	try {
	    	int handsWon = stats.getHandsWon();
	    	int handsPlayed = stats.getHandsPlayed();
	    	int blackjacks = stats.getBlackjacks();
	    	int highMoney = stats.getHighMoney();
	    	int totalMoney = stats.getTotalMoney();
    		obj = BlackjackDatabase.updateStatsTable(id, handsWon, handsPlayed, blackjacks, highMoney, totalMoney);
    	} catch (Exception e) {
    		System.out.println(e);
    	}
    	return obj.toString();
    }
    
}