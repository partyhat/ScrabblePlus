package edu.cmu.cs.cs214.hw4.gui;

import java.util.ArrayList;


import edu.cmu.cs.cs214.hw4.core.Coordinates;
import edu.cmu.cs.cs214.hw4.core.Game;
import edu.cmu.cs.cs214.hw4.player.Player;
import edu.cmu.cs.cs214.hw4.tiles.LetterTile;
import edu.cmu.cs.cs214.hw4.tiles.SpecialTile;
import edu.cmu.cs.cs214.hw4.tiles.Tile;

/**
 * Mediator is the class that allows the GUI to interact with Game.
 * GUI holds instance of the mediator in the Main class and
 * @author osyed94
 */
public class Mediator {
	private ArrayList<String> names = new ArrayList<String>();
	private Game scrabble;
	private final static int MAX_NAME_LENGTH = 10;
	
	/**
	 * Adds name entered in GUI to the name ArrayList.  
	 * Only uses first 10 letters to keep uniform size
	 * @param name String denoting the name
	 */
	public void addName(String name){
		if(name.length()>MAX_NAME_LENGTH){
			name = name.substring(0, MAX_NAME_LENGTH);
		}
		//default name if not specified
		if(name.length() == 0){
			name = "Player "+(names.size()+1);
		}
		names.add(name);
	}
	/**
	 * @return number of players in the game
	 */
	public int numberOfPlayers(){
		return names.size();
	}
	/**
	 * Clears all the names for a new game
	 */
	public void clearNames(){
		names.clear();
	}
	
	/**
	 * Starts a game by calling the constructor of the Game class
	 */
	public void startGame(){
		String[] nameArray = new String[names.size()];
		scrabble = new Game(names.size(), names.toArray(nameArray));	
	}
	
	/**
	 * @return Number of letters in the current player's hand
	 */
	public int numberOfLetters() {
		return scrabble.getCurrentPlayer().getNumberOfLetters();
	}
	/**
	 * 
	 * @return Number of tiles in total (includes special tiles) in the current player's hand
	 */
	public int numberOfTiles() {
		return scrabble.getCurrentPlayer().getNumberOfTiles();
	}
	
	/**
	 * Gets the string on the tile at the index in the players hand
	 * @param index Index of hand
	 * @return String representing the tile
	 */
	public String getTextFromHand(int index) {
		String lt = scrabble.getCurrentPlayer().getIdentifier(index);
		return lt;
	}
	
	/**
	 * Checks if a tile can be removed
	 * @param row Row of the tile
	 * @param col Col of the tile
	 * @return True if it can be returned, false if not.  Depends on if it is the CurrentMove
	 */
	public boolean isRemoveable(int row, int col){
		return scrabble.getCurrentMove().isInCurrentMove(row, col);
	}
	
	/**
	 * Adds tile to board by just calling that function in the instance of the Game
	 * @param index Index of the tile in the player's hand
	 * @param row Destination row on board
	 * @param col Destination col on board
	 */
	public void addTileToBoard(int index, int row, int col){
		scrabble.placeTileOnBoard(index, row, col);
	}
	
	/**
	 * Gets the text on the board at that location that the current player is allowed to see
	 * @param row Row of tile on board
	 * @param col Col of tile on board
	 * @return String representing the tile.  Note that if current player cannot see the tile, null is returned
	 */
	public String getBoardSpaceText(int row, int col){
		Player p = scrabble.getCurrentPlayer();
		Tile boardTile = scrabble.getBoard().getBoardSpace(row, col).getViewableTile(p);
		if(boardTile instanceof LetterTile){
			LetterTile lt = ((LetterTile)boardTile);
			return lt.getIdentifier(); 
		}
		else if(boardTile instanceof SpecialTile){
			return boardTile.getIdentifier();
		}
		else{
			return null;
		}
	}
	/**
	 * Returns true if the board is empty at that row and col
	 * @param row Row in question
	 * @param col Coloumn in question
	 * @return
	 */
	public boolean isEmpty(int row, int col){
		return scrabble.getBoard().isEmpty(row, col);
	}
	
	/**
	 * Removes tile
	 * @param row Row of tile 
	 * @param col Column of tile being removed
	 */
	public void removeTile(int row, int col){
		scrabble.removeTile(row, col);
	}
	/**
	 * Calls nextTurn in the game.
	 */
	public void nextTurn(){
		scrabble.nextTurn();
	}
	/**
	 * Returns the list of names of the players
	 * @return
	 */
	public ArrayList<String> getNames(){
		return names;
	}
	/**
	 * @param index Index of player in the list of players
	 * @return Score of that particular player
	 */
	public int getScore(int index){
		return scrabble.getPlayerScore(index);
	}
	/**
	 * Calls submitMove of the Game class
	 * @return true if submission worked
	 */
	public boolean submitMove(){
		return scrabble.submitMove();
	}
	/**
	 * Gets the name of the current player
	 * @return current player's name
	 */
	public String getCurrentPlayerName(){
		return scrabble.getCurrentPlayer().getName();
	}
	/**
	 * Calls exchange tile in the Game class
	 * @return True if letters were exchanged properly
	 */
	public boolean exchangeTile(){
		return scrabble.exchangeLetters();
	}
	/**
	 * @return List of coordinates of letter tiles placed on the board in this move
	 */
	public ArrayList<Coordinates> getCurrentLetterMoves(){
		return scrabble.getCurrentMove().getLetterTiles();
	}
	/**
	 * @return List of coordinates of special tiles placed on the board in this move
	 */
	public ArrayList<Coordinates> getCurrentSpecialMoves(){
		return scrabble.getCurrentMove().getSpecialTiles();
	}
	
	/**
	 * Calls skip turn of the Game class
	 */
	public void skipTurn(){
		scrabble.skipTurn();
	}
	
	/**
	 * Checks if the game has ended
	 * @return
	 */
	public boolean gameEnd(){
		return scrabble.gameEnd;
	}
	/**
	 * Finds player with highest score
	 * @return String of the player's name who has the highest score
	 */
	public String getWinnerName(){
		Player[] players = scrabble.getPlayers();
		int highScore = players[0].getScore();
		int index = 0;
		for(int i = 0; i < players.length; i++){
			if(players[i].getScore() > highScore){
				highScore = players[i].getScore();
				index = i;
			}
		}
		return players[index].getName();
	}
	
	/**
	 * Buys a special tile specified by the identifier
	 * @param identifier String representing the SpecialTile
	 * @return True if purchase was successful, false if not
	 */
	public boolean buySpecialTile(String identifier){
		return scrabble.buySpecial(identifier);
	}
	/**
	 * @return Whether or not special tile was played
	 */
	public boolean specialTilePlayed() {
		return scrabble.specialTilePlayed;
	}
	/**
	 * @return Owner of the special tile played
	 */
	public String getSpecialTileOwner() {
		return scrabble.specialTilePlayedOwner;
	}
	/**
	 * @return Type of special tile played
	 */
	public String getSpecialTileType() {
		return scrabble.specialTileName;
	}

}
