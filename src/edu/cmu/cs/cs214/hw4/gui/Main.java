package edu.cmu.cs.cs214.hw4.gui;

import javax.swing.*;

import edu.cmu.cs.cs214.hw4.gui.gameplay.GamePanel;
import edu.cmu.cs.cs214.hw4.gui.startMenu.StartMenu;

public class Main {

	final static int MAX_PLAYERS = 4;
	static JFrame frame;
	static JPanel outer;
	public static Mediator mediator = new Mediator();
	
	public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
            	initGame();
            }
        });
    }
	
	/**
	 * Changes the frame to display Scrabble
	 */
	public static void changeToScrabble(){
		frame.setVisible(false);
		frame = new JFrame("Scrabble with Stuff");
		JPanel outer1 = new GamePanel();
		frame.add(outer1);
		frame.setSize(1205, 985);	
		//frame.pack();
		frame.setVisible(true);
		frame.setResizable(false);
		
	}
	/**
	 * Changes the frame to display the start menu
	 */
	public static void initGame(){
		frame = new JFrame("Scrabble with Stuff");
		outer = new StartMenu();
		frame.add(outer);
		frame.pack();	
		frame.setVisible(true);
	}
	/**
	 * Hides the frame
	 */
	public static void hideFrame(){
		frame.setVisible(false);
	}
	/**
	 * Packs frame
	 */
	public static void packFrame(){
		frame.pack();
	}
	
}
