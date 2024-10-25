package com.bptn.bptn_hearts_game;

public class Card {
	
	//instance variable for Card
	private String cardType;
	private String cardWeight;
	
	//creating a constructor for Card that takes 2 string parameters 
	public Card(String cardType, String cardWeight) {
		this.cardType = cardType;
		this.cardWeight = cardWeight;
	}
	
	
	//using eclipse to create getters for my variables
	public String getCardType() {
		return cardType;
	}

	public String getCardWeight() {
		return cardWeight;
	}

	public int getCardValue() {
		switch (cardWeight) {
		case "2": return 2;
		case "3": return 3;
		case "4": return 4;
		case "5": return 5;
		case "6": return 6;
		case "7": return 7;
		case "8": return 8;
		case "9": return 9;
		case "10": return 10;
		case "Jack": return 11;
		case "Queen": return 12;
		case "King": return 13;
		case "Ace": return 14;
		default: return 0; //invalid
		}
	}
	
	//using eclipse to override toString
	@Override
	public String toString() {
		return "Card [cardType=" + cardType + ", cardWeight=" + cardWeight + "]";
	}
	
	
}
