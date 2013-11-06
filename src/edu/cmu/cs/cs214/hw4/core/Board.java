package edu.cmu.cs.cs214.hw4.core;

import java.util.ArrayList;
import java.util.Arrays;

import edu.cmu.cs.cs214.hw4.modifiers.Modifier;
import edu.cmu.cs.cs214.hw4.modifiers.TileModifier;
import edu.cmu.cs.cs214.hw4.modifiers.WordModifier;
import edu.cmu.cs.cs214.hw4.player.Player;
import edu.cmu.cs.cs214.hw4.tiles.*;

public class Board {

	private BoardSpace[][] scrabbleBoard;
	private final static int SIZE = 15;
	
	
	public Board(){
		//had to hard code tile spaces with special modifiers
		ArrayList<Integer> tripleWords = new ArrayList<Integer>(Arrays.asList(0,7,14,105, 119, 210, 217,224));
		ArrayList<Integer> doubleWords = new ArrayList<Integer>(Arrays.asList(16,28,32, 42, 48,56,64, 70,154, 160,168,176,182,192,196,208));
		ArrayList<Integer> doubleLetter = new ArrayList<Integer>(Arrays.asList(3, 11, 36, 38,45, 52, 59,92, 96,98, 102, 108, 116,122, 126, 128,132,165,172, 179, 186, 188, 213, 221));
		ArrayList<Integer> tripleLetter = new ArrayList<Integer>(Arrays.asList(20,24, 80,84, 140, 144, 200, 204, 76, 136, 88, 148));
		this.scrabbleBoard = new BoardSpace[SIZE][SIZE];
		int spaceNumber;
		//adds modifiers to the correct tile spaces
		for(int r = 0; r < SIZE; r++){
			for(int c = 0; c < SIZE; c++){
				scrabbleBoard[r][c] = new BoardSpace();
				spaceNumber = (r*SIZE)+c;
				if(tripleWords.contains(spaceNumber)){
					Modifier modifier = new WordModifier(3);
					scrabbleBoard[r][c].addModifier(modifier);
				}
				else if(doubleWords.contains(spaceNumber)){
					Modifier modifier = new WordModifier(2);
					scrabbleBoard[r][c].addModifier(modifier);
				}
				else if(doubleLetter.contains(spaceNumber)){
					Modifier modifier = new TileModifier(2);
					scrabbleBoard[r][c].addModifier(modifier);
				}
				else if(tripleLetter.contains(spaceNumber)){
					Modifier modifier = new TileModifier(3);
					scrabbleBoard[r][c].addModifier(modifier);
				}
				
			}
		}
		
	}
	
	/**
	 * Places tile onto board
	 * @param row Row on Board
	 * @param col Col on Board
	 * @param tile Tile being added
	 * @param currentPlayer currentPlayer
	 * @return
	 */
	public boolean placeTile(int row, int col, Tile tile, Player currentPlayer){
		BoardSpace currentSpace = scrabbleBoard[row][col];
		//currentSpace may be empty to a particular player who can't see a special tile
		if(currentSpace.isEmpty(currentPlayer)){
			currentSpace.holdTile(tile);
			return true;
		}
		else{
			return false;
		}	
	}
	
	/**
	 * Removes tile at specified row, col
	 * @param row Row on Board
	 * @param col Col on Board
	 * @param currentPlayer Player removing board
	 * @return The tile removed from board, null if nothing returned
	 */
	public Tile removeTile(int row, int col, Player currentPlayer){
		BoardSpace currentSpace = scrabbleBoard[row][col];
		Tile currentTile = currentSpace.getViewableTile(currentPlayer);
		//only return the tile if the player can see it
		if(!(currentSpace.isEmpty(currentPlayer)) ){
			currentSpace.removeViewableTile();
			return currentTile;
		}
		return null;
		
		
	}
	/**
	 * Forms a word from the starting and ending locations specified
	 * @param srow Start Row
	 * @param erow End Row
	 * @param scol Start Col
	 * @param ecol End col
	 * @param usedModifiers ArrayList to keep track of modifiers to disable them afterwards
	 * @return The Word created
	 */
	public Word processWord(int srow, int erow, int scol, int ecol, ArrayList<Modifier> usedModifiers){
		boolean sameRow = (srow == erow); //highest row == lowest row
		boolean sameCol = (scol == ecol); //highest col == lowest col
		Word currentWord = new Word();
		
		int row = srow;
		int col = scol;
		BoardSpace currentSpace = scrabbleBoard[row][col];
		//keeps adding adjacent letters to the word until the next space is blank
		while(currentSpace!= null && currentSpace.isLetterTile()){
			LetterTile currentLetterTile = (LetterTile)(currentSpace.getViewableTile());
			currentWord.addLetter(currentLetterTile.getIdentifier());
			currentWord.addToScore(currentSpace.getScore());
			if(currentSpace.hasModifier()){
				Modifier currentModifier = currentSpace.getModifier();
				usedModifiers.add(currentModifier);
				if(currentModifier instanceof WordModifier && currentModifier.isActive()){
					currentWord.addModifier((WordModifier) currentModifier);
				}
			}
			//specifies direction of word processing
			if(sameRow){
				col++;
			}
			else if(sameCol){
				row++;
			}
			//makes sure there is no null pointer exceptions
			if(inBounds(row)&&inBounds(col)){
				currentSpace = scrabbleBoard[row][col];
			}
			else{
				currentSpace = null;
			}
		}
		return currentWord;
		
	}
	
	/**
	 * Given a starting location, it gets the first possible column from which to start reading the word
	 * @param row starting row
	 * @param col starting col
	 * @return the column furthest to the left that is connected to the starting location
	 */
	public int getStartCol(int row, int col){
		while(col-1>=0 && scrabbleBoard[row][col-1].isLetterTile()){
			col--;
		}
		return col;
	}
	/**
	 * Given a starting location, it gets the first possible row from which to start reading the word
	 * @param row starting row
	 * @param col starting col
	 * @return the row furthest to the top that is connected to the starting location
	 */
	public int getStartRow(int row, int col){
		while(row-1>=0 && scrabbleBoard[row-1][col].isLetterTile()){
			row--;
		}
		return row;
	}
	
	/**
	 * Gets board space at that row and col
	 * @param row
	 * @param col
	 * @return BoardSpace at row and col
	 */
	public BoardSpace getBoardSpace(int row, int col){
		return scrabbleBoard[row][col];
	}
	/**
	 * Returns if that BoardSpace is empty for playing a tile ontop of
	 * @param row
	 * @param col
	 * @return True if viewableTile == null, false if not.
	 */
	public boolean isEmpty(int row, int col){
		return scrabbleBoard[row][col].isEmpty(null);
	}
	
	
	

	
	
/***************************************Checks Physical Placement*****************************************/
	/**
	 * Checks if all tiles are either same row or same column
	 * @param extremes start and end row and columns in an int[]
	 * @return true if same row or column, false if not
	 */
	public boolean validNextToEachOther(int[] extremes){
		boolean sameRow = (extremes[0] == extremes[1]); //highest row == lowest row
		boolean sameCol = (extremes[2] == extremes[3]); //highest col == lowest col
		
		return (sameRow||sameCol);
	}
	/**
	 * Checks if all tiles are connected to eachother within their row or column.
	 * (No spaces between tiles)
	 * @param extremes start and end row and columns in an int[]
	 * @return true if connected, false if not
	 */
	public boolean allAdjacentLetterTiles(int[] extremes){
		boolean sameRow = (extremes[0] == extremes[1]); //highest row == lowest row
		boolean sameCol = (extremes[2] == extremes[3]); //highest col == lowest col
		
		if(sameRow){
			int row = extremes[0];
			for(int col = extremes[3]; col<= extremes[2]; col++){
				if(!scrabbleBoard[row][col].isLetterTile()){
					return false;
				}
			}
		}
		else if(sameCol){
			int col = extremes[2];
			for(int row = extremes[1]; row<=extremes[0]; row++){
				if(!scrabbleBoard[row][col].isLetterTile()){
					return false;
				}
			}
		}
		
		return true;
	}
	
	/**
	 * Checks if its touching another tile that is already on the board
	 * @param extremes
	 * @return true if touching, false if not
	 */
	public boolean adjacentToOtherLetters(int[] extremes){
		boolean sameRow = (extremes[0] == extremes[1]); //highest row == lowest row
		boolean sameCol = (extremes[2] == extremes[3]); //highest col == lowest col
		//two cases, but in both cases, just checks all around the word.
		if(sameRow){
			int row = extremes[0];
			int rowBefore = row-1;
			int rowAfter = row+1;
			int colBefore = extremes[3]-1;
			int colAfter = extremes[2]+1;
			
			for(int col = extremes[3]; col<=extremes[2]; col++){
				if(inBounds(rowBefore) && scrabbleBoard[rowBefore][col].isLetterTile()){
					return true;
				}
			}
			for(int col = extremes[3]; col<=extremes[2]; col++){
				if(inBounds(rowAfter) && scrabbleBoard[rowAfter][col].isLetterTile()){
					return true;
				}
			}
			if(inBounds(colBefore) && scrabbleBoard[row][colBefore].isLetterTile()){
				return true;
			}
			if(inBounds(colAfter) && scrabbleBoard[row][colAfter].isLetterTile()){
				return true;
			}
		}
		else if(sameCol){
			
			int col = extremes[2];
			int colBefore = col -1;
			int colAfter = col +1;
			int rowBefore = extremes[1] -1;
			int rowAfter = extremes[0] +1;
			
			for(int row = extremes[1]; row <= extremes[0]; row++){
				if(inBounds(colBefore) && scrabbleBoard[row][colBefore].isLetterTile()){
					return true;
				}
				if(inBounds(colAfter) && scrabbleBoard[row][col].isLetterTile()){
					return true;
				}
			}
			if(inBounds(rowBefore) && scrabbleBoard[rowBefore][col].isLetterTile()){
				return true;
			}
			if(inBounds(rowAfter) && scrabbleBoard[rowAfter][col].isLetterTile()){
				return true;
			}
		}
		return false;
		
	}
	
	private boolean inBounds(int place) {
		return (place>=0 && place<SIZE);
	}
	
/*********************************************************************************************************/
}
