
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
		initializedDeck();
	}
	
	//creating a method to initialize my deck
	private void initializedDeck() {
	
		String[] suits = {"Hearts", "Diamonds", "Clubs", "Spades"};
		for (String suit : suits) {
			for (int value = 1; value <= 13; value++) {
				cards.add(new Card(suit, value));
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
