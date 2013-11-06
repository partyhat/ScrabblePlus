package edu.cmu.cs.cs214.hw4.gui.startMenu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import edu.cmu.cs.cs214.hw4.gui.Main;

@SuppressWarnings("serial")
public class RemovePlayer extends JButton implements ActionListener {
	private StartMenu parent;
	
	public RemovePlayer(String display, StartMenu parent){
		this.parent = parent;
		this.addActionListener(this);
		this.setText(display);
	}
	
	@Override
	/**
	 * Removes a player when possible, and enables AddPlayer when necessary
	 */
	public void actionPerformed(ActionEvent e) {
		JPanel players = (JPanel) this.parent.getComponent(1);
		int count = players.getComponentCount();
		if(count > 2){
			players.remove(count-1);
		}
		if(count == 4){
			parent.addPlayer.setEnabled(true);
		}	
		if(count== 3){
			this.setEnabled(false);
		}
		Main.packFrame();
	}
	

}
