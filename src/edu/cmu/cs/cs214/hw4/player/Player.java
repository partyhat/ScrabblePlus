package edu.cmu.cs.cs214.hw4.player;

import java.util.ArrayList;

import edu.cmu.cs.cs214.hw4.tiles.*;

public class Player {

	private int score;
	private String name;
	private ArrayList<Tile> hand;
	private int numberOfLetters;
	public boolean loseTurn;
	
	public Player(String name, ArrayList<Tile> initialHand){
		this.name = name;
		this.hand = initialHand;
		this.numberOfLetters = initialHand.size();
		this.loseTurn = false;
	}
	
	public void addTileToHand(Tile tile){
		if(tile instanceof LetterTile){
			numberOfLetters++;
			hand.add((LetterTile)tile);
		}
		else{
			hand.add((SpecialTile)tile);
		}
		
	}
	/**
	 * Adds an array list of tiles to the hand.  Simply calls singe Tile version of metod
	 * @param tiles ArrayList of tiles to be added
	 */
	public void addTileToHand(ArrayList<Tile>tiles){
		for(Tile t : tiles){
			addTileToHand(t);
		}
	}

	/**
	 * @return Returns player's score
	 */
	public int getScore(){
		return score;
	}
	/**
	 * @return Return's player's name
	 */
	public String getName(){
		return name;
	}
	/**
	 * Gets tile identifier from particular location in player's hand
	 * @param index Index of tile in hand
	 * @return String representing the tile
	 */
	public String getIdentifier(int index){
		return hand.get(index).getIdentifier();	
	}
	/**
	 * Removes tile at particular index from hand and returns it
	 * @param index Index of tile in hand
	 * @return Tile from hand
	 */
	public Tile getTileFromHand(int index){
		Tile currentTile =  hand.get(index);
		removeTile(currentTile);
		return currentTile;	
	}
	
	private void removeTile(Tile tile){
		hand.remove(tile);
		if(tile instanceof LetterTile){
			this.numberOfLetters--;
		}
	}
	/**
	 * @return Returns number of letters in hand
	 */
	public int getNumberOfLetters(){
		return this.numberOfLetters;
	}
	/**
	 * @return Returns total number of tiles in hand
	 */
	public int getNumberOfTiles(){
		return hand.size();
	}
	/**
	 * Returns true if the tile at that index in the hand is a letter
	 * @param index Index of inquiry
	 * @return True if letter, false if not
	 */
	public boolean isLetterTile(int index){
		return this.hand.get(index) instanceof LetterTile;
	}
	/**
	 * Adds score to tile
	 * @param points
	 */
	public void addToScore(int points){
		score+=points;
	}
}