package blackjack.controllers;


import org.json.JSONArray;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import blackjack.Console_Game.*;
import blackjack.database.*;
import blackjack.models.*;


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
    
    @CrossOrigin
    @PostMapping("/signup")
    public String signup(@RequestBody Signup signup) throws Exception {
	   try {
		   Database.createUserTable();
	   } catch (Exception e) {
		   System.out.println(e);
	   }
	   
	   JSONArray array = new JSONArray();
    	
	   try {
    		String name = signup.getName();
    		String email = signup.getEmail();
    		String username = signup.getUsername();
    		String password = signup.getPassword();
    		
    		array = Database.insertIntoUserTable(name, email, username, password);
    	}
    	catch (Exception e) {
    		System.out.println(e);
    	}
    	
    	return array.toString();
    }
    
    @PostMapping("/login")
    public String login(@RequestBody Login login) throws Exception {
    	try {
 		   Database.createUserTable();
 	   } catch (Exception e) {
 		   System.out.println(e);
 	   }
    	
    	JSONArray array = new JSONArray();
    	
    	try {
    		String email = login.getEmail();
    		String password = login.getPassword();
    		
    		array = Database.checkUserTable(email, password);
    	} catch (Exception e) {
    		System.out.println(e);
    	}
    	
    	return array.toString();
    }
    
    @GetMapping("/shuffle")
	public String shuffle() throws Exception {
    	Deck newDeck = new Deck();
    	newDeck.createFullDeck();
    	newDeck.shuffle();
    	
    	String obj = newDeck.deckToJSON().toString();
		return obj;
	}
}