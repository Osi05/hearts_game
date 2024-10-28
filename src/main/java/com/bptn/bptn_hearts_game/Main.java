package com.bptn.bptn_hearts_game;

import java.util.ArrayList;

public class Main {
	
	public static void main(String[] args) {
		
		AccountManager.loginOrCreateAccount();
		ArrayList<Player> players = AccountManager.getPlayers();
		
		
//		AccountManager.chooseDifficulty();
//		
		Game game = new Game(players);

		game.startGame();
		
	}
		
}