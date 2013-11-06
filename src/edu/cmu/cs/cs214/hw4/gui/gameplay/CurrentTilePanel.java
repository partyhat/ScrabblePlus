package edu.cmu.cs.cs214.hw4.gui.gameplay;

import java.awt.Color;
import java.awt.Font;

import javax.swing.*;

@SuppressWarnings("serial")
/**
 * Current tile from hand that has been selected
 * @author osyed94
 *
 */
public class CurrentTilePanel extends JPanel {
	
	private int index = -1;
	JLabel currentTile;
	
	public CurrentTilePanel(){
		currentTile = new JLabel();
		this.currentTile.setAlignmentX(CENTER_ALIGNMENT);
		//identifies the area
		JLabel text = new JLabel();
		text.setText("Selected Tile From Hand:");
		text.setFont(new Font("Chalkduster",Font.PLAIN, 13));
		text.setAlignmentX(CENTER_ALIGNMENT);
		
		
		JPanel currentSpaceHolder = new JPanel();
		currentSpaceHolder.setLayout(new BoxLayout(currentSpaceHolder, BoxLayout.Y_AXIS));
		currentSpaceHolder.add(text);
		currentSpaceHolder.add(currentTile);
		currentSpaceHolder.setOpaque(true);
		currentSpaceHolder.setBackground(new Color(101, 137, 212));
		
		this.add(currentSpaceHolder);
		
		
	}
	
	/**
	 * updates icon and index to that of the new selected tile from the hand
	 * @param imgIcon icon of selected tile
	 * @param index index of selected tile
	 */
	public void changeCurrentTile(ImageIcon imgIcon, int index){
		this.index = index;
		this.currentTile.setIcon(imgIcon);
	}
	/**
	 * Refresh consists of just clearing the display so no tile has been selected
	 */
	public void refresh(){
		this.index = -1;
		this.currentTile.setIcon(null);
	}
	/**
	 * @returns Index of tile that has been selected
	 */
	public int returnIndex(){
		return index;
	}

}
