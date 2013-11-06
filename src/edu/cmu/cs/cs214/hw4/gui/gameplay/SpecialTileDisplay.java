package edu.cmu.cs.cs214.hw4.gui.gameplay;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

@SuppressWarnings("serial")
/**
 * The display for special tiles which are clicked to determine which one you want to buy
 */
public class SpecialTileDisplay extends JLabel implements MouseListener {

	private String identifier;
	private int price;
	private GamePanel gamePanel;
	
	public SpecialTileDisplay(String identifier, GamePanel gp){
		this.gamePanel = gp;
		this.identifier = identifier;
		ImageIcon icon = new ImageIcon("assets/"+identifier+".png");
		this.setIcon(icon);
		//Only negative points costs 10 points
		if(identifier.equals("Negative Points")){
			price = 10;
		}
		else{
			price = 5;
		}
		this.addMouseListener(this);
	}
	@Override
	/**
	 * Gives the information about which tile to buy to the button that confirms purchase
	 */
	public void mouseClicked(MouseEvent e) {
		this.gamePanel.updateBuySpecialTile(identifier, price);
	}
	public void mousePressed(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	

}
