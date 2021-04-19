import java.util.*;
import java.io.IOException;
import java.io.FileInputStream;
import java.util.Scanner;

/**
 * Reads and returns the files with words 
 * to be translated, and their translations.
 *
 * @author Emily Ruttenberg
 * @version 12/10/2020
 */
public class WordGenerator
{ 
	ArrayList<String> englishWords = new ArrayList<String>();
    ArrayList<String> spanishWords = new ArrayList<String>();
    String prompt = "";
    String answer = "";
    String englishWord = "";
    String spanishWord = "";
    int index = 0;
    
    /**
     * Reads a file and puts the list
     * of words into an array.
     * @param arrayList arraylist of words from file
     * @param fileName file of words being put
     * into array
     * @throws IOException 
     */
    public void fileToArray(ArrayList<String> arrayList, String fileName) throws IOException {	
    	FileInputStream fileByteStream = null;
        Scanner inFS = null;
        
        fileByteStream = new FileInputStream(fileName);
        inFS = new Scanner(fileByteStream);
        
        //add words from file to arraylist, split at new lines
        while(inFS.hasNextLine()) {
        	String i = inFS.nextLine();
        	arrayList.add(i);
        }
        
        fileByteStream.close();
        inFS.close();
    }
   
    /**
     * Gets random index from arrays
     * @return random index
     */
    public int getRandomIndex() {
        Random rand = new Random();
        index = rand.nextInt(englishWords.size());
        return index;
    }
    
    /**
     * Gets the current index generated
     * @return current index
     */
    public int getIndex() {
        return index;
    }
    
    /**
     * Makes english and spanish prompt 
     * and answer placement random
     */
    public void generateWordAndAnswer(String englishWord, String spanishWord) {
        Random rnd = new Random();
        int randomWord = rnd.nextInt(2);
           
        switch(randomWord) {
        case 0: 
            prompt = englishWord;
            answer = spanishWord;
            break;
        case 1: 
            prompt = spanishWord;
            answer = englishWord;
            break;
        default: 
            break;  
        }
        
         if(prompt == englishWord) {
            answer = spanishWord;
        } else {
            answer = englishWord;
        }
    }
    
    /**
     * Gets the current promt generated
     * @return current word prompt
     */
    public String getPrompt() {
        return prompt;
    }

    /**
     * Gets the current answer generated
     * @return current answer
     */
    public String getAnswer() {
        return answer;
    }
    
    /**
     * Reads the english and spanish words files 
     * using fileToArray method
     * @throws IOException 
     */
    public void readFiles() throws IOException {
    	fileToArray(englishWords, "english.txt");
        fileToArray(spanishWords, "spanish.txt");
    }
    
    /**
     * Generates a random prompt and answer
     * @throws IOException 
     */
    public void wordGenerating() throws IOException {
        //random number for index
        int updateIndex = getRandomIndex();
        
        //get random english and spanish word at matching index
        englishWord = englishWords.get(updateIndex);
        spanishWord = spanishWords.get(updateIndex);
        
    	//get the word prompted and the answer to it
        generateWordAndAnswer(englishWord, spanishWord);
        prompt = getPrompt();
        answer = getAnswer();
    }
}
