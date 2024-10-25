package com.bptn.bptn_hearts_game;

import java.util.ArrayList;
import java.util.Scanner;

public class Player {

	//creating the instance variables
	private String name;
	private ArrayList<Card> hand;
	private int score;
	public boolean user;
	private int difficulty;
	
	//creating constructor for Player with 2 parameters String name and boolean users
	public Player(String name, boolean user) {
		this.name = name;
		this.score = 0;
		this.user = user;
		this.hand = new ArrayList<>();
		this.difficulty = difficulty;
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
				
				System.out.println("Invalid choice. Please select a valid card.");
				}
			else {
				Card cardPicked = hand.remove(choice);
				System.out.println("You played: " + cardPicked);
				return cardPicked;
				}
			}
			while (true);
	}
	
	
	private Card computerPlayer(String leadingCardType) {
		
		//checking for cards of the cardType in play
//		ArrayList<Card> playableCards = new ArrayList<Card>();
//		for (Card card : hand) {
//			if (card.getCardType().equals(leadingCardType)) {
//				playableCards.add(card);
//			}
//		}
//		
//		//if statement for player to play cards from playableCards
//		if (!playableCards.isEmpty()) {
//			Card pickedCard = playableCards.get(0);
//			hand.remove(pickedCard);
//			System.out.println(name + " played: " + pickedCard);
//			return pickedCard;
//		}
//		else {
//			Card pickedCard = hand.remove(0);
//			System.out.println(name + " played: " + pickedCard);
//			return pickedCard;
//		}
		
		switch(difficulty) {
		case 1: return easyDifficulty(leadingCardType);
		case 2: return hardDifficulty(leadingCardType);
		case 3: return expertDifficulty(leadingCardType);
		default: return hand.remove(0);
		}
	}
	
	
	private Card easyDifficulty(String leadingCardType) {

		return hand.remove(0);
	}



	private Card hardDifficulty(String leadingCardType) {
	
		//checking for cards of the cardType in play
		ArrayList<Card> playableCards = new ArrayList<Card>();
		for (Card card : hand) {
			if (card.getCardType().equals(leadingCardType)) {
				playableCards.add(card);
			}
		}
		
		//playing first card that following current card
		if (!playableCards.isEmpty()) {
			return playableCards.get(0);
		}
		else {
			return hand.remove(0);
		}
	}



	private Card expertDifficulty(String leadingCardType) {
		
		ArrayList<Card> playableCards = new ArrayList<Card>();
		for (Card card : hand) {
			if (card.getCardType().equals(leadingCardType)) {
				playableCards.add(card);
			}
		}
		
		//playing lowest card of card in play
		if (!playableCards.isEmpty()) {
			Card pickedCard = playableCards.get(0);
			for (Card card : playableCards) {
				if (card.getCardValue() < pickedCard.getCardValue()) {
					pickedCard = card;
				}
			}
			hand.remove(pickedCard);
			return pickedCard;
		}
		else {
			//play highest card in hand
			Card pickedCard = hand.get(0);
			for (Card card : hand) {
				if (card.getCardValue() < pickedCard.getCardValue()) {
					pickedCard = card;
				}
			}
			hand.remove(pickedCard);
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


	public void displayHand() {
		//displaying hand
		for (int i = 0; i < hand.size(); i++) {
			System.out.println((i + 1) + ": " + hand.get(i));
		}
		
	}



	 boolean hasCardType(String leadingCard) {
		for (Card card : hand) {
			if (card.getCardType().equals(leadingCard)) {
				return true;
			}
		}
		return false;
	}


	
	
	
	
	
	
}
