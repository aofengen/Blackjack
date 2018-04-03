package Blackjack;

import java.util.ArrayList;
import java.util.Random;

public class Deck {
	
	private ArrayList<Card> cards;
	
	public Deck() {
		//create new deck
		this.cards = new ArrayList<Card>();
	}
	
	public void createFullDeck() {
		//fill deck with cards
		for(Suit cardSuit : Suit.values()) {
			for(Value cardValue : Value.values()) {
				this.cards.add(new Card(cardSuit, cardValue));
			}
		}
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
	
	public static boolean checkBlackjack(Deck deck) {
		boolean blackjack;
		String x = deck.getCard(0).getValue().toString();
		String y = deck.getCard(1).getValue().toString();
		if (x.equals("ACE") && (y.equals("JACK") || y.equals("QUEEN") || y.equals("KING"))) {
			blackjack = true;
		} else if (y.equals("ACE") && (x.equals("JACK") || x.equals("QUEEN") || x.equals("KING"))) {
			blackjack = true;
		} else {
			blackjack = false;
		}
		return blackjack;
	}

}
