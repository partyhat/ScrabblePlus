package edu.cmu.cs.cs214.hw4.gui.startMenu;


import java.awt.FlowLayout;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


@SuppressWarnings("serial")
public class StartMenu extends JPanel {
	final JPanel addOrRemove = new JPanel();
	final JPanel playerHolder = new JPanel();
	final JTextField player1 = new JTextField(10);
	final JTextField player2 = new JTextField(10);
	final JButton addPlayer = new AddPlayer("Add Another Player", this);
	final JButton removePlayer = new RemovePlayer("Remove a Player", this);
	final JLabel inputLabel = new JLabel("Enter player names: ");
	
	
	
	
	public StartMenu(){
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		final JButton submit = new StartButton("Start Game", this);
		submit.setAlignmentX(CENTER_ALIGNMENT);
		
		addOrRemove.setLayout(new FlowLayout());
		addOrRemove.add(addPlayer);
		addOrRemove.add(removePlayer);
		
		playerHolder.setLayout(new BoxLayout(playerHolder, BoxLayout.Y_AXIS));
		playerHolder.add(player1);
		playerHolder.add(player2);
		
		inputLabel.setAlignmentX(CENTER_ALIGNMENT);
		
		this.add(inputLabel);		
		this.add(playerHolder);
		this.add(addOrRemove);
		this.add(submit);
	}
	/**
	 * @return Returns player count
	 */
	public int getNumberOfPlayers(){
		return this.playerHolder.getComponentCount();
	}
	/**
	 * @param index Index of selected player
	 * @return String representing name of current player
	 */
	public String getPlayerName(int index){
		return ((JTextField)this.playerHolder.getComponent(index)).getText();
	}
	
	
	
	
	

}
