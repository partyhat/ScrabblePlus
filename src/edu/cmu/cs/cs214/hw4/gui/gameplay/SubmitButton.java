package edu.cmu.cs.cs214.hw4.gui.gameplay;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import edu.cmu.cs.cs214.hw4.gui.Main;

@SuppressWarnings("serial")
public class SubmitButton extends JButton implements ActionListener {

	private GamePanel gamePanel;
	
	public SubmitButton(GamePanel gp){
		this.gamePanel = gp;
		this.setText("Submit Move");
		this.addActionListener(this);
		
		
	}
	@Override
	/**
	 * Submits the move, if possible and refreshes the board.
	 */
	public void actionPerformed(ActionEvent e) {
		boolean enable = true;
		//if submitm ove does not go through then word is invalid
		if(!Main.mediator.submitMove()){
			JOptionPane.showMessageDialog((Component)gamePanel, "Invalid Move");	
		}
		//no need to disable the button if the turn is the same.
		else{
			enable = false;
			if(Main.mediator.specialTilePlayed()){
				String name = Main.mediator.getSpecialTileOwner();
				String identifier = Main.mediator.getSpecialTileType();
				JOptionPane.showMessageDialog((Component)gamePanel, "You played on "+identifier+" placed by "+name+"!");
			}
		}
		gamePanel.refreshAllButBoard();
		gamePanel.refreshEntireBoard();
		if(enable){
			this.setEnabled(true);
		}
	}

}
