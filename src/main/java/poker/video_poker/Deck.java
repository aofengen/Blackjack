package poker.video_poker;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONObject;

public class Deck {
	
	ArrayList<Card> cards;
	String handValue;
	
	public Deck() {
		this.cards = new ArrayList<Card>();
		this.handValue = "";
	}
	
	public Deck(Deck eDeck) {
		this.cards = eDeck.cards;
		this.handValue = "";
	}
	
	public void createDeck() {
		for(Suit cardSuit : Suit.values()) {
			for(Value cardValue : Value.values()) {
				this.cards.add(new Card(cardSuit, cardValue));
			}
		}
		this.shuffle();
	}
	
	public String getWinner() {
		return this.handValue;
	}
	
	public void setWinner(String hV) {
		this.handValue = hV;
	}
	
	public void removeCard(int i) {
		this.cards.remove(i);
	}
	
	public Card getCard(int i) {
		return this.cards.get(i);
	}
	
	public void addCard(Card addCard) {
		this.cards.add(addCard);
	}
	
	public void draw(Deck comingFrom, int cards) {
		for (int i = 0; i < cards; i++) {
			//get the first thing in the deck
			this.cards.add(comingFrom.getCard(0));
			//Remove card from deck
			comingFrom.removeCard(0);
		}
	}
	
//	public void testFlush() {
//		this.cards.add(new Card(Suit.SPADE, Value.NINE));
//		this.cards.add(new Card(Suit.SPADE, Value.KING));
//		this.cards.add(new Card(Suit.SPADE, Value.QUEEN));
//		this.cards.add(new Card(Suit.SPADE, Value.JACK));
//		this.cards.add(new Card(Suit.SPADE, Value.TEN));
//	}
//	
//	public void testPair() {
//		this.cards.add(new Card(Suit.SPADE, Value.JACK));
//		this.cards.add(new Card(Suit.CLUB, Value.SEVEN));
//		this.cards.add(new Card(Suit.DIAMOND, Value.TEN));
//		this.cards.add(new Card(Suit.HEART, Value.JACK));
//		this.cards.add(new Card(Suit.HEART, Value.TEN));
//	}
//	
//	public void testStraight() {
//		this.cards.add(new Card(Suit.HEART, Value.QUEEN));
//		this.cards.add(new Card(Suit.HEART, Value.KING));
//		this.cards.add(new Card(Suit.HEART, Value.JACK));
//		this.cards.add(new Card(Suit.HEART, Value.ACE));
//		this.cards.add(new Card(Suit.HEART, Value.TEN));
//	}
	
	public Card draw(Deck comingFrom) {
		//get the first thing in the deck
		Card newCard = comingFrom.getCard(0);
		//Remove card from deck
		comingFrom.removeCard(0);
		
		return newCard;
	}
	
	public int deckSize() {
		return this.cards.size();
	}
	
	public void shuffle() {
		ArrayList<Card> tmpDeck = new ArrayList<Card>();
		//Create instance of Random object
		Random random = new Random();
		int randomCardIndex = 0;
		int originalSize = this.cards.size();
		
		for(int i = 0; i < originalSize; i++) {
			//Generate random index
			randomCardIndex = random.nextInt((this.cards.size()-1-0)+1)+0;
			tmpDeck.add(this.cards.get(randomCardIndex));
			//remove from the original deck
			this.cards.remove(randomCardIndex);
		}
		//Put cards back into original deck
		this.cards = tmpDeck;
	}
	
	public void resetOptions() {
		this.handValue = "";
	}
	
	@Override
	public String toString() {
		String cardListOutput = "";
		for(Card aCard : this.cards) {
			cardListOutput += "\n" + aCard.toString();
		}
		return cardListOutput;
	}

	public void keepCards(Scanner sc, Deck mainDeck) {
		Deck removeDeck = new Deck();
		Deck receiveDeck = new Deck();
		int i = 0;
		while(i < this.cards.size()) {
			System.out.println("");
			System.out.println("Would you like to keep " + this.getCard(i) + "? (1) yes or (2) no");
			while(!sc.hasNextInt()) {
				System.out.println("Invalid Response.");
				System.out.println("Would you like to keep " + this.getCard(i) + "? (1) yes or (2) no");
				sc.nextLine();
			}
			int choice = sc.nextInt();
			switch (choice) {
				case 1:
					i++;
					break;
				case 2:
					removeDeck.cards.add(this.getCard(i));
					this.cards.set(i, this.draw(mainDeck));
					receiveDeck.addCard(this.getCard(i));
					i++;
					break;
				default:
					System.out.println("Invalid Selection.");
					break;
			}
		}
		if (removeDeck.deckSize() > 0) {
			System.out.println("You traded in: " + removeDeck.cards);
			System.out.println("");
			System.out.println("You received: " + receiveDeck.cards);
			System.out.println("");
		} else {
			System.out.println("You didn't trade in any cards.");
			System.out.println("");
		}
	}
	
	public String cardValue(int i) {
		String x = this.getCard(i).getValue().toString();
		return x;
	}
	
	public String cardSuit(int i) {
		String x = this.getCard(i).getSuit().toString();
		return x;
	}
	
	public JSONArray deckToJSON() throws Exception {
		JSONArray array = new JSONArray();
    	while(this.deckSize() > 0) {
    		JSONObject childObject = new JSONObject();
    		childObject.put("suit", this.cardSuit(0));
    		childObject.put("value", this.cardValue(0));
    		this.removeCard(0);
    		array.put(childObject);
    	}
		return array;
	}
}
