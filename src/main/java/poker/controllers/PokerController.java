package poker.controllers;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

//import blackjack.Console_Game.*;
//import blackjack.database.*;
//import blackjack.models.*;

@CrossOrigin
@RestController
public class PokerController {
	
	@GetMapping("/poker")
	public String index() {
		return "future home of 5 card draw poker!";
	}

	@GetMapping("/poker/draw")
	public String draw() {
		return "test draw poker";
	}
	
	@GetMapping("/poker/draw/shuffle")
	public String shuffe() {
		return "testing draw poker shuffle";
	}
}
