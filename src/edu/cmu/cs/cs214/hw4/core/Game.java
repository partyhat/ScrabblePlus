package edu.cmu.cs.cs214.hw4.core;

import java.util.ArrayList;

import edu.cmu.cs.cs214.hw4.modifiers.Modifier;
import edu.cmu.cs.cs214.hw4.player.Player;
import edu.cmu.cs.cs214.hw4.tiles.*;

public class Game {

	private final static int HAND_SIZE = 7;
	private Board board;
	private Player[] players;
	private Player currentPlayer;
	private TileBag tileBag;
	private CurrentMove currentMove;
	private int index;
	private Dictionary dictionary;
	public int turnCount;
	public int specialModifier = 1;
	public int direction;
	private int numberOfPlayers;
	private int skipTurnCount = 0;
	public boolean gameEnd = false;
	public boolean specialTilePlayed = false;
	public String specialTilePlayedOwner;
	public String specialTileName;
	
	/**
	 * Instantiating this class starts the game
	 * @param numberOfPlayers - number of plaers gotten from GUI
	 * @param names - string of names, entered through GUI
	 */
	public Game(int numberOfPlayers, String[] names){
		this.tileBag = new TileBag();
		this.board = new Board();
		this.players = new Player[numberOfPlayers];
		for(int i = 0; i< numberOfPlayers; i++){
			ArrayList<Tile>inithand = this.tileBag.getTiles(HAND_SIZE);
			players[i] = new Player(names[i], inithand);
		}
		this.currentPlayer = players[0];
		this.currentMove = new CurrentMove();
		this.index = 0;
		this.turnCount = 0;
		this.dictionary = new Dictionary();
		this.direction = 1; //forward
		this.numberOfPlayers = numberOfPlayers;
	}
	
	
	
	/**
	 * 
	 * @return the current player
	 */
	public Player getCurrentPlayer(){
		return this.currentPlayer;
		
	}
	/**
	 * 
	 * @return array of all the players
	 */
	public Player[] getPlayers(){
		return players;
	}
	
	/**
	 * returns the score of the player at the particular index
	 * @param index Index of the player in the player array
	 * @return The score of the player
	 */
	public int getPlayerScore(int index){
		return this.players[index].getScore();
	}
	
	/**
	 * 
	 * @return The number of players
	 */
	public int numberOfPlayers(){
		return numberOfPlayers;
	}
	/**
	 * 
	 * @return The scrabble board with all its tiles
	 */
	public Board getBoard(){
		return board;
	}
	/**
	 * 
	 * @return The current move which includes coordinates and modifiers
	 */
	public CurrentMove getCurrentMove(){
		return this.currentMove;
	}
	/**
	 * Sets the game to have ended
	 */
	public void endGame(){
		this.gameEnd = true;
	}
	/**
	 * Changes game state to be the next turn
	 */
	public void nextTurn(){
		// If all players have skipped their turn then the game is over
		if(this.skipTurnCount == this.numberOfPlayers){
			endGame();
		}
		else{
			//increase turn count and set current player to the next player
			this.specialModifier = 1;
			this.index += direction;
			this.turnCount++;
			this.currentPlayer = players[index % numberOfPlayers];
			this.currentMove.clearAll();
			//if the new player has lost a turn then call next turn again
			if(this.currentPlayer.loseTurn){
				this.currentPlayer.loseTurn = false;
				nextTurn();
			}
		}
	}
	
	/**
	 * Increments skipTurnCount before calling nextTurn so that game can check
	 * if everyone has skipped their turn
	 */
	public void skipTurn(){
		//Takes all placed tiles off the board that the player put on
		while(this.currentMove.getLetterTiles().size() > 0){
			Coordinates c = this.currentMove.getLetterTiles().get(0);
			removeTile(c.getRow(), c.getCol());
		}
		//Takes all placed tiles off the board that the player put on
		while(this.currentMove.getSpecialTiles().size() > 0){
			Coordinates c = this.currentMove.getLetterTiles().get(0);
			removeTile(c.getRow(), c.getCol());	
		}
		this.skipTurnCount++;
		nextTurn();	
	}
	
	/**
	 * Buys a special tile for the player
	 * @param identifier String representing which tile to buy
	 * @return True if the transaction went through, false otherwise
	 */
	public boolean buySpecial(String identifier){
		if(identifier.equals("Reverse Order")){
			if(currentPlayer.getScore() > 5){
				currentPlayer.addToScore(-5);
				ReverseOrder ro = (ReverseOrder)tileBag.buySpecialTile(identifier, currentPlayer);
				currentPlayer.addTileToHand(ro);
				return true;
			}
		}
		else if(identifier.equals("Lose Turn")){
			if(currentPlayer.getScore() > 5){
				currentPlayer.addToScore(-5);
				LoseTurn lt = (LoseTurn)tileBag.buySpecialTile(identifier, currentPlayer);
				currentPlayer.addTileToHand(lt);
				return true;
			}
		}
		else if(identifier.equals("Negative Points")){
			if(currentPlayer.getScore() > 10){
				currentPlayer.addToScore(-10);
				NegativePoints np = (NegativePoints)tileBag.buySpecialTile(identifier, currentPlayer);
				currentPlayer.addTileToHand(np);
				return true;
			}
		}
		return false;
		
	}
	
	/**
	 * Removes a tile from the board and adds it to the current player's hand
	 * Can only remove a tile if it is in the current move
	 * @param row Row of the tile
	 * @param col Column of the tile
	 */
	public void removeTile(int row, int col){
		Tile tempTile;
		if(this.currentMove.isInCurrentMove(row, col)){
			this.currentMove.removeMove(row, col);
			tempTile = this.board.removeTile(row, col, currentPlayer);
			currentPlayer.addTileToHand(tempTile);
		}
		else{
			System.out.println("cant remove that tile");
		}
		
		
	}
	
	
	/**
	 * Places a tile from the currentPlayer's hand onto the board
	 * @param index Index of the tile in the player's hand
	 * @param row Destination row on the board
	 * @param col Destination col on the board
	 */
	public void placeTileOnBoard(int index, int row, int col){
		
		//take tile from the players hand
		Tile currentTile = this.currentPlayer.getTileFromHand(index);
		
		//place on board
		if(this.board.placeTile(row, col, currentTile, currentPlayer)){
			this.currentMove.addMove(row, col, currentTile);
		}
		else{
			currentPlayer.addTileToHand(currentTile);
		}
	}
	
	/**
	 * Exchanges a player's hand.  Results in a lost turn for the player
	 * @return True if the exchange was successful, false if not
	 */
	public boolean exchangeLetters(){
		//if there are no tiles left to exchange with, return false
		if(tileBag.allGone()){
			return false;
		}
		
		//Takes all placed tiles off the board that the player put on
		while(this.currentMove.getLetterTiles().size() > 0){
			Coordinates c = this.currentMove.getLetterTiles().get(0);
			removeTile(c.getRow(), c.getCol());
		}
		//Takes all placed tiles off the board that the player put on
		while(this.currentMove.getSpecialTiles().size() > 0){
			Coordinates c = this.currentMove.getLetterTiles().get(0);
			removeTile(c.getRow(), c.getCol());	
		}
		//removes tiles from the players hand and adds them to the tileBaf
		int i = 0;
		while(this.currentPlayer.getNumberOfLetters()>0){
			if(this.currentPlayer.isLetterTile(i)){
				Tile tempTile = this.currentPlayer.getTileFromHand(i);
				this.tileBag.addTile(tempTile);
			}
			else{
				i++;
			}
		}
		//get new hand for player
		ArrayList<Tile> newLetters = this.tileBag.getTiles(HAND_SIZE);
		currentPlayer.addTileToHand(newLetters);
		nextTurnNoIncrement(); // exchanges do not increment turncount
		return true;
	}

	/**
	 * Makes all the checks for validating a move and then submits it to the board
	 * @return True if move was successful, false if not
	 */
	public boolean submitMove(){
		//reset this
		this.specialTilePlayed = false;
		//checks if tiles are in same row
		if(currentMove.getLetterTiles().size() > 0){
			currentMove.clearWordsAndModifiers();
			int[] extremes = this.currentMove.getMaxAndMinRowAndCol();
			int highestRow = extremes[0];
			int lowestRow = extremes[1];
			int highestCol = extremes[2];
			int lowestCol = extremes[3];
			boolean sameRow = (highestRow == lowestRow); //highest row == lowest row
			boolean sameCol = (highestCol == lowestCol); //highest col == lowest col
	
			if(checkAllValidities(extremes) == false){
				return false;
			}
			
			/*If move is in same row and col, it is one letter and just needs processing once
			  verticaly and once horizontally*/
			if(sameRow && sameCol){
				//if only one tile is played than it must be next to other tiles
				if(!this.board.adjacentToOtherLetters(extremes)){
					return false;
				}
				int row = highestRow;//doesn't matter
				int startCol = this.board.getStartCol(row, lowestCol);
				//process horizontal words by making startCol and highestCol different (+1)
				if(processWord(lowestRow, highestRow, startCol, highestCol+1, false) == false){
					return false;				
				}
				int col = highestCol;
				int startRow = this.board.getStartRow(lowestRow, col);
				//process vertical words by making startRow and highestRow different (+1)
				if(processWord(startRow, highestRow+1, lowestCol, highestCol, false) == false){
					return false;
				}
				
			}
			else if(sameRow){
				int row = highestRow;//doesn't matter
				int startCol = this.board.getStartCol(row, lowestCol);
				//process horizontal word by making startCol and highestCol different (+1)
				if(processWord(lowestRow, highestRow, startCol, highestCol+1, true) ==false){
					return false;
				}
				//for each tile played, process the words vertically
				for(Coordinates c: currentMove.getLetterTiles()){
					int tileRow = c.getRow();
					int tileCol = c.getCol();
					int startRow = board.getStartRow(tileRow, tileCol);
					if(processWord(startRow, tileRow+1, tileCol, tileCol, false)==false){
						return false;
					}
				}
			}
			else if(sameCol){
				int col = highestCol;
				int startRow = this.board.getStartRow(lowestRow, col);
				if(processWord(startRow, highestRow+1, lowestCol, highestCol, true)==false){
					return false;
				}
				for(Coordinates c: currentMove.getLetterTiles()){
					int tileRow = c.getRow();
					int tileCol = c.getCol();
					int startCol = board.getStartCol(tileRow, tileCol);
					if(processWord(tileRow, tileRow, startCol, tileCol+1, false)==false){
						return false;
					}
				}
			}
		}
		
		addMoveToBoard(); //if all words are valid, add to the board
		updateScore(); //updateScore
		replenishHand();//Give player tiles for playing tiles on board
		this.skipTurnCount = 0; //set skipTurnCount to 0 since move has been played
		nextTurn();	
		return true;
	}
	
	private boolean processWord(int sRow, int eRow, int sCol, int eCol, boolean multipleLetters){
		ArrayList<Modifier> usedModifiers = this.currentMove.getUsedModifiers();
		Word currentWord = board.processWord(sRow, eRow, sCol, eCol, usedModifiers);
		boolean validWord = dictionary.isValidWord(currentWord.getWord());
		if(!validWord && (multipleLetters || currentWord.getWord().length() > 1)){
			return false;
		}
		else if(validWord){
			currentMove.addWord(currentWord);
		}
		return true;
	}
	
	private boolean checkAllValidities(int[] extremes){
		if(!this.board.validNextToEachOther(extremes)){
			return false;
		}
		
		if(!this.board.allAdjacentLetterTiles(extremes)){
			return false;
		}
		if( (turnCount > 0) && !this.board.adjacentToOtherLetters(extremes)){
			return false;
		}
		return true;
	}
	
	private void replenishHand(){
		int tilesNeeded = (HAND_SIZE - this.currentPlayer.getNumberOfLetters());
		ArrayList<Tile> newLetters = this.tileBag.getTiles(tilesNeeded);
		currentPlayer.addTileToHand(newLetters);
		
	}
	
	private void addMoveToBoard(){
		for(Coordinates c : currentMove.getLetterTiles()){
			int row = c.getRow();
			int col = c.getCol();
			BoardSpace currentSpace = board.getBoardSpace(row, col);
			currentSpace.submitTile(board, this);	
		}
		for(Coordinates c : currentMove.getSpecialTiles()){
			int row = c.getRow();
			int col = c.getCol();
			BoardSpace currentSpace = board.getBoardSpace(row, col);
			currentSpace.submitTile(board, this);
		}
	}
	
	private void updateScore(){
		int points = specialModifier * this.currentMove.getScore();
		currentPlayer.addToScore(points);
	}
	
	private void nextTurnNoIncrement(){
		this.specialModifier = 1;
		this.index += direction;
		this.currentPlayer = players[index % numberOfPlayers];
		this.currentMove.clearAll();
		if(this.currentPlayer.loseTurn){
			this.currentPlayer.loseTurn = false;
			nextTurnNoIncrement();
		}
	}

	/**
	 * Sets a flag so that the GUI can check if a special tile was played
	 * @param identifier Identifier of the special tile
	 * @param name name of player who played special tile.
	 */
	public void setSpecialTileFlag(String identifier, String name) {
		this.specialTilePlayed = true;
		this.specialTilePlayedOwner = name;
		this.specialTileName = identifier;
	}
	
	
}
