
package com.bptn.bptn_hearts_game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck {

	//creating an ArrayList of cards to hold my cardType and weight
	private List<Card> cards;
	
	//creating a constructor for my deck
	public Deck() {
		cards = new ArrayList<>();
		initializeDeck();
	
	}
	
	//creating a method to initialize my deck
	public void initializeDeck() {

//		getCards().clear();
		String[] cardTypes = {"Hearts", "Diamonds", "Clubs", "Spades"};
		for (String cardType : cardTypes) {
			for (int i = 1; i <= 13; i++) {
				cards.add(new Card(cardType, i));
			}
		}
	}
	
	//creating a method to shuffle my deck
	public void shuffle() {
		Collections.shuffle(cards);
	}

	//creating a method to deal my deck
	public List<Card> deal(int count) {
		List<Card> dealtCards = new ArrayList<>();
	    
		for (int i = 0; i < count; i++) {
			dealtCards.add(cards.remove(0));
		}
		return dealtCards;
	}
	
}
