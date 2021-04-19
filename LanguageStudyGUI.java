import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.Timer;

/**
 * A language study GUI that has the user translate
 * words like a flash card game.
 *
 * @author Emily Ruttenberg
 * @version 12/15/2020
 */

public class LanguageStudyGUI extends JFrame implements ActionListener
{
    // instance variables
    private JLabel languageLabel;
    private JLabel word;
    private JLabel wordsCorrect;
    private JLabel wordsIncorrect;
    private JButton checkButton;
    private JTextField userInput;
    private WordGenerator wg = new WordGenerator();
    private int numCorrect = 0;
    private int numIncorrect = 0;
    private Timer timer;
    private Timer timer2;
    
    /**
     * Constructor creates GUI components and adds GUI 
     * components using a GridBagLayout
     * @throws IOException 
     */
    public LanguageStudyGUI() throws IOException
    {
    	//get words/answers
    	wg.readFiles();
    	wg.wordGenerating();
    	
    	//create bridbag constraints
        GridBagConstraints layoutConst = null;
         
        //set title and size
        setTitle("LanguageGUI");
        
        //instantiate fields
        languageLabel = new JLabel("Type the translation into the field below.");
        word = new JLabel(wg.getPrompt());
        wordsCorrect = new JLabel("Correct: " + numCorrect);
        wordsIncorrect = new JLabel("Incorrect: " + numIncorrect);
        checkButton = new JButton("Ok!");
        
        userInput = new JTextField();
        userInput.setEditable(true);
              
        //Use a GridBagLayout
        setLayout(new GridBagLayout());
        layoutConst = new GridBagConstraints();
        layoutConst.gridx = 0;
        layoutConst.gridy = 0;
        layoutConst.insets = new Insets(5, 5, 5, 5);
        
        //add language label
        layoutConst.gridx = 1;
        layoutConst.gridy = 0;
        layoutConst.insets = new Insets(5, 5, 5, 5);
        add(languageLabel, layoutConst);
        
        //add word prompt label
        layoutConst.gridx = 0;
        layoutConst.gridy = 1;
        layoutConst.insets = new Insets(5, 5, 5, 5);
        add(word, layoutConst);
        
        //add number of words correct label
        layoutConst.gridx = 0;
        layoutConst.gridy = 2;
        layoutConst.insets = new Insets(5, 5, 5, 5);
        add(wordsCorrect, layoutConst);
        
        //add number of words incorrect label
        layoutConst.gridx = 1;
        layoutConst.gridy = 2;
        layoutConst.insets = new Insets(5, 5, 5, 5);
        add(wordsIncorrect, layoutConst);
        
        //layout for userInput
        userInput.setPreferredSize(new Dimension(200, 20));
        userInput.setMinimumSize(userInput.getPreferredSize());
        layoutConst.gridx = 1;
        layoutConst.gridy = 1;
        layoutConst.insets = new Insets(10, 10, 10, 10);
        add(userInput, layoutConst);
        
        //GridBag layout for checkButton
        layoutConst.gridx = 2;
        layoutConst.gridy = 1;
        layoutConst.insets = new Insets(10, 10, 10, 10);
        add(checkButton, layoutConst);
        
        //action listener for pressing ok button
        checkButton.addActionListener(this);
        //action listener for pressing enter key
        userInput.addActionListener(this);

        
        //timer, checks input after five seconds of word being displayed
        timer = new Timer(5000, this);
        timer.setRepeats(true);
        timer.start();

        timer2 = new Timer(3000, null);
        timer2.setRepeats(false);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
    	//stop timer
    	timer.stop(); 
    	
    	//make textfild not editable
    	userInput.setEditable(false);
    	
        checkInput();
    	
        //timer waits 3 seconds before resetting
    	timer2.start();
    
        //reset words and fields
		try {
			refreshGUI();
		} catch (IOException event) {
			event.printStackTrace();
		}
		timer.restart();
    }
    
    /**
     * Checks answer user inputs.
     */
    public void checkInput() {
        try{
          //check if correct, not case sensitive 
          if(userInput.getText().equalsIgnoreCase(wg.getAnswer())) {
          	languageLabel.setText("Correct!");
          	
          	//update words correct label
          	numCorrect += 1;
          	wordsCorrect.setText("Correct: " + numCorrect);
          	
          } else {
          	languageLabel.setText("Incorrect! Answer: " + wg.getAnswer());
          	
          	//update words incorrect label
          	numIncorrect += 1;
          	wordsIncorrect.setText("Incorrect: " + numIncorrect);
          }
        } catch (Exception ex) {
          	ex.printStackTrace();
          }
    }
    
    /**
     * Refreshes the frame after user answers
     * @throws IOException 
     */
    public void refreshGUI() throws IOException {
    	//new prompt and answer words
    	wg.wordGenerating();

        word.setText(wg.getPrompt());
        //languageLabel.setText("Type the translation into the field below.");
        //set input field empty and editable
    	userInput.setText("");
    	userInput.setEditable(true);
    }
    
    public static void main(String[] args) throws IOException {
        //create the jframe
        LanguageStudyGUI test = new LanguageStudyGUI();
        
        //terminate program when window closes
        test.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        //change gui color
        test.getContentPane().setBackground(Color.LIGHT_GRAY);
        
        //sets size and location
        test.setPreferredSize(new Dimension(450, 150));
        test.setLocationRelativeTo(null);
        test.pack();
        
        //display window
        test.setVisible(true);
    }
}
