package blackjack;

import java.util.ArrayList;
import java.util.Random;

import org.json.*;

public class Deck {
	
	ArrayList<Card> cards;
	String handWinner;
	boolean doubleDown, split, splitAces, bust, checked, stand, blackjack;
	int numHands;
	
	public Deck() {
		//create new deck
		this.cards = new ArrayList<Card>();
		this.handWinner = "";
		this.doubleDown = false;
		this.split = false;
		this.splitAces = false;
		this.numHands = 1;
		this.bust = false;
		this.checked = false;
		this.stand = false;
		this.blackjack = false;
	}
	
	public void createFullDeck() {
		//create six deck shoe
		for (int i = 0; i < 6; i++) { 
			for(Suit cardSuit : Suit.values()) {
				for(Value cardValue : Value.values()) {
					this.cards.add(new Card(cardSuit, cardValue));
				}
			}
		}
	}
	
	//methods to recall object values
	public String getWinner() {
		return this.handWinner;
	}
	
	public boolean getDoubleDown() {
		return this.doubleDown;
	}
	
	public boolean getSplit() {
		return this.split;
	}		
	
	public boolean getSplitAces() {
		return this.splitAces;
	}
	
	public int getNumHands() {
		return this.numHands;
	}
	
	public boolean getBust() {
		return this.bust;
	}
	
	public boolean getChecked() {
		return this.checked;
	}
	
	public boolean getStand() {
		return this.stand;
	}
	
	public boolean getBlackjack() {
		return this.blackjack;
	}
	
	//methods to set object values
	
	public void setWinner(String hW) {
		this.handWinner = hW;
	}
	
	public void setDoubleDown(boolean dD) {
		this.doubleDown = dD;
	}

	public void setSplit(boolean sp) {
		this.split = sp;
	}

	public void setSplitAces(boolean sA) {
		this.splitAces = sA;
	}
	
	public void setNumHands(int i) {
		this.numHands = i;
	}
	
	public void addHand() {
		this.numHands += 1;
	}

	public void setBust(boolean bu) {
		this.bust = bu;
	}

	public void setChecked(boolean c) {
		this.checked = c;
	}
	
	public void setStand(boolean st) {
		this.stand = st;
	}
	
	public void setBlackjack(boolean bl) {
		this.blackjack = bl;
	}
	
	public void resetOptions() {
		this.handWinner = "";
		this.doubleDown = false;
		//this.split = false;
		//this.splitAces = false;
		this.bust = false;
		this.checked = false;
		this.stand = false;
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
	
	public String toString() {
		String cardListOutput = "";
		for(Card aCard : this.cards) {
			cardListOutput += "\n" + aCard.toString();
		}
		return cardListOutput;
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
	
	public void draw(Deck comingFrom) {
		//get the first thing in the deck
		this.cards.add(comingFrom.getCard(0));
		//Remove card from deck
		comingFrom.removeCard(0);
	}
	
	public int deckSize() {
		return this.cards.size();
	}
	
	public int cardsValue() {
		int totalValue = 0;
		int aces = 0;
		for(Card aCard : this.cards) {
			switch(aCard.getValue()) {
			case TWO: totalValue+=2; break;
			case THREE: totalValue+=3; break;
			case FOUR: totalValue+=4; break;
			case FIVE: totalValue+=5; break;
			case SIX: totalValue+=6; break;
			case SEVEN: totalValue+=7; break;
			case EIGHT: totalValue+=8; break;
			case NINE: totalValue+=9; break;
			case TEN: totalValue+=10; break;
			case JACK: totalValue+=10; break;
			case QUEEN: totalValue+=10; break;
			case KING: totalValue+=10; break;
			case ACE: aces+=1; break;
			default: break;
			}
		}
		for(int i=0; i<aces; i++) {
			if(totalValue>10) {
				totalValue+=1;
			} else {
				totalValue+=11;
			}
		}
		return totalValue;
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
	
	public void moveAllToDeck(Deck moveTo) {
		int thisDeckSize = this.cards.size();
		
		//put cards in moveTo deck
		for(int i = 0; i < thisDeckSize; i++) {
			moveTo.addCard(this.getCard(i));
		}
		for(int i = 0; i < thisDeckSize; i++) {
			this.removeCard(0);
		}
	}
	
	public boolean checkBlackjack() {
		boolean blackjack;
		String x = this.getCard(0).getValue().toString();
		String y = this.getCard(1).getValue().toString();
		if (x.equals("ACE") && (y.equals("TEN") || y.equals("JACK") || y.equals("QUEEN") || y.equals("KING"))) {
			blackjack = true;
		} else if (y.equals("ACE") && (x.equals("TEN") || x.equals("JACK") || x.equals("QUEEN") || x.equals("KING"))) {
			blackjack = true;
		} else {
			blackjack = false;
		}
		return blackjack;
	}
	
	public static void checkHandDouble(Deck pDeck, Deck dDeck) {
		System.out.println("You double down card is " + pDeck.getCard(pDeck.deckSize()-1).toString());
		System.out.println("Your total is " + pDeck.cardsValue());
	}
	
	public static boolean checkSplit(Deck pDeck) {
		boolean split;
		Value x = pDeck.getCard(0).getValue();
		Value y = pDeck.getCard(1).getValue();

		if (x == y || (x.toString() == "TEN" || x.toString() == "JACK" || x.toString() == "QUEEN" || x.toString() == "KING") && (y.toString() == "TEN" || y.toString() == "JACK" || y.toString() == "QUEEN" || y.toString() == "KING")) {
			split = true;
		} else {
			split = false;
		}
		return split;
	}
	
	public void splitDeck(Deck pDeck) {
		this.addCard(pDeck.getCard(1));
		pDeck.removeCard(1);
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
}
