package edu.cmu.cs.cs214.hw4.gui.gameplay;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import edu.cmu.cs.cs214.hw4.core.Coordinates;
import edu.cmu.cs.cs214.hw4.gui.Main;

@SuppressWarnings("serial")
/**
 * Button for skipping turn
 * @author osyed94
 */
public class SkipTurn extends JButton implements ActionListener {

	private GamePanel gamePanel;
	public SkipTurn(GamePanel gp){
		this.gamePanel = gp;
		this.setText("Skip Turn");
		this.addActionListener(this);
		this.setAlignmentX(CENTER_ALIGNMENT);
	}
	
	@Override
	/**
	 * If all players skipped their turns in a row, then game ended
	 * If not though, then just refresh for the next turn
	 */
	public void actionPerformed(ActionEvent e) {
		ArrayList<Coordinates> curMove = new ArrayList<Coordinates>();
		curMove = Main.mediator.getCurrentLetterMoves();
		clearSpaces(curMove);
		curMove = Main.mediator.getCurrentSpecialMoves();
		clearSpaces(curMove);
		
		Main.mediator.skipTurn();
		//if game has ended bring up the dialog to end the game.
		if(Main.mediator.gameEnd()){
			Object winnerName = Main.mediator.getWinnerName();
			int n = JOptionPane.showConfirmDialog(
				    (Component)gamePanel,
				    winnerName + " WON!!!\n"+ "Would you like to play again?",
				    "Game Over",
				    JOptionPane.YES_NO_OPTION);
			
			if(n == 0){
				Main.mediator.startGame();
				Main.changeToScrabble();
			}
			else{
				Main.mediator.clearNames();
				Main.hideFrame();
				Main.initGame();
			}
		}
		//if game hasnt ended just refresh stuff for the next turn
		else{
			gamePanel.refreshAllButBoard();
			gamePanel.refreshEntireBoard();
		}
	}
	//Clears the spaces given by the coordinates in the array list
	private void clearSpaces(ArrayList<Coordinates> curMove){
		for(Coordinates c : curMove){
			gamePanel.clearBoardSpace(c.getRow(), c.getCol());
		}
	}

}
