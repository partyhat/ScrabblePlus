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
 * Button for exchanging tiles
 * @author osyed94
 *
 */
public class ExchangeButton extends JButton implements ActionListener {

	private GamePanel gamePanel;
	
	public ExchangeButton(GamePanel gp){
		this.gamePanel = gp;
		this.setText("Exchange Tiles");
		this.addActionListener(this);
	}
	@Override
	/**
	 * Removes all the tiles that the player may have placed on the board
	 * Then exchanges them if possible
	 */
	public void actionPerformed(ActionEvent e) {
		ArrayList<Coordinates> curMove = new ArrayList<Coordinates>();
		curMove = Main.mediator.getCurrentLetterMoves();
		
		for(Coordinates c : curMove){
			gamePanel.clearBoardSpace(c.getRow(), c.getCol());
		}
		if(Main.mediator.exchangeTile()){
			gamePanel.refreshCurrentPlayer();
			gamePanel.refreshHand();
			gamePanel.disableSubmitButton();
		}
		//Notifies player if there are no tiles left in the tile bag to exchange with
		else{
			JOptionPane.showMessageDialog((Component)gamePanel, "No Tiles Left");
		}
		
	}
	
	

}
