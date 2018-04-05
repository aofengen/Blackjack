package Blackjack;

import java.util.ArrayList;
import java.util.Random;

public class Deck {
	
	private ArrayList<Card> cards;
	String handWinner;
	boolean doubleDown, split, splitAces, bust, checked, stand, blackjack;
	
	public Deck() {
		//create new deck
		this.cards = new ArrayList<Card>();
		this.handWinner = "";
		this.doubleDown = false;
		this.split = false;
		this.splitAces = false;
		this.bust = false;
		this.checked = false;
		this.stand = false;
		this.blackjack = false;
	}
	
	public void createFullDeck() {
		//fill deck with cards
		for(Suit cardSuit : Suit.values()) {
			for(Value cardValue : Value.values()) {
				this.cards.add(new Card(cardSuit, cardValue));
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
}
