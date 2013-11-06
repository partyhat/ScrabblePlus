package edu.cmu.cs.cs214.hw4.gui.gameplay;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.*;

import edu.cmu.cs.cs214.hw4.gui.Main;

@SuppressWarnings("serial")
public class ScrabbleBoardGrid extends JPanel {
	private final static int boardSize = 15;
	private GamePanel gamePanel;
	//all the possible icons to be used
	private final static ImageIcon blankIcon = new ImageIcon("assets/boardSpace.jpg");
	private final static ImageIcon doubleWordIcon = new ImageIcon("assets/doubleWord.jpg");
	private final static ImageIcon tripleWordIcon = new ImageIcon("assets/tripleWord.jpg");
	private final static ImageIcon doubleLetterIcon = new ImageIcon("assets/doubleLetter.jpg");
	private final static ImageIcon tripleLetterIcon = new ImageIcon("assets/tripleLetter.png");
	private final static ImageIcon homeIcon = new ImageIcon("assets/homeTile.png");
	//Tile modifier spaces have to be hard coded
	private final static ArrayList<Integer> tripleWords = new ArrayList<Integer>(Arrays.asList(0,7,14,105, 119, 210, 217,224));
	private final static ArrayList<Integer> doubleWords = new ArrayList<Integer>(Arrays.asList(16,28,32, 42, 48,56,64, 70,154, 160,168,176,182,192,196,208));
	private final static ArrayList<Integer> doubleLetter = new ArrayList<Integer>(Arrays.asList(3, 11, 36, 38,45, 52, 59,92, 96,98, 102, 108, 116,122, 126, 128,132,165,172, 179, 186, 188, 213, 221));
	private final static ArrayList<Integer> tripleLetter = new ArrayList<Integer>(Arrays.asList(20,24, 80,84, 140, 144, 200, 204, 76, 136, 88, 148));
	
	public ScrabbleBoardGrid(GamePanel gamePanel){
		GridLayout gl = new GridLayout(boardSize, boardSize,0,0);
		this.setLayout(gl);
		this.gamePanel = gamePanel;
		createGrid();
		this.setSize(870, 870);
		Dimension d = new Dimension(600, 600);
		this.setPreferredSize(d);
	}
	
	private void createGrid(){
		//Gives each board space(JLabel) the right icon
		for(int row = 0; row<boardSize; row++){
			for(int col = 0; col < boardSize; col++){
				int boardNumber = (row*boardSize)+col;
				JLabel boardSpace = new BoardSpaceLabel(row, col, gamePanel);
				boardSpace.setVerticalTextPosition(SwingConstants.TOP);
				if(tripleWords.contains(boardNumber)){
					boardSpace.setIcon(tripleWordIcon);
				}
				else if(doubleWords.contains(boardNumber)){
					boardSpace.setIcon(doubleWordIcon);
				}
				else if(doubleLetter.contains(boardNumber)){
					boardSpace.setIcon(doubleLetterIcon);
				}
				else if(tripleLetter.contains(boardNumber)){
					boardSpace.setIcon(tripleLetterIcon);
				}
				else if(boardNumber == 112){
					boardSpace.setIcon(homeIcon);
				}
				else{
					boardSpace.setIcon(blankIcon);
				}
				this.add(boardSpace);
			}
		}
	}
	
	/**
	 * Refreshing updates the icons of all the board spaces
	 */
	public void refresh(){
		for(int row = 0; row<boardSize; row++){
			for(int col = 0; col < boardSize; col++){
				int boardNumber = (row*boardSize)+col;
				JLabel boardSpace = (JLabel) this.getComponent(col + (row*boardSize));
				String identifier = (Main.mediator.getBoardSpaceText(row, col));
				ImageIcon icon;
				if(identifier != null){
					icon = new ImageIcon("assets/"+identifier+".png");
				}
				else if(tripleWords.contains(boardNumber)){
					icon = tripleWordIcon;
				}
				else if(doubleWords.contains(boardNumber)){
					icon = doubleWordIcon;
				}
				else if(doubleLetter.contains(boardNumber)){
					icon= (doubleLetterIcon);
				}
				else if(tripleLetter.contains(boardNumber)){
					icon = (tripleLetterIcon);
				}
				else if(boardNumber == 112){
					icon = homeIcon;
				}
				else{
					icon = blankIcon;
				}
				boardSpace.setIcon(icon);		
			}
		}
	}
	/**
	 * Refreshes one specific board space
	 * @param row Row of space
	 * @param col Column of space
	 */
	public void refresh(int row, int col){
		int boardNumber = (row*boardSize)+col;
		JLabel boardSpace = (JLabel) this.getComponent(col + (row*boardSize));
		String identifier = (Main.mediator.getBoardSpaceText(row, col));
		ImageIcon icon;
		if(identifier != null){
			icon = new ImageIcon("assets/"+identifier+".png");
		}
		else if(tripleWords.contains(boardNumber)){
			icon = tripleWordIcon;
		}
		else if(doubleWords.contains(boardNumber)){
			icon = doubleWordIcon;
		}
		else if(doubleLetter.contains(boardNumber)){
			icon= (doubleLetterIcon);
		}
		else if(tripleLetter.contains(boardNumber)){
			icon = (tripleLetterIcon);
		}
		else if(boardNumber == 112){
			icon = homeIcon;
		}
		else{
			icon = blankIcon;
		}
		boardSpace.setIcon(icon);		
	}
	/**
	 * Removes whatever tile is on the board and buts original icon on
	 * @param row Row of tile
	 * @param col Column of tile
	 */
	public void clearSpace(int row, int col){
		ImageIcon icon = null;
		int boardNumber = (row*boardSize)+col;
		if(tripleWords.contains(boardNumber)){
			icon = tripleWordIcon;
		}
		else if(doubleWords.contains(boardNumber)){
			icon = doubleWordIcon;
		}
		else if(doubleLetter.contains(boardNumber)){
			icon= (doubleLetterIcon);
		}
		else if(tripleLetter.contains(boardNumber)){
			icon = (tripleLetterIcon);
		}
		else if(boardNumber == 112){
			icon = homeIcon;
		}
		else{
			icon = blankIcon;
		}
		((JLabel) this.getComponent(col + (row*boardSize))).setIcon(icon);
	}

}
