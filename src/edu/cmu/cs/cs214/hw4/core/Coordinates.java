package edu.cmu.cs.cs214.hw4.core;

public class Coordinates {

	private int row;
	private int col;
	
	public Coordinates(int row, int col){
		this.row = row;
		this.col = col;
	}
	/**
	 * @return The row of this coordinate
	 */
	public int getRow(){
		return this.row;
	}
	/**
	 * @return The column of this coordinate
	 */
	public int getCol(){
		return this.col;
	}
	
	@Override
	/**
	 * Coordinates are equal if same row and column
	 */
	public boolean equals(Object o){
		if(!(o instanceof Coordinates)){
			return false;
		}
		else{
			Coordinates c = (Coordinates)o;
			return(this.row == c.row && this.col == c.col);
		}
	}
	@Override
	public int hashCode(){
		//random hash code
		return (this.row*34 + this.col*7) % 51;
	}
}
