package edu.cmu.cs.cs214.hw4.gui.gameplay;

import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import edu.cmu.cs.cs214.hw4.gui.Main;

@SuppressWarnings("serial")
public class ScorePanel extends JPanel {

	private final static int MAX_NAME_SIZE = 10;
	private final static String BLANK_SPACES = "          "; //used to keep the space each name takes the same
	private ArrayList<String> names;
	private String[] spacesArray;
	
	public ScorePanel(){
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		names =  Main.mediator.getNames();
		spacesArray = new String[names.size()];
		int index = 0;
		for(String name : names){
			int length = name.length();
			int numberOfExtraSpaces = MAX_NAME_SIZE - length;
			String extraSpaces = BLANK_SPACES.substring(0,numberOfExtraSpaces);
			spacesArray[index] = extraSpaces;
			JLabel label = new JLabel();
			//allows different length names to all take up same space by adding
			//spaces to the end
			label.setText(name + ": 0 "+ extraSpaces);
			this.add(label);
			index++;
		}
	}
	/**
	 * Gets score for each player and reprints it
	 */
	public void refresh(){
		for(int i = 0; i <names.size(); i++){
			JLabel label = (JLabel) this.getComponent(i);
			label.setText(names.get(i)+ ": "+ Main.mediator.getScore(i) + " " + this.spacesArray[i]);
		}
	}
	
	
	
	
}
