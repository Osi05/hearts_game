
package com.bptn.bptn_hearts_game;

import java.util.ArrayList;
import java.util.Collections;

public class Deck {

	//creating an ArrayList of cards to hold my cardType and weight
	private ArrayList<Card> cards;
	
	//creating a constructor for my deck
	public Deck() {
		cards = new ArrayList<Card>();
		
		//creating 2 String arrays to hold my cardType and cardWeight
		String[] cardTypes = {"Hearts", "Diamonds", "Clubs", "Spades"};
		String[] cardWeights = {"2","3","4","5","6", "7", "8", "9", "10", "Jack", "Queen", "King", "Ace"};
		
		for (String cardType : cardTypes) {
			for (String cardWeight : cardWeights) {
				cards.add(new Card(cardType, cardWeight));
			}
		}
		
		//using Collections method shuffle() to shuffle the cards
		Collections.shuffle(cards);
	}
	
	
	//creating the deal method, it will take an int parameter 
	public ArrayList<Card> deal(int num) {
		ArrayList<Card> cardsPlayed = new ArrayList<>();
		int cardsToPlay;
		
		//checking if num is more than the cards available
		if (num > cards.size()) {
			cardsToPlay = cards.size();		
			}
		else {
			cardsToPlay = num;
		}
		
		//using traditional for loop to iterate cardsToPlay and adding the card at index
		for (int i = 0; i < cardsToPlay; i++) {
			cardsPlayed.add(cards.get(i));
		}
		
		//using traditional for loop to remove the played card at index
		for (int i = 0; i < cardsToPlay; i++) {
			cards.remove(0);
		}
		
		return cardsPlayed;
 	
	}
	
	
	
	
	
}
