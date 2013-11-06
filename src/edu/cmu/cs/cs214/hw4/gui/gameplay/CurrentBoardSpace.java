package edu.cmu.cs.cs214.hw4.gui.gameplay;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import edu.cmu.cs.cs214.hw4.gui.Main;

@SuppressWarnings("serial")
/**
 * Display of the board space that is displayed and the button to remove it
 * @author osyed94
 *
 */
public class CurrentBoardSpace extends JPanel {
	
	private JButton removeFromBoard;
	private JLabel currentSpace;
	private GamePanel gamePanel;
	protected int row;
	protected int col;
	
	/*
	 * Action listener that attempts to remove a selected tile if possible
	 */
	ActionListener clickedRemove = new ActionListener(){
		@Override
		public void actionPerformed(ActionEvent e) {
			Main.mediator.removeTile(row, col);
			gamePanel.refreshBoard(row, col);
			gamePanel.refreshHand();
		
			if(Main.mediator.getCurrentSpecialMoves().size() == 0 && 
			   Main.mediator.getCurrentLetterMoves().size() == 0){
				gamePanel.disableSubmitButton();
			}
			currentSpace.setIcon(null);
			
		}
	};
	
	public CurrentBoardSpace(GamePanel gp){
		this.gamePanel = gp;
		currentSpace = new JLabel();
		currentSpace.setAlignmentX(CENTER_ALIGNMENT);
		JPanel currentSpaceHolder = new JPanel();
		JLabel text = new JLabel();
		//identifies the area
		text.setText("Selected Board Space:");
		text.setFont(new Font("Chalkduster",Font.PLAIN, 13));
		text.setAlignmentX(CENTER_ALIGNMENT);
		//holds the icon of the space selected
		currentSpaceHolder.setLayout(new BoxLayout(currentSpaceHolder, BoxLayout.Y_AXIS));
		currentSpaceHolder.add(text);
		currentSpaceHolder.add(currentSpace);
		currentSpaceHolder.setOpaque(true);
		currentSpaceHolder.setBackground(new Color(255, 67, 72));
		//button for removing
		removeFromBoard = new JButton("Remove From Board");
		removeFromBoard.addActionListener(clickedRemove);
		removeFromBoard.setEnabled(false);
		JPanel buttonHolder = new JPanel();
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.add(currentSpaceHolder);
		buttonHolder.add(removeFromBoard);
		this.add(buttonHolder);
	}
	/**
	 * Change the current space display to the icon provided
	 * @param imgIcon Icon of new selected space
	 * @param row Row of selected space
	 * @param col Column of selected space
	 */
	public void changeCurrentSpace(ImageIcon imgIcon, int row, int col){
		currentSpace.setIcon(imgIcon);
		this.row = row;
		this.col = col;
		if(Main.mediator.isEmpty(row, col)){
			removeFromBoard.setEnabled(false);
		}
		else{
			removeFromBoard.setEnabled(true);
		}	
	}
	/**
	 * refreshing means no tile has been selected
	 */
	public void refresh(){
		currentSpace.setIcon(null);
	}

}
