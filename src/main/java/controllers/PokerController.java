package controllers;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import poker.video_poker.*;
import models.PokerStats;
import database.PokerDatabase;

@CrossOrigin(origins = "*")
@RestController
public class PokerController {
	
	@GetMapping("/poker/shuffle")
	public String shuffle() throws Exception {
    	Deck newDeck = new Deck();
    	newDeck.createDeck();
    	newDeck.shuffle();
    	
    	String obj = newDeck.deckToJSON().toString();
		return obj;
	} 
	
	@GetMapping("/poker/leaderboard")
    public String getLeaderboard() throws Exception {
    	JSONArray array = new JSONArray();
    	array = PokerDatabase.getTopTen();
    	return array.toString();
    }
	
    @GetMapping("/poker/stats/{id}")
    public String getStats(@PathVariable(name = "id") int id) throws Exception {
    	try {
    		PokerDatabase.createVideoPokerStatsTable();
    	} catch (Exception e) {
    		System.out.println(e);
    	}
    	
    	JSONObject obj = new JSONObject();
    	try {
	    	obj = PokerDatabase.getStats(id);
    	} catch (Exception e) {
    		System.out.println(e);
    	}
    	return obj.toString();
    }
	
    @PostMapping("/poker/stats/{id}")
    public String postStats(@PathVariable(name = "id") int id, @RequestBody PokerStats stats) throws Exception {
    	try {
    		PokerDatabase.createVideoPokerStatsTable();
    	} catch (Exception e) {
    		System.out.println(e);
    	}
    	
    	JSONObject obj = new JSONObject();
    	
    	try {
	    	int handsWon = stats.getHandsWon();
	    	int handsPlayed = stats.getHandsPlayed();
	    	int highMoney = stats.getHighMoney();
	    	int totalMoney = stats.getTotalMoney();
	    	int royalFlush = stats.getRoyalFlush();
	    	int straightFlush = stats.getStraightFlush();
	    	int fourKind = stats.getFourKind();
	    	int fullHouse = stats.getFullHouse();
	    	int flush = stats.getFlush();
	    	int straight = stats.getStraight();
	    	int threeKind = stats.getThreeKind();
    		obj = PokerDatabase.updateStatsTable(id, handsWon, handsPlayed, highMoney, totalMoney, royalFlush, straightFlush, fourKind, fullHouse, flush, straight, threeKind);
    	} catch (Exception e) {
    		System.out.println(e);
    	}
    	return obj.toString();
//    	return obj;
    }
}
