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
			
			double playerMoney = 100.00;
			
			//variables to be used later
			Options options = new Options("", false, false, false, false, false, false);

			System.out.println("Welcome to the game!");
			
			//playerTurn(decks, monies, options, sc);
			//Game Loop
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
				monies.add(0, playerMoney);
				monies.add(1, playerBet);
				monies.add(2, splitBet1);
				monies.add(3, splitBet2);
				monies.add(4, splitBet3);
				
				System.out.println("You bet $" + playerBet);
				
				//Start dealing
				
				playerDeck.draw(mainDeck);
				dealerDeck.draw(mainDeck);
				
				playerDeck.draw(mainDeck);
				dealerDeck.draw(mainDeck);
				

				
				while(true) {
					if (options.getBust( )== true || options.getDoubleDown() == true) {
						break;
					}
					System.out.println("Your hand: ");
					System.out.println(playerDeck.toString());
					System.out.println("Your hand is: " + playerDeck.cardsValue());
					
					//make variable for later
					int response;
					
					//Check for blackjack if only 2 cards in hand
					int numCards = playerDeck.deckSize();
					
					boolean playerBlackjack = Deck.checkBlackjack(playerDeck);
					boolean dealerBlackjack = Deck.checkBlackjack(dealerDeck);
					if (numCards == 2 && options.getChecked() == false) {
						if (playerBlackjack == true && dealerBlackjack == true) {
							System.out.println("Push");
							options.setEndRound(endHand(mainDeck, playerDeck, dealerDeck));
							break;
						} else if (playerBlackjack == true) {
							playerMoney = handleMoney("blackjack", playerMoney, playerBet);
							options.setEndRound(endHand(mainDeck, playerDeck, dealerDeck));
							break;
						} else if (dealerBlackjack == true){
							System.out.println("Dealer Cards:" + dealerDeck.toString());
							System.out.println("Dealer Blackjack!!!");
							playerMoney = handleMoney("dealer", playerMoney, playerBet);
							options.setEndRound(endHand(mainDeck, playerDeck, dealerDeck));
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
									playerDeck.draw(mainDeck);
									System.out.println("You draw a: " + playerDeck.getCard(playerDeck.deckSize()-1).toString());
									//bust if over 21
									if(playerDeck.cardsValue() > 21) {
										System.out.println("Bust. Currently valued at: " + playerDeck.cardsValue());
										playerMoney = handleMoney("dealer", playerMoney, playerBet);
										options.setEndRound(endHand(mainDeck, playerDeck, dealerDeck));
										options.setBust(true);
									}
									break;
								case 2:
									options.setChecked(true);
									options.setStand(true);
									break;
								case 3:
									if (playerBet * 2 > playerMoney) {
										System.out.println("Not enough money to double down.");
									} else {
										System.out.println("Doubling Down");
										playerDeck.draw(mainDeck);
										System.out.println("Your card will be hidden until the end of the dealer's turn");
										options.setDoubleDown(true);
										playerBet += playerBet;
										if(playerDeck.cardsValue() > 21) {
											System.out.println("Bust. Currently valued at: " + playerDeck.cardsValue());
											System.out.println("Your double down card was: " + playerDeck.getCard(playerDeck.deckSize()-1).toString());
											playerMoney = handleMoney("dealer", playerMoney, playerBet);
											options.setEndRound(endHand(mainDeck, playerDeck, dealerDeck));
											options.setBust(true);
										}
									}
									break;
								case 4:
									if (!Deck.checkSplit(playerDeck)) {
										System.out.println("Response not valid. " + options);
									} else {
										System.out.println("Splitting cards.");
										if (splitDeck2.deckSize() > 0) {
											
											splitDeck3.splitDeck(playerDeck);
											
											//Get second cards for each hand
											playerDeck.draw(mainDeck);
											splitDeck3.draw(mainDeck);
											
											//add second bet
											splitBet3 = playerBet;
											
											System.out.println("Your first hand is now: " + playerDeck.toString());
											System.out.println("your fourth hand is: " + splitDeck3.toString());
										} else if (splitDeck1.deckSize() > 0) {
											
											splitDeck2.splitDeck(playerDeck);
											
											//Get second cards for each hand
											playerDeck.draw(mainDeck);
											splitDeck2.draw(mainDeck);
											
											//add second bet
											splitBet2 = playerBet;

											System.out.println("Your first hand is now: " + playerDeck.toString());
											System.out.println("your third hand is: " + splitDeck2.toString());
											
										} else {										
											splitDeck1.splitDeck(playerDeck);
											
											//Get second cards for each hand
											playerDeck.draw(mainDeck);
											splitDeck1.draw(mainDeck);
											
											//add second bet
											splitBet1 = playerBet;
											options.setSplit(true);
											System.out.println("Your first hand is now: " + playerDeck.toString());
											System.out.println("your second hand is: " + splitDeck1.toString());
										}
									}
									break;
								default:
									System.out.println("Response not valid. " + options);
									break;
							}
						}
					} else if (options.getStand() || options.getDoubleDown() || options.getBust()) {
						break;
						} else {
						
						System.out.println("Would you like to (1) hit or (2) stand?");
						
						response = sc.nextInt();
						
						if(response==1) {
							playerDeck.draw(mainDeck);
							System.out.println("You draw a: " + playerDeck.getCard(playerDeck.deckSize()-1).toString());
							//bust if over 21
							if(playerDeck.cardsValue() > 21) {
								System.out.println("Bust. Currently valued at: " + playerDeck.cardsValue());
								playerMoney = handleMoney("dealer", playerMoney, playerBet);
								options.setBust(true);
								break;
							}
						}
						if(response==2) {
							break;
						}
						
					}
				}
				
				//show dealer cards
				if (options.getEndRound() == false && options.getBust() != true) {
					System.out.println("Dealer Cards:" + dealerDeck.toString());
					
					//display total for dealer
					System.out.println("Dealer's hand is valued at: " + dealerDeck.cardsValue());
					
					//see if dealer's cards are better than player's cards
					if((dealerDeck.cardsValue() >= 17)) {
						if (options.getDoubleDown()) {
							checkHandDouble(playerDeck);
						}
						options.setWinner(checkHand(playerDeck, dealerDeck));
						playerMoney = handleMoney(options.getWinner(), playerMoney, playerBet);
						options.setEndRound(endHand(mainDeck, playerDeck, dealerDeck));
					} else {
					//dealers draws at 16 and hits at 17
						while ((dealerDeck.cardsValue() < 17) && options.getEndRound()) {
							dealerDeck.draw(mainDeck);
							System.out.println("Dealer draws :" + dealerDeck.getCard(dealerDeck.deckSize()-1).toString());
							
							//display total for dealer
							System.out.println("Dealer's hand is valued at: " + dealerDeck.cardsValue());
						}

						//Did dealer bust?
						if((dealerDeck.cardsValue() > 21) && options.getEndRound()) {
							if (options.getDoubleDown()) {
								checkHandDouble(playerDeck);
							}
							System.out.println("Dealer Busts");
							playerMoney = handleMoney("player", playerMoney, playerBet);
							options.setEndRound(endHand(mainDeck, playerDeck, dealerDeck));
						} else {
						//Who wins?
							if (options.getDoubleDown()) {
								checkHandDouble(playerDeck);
							}
						options.setWinner(checkHand(playerDeck, dealerDeck));
						playerMoney = handleMoney(options.getWinner(), playerMoney, playerBet);
						options.setEndRound(endHand(mainDeck, playerDeck, dealerDeck));
						}
					}
				} else {
					options.setEndRound(endHand(mainDeck, playerDeck, dealerDeck));
					}
				
				if (splitDeck1.deckSize() > 0) {
					splitDeck1.moveAllToDeck(mainDeck);
				}
				if (splitDeck2.deckSize() > 0) {
					splitDeck2.moveAllToDeck(mainDeck);
				}
				if (splitDeck3.deckSize() > 0) {
					splitDeck3.moveAllToDeck(mainDeck);
				}
			}
			
			System.out.println("You're out of money. Game Over!");
			playAgain(sc);
		}
		
//		public static void playerTurn(ArrayList<Deck> dList, Options options, ArrayList<Double> mList, Scanner sc) {
//			//Game Loop
//			//Every time player has a turn, run the loop once
//			while(playerMoney > 0) {
//				//play on
//				//Take players bet
//				double playerBet = getBet(playerMoney, sc);
//				
//				//create extra bets in case of splits
//				double splitBet1 = playerBet;
//				double splitBet2 = playerBet;
//				double splitBet3 = playerBet;
//				
//				
//				//populate list of money items
//				monies.add(0, playerMoney);
//				monies.add(1, playerBet);
//				monies.add(2, splitBet1);
//				monies.add(3, splitBet2);
//				monies.add(4, splitBet3);
//				
//				System.out.println("You bet $" + playerBet);
//				
//				//Start dealing
//				
//				playerDeck.draw(mainDeck);
//				dealerDeck.draw(mainDeck);
//				
//				playerDeck.draw(mainDeck);
//				dealerDeck.draw(mainDeck);
//				
//				while(true) {
//					if (bust == true || doubleDown == true) {
//						break;
//					}
//					System.out.println("Your hand: ");
//					System.out.println(playerDeck.toString());
//					System.out.println("Your hand is: " + playerDeck.cardsValue());
//					
//					//make variable for later
//					int response;
//					
//					//Check for blackjack if only 2 cards in hand
//					int numCards = playerDeck.deckSize();
//					
//					boolean playerBlackjack = Deck.checkBlackjack(playerDeck);
//					boolean dealerBlackjack = Deck.checkBlackjack(dealerDeck);
//					if (numCards == 2 && checked == false) {
//						if (playerBlackjack == true && dealerBlackjack == true) {
//							System.out.println("Push");
//							endRound = endHand(mainDeck, playerDeck, dealerDeck);
//							break;
//						} else if (playerBlackjack == true) {
//							playerMoney = handleMoney("blackjack", playerMoney, playerBet);
//							endRound = endHand(mainDeck, playerDeck, dealerDeck);
//							break;
//						} else if (dealerBlackjack == true){
//							System.out.println("Dealer Cards:" + dealerDeck.toString());
//							System.out.println("Dealer Blackjack!!!");
//							playerMoney = handleMoney("dealer", playerMoney, playerBet);
//							endRound = endHand(mainDeck, playerDeck, dealerDeck);
//							break;
//						} else {
//						//Display dealer hand
//						System.out.println("No blackjack!");
//						System.out.println("Dealer hand: " + dealerDeck.getCard(0).toString() + " and [hidden]");
//						
//						String options = playerOptions(playerDeck, numCards);
//						
//						//What does the player want to do?
//							System.out.println(options);
//							response = sc.nextInt();
//							
//							switch(response) {
//								case 1:
//									playerDeck.draw(mainDeck);
//									System.out.println("You draw a: " + playerDeck.getCard(playerDeck.deckSize()-1).toString());
//									//bust if over 21
//									if(playerDeck.cardsValue() > 21) {
//										System.out.println("Bust. Currently valued at: " + playerDeck.cardsValue());
//										playerMoney = handleMoney("dealer", playerMoney, playerBet);
//										endRound = endHand(mainDeck, playerDeck, dealerDeck);
//										bust = true;
//									}
//									break;
//								case 2:
//									checked = true;
//									stand = true;
//									break;
//								case 3:
//									if (playerBet * 2 > playerMoney) {
//										System.out.println("Not enough money to double down.");
//									} else {
//										System.out.println("Doubling Down");
//										playerDeck.draw(mainDeck);
//										System.out.println("Your card will be hidden until the end of the dealer's turn");
//										doubleDown = true;
//										playerBet += playerBet;
//										if(playerDeck.cardsValue() > 21) {
//											System.out.println("Bust. Currently valued at: " + playerDeck.cardsValue());
//											System.out.println("Your double down card was: " + playerDeck.getCard(playerDeck.deckSize()-1).toString());
//											playerMoney = handleMoney("dealer", playerMoney, playerBet);
//											endRound = endHand(mainDeck, playerDeck, dealerDeck);
//											bust = true;
//										}
//									}
//									break;
//								case 4:
//									if (!Deck.checkSplit(playerDeck)) {
//										System.out.println("Response not valid. " + options);
//									} else {
//										System.out.println("Splitting cards.");
//										if (splitDeck2.deckSize() > 0) {
//											
//											splitDeck3.splitDeck(playerDeck);
//											
//											//Get second cards for each hand
//											playerDeck.draw(mainDeck);
//											splitDeck3.draw(mainDeck);
//											
//											//add second bet
//											splitBet3 = playerBet;
//											
//											System.out.println("Your first hand is now: " + playerDeck.toString());
//											System.out.println("your fourth hand is: " + splitDeck3.toString());
//										} else if (splitDeck1.deckSize() > 0) {
//											
//											splitDeck2.splitDeck(playerDeck);
//											
//											//Get second cards for each hand
//											playerDeck.draw(mainDeck);
//											splitDeck2.draw(mainDeck);
//											
//											//add second bet
//											splitBet2 = playerBet;
//
//											System.out.println("Your first hand is now: " + playerDeck.toString());
//											System.out.println("your third hand is: " + splitDeck2.toString());
//											
//										} else {										
//											splitDeck1.splitDeck(playerDeck);
//											
//											//Get second cards for each hand
//											playerDeck.draw(mainDeck);
//											splitDeck1.draw(mainDeck);
//											
//											//add second bet
//											splitBet1 = playerBet;
//											split = true;
//											System.out.println("Your first hand is now: " + playerDeck.toString());
//											System.out.println("your second hand is: " + splitDeck1.toString());
//										}
//									}
//									break;
//								default:
//									System.out.println("Response not valid. " + options);
//									break;
//							}
//						}
//					} else if (stand == true || doubleDown == true || bust == true) {
//						break;
//						} else {
//						
//						System.out.println("Would you like to (1) hit or (2) stand?");
//						
//						response = sc.nextInt();
//						
//						if(response==1) {
//							playerDeck.draw(mainDeck);
//							System.out.println("You draw a: " + playerDeck.getCard(playerDeck.deckSize()-1).toString());
//							//bust if over 21
//							if(playerDeck.cardsValue() > 21) {
//								System.out.println("Bust. Currently valued at: " + playerDeck.cardsValue());
//								playerMoney = handleMoney("dealer", playerMoney, playerBet);
//								bust = true;
//								break;
//							}
//						}
//						if(response==2) {
//							break;
//						}
//						
//					}
//				}
//		}

		private static void checkHandDouble(Deck pDeck) {
			System.out.println("You double down card was " + pDeck.getCard(pDeck.deckSize()-1).toString());
			System.out.println("Your total was " + pDeck.cardsValue());
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
					System.out.println("You must bet ata least 1 dollar.");
				} else {
					System.out.println("You cannot bet more money than you currently have.");
				}
			}
			return pBet;
		}

		private static boolean endHand(Deck mainDeck, Deck pDeck, Deck dDeck) {
			pDeck.moveAllToDeck(mainDeck);
			dDeck.moveAllToDeck(mainDeck);
			System.out.println("End of Hand");
			return true;
		}

		public static void playAgain(Scanner sc) {
			boolean gameOver = false;
			while (gameOver == false) {
				String again = "Would you like to play again? (1)yes or (2)no";
				System.out.println(again);
				int response = sc.nextInt();
				
				if (response == 1) {
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
