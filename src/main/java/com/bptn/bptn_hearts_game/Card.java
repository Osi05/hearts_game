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

	
	
	//using eclipse to override toString
	@Override
	public String toString() {
		return "Card [cardType=" + cardType + ", cardWeight=" + cardWeight + "]";
	}
	
	
}
