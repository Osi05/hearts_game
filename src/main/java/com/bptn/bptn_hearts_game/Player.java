package com.bptn.bptn_hearts_game;

import java.util.ArrayList;
import java.util.List;

public class Player {

	//creating the instance variables
	private String username;
	private ArrayList<Card> cards;
	private List<Card> turns;

	
	//creating constructor for Player with 1 parameter username
	public Player(String username) {
		this.username = username;
		this.cards = new ArrayList<>();
		this.turns = new ArrayList<>();
	}
	
	//creating getter method for username
	public String getUsername() {
		return username;
	}
	
	//creating getter method for cards
	public List<Card> getCards() {
		return cards;
	}
	
	//creating method for adding card
	public void addCard(Card card) {
		cards.add(card);
	}
	
	//creating method for removing card
	public void removeCard(Card card) {
		cards.remove(card);
	}

	public List<Card> getTurns() {
		return turns;
	}
	
	public void clearTurns() {
		turns.clear();
	}
	
	public void addTurn(List<Card> turn) {
		turns.addAll(turn);
	}

}
