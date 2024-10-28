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
	private int winnerTurnIndex;
	private Scanner scan;
	private List<Card> cardsLastPlayed;

	// creating a constructor for Game
	public Game(ArrayList<Player> players) {
		this.players = players;
		this.deck = new Deck();
		this.scores = new int[players.size()];
		this.roundCount = 1;
		this.heartsBroken = false;
		this.winnerTurnIndex = 0;
		this.scan = new Scanner(System.in);
		this.cardsLastPlayed = new ArrayList<>();
	}

	public void startGame() {
		System.out.println("\nDo you want to see the turotial? (Y/N)");
		String showTutorial = scan.nextLine();
		if (showTutorial.equalsIgnoreCase("Y")) {
			showTutorial();
		}
		
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

	private void showTutorial() {
		System.out.println("\nWelcome to the Hearts game tutorial!");
		System.out.println("In this game, you will:");
		System.out.println("1. Create or log into your account.");
		System.out.println("2. Decide if you want to play alone or with additional human players.");
		System.out.println("3. Set the difficulty level for computer players.");
		System.out.println("4. Start the game with 4 players (you, other human players, and/or computer players).");
		System.out.println("5. Each player gets 13 cards. Your goal is to avoid taking hearts and the queen of spades.");
		System.out.println("6. Follow the leading suit if you have it; if not, play any other card.");
		System.out.println("7. Hearts cannot be played until they are broken (a heart has been played in a previous turn).");
		System.out.println("8. The player who wins the trick (highest card of the leading suit) takes all the cards from that trick.");
		System.out.println("9. Hearts count as 1 point each, and the queen of spades counts as 13 points.");
		System.out.println("10. The player with the fewest points at the end of the game wins.");
		System.out.println("11. You can undo your last move if necessary.");
		System.out.println("Let's start the game and have fun!\n");
	}

	// method to playRound
	public void playRound() {
		dealCards();
		passCards();

		for (Player player : players) {
			player.clearTurns();
		}

		playTurns();
	}

	// method for dealCards
	private void dealCards() {
		System.out.println("---- Dealing Cards ---");
		deck.shuffle();
		List<Card> allCards = deck.deal(52);
		System.out.println("All cards dealt: " + allCards.size());
		System.out.println("Number of players: " + players.size());

		// Distributing cards
		for (int i = 0; i < 13; i++) {
			for (Player player : players) {
				player.addCard(allCards.remove(0));
			}
		}
		System.out.println("Your cards:");
		displayCards(players.get(0).getCards()); // Assuming the first player is the main player
	}

	// method for displayCards
	private void displayCards(List<Card> cards) {
		for (int i = 0; i < cards.size(); i++) {
			System.out.println((i + 1) + ": " + cards.get(i));
		}
	}

	// method for passCards
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

		// for computerPlayer to pass cards
		for (int i = 1; i < players.size(); i++) {
			Player computerPlayer = players.get(i);
			List<Card> cpCardsToPass = new ArrayList<>();
			for (int m = 0; m < 3; m++) {
				cpCardsToPass.add(computerPlayer.getCards().remove(0));
			}
			System.out.println(computerPlayer.getUsername() + " passes cards: " + cpCardsToPass);
		}

		System.out.println("\nCards passed successfully.");

		// adding passed cards ro main player
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
		List<Card> turnCards = new ArrayList<>();
		for (int i = 0; i < players.size(); i++) {
			int playerIndex = (winnerTurnIndex + i) % players.size();
			Card playedCard;
			if (turnCards.isEmpty()) {
				if (playerIndex == 0) {
					playedCard = playerPlay(null);
				} else {
					playedCard = cpPlay(players.get(playerIndex), null);
				}
			} else {
				String leadingCardType = turnCards.get(0).getCardType();
				if (playerIndex == 0) {
					playedCard = playerPlay(leadingCardType);
				} else {
					playedCard = cpPlay(players.get(playerIndex), leadingCardType);
				}
			}
			turnCards.add(playedCard);
		}
		winnerTurnIndex = determineTurnWinner(turnCards);
		System.out.println("\nPlayer " + players.get(winnerTurnIndex).getUsername() + " wins this turn with "
				+ turnCards.get(winnerTurnIndex));
		if (!heartsBroken) {
			heartsBroken = turnCards.stream().anyMatch(card -> card.getCardType().equals("Hearts"));
		}

		// Assign the turn cards to the winning player
		players.get(winnerTurnIndex).addTurn(turnCards);

		cardsLastPlayed = new ArrayList<>(turnCards);
		displayPlayedCards(turnCards);
		System.out.println("\nPress 'u' to undo your last move or any other key to continue: ");
		String undo = scan.nextLine();
		if (undo.equalsIgnoreCase("u")) {
			undoLastMove();
		}
	}

	private void undoLastMove() {

		if (!cardsLastPlayed.isEmpty()) {

			Card lastCard = cardsLastPlayed.get(0);
			players.get(0).addCard(lastCard);
			System.out.println("Undid last move, card " + lastCard + " added back to your hand");

			// modify other players
			System.out.println("Other players notified that the last move was undone");

			// remove the card from last played cards
			cardsLastPlayed.remove(0);
		} else {
			System.out.println("No moves to undo");
		}

	}

	// method for displayPlayedCards
	private void displayPlayedCards(List<Card> turnCards) {
		System.out.println("\nCards played this turn:");
		for (int i = 0; i < turnCards.size(); i++) {
			System.out.println(players.get(i).getUsername() + " played: " + turnCards.get(i));
		}
	}

	// method for playerPlay
	private Card playerPlay(String leadingCardType) {
		System.out.println("\nYour turn to play. Your cards:");
		displayCards(players.get(0).getCards());
		Card playedCard;
		while (true) {
			System.out.print("Choose a card to play (1 to " + players.get(0).getCards().size() + "): ");
			int choice = Integer.parseInt(scan.nextLine()) - 1;
			playedCard = players.get(0).getCards().get(choice);

			// Ensure player follows leading card type if they have it
			if (leadingCardType == null || playedCard.getCardType().equals(leadingCardType)
					|| !hasCardType(players.get(0).getCards(), leadingCardType)) {
				// Ensure hearts are not played unless broken
				if (!heartsBroken && playedCard.getCardType().equals("Hearts")) {
					if (hasOtherCardType(players.get(0).getCards(), "Hearts")) {
						System.out.println("Hearts cannot be played until they are broken.");
						continue;
					}
				}
				players.get(0).getCards().remove(choice);
				System.out.println("You played: " + playedCard);
				return playedCard;
			} else {
				System.out.println("You must play a card of the leading type if you have one");
			}
		}
	}

	private boolean hasCardType(List<Card> cards, String cardType) {

		return cards.stream().anyMatch(card -> card.getCardType().equals(cardType));
	}

	private boolean hasOtherCardType(List<Card> cards, String excludedSuit) {
		return cards.stream().anyMatch(card -> !card.getCardType().equals(excludedSuit));
	}

	private Card cpPlay(Player cpPlayer, String leadingCardType) {
		Card playedCard;
		String difficulty = cpPlayer.getDifficulty();

		switch (difficulty) {
		case "easy":
			// Randomly play any card
			playedCard = cpPlayer.getCards().get(0);
			break;
		case "hard":
			// Play a card following basic strategy
			playedCard = cpPlayer.getCards().stream().filter(card -> card.getCardType().equals(leadingCardType))
					.findFirst().orElse(cpPlayer.getCards().get(0));
			break;
		case "expert":
			// Play a card following advanced strategy
			playedCard = cpPlayer.getCards().stream().filter(card -> card.getCardType().equals(leadingCardType))
					.max((c1, c2) -> Integer.compare(c1.getCardWeight(), c2.getCardWeight()))
					.orElse(cpPlayer.getCards().get(0));
			break;
		default:
			throw new IllegalStateException("Unexpected difficulty level: " + difficulty);
		}
		cpPlayer.removeCard(playedCard);
		System.out.println(cpPlayer.getUsername() + " played: " + playedCard);
		return playedCard;
	}

	private int determineTurnWinner(List<Card> turnCards) {
		Card winningCard = turnCards.get(0);
		int winnerIndex = 0;

		for (int i = 1; i < turnCards.size(); i++) {
			Card card = turnCards.get(i);
			if (card.getCardType().equals(winningCard.getCardType())
					&& card.getCardWeight() > winningCard.getCardWeight()) {
				winningCard = card;
				winnerIndex = i;
			}
		}
		return (winnerTurnIndex + winnerIndex) % players.size();
	}

	// method for updateScores
	private void updateScores() {
		for (Player player : players) {
			int roundScore = 0;
			for (Card card : player.getTurns()) {
				if (card.getCardType().equals("Hearts")) {
					roundScore++;
				} else if (card.getCardType().equals("Spades") && card.getCardWeight() == 12) {
					roundScore += 13;
				}
			}
			scores[players.indexOf(player)] += roundScore;
		}

		System.out.println("\nScores updated: ");
		for (int i = 0; i < players.size(); i++) {
			System.out.println(players.get(i).getUsername() + ": " + scores[i]);
		}
	}

	private boolean checkForWinner() {
		for (int score : scores) {
			if (score >= 100) {
				System.out.println("\nWe have a winner!");
				return true;
			}
		}
		return false;
	}

	// method to displayFinalScores
	private void displayFinalScores() {
		System.out.println("\n--- Final Scores ---");
		for (int i = 0; i < players.size(); i++) {
			System.out.println(players.get(i).getUsername() + ": " + scores[i]);
		}
	}

}