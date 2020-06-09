

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Scanner;

public class WordConvertor {
	
	//The function find the best path(route) of all paths
	public static ArrayList<String> bestPath(HashSet<String> dict, String startWord, String endWord) {
		//Create a queue of Strings
		LinkedList<String> queue = new LinkedList<String>();
		//Create a Hashmap that can hold 2 Strings this is very helpful for the exercise
		HashMap<String, String> backtracking = new HashMap<String, String>();
		//Create a HashSet of Strings to know which words we have visisted
		HashSet<String> visitedWords = new HashSet<String>();
		//Create an ArrayList of Strings for the path
		ArrayList<String> path = new ArrayList<String>();
		//We insert our first word into the queue and into the our visisted Words
		queue.add(startWord);
		visitedWords.add(startWord);
		//While our queue isn't empty do the following
		while (!queue.isEmpty()) {
			//We put the first word of our queue into a String named word
			String word = queue.remove();
			//We call the function transformOneLetter with our word and the dictionary
			//The function returns our newWord where one letter has changed
			for (String newWord : transformOneLetter(word, dict)) {
				//if its not our visited HashSet then we add it and we also add the newWord and the previews
				//word to our Hashmap. This is gonna be useful to our backtracking to find the best path
				if (!visitedWords.contains(newWord)) {
					visitedWords.add(newWord);
					backtracking.put(newWord, word);
					//if the new word is the same as our final word then we call the backtrack
					//in order to to create our path and to return it 
					if (newWord.equals(endWord)) {
						backtrack(startWord, newWord, backtracking, path);
						break;
					}
					//we add the newWord to our queue if its not equal to the final
					queue.add(newWord);
				}
			}
		}
		return path;
	}
    //This is a function to find just a route to the final words
	public static ArrayList<String> route(HashSet<String> dict, String startWord, String endWord) {
		//Create a queue of Strings
		LinkedList<String> queue = new LinkedList<String>();
		//Create an ArrayList for our path
		ArrayList<String> path = new ArrayList<String>();
		//Create a HashSet of Strings to know which words we have visisted
		HashSet<String> visitedWords = new HashSet<String>();
		//We add the starting word to our queue and to our path and visitedWord
		queue.add(startWord);
		path.add(startWord);
		visitedWords.add(startWord);
		//while the queue isn't empty do the following
		while (!queue.isEmpty()) {
			//We put the first String of the queue to a String word
			String word = queue.remove();
			//We transform the word by calling the function transformOneLetter which change our word
			//by one letter that is equal to one word in our dictionary
			for (String newWord : transformOneLetter(word, dict)) {
				//if we didnt took that route then we continue adding words to our path
				if (!visitedWords.contains(newWord)) {
					visitedWords.add(newWord);
				     path.add(newWord);
				//if the newWord is the same as the final word then return the path
				if (newWord.equals(endWord)) {
						
						return path;
					}
				//if its not the final word add the newWord to our queue
					queue.add(newWord);
				}
			}
		}
		//if the words aren't in the dictionary then return a clear path
		path.clear();
		return path;
	}
    // This function transforms the given word into a newWord by changing one letter
	private static HashSet<String> transformOneLetter(String word, HashSet<String> dictionary) {
		//We create a HashSet of String named path
		HashSet<String> path = new HashSet<String>();
		//We go throw our words length in order to change every letter of the word
		for (int i = 0; i < word.length(); ++i) {
			//I used the StringBuilder which is easy to to build Strings with this
			StringBuilder sb = new StringBuilder(word);
			//Going throw every letter and change it 
			for (char letter = 'a'; letter <= 'z'; ++letter) {
				if (sb.charAt(i) != letter) {
					sb.setCharAt(i, letter);
					//each time we build a String with a change of one letter
					String newWord = sb.toString();
					//we check if the word we build is in our dictionary
					if (dictionary.contains(newWord)) {
						//if it is we add it to our HashSet and we return it
						path.add(newWord);
					}
				}
			}
		}
		return path;
	}
    //This function creates the path we want(the best path)
	private static void backtrack(String startWord, String newWord, HashMap<String, String> backtracking,
			ArrayList<String> path) {
		//put the new word to our path
		path.add(newWord);
		//we get the HashMap and we put our words in order every word goes in front 
		// until we find the starting word 
		while (backtracking.containsKey(newWord)) {
			if (newWord.equals(startWord))
				break;
			newWord = backtracking.get(newWord);
			path.add(0, newWord);
		}
	}
	public static void main(String[] args) throws IOException {

		System.out.println("Please enter the Starting Word and the Ending Word:");
		Scanner sc = new Scanner(System.in);
		System.out.println("Starting word:");
		//Input starting word
		String startWord = sc.next();
		System.out.println("Ending word:");
		//Input ending word
		String endWord = sc.next();
		File f = new File(args[0]);
		BufferedReader br = new BufferedReader(new FileReader(f));

		String st;

		int i = 0;
		//find how many words there are in the txt file
		while ((st = br.readLine()) != null) {
			i++;
		}
		//array of Strings for our dictionary
		String words[] = new String[i];
		BufferedReader BR = new BufferedReader(new FileReader(f));

		String text;

		i = 0;
		//Fill the array with the Strings of the txt file
		while ((text = BR.readLine()) != null) {
			words[i] = text;
			i++;
		}
		//Create our HashSet (our dictionary) just putting the array and it fill it
		HashSet<String> dictionary = new HashSet<String>(Arrays.asList(words));
		System.out.println("Choose which of the followings you want:(1,2,3) \n"
				+ "1: Find a route for the start word to convert into the other \n"
				+ "2: Find the best route for the start word to convert into the other \n" + "3: Exit ");
		//give which option you want
		int answer = sc.nextInt();
		//Give the correct option
		while(answer<=0 || answer>3) {
			System.out.println("Wrong output please enter one of the followings:(1,2,3)");
			answer = sc.nextInt();
		}
			
		switch (answer) {
		case 1:
			
			long startTime = System.currentTimeMillis();
			//print the path(ArrayList)
			System.out.println(route(dictionary, startWord, endWord));
			long endTime = System.currentTimeMillis();
			//give the time the function needed to executed
			System.out.println("That took " + (endTime - startTime) + " milliseconds");
			break;
		case 2:
			 startTime = System.currentTimeMillis();
			 //print the bestPath(ArrayList)
			System.out.println(bestPath(dictionary, startWord, endWord));
			 endTime = System.currentTimeMillis();
			//give the time the function needed to executed
			System.out.println("That took " + (endTime - startTime) + " milliseconds");
			break;
		case 3:
			System.out.println("You have ended the program");
		}
	}
}
