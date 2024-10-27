package com.bptn.bptn_hearts_game;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class Game {

	// instance variables
	private ArrayList<Player> players;
	private Deck deck;
	private int[] scores;
	private int roundCount;
	private boolean heartsBroken;
	private int WinnerIndexTurn;
	private Scanner scan;
	private List<Card> cardLastPlayed;

	
	// creating a constructor for Game
	public Game(ArrayList<Player> players) {
		this.players = players;
		this.deck = new Deck();
		this.scores = new int[players.size()];
		this.roundCount = 1;
		this.heartsBroken = false;
		this.WinnerIndexTurn = 0;
		this.scan = new Scanner(System.in);
		this.cardLastPlayed = new ArrayList<>();
	}
	
	
	public void startGame() {
		while (true) {
			System.out.println("------ Starting Round " + roundCount + " -----");
			playRound();
			roundCount++;
			if (checkForWinner()) {
				break;
			}
		}
		displayFinalScores();
	}
	
	
	// method to playRound
	public void playRound() {
//		deck.shuffle();
		dealCards();
		passCards();
		playTurns();
	}
	
	//method for dealCards
	private void dealCards() {
		System.out.println("---- Dealing Cards ---");
		deck.shuffle();
		List<Card> allCards = deck.deal(52);
		
		for (int i = 0; i < players.size(); i++) {
			players.get(i).addCard(allCards.remove(0));
		}
		
		System.out.println("Your cards:");
		displayCards(players.get(0).getCards()); // Assuming the first player is the main player
	}
	
	//method for passCards
	private void displayCards(List<Card> cards) {
		for (int i = 0; i < cards.size(); i++) {
			System.out.println((i + 1) + ": " + cards.get(i));
		}
	}
	
	//method for passCards
	private void passCards() {
		System.out.println("Passing cards...");
		List<Card> cardsToPass = new ArrayList<>();
		
		for (int i = 0; i < 3; i++) {
			System.out.print("\nChoose a card to pass (1 to " + players.get(0).getCards().size() + "): ");
			int choice = Integer.parseInt(scan.nextLine()) - 1;
			cardsToPass.add(players.get(0).getCards().remove(choice));
			System.out.println("\nUpdated hand:");
			displayCards(players.get(0).getCards());
		}
		
		//for computerPlayer to pass cards
		for (int i = 1; i <players.size(); i++) {
			Player computerPlayer = players.get(i);
			List<Card> cpCardsToPass = new ArrayList<>();
			for (int m = 0; m < 3; m++) {
				cpCardsToPass.add(computerPlayer.getCards().remove(0));
			}
			System.out.println(computerPlayer.getUsername() + " passes cards: " + cpCardsToPass);
		}
		
		System.out.println("\nCards passed successfully.");
		
		//adding passed cards ro main player
		players.get(0).getCards().addAll(cardsToPass);
		System.out.println("Your remaining cards:");
		displayCards(players.get(0).getCards());
	}

	private void playTurns() {
		System.out.println("--- Playing Turns ---");
		for (int i = 1; i <= 13; i++) {
			System.out.println("--- Turn " + i + " ---");
			playTurn();
			updateScores();
		} 
	}
	
	private void playTurn() {
		// TODO Auto-generated method stub
		
	}


	private boolean checkForWinner() {
		for (int score : scores) {
			if (score >= 100) {
				System.out.println("We have a winner!");
				return true;
			}
		}
		return false;
	}
	
}