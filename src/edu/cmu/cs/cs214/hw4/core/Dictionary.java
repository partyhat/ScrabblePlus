package edu.cmu.cs.cs214.hw4.core;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Scanner;


public class Dictionary {
	public HashSet<String> dictionary = new HashSet<String>();
	public boolean isValidWord(String word){
		if(dictionary.contains(word)){
			return true;
		}
		else{
			return false;
		}
	};
	
	public Dictionary(){
		File words = new File("assets/words.txt");
		Scanner scanner = null;
		try {
			scanner = new Scanner(words);
		} 
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		while(scanner.hasNext()){
			dictionary.add(scanner.next());
		}
		scanner.close();
	}
	

}
