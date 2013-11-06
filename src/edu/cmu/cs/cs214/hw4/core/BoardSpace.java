package edu.cmu.cs.cs214.hw4.core;

import edu.cmu.cs.cs214.hw4.modifiers.Modifier;
import edu.cmu.cs.cs214.hw4.modifiers.TileModifier;
import edu.cmu.cs.cs214.hw4.player.Player;
import edu.cmu.cs.cs214.hw4.tiles.LetterTile;
import edu.cmu.cs.cs214.hw4.tiles.SpecialTile;
import edu.cmu.cs.cs214.hw4.tiles.Tile;

public class BoardSpace {

	private Tile actualTile;
	private Tile viewableTile;
	private Modifier modifier;
	
	
	/**
	 * Submits tile held on the board to be kept on the board.
	 * Board and Game are needed to enact special tile effects
	 * @param board The Board the tile is on
	 * @param game The Game this space is in
	 */
	public void submitTile(Board board, Game game){
		if(actualTile instanceof SpecialTile){
			((SpecialTile) this.actualTile).doEffect(board, game);
		}
		this.actualTile = this.viewableTile;
		if(this.actualTile instanceof SpecialTile){
			this.viewableTile = null;
		}
	}
	
	/**
	 * places Tile on board just for viewing.  Before actual submission
	 * @param tile Tile to be held on the board
	 */
	public void holdTile(Tile tile){
		this.viewableTile = tile;
	}
	/**
	 * Takes the tile being held off the board
	 */
	public void removeViewableTile(){
		this.viewableTile = null;
	}
	/**
	 * 
	 * @return Returns the tile being held on the board
	 */
	public Tile getViewableTile(){
		return this.viewableTile;
	}
	/**
	 * 
	 * @return The Modifier on this space
	 */
	public Modifier getModifier(){
		return this.modifier;
	}
	/**
	 * 
	 * @return True if there is a modifier on the space, false if not
	 */
	public boolean hasModifier(){
		return !(modifier == null);
	}
	/**
	 * True if the tile being held on the board is a LetterTile
	 * @return
	 */
	public boolean isLetterTile(){
		return (this.viewableTile instanceof LetterTile);
	}
	
	//For the gui for displaying the board on someone's turn
	/**
	 * Returns the tile that the currentPlayer can see on the board.
	 * @param currentPlayer The current player trying to get the tile
	 * @return The tile on the board
	 */
	public Tile getViewableTile(Player currentPlayer){
		if(this.viewableTile != null){
			return this.viewableTile;
		}
		//if the tile is SpecialTile then it is hidden by viewable tile being null
		else if(this.actualTile != null && ((SpecialTile)actualTile).getOwner().equals(currentPlayer)){
			return this.actualTile;
		}
		else{
			return null;
		}
	}
	
	/**
	 * Gets the score based on the modifiers in this space
	 * @return The score of this tile with modifiers taken into account
	 */
	public int getScore(){
		LetterTile letterTile = (LetterTile)viewableTile;
		//only modify score for the space if it is an active tile modifier
		if(modifier instanceof TileModifier && modifier.isActive()){
			return modifier.modifyScore(letterTile.getValue());
		}
		else{
			return letterTile.getValue();
		}
		
	}
	/**
	 * Checks if the space is empty for the current player
	 * @param currentPlayer The player checking if space is empty
	 * @return True if empty for the player, false if not
	 */
	public boolean isEmpty(Player currentPlayer){
		return getViewableTile(currentPlayer) == null;
	}
	/**
	 * Adds modifier to the space
	 * @param modifier Modifier being added
	 */
	public void addModifier(Modifier modifier){
		this.modifier = modifier;
	}

}
