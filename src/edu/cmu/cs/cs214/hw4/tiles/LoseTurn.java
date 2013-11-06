package edu.cmu.cs.cs214.hw4.tiles;

import edu.cmu.cs.cs214.hw4.core.Board;
import edu.cmu.cs.cs214.hw4.core.Game;
import edu.cmu.cs.cs214.hw4.player.Player;

public class LoseTurn extends SpecialTile {

	public LoseTurn(Player owner, String identifier) {
		super(owner, identifier);
		// TODO Auto-generated constructor stub
	}
	@Override
	public void doEffect(Board board, Game game) {
		super.doEffect(board, game);
		game.getCurrentPlayer().loseTurn = true;
		
	}
	

}
