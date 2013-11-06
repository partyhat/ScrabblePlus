package edu.cmu.cs.cs214.hw4.core;

import java.util.ArrayList;

import edu.cmu.cs.cs214.hw4.modifiers.Modifier;
import edu.cmu.cs.cs214.hw4.tiles.SpecialTile;
import edu.cmu.cs.cs214.hw4.tiles.Tile;

public class CurrentMove {

	private ArrayList<Coordinates> letterMoves;
	private ArrayList<Coordinates> specialTileMoves;
	private ArrayList<Word> currentWords;
	private ArrayList<Modifier> modifiers;

	
	public CurrentMove(){
		letterMoves = new ArrayList<Coordinates>();
		specialTileMoves = new ArrayList<Coordinates>();
		currentWords = new ArrayList<Word>();
		modifiers = new ArrayList<Modifier>();
	}
	
	/**
	 * Adds a move by creating a coordinate and putting it either in the
	 * arrayList for special coordinates of letter tile coordinates
	 * @param row
	 * @param col
	 * @param currentTile
	 */
	public void addMove(int row, int col, Tile currentTile){
		Coordinates currentCoordinates = new Coordinates(row, col);
		if(currentTile instanceof SpecialTile){
			specialTileMoves.add(currentCoordinates);
		}
		else{
			letterMoves.add(currentCoordinates);
		}
	}
	/**
	 * Adds Word to list of current words
	 * @param e Word
	 */
	public void addWord(Word e){
		currentWords.add(e);
	}
	/**
	 * @return Returns the combined score of all the words formed in this move, with their respective modifiers
	 */
	public int getScore(){
		int totalScore = 0;
		for(Word word : currentWords){
			totalScore += word.getScore();
		}
		return totalScore;
	}
	/**
	 * Removes the coordinate from this move
	 * @param row obviously row
	 * @param col col
	 */
	public void removeMove(int row, int col){
		Coordinates targetCoordinate = new Coordinates(row, col);
		for(Coordinates c : letterMoves){
			if(c.equals(targetCoordinate)){
				letterMoves.remove(c);
				return;
			}
		}
		for(Coordinates d : letterMoves){
			if(d.equals(targetCoordinate)){
				letterMoves.remove(d);
				return;
			}
		}
	}
	
	/**
	 * Finds the max/min row and colum
	 * @return returns the max/min row and column as an integer array
	 */
	public int[] getMaxAndMinRowAndCol() {
		Coordinates highestCol = letterMoves.get(0);
		Coordinates  lowestCol = highestCol,  highestRow = highestCol, lowestRow = highestCol;
		
		for(Coordinates c: letterMoves){
			if(c.getCol() > highestCol.getCol()){
				highestCol = c;
			}
			if(c.getCol() < lowestCol.getCol()){
				lowestCol = c;
			}
			if(c.getRow() > highestRow.getRow()){
				highestRow = c;
			}
			if(c.getRow() < lowestRow.getRow()){
				lowestRow = c;
			}
		}
		int[] extremes = {highestRow.getRow(), lowestRow.getRow(), highestCol.getCol(), lowestCol.getCol()};
		return extremes;
	}
	
	/**
	 * Clears the words and modifiers in this move for a new turn.
	 */
	public void clearWordsAndModifiers(){
		this.currentWords.clear();
		this.modifiers.clear();
	}
	
	/**
	 * Checks if a row and column coordinate is in the array of currentMoves
	 * @param row
	 * @param col
	 * @return
	 */
	public boolean isInCurrentMove(int row, int col){
		Coordinates currentCoordinates = new Coordinates(row, col);
		if(letterMoves.contains(currentCoordinates) || specialTileMoves.contains(currentCoordinates)){
			return true;
		}
		else{
			return false;
		}
	}
	/**
	 * @return Returns list of used modifiers in this move
	 */
	public ArrayList<Modifier> getUsedModifiers() {
		return this.modifiers;
	}
	
	public ArrayList<Coordinates> getLetterTiles(){
		return letterMoves;
	}
	
	public ArrayList<Coordinates> getSpecialTiles(){
		return specialTileMoves;
	}
	/**
	 * inactivates all modifiers and clears all arraylists for new move
	 */
	public void clearAll(){
		letterMoves.clear();
		specialTileMoves.clear();
		currentWords.clear();
		for(Modifier m : modifiers){
			m.inactivate();
		}
		modifiers.clear();
	}
	

}
