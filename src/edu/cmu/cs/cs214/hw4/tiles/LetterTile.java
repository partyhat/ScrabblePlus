package edu.cmu.cs.cs214.hw4.tiles;


public class LetterTile extends Tile {

	private int value;
	private String letter;
	
	public LetterTile(String letter, int value){
		this.letter = letter;
		this.value = value;
	}

	@Override
	public String getIdentifier() {
		return letter;
	}

	public int getValue(){
		return value;
	}
	@Override
	public boolean equals(Object o){
		if(!(o instanceof LetterTile)){
			return false;
		}
		else{
			return ((LetterTile)o).letter.equals(this.letter);
		}
	}

}
