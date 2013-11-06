package edu.cmu.cs.cs214.hw4.modifiers;

public class Modifier {

	protected int multiplier;
	protected boolean active;
	public Modifier(int multiplier){
		this.multiplier = multiplier;
		this.active = true;
	}
	/**
	 * Actually modifies a given score
	 * @param score score to be modified
	 * @return Modified score
	 */
	public int modifyScore(int score){
		return score*multiplier;
	}
	/**
	 * inactivates to make sure modifier is only used once
	 */
	public void inactivate(){
		this.active = false;
	}
	/**
	 * @return Returns true if modifier is active
	 */
	public boolean isActive(){
		return active;
	}
}
