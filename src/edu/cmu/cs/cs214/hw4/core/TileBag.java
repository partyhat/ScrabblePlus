package edu.cmu.cs.cs214.hw4.core;

import java.util.ArrayList;

import edu.cmu.cs.cs214.hw4.player.Player;
import edu.cmu.cs.cs214.hw4.tiles.*;

public final class TileBag {

												        //A,B,C,D,E, F,G,H,I,J,K,L,M,N,O,P,Q, R,S,T,U,V,W,X,Y,Z
	private final static int[] LETTER_FREQUENCY = 		 {9,3,3,4,12,3,3,2,9,1,1,4,2,6,8,2,1, 6,4,6,4,2,2,1,2,1};
	private final static int[] LETTER_VALUE =             {1,3,3,2,1, 4,2,4,1,8,5,1,3,1,1,3,10,1,1,1,1,4,4,8,4,10}; 
	private static ArrayList<String> actualLetters; 
	
	public TileBag(){
		//Creates array list of Strings for all the letters.
		createList();
	}
	
	private void createList(){
		actualLetters = new ArrayList<String>();
		for(int i = 0; i <26; i++){
			//Makes sure to add the correct amount of each letter
			for(int k = 0; k<LETTER_FREQUENCY[i]; k++){
				actualLetters.add(Character.toString((char)(65+i)) );
			}
		}
	}
	
	/**
	 * Gets size amount of tiles from the tile bag
	 * @param size Number of tiles needed
	 * @return ArrayList of tiles
	 */
	public ArrayList<Tile> getTiles(int size){
		ArrayList<Tile> thisList = new ArrayList<Tile>();
		while(thisList.size() < size && !allGone()){
			int index = (int) (Math.random()* actualLetters.size());
			String letter = actualLetters.get(index);
			actualLetters.remove(index);
			int valIndex = (letter.charAt(0)-65);
			int value = LETTER_VALUE[valIndex];
			LetterTile newTile = new LetterTile(letter, value);
			thisList.add(newTile);
		}
		return thisList;
	}
	/**
	 * checks if there are any tiles left in tile bag
	 * @return
	 */
	public boolean allGone(){
		return (actualLetters.size() == 0);
	}

	/**
	 * Adds a tile back into the tile bag
	 * @param tile The tile being added back in
	 */
	public void addTile(Tile tile) {
		actualLetters.add(tile.getIdentifier());
	}
	
	/**
	 * Creates a SpecialTile for a given player
	 * @param id The identification for which type of special tile is needed
	 * @param player The Players who becomes the owner of these tiles
	 * @return The SpecialTile
	 */
	public SpecialTile buySpecialTile(String id, Player player){
		if(id == "Reverse Order"){
			return new ReverseOrder(player, "Reverse Order");
		}
		else if(id == "Lose Turn"){
			return new LoseTurn(player, "Lose Turn");
		}
		else if(id == "Negative Points"){
			return new NegativePoints(player, "Negative Points");
		}
		else{
			return null;
		}
	}
	

}
