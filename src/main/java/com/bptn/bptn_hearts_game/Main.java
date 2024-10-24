package com.bptn.bptn_hearts_game;

import java.util.ArrayList;

public class Main {
	
	public static void main(String[] args) {
		ArrayList<Player> players = new ArrayList<>();
		players.add(new Player("John", true));
		players.add(new Player("Joy", false));
		players.add(new Player("Ella", false));
		players.add(new Player("Bob", false));
		
		
		Game game = new Game(players);
		
		game.startGame();
		
	}
}
