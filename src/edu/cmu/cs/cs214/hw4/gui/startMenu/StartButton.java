package edu.cmu.cs.cs214.hw4.gui.startMenu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import edu.cmu.cs.cs214.hw4.gui.Main;

@SuppressWarnings("serial")
public class StartButton extends JButton implements ActionListener{
	private StartMenu parent;
	
	public StartButton(String display, StartMenu parent){
		this.setText(display);
		this.addActionListener(this);
		this.parent = parent;
	}
	
	@Override
	/**
	 * Gives names to the mediator and starts the game
	 */
	public void actionPerformed(ActionEvent e) {
		for(int i = 0; i <this.parent.getNumberOfPlayers(); i++){
			Main.mediator.addName(parent.getPlayerName(i));
			
		}
		Main.mediator.startGame();
		Main.changeToScrabble();
	}
}
