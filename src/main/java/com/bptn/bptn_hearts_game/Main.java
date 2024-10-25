package com.bptn.bptn_hearts_game;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
	
	public static void main(String[] args) {

		Scanner scan = new Scanner(System.in);
		boolean playAgain;
		
		do {
			//showing tutorial
			System.out.println("Welcome to the Hearts Card Game!");
			System.out.println("Here are the basic rules: ");
			System.out.println("1. Each player is dealt 13 cards at the start pf the game.");
			System.out.println("2. Players take turns playing one card at a time.");
			System.out.println("3. The player who plays the highest card of the card in play takes the points in that round.");
			System.out.println("4. The Queen of Spades is worth 13 points.");
			System.out.println("5. The objective is to avoid gaining points. The player with the fewest points at the end of the game wins.");
			System.out.println("Please Enter to continue....");
			String startGame = scan.nextLine();
			
			//choosing difficulty 
			System.out.println("Choose difficulty : (1 - Easy, 2 - Hard, 3 - Expert): ");
			int difficulty = scan.nextInt();
			scan.nextLine();
			
			//logic for human multiplayers
			ArrayList<Player> players = new ArrayList<Player>();
			int userCount;
			
			System.out.println("How many human players (1-4)? ");
			userCount = scan.nextInt();
			scan.nextLine();
			
			
			for (int i = 1; i <= userCount; i++) {
				System.out.println("Enter name for player " + i + ": ");
				String playerName = scan.nextLine();
				players.add(new Player(playerName, true, 0));
			}
			
			//use computerPlayersn  if players less than 4
			int computerCount = 4 - userCount;
			for (int i = 1; i <= computerCount; i++) {
				players.add(new Player("computerPlayer " + i, false, difficulty));
			}
			
			//starting actual game
			Game game = new Game(players);
			game.startGame();
			
			//ask if player wants to play again
			System.out.println("Game over! Do you want to play again? (Y/N)");
			String userInput = scan.nextLine();			
			
			
			playAgain = userInput.equals("Y");
		}
		while (playAgain);
		System.out.println("Thanks for playing!");
		scan.close();
		
	}
}
