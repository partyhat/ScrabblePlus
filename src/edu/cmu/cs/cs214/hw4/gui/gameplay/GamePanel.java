package edu.cmu.cs.cs214.hw4.gui.gameplay;

import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import edu.cmu.cs.cs214.hw4.gui.Main;


@SuppressWarnings("serial")
/**
 * Facilitates communication between all the GUI components
 * @author osyed94
 */
public class GamePanel extends JPanel{
	private HandRack handRack;
	private CurrentTilePanel curTilePanel;
	private CurrentBoardSpace curBoardSpace;
	private JPanel rightEnd =  new JPanel();;
	private ScrabbleBoardGrid board;
	private SubmitButton submitButton;
	private ExchangeButton exchangeButton;
	private ScorePanel scores;
	private JPanel bottom = new JPanel();;
	private JLabel currentPlayer = new JLabel();;
	private JPanel buttons = new JPanel();
	private SkipTurn skipTurn;
	private BuySpecialTile specialTileButton;
	private JPanel specialTiles = new JPanel();
	
	public GamePanel(){
		this.setLayout(new BorderLayout());
		
		//score display
		this.scores = new ScorePanel();
		this.add(scores, BorderLayout.LINE_START);
		//hand rack
		this.handRack = new HandRack(this);
		//current player name (displayed at the bottom)
		currentPlayer.setText(Main.mediator.getCurrentPlayerName());
		currentPlayer.setFont(new Font("Serif", Font.BOLD, 28));
		currentPlayer.setAlignmentX(CENTER_ALIGNMENT);
		this.bottom.setLayout(new BoxLayout(this.bottom, BoxLayout.Y_AXIS));
		bottom.add(handRack);
		bottom.add(currentPlayer);
		this.add(bottom, BorderLayout.PAGE_END); //adds handrack and current player
		
		//displays of current tile in rack selected
		this.curTilePanel = new CurrentTilePanel();
		//display of the current space on the board selected
		this.curBoardSpace = new CurrentBoardSpace(this);
		
		this.submitButton = new SubmitButton(this);
		this.submitButton.setEnabled(false);
		
		this.exchangeButton = new ExchangeButton(this);
		this.buttons.add(submitButton);
		this.buttons.add(exchangeButton);
		//Special Tile Store
		JLabel loseTurn = new SpecialTileDisplay("Lose Turn", this);
		JLabel reverseOrder = new SpecialTileDisplay("Reverse Order", this);
		JLabel negativePoints = new SpecialTileDisplay("Negative Points", this);
		this.specialTiles.add(loseTurn);
		this.specialTiles.add(reverseOrder);
		this.specialTiles.add(negativePoints);
		
		//skip turn and buy special tile button
		this.skipTurn = new SkipTurn(this);
		this.specialTileButton = new BuySpecialTile(this);
		
		//Adds various components to the right of the window
		this.rightEnd.setLayout(new BoxLayout(rightEnd, BoxLayout.Y_AXIS));
		rightEnd.add(curTilePanel);
		rightEnd.add(curBoardSpace);
		rightEnd.add(buttons);
		rightEnd.add(skipTurn);
		rightEnd.add(specialTiles);
		rightEnd.add(specialTileButton);
		this.add(rightEnd, BorderLayout.LINE_END);	
		
		this.board = new ScrabbleBoardGrid(this);
		this.add(board, BorderLayout.CENTER);
	}
	
	/**
	 * Change the display of the current tile from hand that is selected
	 */
	public void changeCurrentTile(ImageIcon imgIcon, int index){
		curTilePanel.changeCurrentTile(imgIcon, index);
	}
	/**
	 * Change the display of the current board place when a new one is clicked
	 * @param imgIcon New image icon to set the icon to
	 * @param row Row of selected space
	 * @param col Column of slected space
	 */
	public void changeBoardSpaceDisplay(ImageIcon imgIcon, int row, int col){
		curBoardSpace.changeCurrentSpace(imgIcon, row, col);
	}
	
	/**
	 * Gets the index of the selected tile from the hand
	 * @return Index of selected tile from hand
	 */
	public int getCurrentTileIndex() {
		return curTilePanel.returnIndex();
	}
	
	/**
	 * All these need to be refreshed at the end of a turn
	 */
	public void refreshAllButBoard(){
		this.handRack.refresh();
		this.curTilePanel.refresh();
		this.curBoardSpace.refresh();
		this.scores.refresh();
		this.submitButton.setEnabled(false);
		currentPlayer.setText(Main.mediator.getCurrentPlayerName());
	}
	/**
	 * specifically refreshes a single tile on the board
	 * @param row Row of specific tile
	 * @param col Column of specific tile
	 */
	public void refreshBoard(int row, int col){
		this.board.refresh(row, col);
	}
	/**
	 * Refreshes all spaces on the board
	 */
	public void refreshEntireBoard(){
		this.board.refresh();
	}
	/**
	 * Refreshes display hand to be the current players hand
	 */
	public void refreshHand(){
		this.handRack.refresh();
	}
	/**
	 * Refreshes score
	 */
	public void refreshScore(){
		this.scores.refresh();
	}
	/**
	 * refreshes current selected tile
	 */
	public void refreshCurrentTile(){
		this.curTilePanel.refresh();
	}

	/**
	 * Clears the board space at the specified row and column
	 * @param row The current row
	 * @param col The current col
	 */
	public void clearBoardSpace(int row, int col){
		this.board.clearSpace(row, col);
	}
	
	/**
	 * Refreshes the name displayed when the player changes
	 */
	public void refreshCurrentPlayer() {
		currentPlayer.setText(Main.mediator.getCurrentPlayerName());	
	}
	/**
	 * Disables submit button if turn has just started and nothing is on the board
	 */
	public void disableSubmitButton(){
		this.submitButton.setEnabled(false);
	}
	/**
	 * Enables submit button
	 */
	public void enableSubmitButton(){
		this.submitButton.setEnabled(true);
	}

	/**
	 * Changes text of the buy submit tile button depending on which special tile is clicked to buy
	 * @param identifier String of the special tile
	 * @param price Integer representing price
	 */
	public void updateBuySpecialTile(String identifier, int price) {
		this.specialTileButton.setEnabled(true);
		this.specialTileButton.updateButton(identifier, price);
		
	}
}
