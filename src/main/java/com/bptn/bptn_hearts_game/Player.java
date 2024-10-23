package com.bptn.bptn_hearts_game;

import java.util.ArrayList;
import java.util.Scanner;

public class Player {

	//creating the instance variables
	private String name;
	private ArrayList<Card> hand;
	private int score;
	private boolean user;
	
	//creating constructor for Player with 2 parameters String name and boolean users
	public Player(String name, boolean user) {
		this.name = name;
		this.score = 0;
		this.user = user;
		this.hand = new ArrayList<>();
	}

	
	
	//using eclipse for getters and setters
	public ArrayList<Card> getHand() {
		return hand;
	}

	public void setHand(ArrayList<Card> hand) {
		this.hand = hand;
	}

	public String getName() {
		return name;
	}

	public int getScore() {
		return score;
	}
	
	//created method to addPoints 
	public void addPoints(int points) {
		this.score += points;
	}
	
	public void passCards(ArrayList<Card> cardsToPass) {
		hand.addAll(cardsToPass);
	}
	
	//method to playCard
	public Card playCard(String leadingCardType) {
		if (user) {
			return humanPlayer(leadingCardType);
		}
		else {
			return computerPlayer(leadingCardType);
		}
	}
	
	
	//using eclipse to create methods for human and computer players
	private Card humanPlayer(String leadingCardType) {
		Scanner scan = new Scanner(System.in); 
		System.out.println( name + ", it's your turn! Here are your cards:");
		displayHand();
			
		int choice;
		do {
			System.out.println("Choose a card to play (1 to " + hand.size() + "): ");
			choice = scan.nextInt() - 1;
				
			if (choice < 0 || choice >= hand.size() || !validPlay(hand.get(choice), leadingCardType)) {
				
				System.out.println("Ivalid choice. Please select a valid card.");
				}
			else {
				Card cardPicked = hand.remove(choice);
				System.out.println("You played: " + cardPicked);
				scan.close();
				return cardPicked;
				}
			}
			while (true);
	}
	
	
	private Card computerPlayer(String leadingCardType) {
		
		//checking for cards of the cardType in play
		ArrayList<Card> playableCards = new ArrayList<Card>();
		for (Card card : hand) {
			if (card.getCardType().equals(leadingCardType)) {
				playableCards.add(card);
			}
		}
		
		//if statement for player to play cards from playableCards
		if (!playableCards.isEmpty()) {
			Card pickedCard = playableCards.get(0);
			hand.remove(pickedCard);
			System.out.println(name + " played: " + pickedCard);
			return pickedCard;
		}
		else {
			Card pickedCard = hand.remove(0);
			System.out.println(name + " played: " + pickedCard);
			return pickedCard;
		}
	}
	
	
	private boolean validPlay(Card card, String leadingCardType) {

		//check if there is a leadingCardTyper
			if (leadingCardType != null && !leadingCardType.isEmpty()) {
				
				boolean allowedCards = false;
				
				for (Card allowedCard : hand) {
					
					if (allowedCard.getCardType().equals(leadingCardType)) {
						
						allowedCards = true;
						break;
						}
					}
				
				if (allowedCards) {
					return card.getCardType().equals(leadingCardType);
					}
				}
			return true;
			}


	private void displayHand() {
		//displaying hand
		for (int i = 0; i < hand.size(); i++) {
			System.out.println((i + 1) + ": " + hand.get(i));
		}
		
	}


	
	
	
	
	
	
}
