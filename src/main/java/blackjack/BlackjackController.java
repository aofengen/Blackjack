package blackjack;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BlackjackController {

    @GetMapping("/")
    public String index() {
    	String houseRules = " House Rules: 1. Blackjack pays 3/2. 2. Dealer hits on 16, stays on all 17s. 3. Maximum of three splits per hand. 4. When splitting aces, you only get one additional card. If that card is an ace, you can split them again.";
    	String message = "Welcome to Blackjack!" + houseRules;
    	
        return message;
    }
    
    @PostMapping("/") 
    	public String startGame() {
    		return "game";
    	}
    
    @GetMapping("/game") 
    	public String game() {
    		return "Greetings, Professor Falcon. Would you like to play a game?";
    	}
   
    @PostMapping("/game")
    	public String endGame() {
    	return "";
    }
}