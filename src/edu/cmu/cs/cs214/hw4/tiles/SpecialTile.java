package edu.cmu.cs.cs214.hw4.tiles;

import edu.cmu.cs.cs214.hw4.core.Board;
import edu.cmu.cs.cs214.hw4.core.Game;
import edu.cmu.cs.cs214.hw4.player.Player;

public abstract class SpecialTile extends Tile {

	private String identifier;
	private Player owner;
	
	public SpecialTile(Player owner, String identifier){
		this.identifier = identifier;
		this.owner = owner;
	}
	
	public Player getOwner(){
		return owner;
	}
	
	@Override
	public String getIdentifier() {
		return identifier;
	}
	
	public void doEffect(Board board, Game game){
		game.setSpecialTileFlag(this.getIdentifier(), this.getOwner().getName());
	}
	


	

}
