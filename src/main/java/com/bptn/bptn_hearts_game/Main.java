package com.bptn.bptn_hearts_game;

import java.util.ArrayList;

public class Main {
	
	public static void main(String[] args) {
		
		AccountManager.loginOrCreateAccount();
		ArrayList<Player> players = AccountManager.getPlayers();
		
        // Ensure players list is not empty
        if (players.isEmpty()) {
            System.out.println("No players available to start the game.");
            return;
        }
		
		Game game = new Game(players);

		game.startGame();
		
	}
		
}