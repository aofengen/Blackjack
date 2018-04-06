package Blackjack;

import java.util.ArrayList;
import java.util.Scanner;

public class blackjack {

	public static void main(String[] args) {
		System.out.println("Welcome to Blackjack!");
		Scanner sc = new Scanner(System.in);
		game(sc);
	}
	
		public static void game(Scanner sc) {
			
			//Create and populate deck
			Deck mainDeck = new Deck();
			mainDeck.createFullDeck();
			mainDeck.shuffle();
			
			//Create decks for player and dealer
			Deck playerDeck = new Deck();
			Deck dealerDeck = new Deck();
			
			//create blank decks to be used for splitting cards
			Deck splitDeck1 = new Deck();
			Deck splitDeck2 = new Deck();
			Deck splitDeck3 = new Deck();
			
			//create an array to hold all decks
			ArrayList<Deck> decks = new ArrayList<Deck>();
			decks.add(0, mainDeck);
			decks.add(1, playerDeck);
			decks.add(2, splitDeck1);
			decks.add(3, splitDeck2);
			decks.add(4, splitDeck3);
			decks.add(5, dealerDeck);
			
			//create list to hold money items
			ArrayList<Double> monies = new ArrayList<Double>();
			
			monies.add(0, 100.00);
			System.out.println("Welcome to the game!");
			
			while (monies.get(0) > 0) {
				playerTurn(decks, monies, sc);
				dealerTurn(decks, monies, sc);
			}
			
			System.out.println("You're out of money. Game Over!");
			playAgain(sc);
		}
		
		public static void playerTurn(ArrayList<Deck> decks, ArrayList<Double> monies, Scanner sc) {
			//Game Loop
			double playerMoney = monies.get(0);
			//Every time player has a turn, run the loop once
			while(playerMoney > 0) {
				//play on
				//Take players bet
				double playerBet = getBet(playerMoney, sc);
				
				//create extra bets in case of splits
				double splitBet1 = playerBet;
				double splitBet2 = playerBet;
				double splitBet3 = playerBet;
				
				
				//populate list of money items
				
				monies.add(1, playerBet);
				monies.add(2, splitBet1);
				monies.add(3, splitBet2);
				monies.add(4, splitBet3);
				
				System.out.println("You bet $" + playerBet);
				
				Deck mainDeck = decks.get(0);
				
				//Start dealing
				for (int i = 1; i < 5; i++) {				

					Deck playerDeck = decks.get(i);
					Deck dealerDeck = decks.get(5);
					
					if (i == 1) {
						playerDeck.draw(mainDeck);
						dealerDeck.draw(mainDeck);
					
						playerDeck.draw(mainDeck);
						dealerDeck.draw(mainDeck);
						
						decks.set(5, dealerDeck);
					} else {
						Deck firstHand = decks.get(1);
						playerDeck.setNumHands(firstHand.getNumHands());
					}
					
					if (playerDeck.deckSize() < 1) {
						break;
					}
					
					playerDeck.resetOptions();
					
					while(true) {
						if (playerDeck.getBust() == true || playerDeck.getDoubleDown() == true) {
							break;
						}
						if (playerDeck.getSplitAces() == true) {
							if (playerDeck.cardValue(1) == "ACE") {
								String question = "Would you like to split aces again? (1) yes or (2) no";
								System.out.println(question);
								int splittingAces = sc.nextInt();
								switch (splittingAces) {
									case 1:
										splitHand(decks, playerDeck, i);
									case 2:
										break;
									default:
										System.out.println("Invalid Response. " + question);
								}
							} else {
								break;
							}
						}
						
						System.out.println("Your hand: ");
						System.out.println(playerDeck.toString());
						System.out.println("Your hand is: " + playerDeck.cardsValue());
						
						//make variable for later
						int response;
						
						//Check for blackjack if only 2 cards in hand
						int numCards = playerDeck.deckSize();
						
						playerDeck.setBlackjack(playerDeck.checkBlackjack());
						dealerDeck.setBlackjack(dealerDeck.checkBlackjack());
						
						if (numCards == 2 && playerDeck.getChecked() == false) {
							if (playerDeck.getBlackjack() == true && dealerDeck.getBlackjack() == true) {
								System.out.println("Push");
								break;
							} else if (playerDeck.getBlackjack() == true) {
								playerDeck.setWinner("blackjack");
								playerMoney = handleMoney(playerDeck.getWinner(), playerMoney, playerBet);
								monies.set(0, playerMoney);
								break;
							} else if (dealerDeck.getBlackjack() == true){
								System.out.println("Dealer Blackjack!!!");
								playerDeck.setWinner("dealer");
								playerMoney = handleMoney(playerDeck.getWinner(), playerMoney, playerBet);
								monies.set(0, playerMoney);
								break;
							} else {
							//Display dealer hand
							System.out.println("No blackjack!");
							System.out.println("Dealer hand: " + dealerDeck.getCard(0).toString() + " and [hidden]");
							String choices = playerChoices(playerDeck, numCards);
							
							//What does the player want to do?
								System.out.println(choices);
								response = sc.nextInt();
								switch(response) {
									case 1:
										//player hits
										playerDeck.draw(mainDeck);
										System.out.println("You draw a: " + playerDeck.getCard(playerDeck.deckSize()-1).toString());
										
										//bust if over 21
										if(playerDeck.cardsValue() > 21) {
											System.out.println("Bust. Currently valued at: " + playerDeck.cardsValue());
											playerDeck.setWinner("dealer");
											playerDeck.setBust(true);
										}
										break;
									case 2:
										//player stands
										playerDeck.setChecked(true);
										playerDeck.setStand(true);										
										decks.set(i, playerDeck);
										break;
									case 3:
										//player doubles down
										if (playerBet * 2 > playerMoney) {
											System.out.println("Not enough money to double down.");
										} else {
											System.out.println("Doubling Down");
											playerDeck.draw(mainDeck);
											System.out.println("Your card will be hidden until the end of the dealer's turn");
											playerDeck.setDoubleDown(true);
											monies.set(1, playerBet += playerBet);
										}
										break;
									case 4:
										//player splits
										int numHands = playerDeck.getNumHands() + 1;
										if (!Deck.checkSplit(playerDeck)) {
											System.out.println("Response not valid. " + choices);
										} else if (i == 4) {
											System.out.println("Maximum number of splits reached.");
										} else if (playerMoney < playerBet * numHands) {
											System.out.println("Not enough money to split!");
										} else {
											System.out.println("Splitting cards.");
											splitHand(decks, playerDeck, i);
										}
										break;
									default:
										System.out.println("Response not valid. " + choices);
										break;
									}
								}
							} else if (playerDeck.getStand() == true || playerDeck.getDoubleDown() == true || playerDeck.getBust() == true) {
								break;
							} else {
							
								String question = "Would you like to (1) hit or (2) stand?";
								System.out.println(question);
								
								response = sc.nextInt();
								
								if(response==1) {
									//player hits
									playerDeck.draw(mainDeck);
									System.out.println("You draw a: " + playerDeck.getCard(playerDeck.deckSize()-1).toString());
									
									//bust if over 21
									if(playerDeck.cardsValue() > 21) {
										System.out.println("Bust. Currently valued at: " + playerDeck.cardsValue());
										playerDeck.setWinner("dealer");
										playerDeck.setBust(true);
										break;
									}
								} else if(response==2) {
									//player stands
									decks.set(i, playerDeck);
									playerDeck.setStand(true);
									break;
								} else {
									System.out.println("Invalid Response. " + question);
							}
						}
					}
				}
			break;
		}
	}

	public static void dealerTurn(ArrayList<Deck> decks, ArrayList<Double> monies, Scanner sc) {
		//show dealer cards
		double playerMoney = monies.get(0);
		double playerBet = monies.get(1);
		
		Deck mainDeck = decks.get(0);
		Deck playerDeck = decks.get(1);
		Deck dealerDeck = decks.get(5);
	
		System.out.println("Dealer Cards:" + dealerDeck.toString());
		
		//display total for dealer
		System.out.println("Dealer's hand is valued at: " + dealerDeck.cardsValue());
		
		//dealers draws at 16 and hits at 17
		while (dealerDeck.cardsValue() < 17) {
			if (playerDeck.getSplit() != true && (playerDeck.getBlackjack() == true || playerDeck.getBust() == true || dealerDeck.getBlackjack() == true)) {
				break;
			}
			dealerDeck.draw(mainDeck);
			System.out.println("Dealer draws: " + dealerDeck.getCard(dealerDeck.deckSize()-1).toString());
			
			//display total for dealer
			System.out.println("Dealer's hand is valued at: " + dealerDeck.cardsValue());
			if (dealerDeck.cardsValue() > 21) {
				System.out.println("Dealer Busts!");
				dealerDeck.setBust(true);
			}
		}
			
		//see who won
		
		for (int i = 1; i < 5; i++) {	
			//Did dealer bust?
			if (decks.get(i).deckSize() > 0) {
				playerDeck = decks.get(i);

				if (playerDeck.getDoubleDown() == true) {
					checkHandDouble(playerDeck, dealerDeck);
					if (playerDeck.cardsValue() > 21 && dealerDeck.getBust() != true) {
						playerDeck.setBust(true);
					}
				}
				if (playerDeck.getBlackjack() !=  true && dealerDeck.getBlackjack() != true) {
					if (playerDeck.getBust() == true) {
						playerDeck.setWinner("dealer");
						playerMoney = handleMoney(playerDeck.getWinner(), playerMoney, playerBet);
						monies.set(0, playerMoney);
					} else if (dealerDeck.getBust() == true) {
						playerDeck.setWinner(checkHand(playerDeck, dealerDeck));
						playerMoney = handleMoney(playerDeck.getWinner(), playerMoney, playerBet);
						monies.set(0, playerMoney);
					} else {
						//Who wins?
						playerDeck.setWinner(checkHand(playerDeck, dealerDeck));
						playerMoney = handleMoney(playerDeck.getWinner(), playerMoney, playerBet);
						monies.set(0, playerMoney);
					}
				}
			}
		}
		
		
		for (int i = 1; i < decks.size(); i++) {
			if (decks.get(i).deckSize() > 0) {
				decks.get(i).moveAllToDeck(mainDeck);
			}
		}
		
		dealerDeck.resetOptions();
		
		decks.set(0, mainDeck);
		decks.set(5, dealerDeck);
		System.out.println("End of Hand");

	}	
	

	private static void checkHandDouble(Deck pDeck, Deck dDeck) {
		System.out.println("You double down card is " + pDeck.getCard(pDeck.deckSize()-1).toString());
		System.out.println("Your total is " + pDeck.cardsValue());
	}

	public static String playerChoices(Deck pDeck, int numCards) {
		String message = "";
		boolean split = Deck.checkSplit(pDeck);
		
		if (numCards == 2 && split == true) {
			System.out.println("Would you like to (1) hit, (2) stand, (3)double down, or (4) split?");
		} else if (numCards == 2) {
			System.out.println("Would you like to (1) hit, (2) stand, or (3)double down?");
		} else {
			System.out.println("Would you like to (1) hit or (2) stand?");
		}
		return message;
	}

	public static double getBet(double pMoney, Scanner sc) {
		boolean validBet = false;
		double pBet = 0;
		while(!validBet) {
			System.out.println("You have $" + pMoney + ". How much would you like to bet?");
			pBet = sc.nextDouble();
			if (pMoney >= pBet) {
				validBet = true;
				return pBet;
			} else if (pBet == 0) {
				System.out.println("You must bet at least 1 dollar.");
			} else {
				System.out.println("You cannot bet more money than you currently have.");
			}
		}
		return pBet;
	}

	public static void splitHand(ArrayList<Deck> decks, Deck playerDeck, int i) {
		Deck mainDeck = decks.get(0);
		Deck sDeck = new Deck();											
		
		
		if (playerDeck.cardValue(0) == "ACE" && playerDeck.cardValue(1) == "ACE") {
			System.out.println("When splitting aces, you only get one card per hand");
			playerDeck.setSplitAces(true);
			sDeck.setSplitAces(true);
		}
		
		sDeck.splitDeck(playerDeck);
		//Get second cards for each hand
		playerDeck.draw(mainDeck);
		sDeck.draw(mainDeck);
		
		playerDeck.setSplit(true);
		playerDeck.addHand();
		System.out.println("Your current hand is now: " + playerDeck.toString());
		System.out.println("Your split hand is: " + sDeck.toString());
	
		//Add split deck to arrayList for use in next round
		for (int j = 1; j <= 3; j++) {
			if (decks.get(i+j).deckSize() < 1) {
				decks.set(i+j, sDeck);
				break;
			}
		}
	}
	public static void playAgain(Scanner sc) {
		boolean gameOver = false;
		while (gameOver == false) {
			String again = "Would you like to play again? (1)yes or (2)no";
			System.out.println(again);
			int response = sc.nextInt();
			
			if (response == 1) {
				System.out.println("How about you just give me the money, I kick you in the nuts, and we call it a day?");
				game(sc);
			} else if (response == 2) {
				sc.close();
				System.out.println("Thanks for playing! See you next time!");
				gameOver = true;
			} else {
				System.out.println("Invalid Response. " + again);
		}
		}
		return;
}
	
	public static String checkHand(Deck pD, Deck dD) {
		String winner = "";
		
		if (pD.getBust() == true) {
			winner = "dealer";
		} else if (dD.getBust() == true) {
			winner = "player";
		} else {
			int x = pD.cardsValue();
			int y = dD.cardsValue();
			
			if(x == y) {
				winner = "push";
			} else if (x > y) {
				winner = "player";
			} else {
				winner = "dealer";
			}
		}
		return winner;
	}
	
	private static double handleMoney(String win, double pM, double pB) {
		
		switch (win) {
			case "blackjack":
				System.out.println("BLACKJACK!!!");
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
			case "push":
				System.out.println("Push!");
			default:
				break;
		}
		return pM;
	}
}
