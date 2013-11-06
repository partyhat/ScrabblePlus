package edu.cmu.cs.cs214.hw4.gui.gameplay;

import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

import edu.cmu.cs.cs214.hw4.gui.Main;

@SuppressWarnings("serial")
public class HandRack extends JPanel {
	MouseListener click;
	
	public HandRack(final GamePanel gamePanel){
		int numberOfTiles = Main.mediator.numberOfTiles();
		/*
		 * Action listener for all each tile in the hand
		 * Changes the current tile display to have information about that current tile
		 */
		click = new MouseListener(){
			@Override
			public void mousePressed(MouseEvent e) {
				int index = -1;
				JComponent jc = (JComponent)e.getSource();
				ImageIcon imgIcon = (ImageIcon) ((JLabel)jc).getIcon();
				Component[] components = ((JLabel)jc).getParent().getComponents();
				for(int i = 0; i < components.length; i++){
					if( ((JLabel)components[i]).getIcon().equals(imgIcon) ){
						index = i;
					}
				}
				gamePanel.changeCurrentTile(imgIcon, index);
			}
			public void mouseReleased(MouseEvent e) {}
			public void mouseEntered(MouseEvent e) {}
			public void mouseExited(MouseEvent e) {}
			public void mouseClicked(MouseEvent e) {}
		};
		putHandTogether(numberOfTiles);
		this.setSize(450, 60);
		this.setLayout(new FlowLayout());
		this.setOpaque(true);
		this.setBackground(new Color(255,179,137));
	}
	/**
	 * Refreshing consists of removing all the tiles then loading all 
	 * the tiles in the new players hand
	 */
	public void refresh(){
		int numberOfTiles = Main.mediator.numberOfTiles();
		this.removeAll();
		this.setVisible(false);
		putHandTogether(numberOfTiles);
		this.setVisible(true);
		this.setSize(450, 60);
	}
	
	private void putHandTogether(int numberOfTiles){
		//adds all the tiles to the hand rack
		for(int i = 0; i<numberOfTiles; i++){
			JLabel tile = new JLabel();
			String text = Main.mediator.getTextFromHand(i);
			ImageIcon icon = new ImageIcon("assets/"+text+".png");
			tile.addMouseListener(click);
			tile.setIcon(icon);
			this.add(tile);
		}
		/*blank tile invisible to the player to keep the size constant
		  when there are 0 tiles in the hand*/
		if(numberOfTiles == 0){
			JLabel tile = new JLabel();
			ImageIcon icon = new ImageIcon("assets/handSpace.png");
			tile.setIcon(icon);
			this.add(tile);
		}
	}

}
