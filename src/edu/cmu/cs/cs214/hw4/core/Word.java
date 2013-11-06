package edu.cmu.cs.cs214.hw4.core;

import java.util.ArrayList;

import edu.cmu.cs.cs214.hw4.modifiers.WordModifier;

public class Word {

	private ArrayList<WordModifier> modifiers;
	private String word;
	private int score;
	
	public Word(){
		this.word = "";
		this.score = 0;
		this.modifiers = new ArrayList<WordModifier>();
	}
	
	public void addToScore(int points){
		this.score += points;
	}
	/**
	 * Gets the score based off of the modifiers in the word
	 * @return
	 */
	public int getScore(){
		int returnScore = this.score;
		if(modifiers.size()>0){
			for(WordModifier m : modifiers){
				returnScore = m.modifyScore(returnScore);
			}
		}
		return returnScore;
	}
	/**
	 * Adds a letter into the word
	 * @param letter
	 */
	public void addLetter(String letter){
		word = word + letter;
	}
	/**
	 * Adds a modifier into the word
	 * @param modifier WordModifier added to the word
	 */
	public void addModifier(WordModifier modifier){
		this.modifiers.add(modifier);
	}
	/**
	 * @return Returns the word in this instance
	 */
	public String getWord(){
		return this.word.toLowerCase();
	}

}
