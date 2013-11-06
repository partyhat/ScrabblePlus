package edu.cmu.cs.cs214.hw4.gui.gameplay;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import edu.cmu.cs.cs214.hw4.gui.Main;

@SuppressWarnings("serial")
/**
 * The JLabel that represents each tile on the board
 * @author osyed94
 *
 */
public class BoardSpaceLabel extends JLabel implements MouseListener{
	private int row;
	private int col;
	//private String text;
	private GamePanel gamePanel;
	
	public BoardSpaceLabel(int row, int col, GamePanel gamePanel){
		this.row = row;
		this.col = col;
		this.gamePanel = gamePanel;
		this.addMouseListener(this);
	}
	
	@Override
	/**
	 * If a tile from the hand has been selected, then when a space is clicked,
	 * add the tile to that space.  If no tile from the hand is selected than simply
	 * display the tile on the right.
	 */
	public void mouseClicked(MouseEvent e) {
		int index = gamePanel.getCurrentTileIndex();
		if(index<0){
			ImageIcon imgIcon = (ImageIcon) this.getIcon();
			gamePanel.changeBoardSpaceDisplay(imgIcon, this.row, this.col);
			
		}
		else{
			Main.mediator.addTileToBoard(index, row, col);
			gamePanel.refreshBoard(row, col);
			gamePanel.refreshHand();
			if(Main.mediator.getCurrentSpecialMoves().size() > 0 || 
			   Main.mediator.getCurrentLetterMoves().size() > 0){
				gamePanel.enableSubmitButton();
			}
			gamePanel.refreshCurrentTile();
		}
	}
		
	@Override
	public void mousePressed(MouseEvent e) {}
	@Override
	public void mouseReleased(MouseEvent e) {}
	@Override
	public void mouseEntered(MouseEvent e) {}
	@Override
	public void mouseExited(MouseEvent e) {}
	

}
