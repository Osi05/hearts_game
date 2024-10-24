package com.bptn.bptn_hearts_game;

import java.util.ArrayList;
import java.util.Scanner;

public class Game {

	// instance variables
	private ArrayList<Player> players;
	private Deck deck;
	private int currentRound;
	private int currentTurn;

	// creating a constructor for Game
	public Game(ArrayList<Player> players) {
		this.players = players;
		this.deck = deck;
		this.currentRound = 1;
		this.currentTurn = 1;
	}

	// method to startGame
	public void startGame() {
		try {
			while (true) {
				// using for each loop to deal 13 cards
				deck = new Deck();
				for (Player player : players) {
					player.setHand(deck.deal(13));
				}

				// using traditional for loop pass cards at the beginner of round
				for (int i = 0; i < players.size(); i++) {
					Player player = players.get(i);
					if (player.user) {
						Scanner scan = new Scanner(System.in);
						System.out.println("\n--- Round " + currentRound + " ---");
						System.out.println(player.getName() + ", choose 3 cards to pass:");
						ArrayList<Card> cardsToPass = new ArrayList<Card>();

						for (int m = 0; m < 3; m++) {
							int choice;
							while (true) {
								player.displayHand();
								System.out.println(
										"Choose card " + (m + 1) + " to pass (1 to " + player.getHand().size() + "): ");
								choice = scan.nextInt();
								if (choice < 1 || choice > player.getHand().size()) {
									System.out.println("Invalid choice. Please select a valid card.");
								} else {
									cardsToPass.add(player.getHand().remove(choice - 1));
									break;
								}
							}
						}

						// pass cards to next player
						int nextPlayer = (i + 1) % players.size();
						players.get(nextPlayer).passCards(cardsToPass);
					} else {
						// randomly select 3 cards fore computer player
						ArrayList<Card> cardsToPass = new ArrayList<Card>();

						for (int k = 0; k < 3; k++) {
							int randomPick = (int) (Math.random() * player.getHand().size());
							cardsToPass.add(player.getHand().remove(randomPick));
						}

						// pass cards to next player
						int nextPlayer = (i + 1) % players.size();
						players.get(nextPlayer).passCards(cardsToPass);

					}
				}

				// calling playRound method
				playRound();
				
				
				//checking to see if game is over 
				if (isGameOver()) {
					break;
				}
				currentRound++;
			}
		} catch (Exception e) {
			System.out.println("An Exception occurred: " + e.getMessage());
			e.printStackTrace();
		}
		
		//declaring winner
		Player winner = players.get(0);
		for (Player player : players) {
			if (player.getScore() > winner.getScore()) {
				winner = player;
			}
		}
		System.out.println("The winner is " + winner.getName() + " with " + winner.getScore() + " points!");
	}

	
	//method to play round
	private void playRound() {
		String leadingCardType = null;
		ArrayList<Card> turn = new ArrayList<Card>();
		int startPlayerIndex = 0;
		
		for (int i = 0; i < 13; i++) {
			System.out.println("\n--- Round " + currentRound + ", Turn " + (i + 1) + " ---");
			turn.clear();
			
			for (int m = 0; m < players.size(); m++) {
				int playerIndex = (startPlayerIndex + 1) % players.size();
				Player currentPlayer = players.get(playerIndex);
				Card playedCard = null;
				
				while (playedCard == null) {
					try {
						playedCard = currentPlayer.playCard(leadingCardType);
						//checking if the card is same as leading card
						if (leadingCardType != null && !playedCard.getCardType().equals(leadingCardType) && currentPlayer.hasCardType(leadingCardType)) {
							throw new IllegalArgumentException("Invalid card played! You must follow leading card: " + leadingCardType);
						}
					}
					catch (IllegalArgumentException e) {
						System.out.println(e.getMessage());
						playedCard = null; //keep prompting player
					}
				}
				
				turn.add(playedCard);
				
				//first card will determine the cards to play for that round
				if (m == 0) {
					leadingCardType = playedCard.getCardType(); 
				}
			}
			
			//determine player with the highest points per turn 
			Player playerHigh = highestPoints(turn, startPlayerIndex, leadingCardType);
			int turnPoints = calculatePoints(turn);
			
			//award all points to player with the highest card weight
			playerHigh.addPoints(turnPoints);
			
			System.out.println(playerHigh.getName() + " receives " + turnPoints + " points!");
			
			//displaying scores
			for (Player player : players) {
				System.out.println(player.getName() + ": " + player.getScore() + " points");
			}
			
			//player starts next turn
			startPlayerIndex = players.indexOf(playerHigh);
		}			
	}
	
	
	//method to check if game is over 
	private boolean isGameOver() {
		for (Player player : players) {
			if (player.getScore() >= 100) {
				return true;
			}
		}
		return false;
	}
	
	
	
	//method for highestPoints
	private Player highestPoints(ArrayList<Card> turn, int startPlayerIndex, String leadingCardType) {
		Card highestCard = null;
		int playerIndex = -1;
		
		for (int i = 0; i < turn.size(); i++) {
			Card currentCard = turn.get(i);
			
			//checking if current card is the carding cardType
			if (currentCard.getCardType().equals(leadingCardType)) {
				if (highestCard == null || currentCard.getCardValue() > highestCard.getCardValue()) {
					highestCard = currentCard;
					
					//updating the index of player with the highest card
					playerIndex = (startPlayerIndex + i) % players.size();  
				}
			}
		}
		//returning the player who played highest card
		return players.get(playerIndex);
	}
	
	
	//calculate points method
	private int calculatePoints(ArrayList<Card> turn) {
		int points = 0;
		for (Card card : turn) {
			if (card.getCardType().equals("Hearts")) {
				points += 1;
			}
			if (card.getCardWeight().equals("Queen") && card.getCardType().equals("Spades")) {
				points += 13;
			}
		}
		return points;
	}
	
}
