package edu.cmu.cs.cs214.hw4.gui.gameplay;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import edu.cmu.cs.cs214.hw4.gui.Main;

@SuppressWarnings("serial")
/**
 * Button that actually buys the tile.  
 * @author osyed94
 *
 */
public class BuySpecialTile extends JButton implements ActionListener {

	private GamePanel gamePanel;
	private String identifier;
	
	public BuySpecialTile(GamePanel gp){
		this.gamePanel = gp;
		this.setText("Buy Special Tile"); //original text identifying what the button does
		this.setEnabled(false);
		this.addActionListener(this);
		this.setPreferredSize(new Dimension(200,20));
		this.setSize(200, 20);
		this.setAlignmentX(CENTER_ALIGNMENT);	
	}
	/**
	 * Updates the button to the information of the SpecialTile that was selected
	 * @param identifier String representing the SpecialTile
	 * @param price Integer representing price of the tile
	 */
	public void updateButton(String identifier, int price){
		this.setText("Buy "+ identifier+ " for "+price+" points");
		this.identifier = identifier;
	}
	@Override
	/**
	 * Calls buySpecialTile, then refreshes score and hand to display the change to both
	 */
	public void actionPerformed(ActionEvent e) {
		if(Main.mediator.buySpecialTile(identifier)){
			gamePanel.refreshHand();
			gamePanel.refreshScore();
		}
		//Notifies player that they do not have enough points to buy the tile.
		//SMH broke people tryna buy thingz
		else{
			JOptionPane.showMessageDialog((Component)gamePanel, "Not Enough Points");
		}
	}

}
