package poker.video_poker;

import java.util.ArrayList;
import java.util.Scanner;


public class Game {

	public static void main(String[] args) {
		System.out.println("Welcome to Video Poker!");
		Scanner sc = new Scanner(System.in);
		game(sc);
		sc.close();
	}
	
	public static void game(Scanner sc) {		
		double playerMoney = 50.00;
		
		while (playerMoney > 0) {
//			playerDeck.resetOptions();
			double playerBet = getBet(playerMoney, sc);
			playerMoney -= playerBet;
			System.out.println("After bet: " + playerMoney);
			
			Deck mainDeck = new Deck();
			mainDeck.createDeck();
			
			Deck playerDeck = new Deck();
			playerDeck.draw(mainDeck, 5);
			
			System.out.println("Your hand is: " + playerDeck.cards);
			playerDeck.keepCards(sc, mainDeck);
			System.out.println("Your hand is now: " + playerDeck.cards);
			
			playerDeck.setWinner(checkValue(playerDeck));
			System.out.println(playerDeck.getWinner());
			playerMoney = handleMoney(playerDeck.getWinner(), playerMoney, playerBet);
//			System.out.println("After hand: " + playerMoney);
		}
		endGame(sc);
	}
	
	private static void endGame(Scanner sc) {
		boolean gameOver = false;
		while (gameOver == false) {
			String again = "Would you like to play again? (1) yes or (2) no.";
			System.out.println("You ran out of money! " + again);
			while(!sc.hasNextInt()) {
				System.out.println("Invalid Response. " + again);
				sc.nextLine();
			}
			
			int response = sc.nextInt();
			
			if (response == 1) {
				System.out.println("How about you just give me the money, I kick you in the nuts, and we call it a day?");
				game(sc);
			} else if (response == 2) {
				System.out.println("Thanks for playing! See you next time!");
				gameOver = true;
			} else {
				System.out.println("Invalid Response. " + again);
			}
		}
		if (gameOver != true) endGame(sc);
		return;
	}

	private static double handleMoney(String result, double pMoney, double pBet) {
		System.out.println(result);
		switch(result) {
			case "royal flush":
				pMoney += pBet * 500;
				break;
			case "straight flush": 
				pMoney += pBet * 100;
				break;
			case "four of a kind":
				pMoney += pBet * 40;
				break;
			case "full house":
				pMoney += pBet * 20;
				break;
			case "flush":
				pMoney += pBet * 10;
				break;
			case "straight":
				pMoney += pBet * 8;
				break;
			case "three of a kind":
				pMoney += pBet * 4;
				break;
			case "two pair":
				pMoney += pBet * 2;
				break;
			case "one pair":
				pMoney += pBet;
				break;
			default:
				break;
		}
		return pMoney;
	}

	public static double getBet(double pMoney, Scanner sc) {
		boolean validBet = false;
		double pBet = 0;
		while(!validBet) {
			
			System.out.println("");
			System.out.println("You have $" + pMoney + ". How much would you like to bet?");
			System.out.println("Max bet is $5.");
			while(!sc.hasNextDouble()) {
				System.out.println("Invalid Response.");
				System.out.println("You have $" + pMoney + ". How much would you like to bet?");
				System.out.println("Max bet is $5.");
				sc.nextLine();
			}
			pBet = sc.nextDouble();
			if (pMoney < pBet) {
				System.out.println("You cannot bet more money than you currently have.");
			} else if (pBet < 1 || pBet > 5) {
				if (pBet > 5) {
					System.out.println("You cannot bet more than the maximum of $5.");
				} else {
					System.out.println("You must bet at least $1.");
				}
			} else {
				validBet = true;
				return pBet;
			}
		}
		return pBet;
	}

	public static String checkValue(Deck pDeck) {
//		Deck tmpDeck = new Deck();
//		tmpDeck.testFlush();
//		tmpDeck.testPair();
//		tmpDeck.testStraight();
		Deck tmpDeck = new Deck(pDeck);

		ArrayList<Suit> suits = new ArrayList<Suit>();
		ArrayList<Value> values = new ArrayList<Value>();
				
		for (Card aCard : tmpDeck.cards) {
			suits.add(aCard.getSuit());
			values.add(aCard.getValue());
		}
		
		ArrayList<Integer> cards = new ArrayList<Integer>();
		System.out.println("test line 139");
		cards = getValues(values);
		System.out.println("test line 141");
		
		boolean flush = checkFlush(suits);
		boolean straight = checkStraight(cards);
		String pair = checkPair(values);
		
		System.out.println("flush: " + flush + ", straight: " + straight + ", pair: " + pair);
		
		if (flush == true) {
			if (straight == true) {
				if (cards.contains(14)) {
					pDeck.setWinner("royal flush");
				} else {
					pDeck.setWinner("straight flush");
				}
			} else {
				pDeck.setWinner("flush");
			}
		} else if (straight == true) {
			pDeck.setWinner("straight");
		} else if (pair != "no pair") {
			pDeck.setWinner(pair);
		} else {
			pDeck.setWinner("nothing");
		}
		return pDeck.getWinner();
	}
	
	private static ArrayList<Integer> getValues(ArrayList<Value> values) {
		int num = 0;
		ArrayList<Integer> numbers = new ArrayList<Integer>();
		for (Value value : values) {
			switch(value) {
				case TWO: num=2; break;
				case THREE: num=3; break;
				case FOUR: num=4; break;
				case FIVE: num=5; break;
				case SIX: num=6; break;
				case SEVEN: num=7; break;
				case EIGHT: num=8; break;
				case NINE: num=9; break;
				case TEN: num=10; break;
				case JACK: num=11; break;
				case QUEEN: num=12; break;
				case KING: num=13; break;
				case ACE: num=1; break;
				default: break;
			}
			
			int x = numbers.size();
			if (x == 0) {
				numbers.add(0, num);
			} else {
				for (int i = 0; i <= x; i++) {
					if (i == x) {
						numbers.add(num);
					} else if (num <= numbers.get(i)) {
						numbers.add(i, num);
						break;
					}
				}
			}
		}
		
		while (numbers.contains(1)) {
			System.out.println("testing ace");
			if (numbers.contains(2) && numbers.contains(3) && numbers.contains(4) && numbers.contains(5)) {
				break;
			} else {
				int index = numbers.indexOf(1);
				numbers.remove(index);
				numbers.add(14);
			}
		}
 		
		return numbers;
	}

	private static String checkPair(ArrayList<Value> values) {
		String isPair = "";
		int matches = 0;
		for (int i = 0; i < values.size(); i++) {
			for (int j = i + 1; j < values.size(); j++) {
				if (values.get(i) == values.get(j)) {
					matches++;
				}
			}
		}
		
		switch (matches) {
			case 0: isPair = "no pair"; break;
			case 1: isPair = "one pair"; break;
			case 2: isPair = "two pair"; break;
			case 3: isPair = "three of a kind"; break;
			case 4: isPair = "full house"; break;
			case 6: isPair = "four of a kind"; break;
			default: isPair = "no pair"; break;
		}
		
		return isPair;
	}

	private static boolean checkStraight(ArrayList<Integer> cards) {
		int i = 0;
		while(i < 4) {
			if ((cards.get(i) + 1) != cards.get(i+1)) {
				return false;
			}
			i++;
		}
		return true;
	}

	private static boolean checkFlush(ArrayList<Suit> suits) {
		Suit suit = suits.get(0);
		if (suit == suits.get(1) && suit == suits.get(2) && suit == suits.get(3) && suit == suits.get(4)) {
			return true;
		} else {
			return false;
		}
	}

}