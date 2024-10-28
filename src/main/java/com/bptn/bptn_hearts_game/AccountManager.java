package com.bptn.bptn_hearts_game;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.*;

public class AccountManager {
	
	private static final String ACCOUNT_FILE = "accounts.txt";
	private static Scanner scan = new Scanner(System.in);
    private static List<Player> players = new ArrayList<>();

	
	
	public static void loginOrCreateAccount() {
		
		System.out.println("Welcome to Hearts!");
		
		//login or create acct for main player
		Player mainPlayer = loginOrCreateSingleAccount();
		players.add(mainPlayer);
		
		//multiplayer instance, ask if other human players
		System.out.println("Do you want to add more human players? (Y/N): ");
		String addPlayers = scan.nextLine();
		
		
		if (addPlayers.equalsIgnoreCase("Y")) {
			int addedPlayers = getNumberOfPlayers();
			for (int i = 1; i <= addedPlayers; i++) {
				System.out.println("Creating account for Player " + (i + 1) + ":");
				Player newPlayer = loginOrCreateSingleAccount();
				players.add(newPlayer);
			}
		}
		
		System.out.println("Players ready: ");
		for (Player player : players) {
			System.out.println(player.getUsername());
		}
		
		//add computer players
		addComputerPlayers();
		
	}
	
	//method to addComputerPlayers
	private static void addComputerPlayers() {
		int humanPlayers = players.size();
		int computerPlayersAdded = 0;
		
		if (humanPlayers == 1) {
			computerPlayersAdded = 3;
			} 
		else if (humanPlayers == 2) {
			computerPlayersAdded = 2;
			}
		else if (humanPlayers == 1) {
			computerPlayersAdded = 1; 
			}
		
		for (int i = 1; i <= computerPlayersAdded; i++) {
			Player computerPlayer = new Player("computerPlayer" + i);
			players.add(computerPlayer);
		}
		
		System.out.println("Players after adding computerPlayers: ");
		for (Player player : players) {
			System.out.println(player.getUsername());
		}
	}
	
	private static Player loginOrCreateSingleAccount() {
		while(true) {
			System.out.println("Do you want to \n (1) Login or \n (2) Create Account? ");
			String choice = scan.nextLine();
			if (choice.equals("1")) {
				Player player = login();
				if (player != null) {
					return player;
				}
			}
			else if (choice.equals("2")) {
				return createAccount();
			}
			else {
				System.out.println("Invalid choice. Please choose either 1 or 2");
			}
			
		}
	}
	
	
	private static int getNumberOfPlayers() {
		System.out.println("How many additional human players? ");
		return Integer.parseInt(scan.nextLine());
	}
	
	private static Player login() {
		System.out.println("Enter username: ");
		String username = scan.nextLine();
		System.out.println("Enter password: ");
		String password = scan.nextLine();
		
		try(BufferedReader readFile = new BufferedReader(new FileReader(ACCOUNT_FILE))) {
			String line;
			while((line = readFile.readLine()) != null) {
				String[] accountDetails = line.split(",");
				if(accountDetails[0].equals(username) && accountDetails[1].equals(password)) {
					System.out.println("\nLogin successful! \nWelcome " + username + "!");
					return new Player(username);
				}
				
			}
			
		}
		catch (IOException e) {
			System.out.println("\nError reading accounts file");
		}
		
		System.out.println("\nWrong username or password. Would you like to create an account? (Y/N)");
		if (scan.nextLine().equalsIgnoreCase("Y")) {
			return createAccount();
		}
		
		return null;
	}
	
	private static Player createAccount() {
		System.out.println("Enter a username: ");
		String username = scan.nextLine();
		System.out.println("Enter a password: ");
		String password = scan.nextLine();
		
		
		try (BufferedWriter writeFile = new BufferedWriter(new FileWriter(ACCOUNT_FILE, true))) {
			writeFile.write(username + "," + password);
			writeFile.newLine();
			System.out.println("\nAccount created sucessfully!");
			return new Player(username);
		}
		catch (IOException e) {
			System.out.println("\nError writing to accounts file.");
			return null;
		}
	}
	
	public static ArrayList<Player> getPlayers() {
		return new ArrayList<>(players);
	}
	
}
