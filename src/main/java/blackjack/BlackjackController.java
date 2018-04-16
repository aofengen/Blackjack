package blackjack;


import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


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
   
    @PostMapping("/signup")
    public String signup(@RequestBody Signup signup) throws Exception {
    	try {
    		String name = signup.getName();
    		String email = signup.getEmail();
    		String username = signup.getUsername();
    		String password = signup.getPassword();
    		
    		Database.insertIntoUserTable(name, email, username, password);
    	}
    	catch (Exception e) {
    		System.out.println(e);
    	}
    	JSONObject message = new JSONObject();
    	message.put("message", "signup request received");
    	JSONArray array = new JSONArray();
    	array.put(message);
    	
    	return array.toString();
    }
    
    @PostMapping("/login")
    public String login(@RequestBody Login login) throws Exception {
    	try {
    		String email = login.getEmail();
    		String password = login.getPassword();
    		
    		Database.checkUserTable(email, password);
    	} catch (Exception e) {
    		System.out.println(e);
    	}
    	JSONObject message = new JSONObject();
    	message.put("message", "login request received");
    	JSONArray array = new JSONArray();
    	array.put(message);
    	
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