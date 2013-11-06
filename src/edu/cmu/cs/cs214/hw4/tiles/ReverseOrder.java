package edu.cmu.cs.cs214.hw4.tiles;

import edu.cmu.cs.cs214.hw4.core.Board;
import edu.cmu.cs.cs214.hw4.core.Game;
import edu.cmu.cs.cs214.hw4.player.Player;

public class ReverseOrder extends SpecialTile {

	public ReverseOrder(Player owner, String identifier) {
		super(owner, identifier);
		// TODO Auto-generated constructor stub
	}

	public void doEffect(Board board, Game game) {
		super.doEffect(board, game);
		if(game.direction == 1){
			game.direction = game.numberOfPlayers() -1;
		}
		else{
			game.direction = 1;
		}
	}
	

}
