package blackjack.controllers;

import org.json.JSONObject;
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
    
    @GetMapping("/shuffle")
	public String shuffle() throws Exception {
    	Deck newDeck = new Deck();
    	newDeck.createFullDeck();
    	newDeck.shuffle();
    	
    	String obj = newDeck.deckToJSON().toString();
		return obj;
	}
   
    @PostMapping("/signup")
    public String signup(@RequestBody Signup signup) throws Exception {
	   try {
		   Database.createUserTable();
	   } catch (Exception e) {
		   System.out.println(e);
	   }
	   
	   JSONObject obj = new JSONObject();

	   try {
    		String name = signup.getName();
    		String email = signup.getEmail();
    		String username = signup.getUsername();
    		String password = signup.getPassword();
    		
    		obj = Database.signup(name, email, username, password);
    	}
    	catch (Exception e) {
    		System.out.println(e);
    	}
    	
	   return obj.toString();
    }
    
    @PostMapping("/login")
    public String login(@RequestBody Login login) throws Exception {
    	try {
 		   Database.createUserTable();
 	   } catch (Exception e) {
 		   System.out.println(e);
 	   }
    	
 	   JSONObject obj = new JSONObject();
    	
    	try {
    		String email = login.getEmail();
    		String password = login.getPassword();
    		
    		obj = Database.login(email, password);
    	} catch (Exception e) {
    		System.out.println(e);
    	}
    	
    	return obj.toString();
    }
    
    @PostMapping("/changeInfo")
    public String changeInfo(@RequestBody Change change) {
    	JSONObject obj = new JSONObject();
    	try {
  		    Database.createUserTable();
  	    } catch (Exception e) {
  		    System.out.println(e);
  	    }
    	
    	try {
    		String name = change.getName();
    		String newEmail = change.getNewEmail();
    		String oldEmail = change.getOldEmail();
    		String username = change.getUsername();
    		String password = change.getPassword();
    		String token = change.getToken();
    		int id = change.getUserIdNumber();
    		
    		obj = Database.changeInfo(name, newEmail, oldEmail, username, password, token, id);
    	}
    	catch (Exception e) {
    		System.out.println(e);
    	}
    	
    	return obj.toString();
    }
    
    
}