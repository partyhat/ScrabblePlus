package edu.cmu.cs.cs214.hw4.gui.startMenu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import edu.cmu.cs.cs214.hw4.gui.Main;

@SuppressWarnings("serial")
public class AddPlayer extends JButton implements ActionListener {
	private static final int MAX_PLAYERS = 4;
	private StartMenu parent;
	
	public AddPlayer(String display, StartMenu parent){
		this.parent = parent;
		this.addActionListener(this);
		this.setText(display);
	}
	
	@Override
	/**
	 * Adds text field for new player when possible and disables itself when MAX_PLAYERS has been reached
	 */
	public void actionPerformed(ActionEvent e) {
		JPanel players = (JPanel) this.parent.getComponent(1);
		int count = players.getComponentCount();
		if(count < MAX_PLAYERS){
			JTextField newPlayer = new JTextField(20);
			players.add(newPlayer);
			if(count == 3){
				this.setEnabled(false);
			}
		}
		if(count == 2){
			this.parent.removePlayer.setEnabled(true);
		}
		Main.packFrame();
	}

}
