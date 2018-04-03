package Blackjack;

import java.util.Scanner;

public class blackjack {

	public static void main(String[] args) {
		System.out.println("Welcome to Blackjack!");
		Scanner sc = new Scanner(System.in);
		game(sc);
	}
	
		public static void game(Scanner sc) {
			//Create variable for later
			String handWinner = "";
			//Create and populate deck
			Deck mainDeck = new Deck();
			mainDeck.createFullDeck();
			mainDeck.shuffle();
			
			//Create decks for player and dealer
			Deck playerDeck = new Deck();
			Deck dealerDeck = new Deck();
			
			double playerMoney = 100.00;
			
			System.out.println("Welcome to the game!");
			
			//Game Loop
			//Every time player has a turn, run the loop once
			while(playerMoney > 0) {
				//play on
				//Take players bet
				double playerBet = getBet(playerMoney, sc);
				System.out.println("You bet $" + playerBet);
				
				boolean endRound = false;
				//Start dealing
				
				playerDeck.draw(mainDeck);
				dealerDeck.draw(mainDeck);
				
				playerDeck.draw(mainDeck);
				dealerDeck.draw(mainDeck);
				
				while(true) {
					System.out.println("Your hand: ");
					System.out.println(playerDeck.toString());
					System.out.println("Your hand is: " + playerDeck.cardsValue());
					
					//Check for blackjack
					boolean playerBlackjack = Deck.checkBlackjack(playerDeck);
					boolean dealerBlackjack = Deck.checkBlackjack(dealerDeck);
					
					if (playerBlackjack == true || dealerBlackjack == true) {
						if (playerBlackjack == true && dealerBlackjack == true) {
							System.out.println("Push");
							endRound = endHand(mainDeck, playerDeck, dealerDeck);
							break;
						} else if (playerBlackjack == true) {
							playerMoney = handleMoney("blackjack", playerMoney, playerBet);
							endRound = endHand(mainDeck, playerDeck, dealerDeck);
							break;
						} else {
							System.out.println("Dealer Cards:" + dealerDeck.toString());
							System.out.println("Dealer Blackjack!!!");
							playerMoney = handleMoney("dealer", playerMoney, playerBet);
							endRound = endHand(mainDeck, playerDeck, dealerDeck);
							break;
						}
					} else {
						//Display dealer hand
						System.out.println("Dealer hand: " + dealerDeck.getCard(0).toString() + " and [hidden]");
						
						//What does the player want to do?
						System.out.println("Would you like to (1) hit or (2) stand?");
						
						int response = sc.nextInt();
						
						if(response==1) {
							playerDeck.draw(mainDeck);
							System.out.println("You draw a: " + playerDeck.getCard(playerDeck.deckSize()-1).toString());
							//bust if over 21
							if(playerDeck.cardsValue() > 21) {
								System.out.println("Bust. Currently valued at: " + playerDeck.cardsValue());
								playerMoney = handleMoney("dealer", playerMoney, playerBet);
								endRound = endHand(mainDeck, playerDeck, dealerDeck);
								break;
							}
						}
						if(response==2) {
							break;
						}
					}
				}
				
				//show dealer cards
				if (endRound == false) {
					System.out.println("Dealer Cards:" + dealerDeck.toString());
					
					//display total for dealer
					System.out.println("Dealer's hand is valued at: " + dealerDeck.cardsValue());
					
					//see if dealer's cards are better than player's cards
					if((dealerDeck.cardsValue() >= 17)) {
						handWinner = checkHand(playerDeck, dealerDeck);
						playerMoney = handleMoney(handWinner, playerMoney, playerBet);
						endRound = endHand(mainDeck, playerDeck, dealerDeck);
					} else {
					//dealers draws at 16 and hits at 17
						while ((dealerDeck.cardsValue() < 17) && endRound == false) {
							dealerDeck.draw(mainDeck);
							System.out.println("Dealer draws :" + dealerDeck.getCard(dealerDeck.deckSize()-1).toString());
							
							//display total for dealer
							System.out.println("Dealer's hand is valued at: " + dealerDeck.cardsValue());
						}

						//Did dealer bust?
						if((dealerDeck.cardsValue() > 21) && endRound == false) {
							System.out.println("Dealer Busts");
							playerMoney = handleMoney("player", playerMoney, playerBet);
							endRound = endHand(mainDeck, playerDeck, dealerDeck);
						} else {
						//Who wins?
						handWinner = checkHand(playerDeck, dealerDeck);
						playerMoney = handleMoney(handWinner, playerMoney, playerBet);
						endRound = endHand(mainDeck, playerDeck, dealerDeck);
						}
					}
				} else {
				endRound = endHand(mainDeck, playerDeck, dealerDeck);
				}
			}
			
			System.out.println("You're out of money. Game Over!");
			playAgain(sc);
		}
		
		private static double getBet(double pMoney, Scanner sc) {
			boolean validBet = false;
			double pBet = 0;
			while(!validBet) {
				System.out.println("You have $" + pMoney + ". How much would you like to bet?");
				pBet = sc.nextDouble();
				if (pMoney >= pBet) {
					validBet = true;
					return pBet;
				} else if (pBet == 0) {
					System.out.println("You must bet ata least 1 dollar.");
				} else {
					System.out.println("You cannot bet more money than you currently have.");
				}
			}
			return pBet;
		}

		private static boolean endHand(Deck mDeck, Deck pDeck, Deck dDeck) {
			pDeck.moveAllToDeck(mDeck);
			dDeck.moveAllToDeck(mDeck);
			System.out.println("End of Hand");
			return true;
		}

		public static void playAgain(Scanner sc) {
			System.out.println("Would you like to play again? (1)yes or (2)no");
			int response = sc.nextInt();

			while (true) {
				if (response == 1) {
					game(sc);
				} else {
					sc.close();
					System.out.println("Thanks for playing! See you next time!");
					break;
				}
			return;
			}
	}
		
		public static String checkHand(Deck pD, Deck dD) {
			String winner = "";
			int x = pD.cardsValue();
			int y = dD.cardsValue();
			
			if (x == y) {
				winner = "push";
				System.out.println("push");
			} else if (x > y) {
				winner = "player";
				System.out.println("player");
			} else {
				winner = "dealer";
				System.out.println("dealer");
			}
			return winner;
		}
		
		private static double handleMoney(String win, double pM, double pB) {
			switch (win) {
				case "blackjack":
					System.out.println("Blackjack!!!");
					pM += pB * 3/2;
					break;
				case "player":
					System.out.println("You win!");
					pM += pB;
					break;
				case "dealer":
					System.out.println("Dealer beats you!");
					pM -= pB;
					break;
				default:
					break;
			}
			return pM;
		}
}
