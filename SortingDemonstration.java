/*
Java™ Project: ICS4U
Package: sorting
Class: SortingDemonstration
Programmer: Shaan Banday

Date Created: Thursday, April 7th, 2022.
Date Completed: Tuesday, April 19th, 2022.

Description: This program serves as a visual teaching aid to demonstrate how the Bubble Sort Algorithm works. The following class does not have a main 
method, but it constructs all the graphical elements that the SortingDemonstrationMain class calls to create the GUI. The constructor itself (this 
first method) is spilt into many constructors since this class is noticeably big and has many variables it needs to initialise. This class also 
implements an ActionListener method and a KeyListener method. To draw certain things, like the prompt to take the inputs and the state of the swapped 
variable, the class interacts with the paint method, which makes it easier to update often, compared to just using JLabels which are always the same 
text throughout the program's run.

This class takes care of taking the input of 10 integers with a maximum of three digits (negative numbers are allowed) from the user to then put them 
in ascending order. To store the values for those numbers and keep track of their position on the screen, the Object-Oriented Programming principle of 
encapsulation is implemented by using a separate Element class. It becomes particularly useful during the sorting process to know when the numbers 
should stop moving.

At the beginning of the program, music is played, instructions are given, and upon pressing the bubble sort button, users can then enter their desired 
integers. The order of the array before sorting is simply the order the numbers is entered in. During the sorting, the numbers are moved around, and 
the largest number is “bubbled” to the top, hence the name bubble sort. To help with understanding the algorithm, a message is displayed which says 
that the variable sorted must be false for 8 comparisons in a row for everything to be sorted. The value of the Boolean variable is displayed using 
the paint method. At the end of the program, a victory song is played, and the number of comparisons made is displayed (which for an array of 10 items
would be 90 or (n)*(n – 1). The time complexity is also displayed.
*/

package sorting; //Launch the class from this package named "sorting"

//Import graphical elements
import java.awt.*; //Import the package with all the graphical objects
import javax.swing.*; //Import the package with more graphical objects
import java.awt.event.*; //Import the Action Listener Class
import javax.swing.border.EtchedBorder; //Import the border class
import javax.swing.text.*; //Import the text editor class

//Import Timer elements
import java.util.Timer; //Import the Timer class to run a task at an certain interval
import java.util.TimerTask; //Import the TimerTask Class for the actual task that needs to be ran over and over again

//Import Audio elements
import javax.sound.sampled.AudioInputStream; //Import the AudioInputStream class which calls the right audio file
import javax.sound.sampled.AudioSystem; //Import the AudioSystem class which connects the AudioInputStream to the Clip
import javax.sound.sampled.Clip; //Import the Clip class to play and stop the music
import java.io.File; //Import the File class to actually open the Audio files

public class SortingDemonstration extends JFrame implements ActionListener, KeyListener //Name of class being referencing by SortingDemonstrationMain
{ //This class implements an ActionListener to respond to button clicks and a KeyListener to respond to key strokes
	/**
	 * 
	 */
	private static final long serialVersionUID = -2241783190395585120L; 

	//Declare all graphical objects
	private JPanel sortingPanel; //Panel to hold everything
	private JButton bubSortButton, takeInputButton, backToStart; //The various buttons do different things
	private JLabel title, bubbleTitle, doneTitle, bubbleInstruction1, bubbleInstruction2; //All the JLabels which display text
	private JTextPane subheading, numberPromptSubheading; //The text pane to display the sub-headings. Better than JLabels since they can wrap text
	private JTextField numTextBox; //Text box to take the integers from the users
	private StyledDocument sortingTextStyle; //The style for the JTextPanes to centre the text always, no matter the size
	private SimpleAttributeSet centerAlignment; //The aforementioned centre alignment
	private Font titleFont, promptTitleFont, subheadingFont, buttonFont, numberFont; //All of the fonts in the program
	private Color purple, magenta, cyan; //These three colours are custom colours which enhance the overall aesthetic of the GUI
	private Rectangle promptArea, comparisonTitle, timeComplexTitle; //Rectangles to hold painted text
	private Image cursorImage, shakerSortImage, insertionSortImage; //Image of the cursor
	private Cursor sortingCursor; //Cursor inside the JFrame 
	
	//Declare Timer and Audio Objects
	private Timer bubbleTimer; //Timer for the sorting
	private AudioInputStream tetrisStream, superMarioStream; //Two AudioInputStreams for each song to be played
	private Clip tetrisThemeSong, marioWinThemeSong; //The clips of the songs to be played
	
	//Declare all arrays
	private Element [] userArray; //Array of Element Objects
	private JTextPane [] numbersAsGraphic; //Array of JTextPanes, which show the visual display of the numbers as they move
	
	//Declare primitive variables
	private byte programState; //Byte to keep track of the state of the program. Can either be 1, 2, or 3
	private byte trackIndex; //Byte to keep track of the index being referenced
	private int comparisons, finalComparisons; //Integers to hold certain values during the sorting
	private boolean up, first, doneSort, swapped; //Booleans to hold certain values during the sorting
	
	//Declare all constants
	private static final short F_WIDTH = 1150, F_HEIGHT = 700; //Width and Height of the JFrame
	private static final byte SPEED_OF_SORTING = 1; //Speed of the sorting
	private static final short DELAY = 700; //Delay while the numbers are highlighted and before they are moved.
	
	public SortingDemonstration() //Constructor which initialises the object, and displays all the proper graphical components.
	{		
		super("Sorting Demonstration"); //Name of JFrame/window
		
		//Call extension methods for the constructor
        constructGUI(); //Construct the GUI
        initialisePrimsArrays(); //Initialise all the primitive data types and arrays
        constructCursorAndImages(); //Construct the cursor for the JPanel
        constructColours(); //Construct all the colours
        constructBorder(); //Construct the border
        constructRectangles(); //Construct the rectangles
        constructFonts(); //Construct the fonts
        constructLabels(); //Construct the JLabel
        constructButtons(); //Construct the buttons
        constructPrompts(); //Construct the prompt elements
		
		playTetris(); //Play the Tetris music
		
		this.repaint(); //Invoke paint method as final constructor line and update the screen
	}
	
	//If any method below is private, it is because other classes do not need access to it
	//If any method below is public, it is because it's visibility cannot be reduced because it is inherited (ActionListener, Paint, KeyListener)
	
	private void constructGUI() //Private constructor method which is an extension of the original constructor to set all the GUI elements
	{
		//GUI Initialisations
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Terminate the program, and close the window if the close button is hit
        this.setSize(F_WIDTH, F_HEIGHT); //Set the size of the window (JFrame) in pixels to the constants initialised outside the constructor
        this.setResizable(false); //Window is unable to be resized.
        this.setVisible(true); //Everything in the JFrame is visible, unless otherwise specified  
		this.addKeyListener(this); //Add the key listener
		this.setFocusable(true); //Make it the focal point if it is used
		
		//Initialise JPanel
        this.sortingPanel = (JPanel)this.getContentPane(); //Create a JPanel to organise contents in the JFrame/Window  
        this.sortingPanel.setLayout(null); //Assign no layout (null) to the JPanel
	}

	private void initialisePrimsArrays() //Private constructor method which is an extension of the original constructor to set variables and arrays
	{
        //Initialise all non-object variables
		this.programState = 0; //Set the state of the program to 0 by default, which means the main menu is displayed
		this.userArray = new Element [10]; //Initialise the size of the userArray
		this.numbersAsGraphic = new JTextPane[this.userArray.length]; //Make the JTextPane array the same size
		this.trackIndex = 0; //Set the index tracker to 0
		this.comparisons = 0; //Set the number of comparisons done to 0
		this.finalComparisons = 0; //Set the number of final comparisons done to 0
		this.up = false; //Set up to false. This becomes true when the numbers need to stop moving upwards during the swap
		this.first = true; //Set first to true. This becomes false after the first run of the Task, which invokes a delay at the start of a comparison
		this.doneSort = false; //Set doneSort to false. This becomes true after everything is done sorting
		this.swapped = false; //A variable to mimic the original bubble sort. Displaying its value will help the user understand how bubble sort works
	}
	
	private void constructCursorAndImages() //Private constructor method which is an extension of the original constructor to set the cursor
	{
		//Initialise Cursor elements
        this.cursorImage = Toolkit.getDefaultToolkit().getImage("cursor.png"); //Set the image for the cursor
        this.sortingCursor = Toolkit.getDefaultToolkit().createCustomCursor(this.cursorImage, new Point (0,0), "sortingCursor"); //Set the cursor
        this.sortingPanel.setCursor(this.sortingCursor); //Add the cursor to the JPanel
        
        this.shakerSortImage = Toolkit.getDefaultToolkit().getImage("shakerSort.gif"); //Set the image for shaker sort
        this.insertionSortImage = Toolkit.getDefaultToolkit().getImage("insertionSort.gif"); //Set the image for insertion sort
	}
	
	private void constructColours() //Private constructor method which is an extension of the original constructor to set the colours
	{
		this.purple = new Color (55, 40, 105); //Dark purple colour for the background
		this.magenta = new Color (213, 10, 128); //Light purpleish colour for most of the text
		this.cyan = new Color (55, 176, 195); //Blueish colour of the buttons and other text
	}
	
	private void constructBorder() //Private constructor method which is an extension of the original constructor to set the border for the JPanel
	{
        this.sortingPanel.setBorder(BorderFactory.createMatteBorder(10, 10, 10, 10, this.cyan)); //Set a cyan border for the JPanel
        this.sortingPanel.setBackground(this.purple); //Set the colour of the background as the custom purple.
	}
	
	private void constructRectangles() //Private constructor method which is an extension of the original constructor to set the rectangles
	{
		this.promptArea = new Rectangle (0, 380, 1150, 80); //Rectangle to hold integer prompt 
		this.comparisonTitle = new Rectangle (10, 450, 565, 80); //Rectangle to hold the number of comparisons
		this.timeComplexTitle = new Rectangle (565, 450, 565, 80); //Rectangle to hold the time complexity title
	}
	
	private void constructFonts() //Private constructor method which is an extension of the original constructor to set the fonts
	{
		this.titleFont = new Font("Rockwell", Font.BOLD, 90); //Font for the programs title
		this.subheadingFont = new Font("Rockwell", Font.BOLD, 40); //Font for the sub-heading text on the main menu
		this.buttonFont = new Font ("Rockwell", Font.ITALIC, 50); //Font for the buttons
		this.promptTitleFont = new Font("Rockwell", Font.BOLD, 70); //Font for the integer prompt
		this.numberFont = new Font("Rockwell", Font.BOLD, 50); //Font for the numbers on the screen that are being sorted
	}
	
	private void constructLabels() //Private constructor method which is an extension of the original constructor to set the JLabels
	{
		//Title Label
		this.title = new JLabel("How Does Sorting Work?"); //Set string for the title label
		this.title.setHorizontalAlignment(SwingConstants.CENTER); //Make the title centre aligned
		this.title.setBounds(new Rectangle(0, 10, 1130, 120)); //Set the bounds of the title within the JFrame
		this.title.setForeground(this.magenta); //Set the colour of the title to magenta
		this.title.setFont(this.titleFont); //Set the font of the title
		this.sortingPanel.add(this.title); //Add the title to the JPanel
		
		//Sub-heading JTextPane. It it more versatile than JLabels since it allows multiple lines and automatically wraps the text
		this.subheading = new JTextPane(); //Declare a new text pane to hold the sub-heading
		this.subheading.setText("Click the button below for an explanation of Bubble Sort. See how the algorithm sorts an array of 10 integers "
				+ "inputted by you in acsending order (negative numbers ARE allowed)."); //Set the text for the sub-heading
		
		this.sortingTextStyle = this.subheading.getStyledDocument(); //Declare the text style for the text pane
		this.centerAlignment = new SimpleAttributeSet(); //Declare the attribute for the text pane
		StyleConstants.setAlignment(this.centerAlignment, StyleConstants.ALIGN_CENTER); //Set the attribute to a centre alignment
		this.sortingTextStyle.setParagraphAttributes(0, this.sortingTextStyle.getLength(), 
				this.centerAlignment, false); //Apply this attribute to the text style for the text pane
		this.subheading.setBounds(new Rectangle(11, 200, 1112, 190)); //Set the bounds for the sub-heading within the JFrame
		this.subheading.setForeground(this.magenta); //Set the colour of the sub-heading to magenta
		this.subheading.setBackground(this.sortingPanel.getBackground()); //Set it's background to match whatever the JPanel has
		this.subheading.setHighlighter(null); //Do not allow the user to highlight the text
		this.subheading.setEditable(false); //Do not allow the user to edit the text
		this.subheading.setFont(this.subheadingFont); //Set the font of the sub-heading
		this.sortingPanel.add(this.subheading); //Add the sub-heading to the JPanel
		
		//Bubble Sort Title (title on the second screen when programState = 1)
		this.bubbleTitle = new JLabel("You Have Chosen Bubble Sort"); //Set string for the bubble sort title
		this.bubbleTitle.setHorizontalAlignment(SwingConstants.CENTER); //Make the bubble sort title centre aligned
		this.bubbleTitle.setBounds(new Rectangle(0, 10, 1150, 80)); //Set the bounds of the bubble sort title within the JFrame
		this.bubbleTitle.setForeground(this.magenta); //Set the colour of the bubble sort title to magenta
		this.bubbleTitle.setFont(this.promptTitleFont); //Set the font style of the bubble sort title
		this.bubbleTitle.setVisible(false); //Make it invisible for now
		this.sortingPanel.add(this.bubbleTitle); //Add the bubble sort title to the JPanel
		
		//First Line of the Bubble Sort Message (message on the second screen when programState = 1)
		this.bubbleInstruction1 = new JLabel("The array is sorted when swapped = false for"); //Set string for the first line of the bubble sort message
		this.bubbleInstruction1.setHorizontalAlignment(SwingConstants.CENTER); //Make the first line of the message centre aligned
		this.bubbleInstruction1.setBounds(new Rectangle(0, 50, 1130, 60)); //Set the bounds of the first line of the message within the JFrame
		this.bubbleInstruction1.setForeground(this.magenta); //Set the colour of the first line of the message to magenta
		this.bubbleInstruction1.setFont(this.subheadingFont); //Set the font style of the first line of the message
		this.bubbleInstruction1.setVisible(false); //Make it invisible for now
		this.sortingPanel.add(this.bubbleInstruction1); //Add the first line of the message to the JPanel
		
		//Second Line of the Bubble Sort Message (message on the second screen when programState = 2)
		this.bubbleInstruction2 = new JLabel("8 comparisons in a row"); //Set string for the second line of the bubble sort message
		this.bubbleInstruction2.setHorizontalAlignment(SwingConstants.CENTER); //Make the second line of the message centre aligned
		this.bubbleInstruction2.setBounds(new Rectangle(0, 100, 1130, 60)); //Set the bounds of the second line of the message within the JFrame
		this.bubbleInstruction2.setForeground(this.magenta); //Set the colour of the second line of the message to magenta
		this.bubbleInstruction2.setFont(this.subheadingFont); //Set the font style of the second line of the message
		this.bubbleInstruction2.setVisible(false); //Make it invisible for now
		this.sortingPanel.add(this.bubbleInstruction2); //Add the second line of the message to the JPanel
		
		//Title to be Displayed at the End of the Sort (when programState = 2)
		this.doneTitle = new JLabel("Sorting Done!"); //Set string for the title to be displayed at the end of the sort
		this.doneTitle.setHorizontalAlignment(SwingConstants.CENTER); //Make the end of sort title centre aligned
		this.doneTitle.setBounds(new Rectangle(0, 10, 1150, 120)); //Set the bounds of the end of sort title within the JFrame
		this.doneTitle.setForeground(this.magenta); //Set the colour of the end of sort title to magenta
		this.doneTitle.setFont(this.titleFont); //Set the font style of the end of sort title
		this.doneTitle.setVisible(false); //Make it invisible for now
		this.sortingPanel.add(this.doneTitle); //Add the end of sort title to the JPanel
	}
	
	private void constructButtons() //Private constructor method which is an extension of the original constructor to set the buttons
	{	
        //Bubble Sort Button, which when pressed, takes the user to the second screen to take the integers
		this.bubSortButton = new JButton("Bubble Sort"); //Set the name of the bubble sort button 
		this.bubSortButton.setBounds(new Rectangle (356, 450, 425, 80)); //Set the bounds of the bubble sort button within the JFrame
		this.bubSortButton.setEnabled(true); //Enable the bubble sort button
        this.bubSortButton.addActionListener(this); //Add an action listener to respond to a button click
        this.bubSortButton.setFocusable(false); //Set the bubble sort button to not be focusable with a tab press
        this.bubSortButton.setBackground(this.cyan); //Set the background of the bubble sort button to cyan
        this.bubSortButton.setForeground(Color.BLACK); //Set the text colour to black to contrast with the cyan
        this.bubSortButton.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED, Color.BLACK, Color.BLACK)); //Add a black border to the button
        this.bubSortButton.setFont(this.buttonFont); //Set the font of the bubble sort button
        this.sortingPanel.add(this.bubSortButton); //Add the bubble sort button to the panel
        
        //Taking the Integer Input From the User Button
        this.takeInputButton = new JButton("Take the Integer"); //Set the name of the integer taking button
        this.takeInputButton.setBounds(new Rectangle (368, 550, 400, 80)); //Set the bounds of the integer taking button within the JFrame
        this.takeInputButton.addActionListener(this); //Add an action listener to respond to a button click
        this.takeInputButton.setFocusable(false); //Set the integer taking button to not be focusable with a tab press
        this.takeInputButton.setBackground(this.cyan); //Set the background of the integer taking button to cyan
        this.takeInputButton.setForeground(Color.BLACK); //Set the text colour to black to contrast with the cyan
        this.takeInputButton.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED, Color.BLACK, Color.BLACK)); //Add a border to the button
        this.takeInputButton.setFont(this.buttonFont); //Set the font of the integer taking button
        this.takeInputButton.setVisible(false); //Make the integer taking button invisible for now
        this.takeInputButton.setEnabled(false); //Disable the integer taking button for now
        this.sortingPanel.add(this.takeInputButton); //Add the integer taking button to the panel
        
        //Back to Start Button
        this.backToStart = new JButton("Back To Start"); //Set the name of the back to start button
        this.backToStart.setBounds(new Rectangle (368, 500, 400, 80)); //Set the bounds of the back to start button within the JFrame
        this.backToStart.addActionListener(this); //Add an action listener to respond to a button click
        this.backToStart.setFocusable(false); //Set the back to start button to not be focusable with a tab press
        this.backToStart.setBackground(this.cyan); //Set the background of the back to start button to cyan
        this.backToStart.setForeground(Color.BLACK); //Set the text colour to black to contrast with the cyan
        this.backToStart.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED, Color.BLACK, Color.BLACK)); //Add a border to the button
        this.backToStart.setFont(this.buttonFont); //Set the font of the back to start button
        this.backToStart.setVisible(false); //Make the back to start invisible for now
        this.backToStart.setEnabled(false); //Disable the back to start button for now
        this.sortingPanel.add(this.backToStart); //Add the back to start to the panel
	}
	
	private void constructPrompts() //Private constructor method which is an extension of the original constructor to set the prompts
	{	
		//Number prompt sub-heading JTextPane (on the second screen when programState = 1)
		this.numberPromptSubheading = new JTextPane(); //Declare a new text pane to hold the message
		this.numberPromptSubheading.setText("Enter an integer below with a maximum of 3 digits (negatives ARE allowed). The algorithm works for any size "
				+ "of number, there is just limited space on the screen. After, press the button below or the ENTER key "
				+ "on your keyboard to store the number."); //Set the text for the message
		
		this.sortingTextStyle = this.numberPromptSubheading.getStyledDocument(); //Declare the text style for the text pane message
		this.centerAlignment = new SimpleAttributeSet(); //Declare the attribute for the text pane message
		StyleConstants.setAlignment(centerAlignment, StyleConstants.ALIGN_CENTER); //Set the attribute to a centre alignment
		this.sortingTextStyle.setParagraphAttributes(0, this.sortingTextStyle.getLength(), 
				this.centerAlignment, false); //Apply this attribute to the text style for the text pane message
		this.numberPromptSubheading.setBounds(new Rectangle(11, 110, 1112, 250)); //Set the bounds for the message within the JFrame
		this.numberPromptSubheading.setForeground(this.magenta); //Set the colour of the message to magenta
		this.numberPromptSubheading.setBackground(this.sortingPanel.getBackground()); //Set it's background to match whatever the JPanel has
		this.numberPromptSubheading.setHighlighter(null); //Do not allow the user to highlight the text
		this.numberPromptSubheading.setEditable(false); //Do not allow the user to edit the text
		this.numberPromptSubheading.setFont(this.subheadingFont); //Set the font of the message
		this.numberPromptSubheading.setVisible(false); //Make the message invisible for now
		this.sortingPanel.add(this.numberPromptSubheading); //Add the message text to the JPanel
		
		//Text Box to Hold the Integer Inputs
		this.numTextBox = new JTextField(); //Create a new text field to take in the input integer to be sorted
		this.numTextBox.setHorizontalAlignment(JTextField.CENTER); //Make it centre aligned
		this.numTextBox.setBounds(new Rectangle(465, 420, 200, 100)); //Set the bounds of the text box within the JFrame
		this.numTextBox.addKeyListener(this); //Add a key listener to respond when a user presses ENTER
		this.numTextBox.setForeground(this.magenta); //Set the text colour to magenta
		this.numTextBox.setFont(this.promptTitleFont); //Set the font of the text box
		this.numTextBox.setBorder(BorderFactory.createMatteBorder(5, 5, 5, 5, this.cyan)); //Add a border to the text box
		this.numTextBox.setVisible(false); //Make the text box invisible for now
		this.numTextBox.setEnabled(false); //Make the text box disabled for now
		this.sortingPanel.add(this.numTextBox); //Add the text box to the JPanel
	}
	
	public void actionPerformed(ActionEvent e) //Inherited ActionListener method, to be invoked when a button is pressed
	{
		//Declare all Variables
		String buttonName = e.getActionCommand(); //String variable that replicates the button name
		
		//Decisions
		switch (buttonName) //Switch statement based on what the button name is
		{
			case "Back To Start": //If the button name is "Back To Start"
				toMainMenu(); //Call the toMainMenu method to handle all the logic for switching menus
				break;
				
			case "Bubble Sort": //If the button name is "Bubble Sort" which means the Bubble Sort Button is pressed
				toNumberPrompt(); //Call the toNumberPrompt method to handle all the logic for switching menus
				break;
				
			case "Take the Integer": //If the button name is "Take the Integer" which means the Integer Input Button is pressed
				checkIfValid(); //Check if the input is valid
				break;	
		}
		repaint(); //After a button is pressed, update the screen and repaint 
	}
	
	private void toMainMenu() //Private command method to handle all the logic of the switching from the sorting menu to the main menu
	{
		this.marioWinThemeSong.close(); //Stop playing the Mario Win theme song
		playTetris(); //Play the Tetris theme song
		
		this.doneTitle.setVisible(false); //Make the End Title invisible
		this.backToStart.setVisible(false); //Make the Back to Start Button Invisible
		this.backToStart.setEnabled(false); //Disable the Back to Start Button
		
		this.programState = 0; //Set the state of the program to 0, which means the main menu is displayed
		
		//Lööps
		for (int i = 0; i < this.numbersAsGraphic.length; i++) //Start the loop at zero, and iterate through all elements of the numberAsGraphic array
		{
			this.numbersAsGraphic[i].setVisible(false); //Make the given JTextPane at element i invisible
		}
		
		initialisePrimsArrays(); //Reset all the variables
		
		this.title.setVisible(true); //Make the Title as visible
		this.subheading.setVisible(true); //Make the Sub-heading as visible
		this.bubSortButton.setVisible(true); //Make the Bubble Sort Button as visible
		this.bubSortButton.setEnabled(true); //Enable the Bubble Sort Button
		repaint(); //Update the screen and repaint 
	}
	
	private void toNumberPrompt() //Private command method to handle all the logic of the switching from the main menu to the number prompt menu
	{
		this.programState = 1; //Change the program state to 1, which is the prompt screen
		this.title.setVisible(false); //Make the Title invisible
		this.subheading.setVisible(false); //Make the Sub-heading invisible
		this.bubSortButton.setVisible(false); //Make the Bubble Sort Button invisible
		this.bubSortButton.setEnabled(false); //Disable the Bubble Sort Button
	
		this.bubbleTitle.setVisible(true); //Make the Bubble Sort Title Visible
		this.numberPromptSubheading.setVisible(true); //Make the Prompt Sub-heading visible
		this.numTextBox.setVisible(true); //Make the Text Box visible
		this.numTextBox.setEnabled(true); //Enable the Text Box
		this.takeInputButton.setVisible(true); //Make the Take Input Button visible
		this.takeInputButton.setEnabled(true); //Make the Take Input Button Enabled
	}
	
	private void checkIfValid() //Private command method to check if the user input is valid or not
	{
		//Try and Catch Statement
		try //Try to take the input
		{
			//Decisions
			if ((Integer.parseInt(this.numTextBox.getText()) < 1000) && (Integer.parseInt(this.numTextBox.getText()) > -100))
			{
				this.userArray[this.trackIndex] = new Element(Short.parseShort(this.numTextBox.getText())); //Take the input from the text box
				//Decisions
				if (trackIndex != 9) //So long as the index being referenced is NOT 9
				{
					this.trackIndex++; //Increase the index
				}
				
				else //Otherwise, if the index being referenced IS 9
				{
					this.programState = 2; //Set the program state 10 2
					this.trackIndex = 0; //Reset the index reference
					
					toSortingScreen(); //Switch screens
				}
				
				this.numTextBox.setText(null); //Clear the text box
			}
		}
		
		catch (NumberFormatException babatunde) {} //Catch any exception if a user inputs an invalid integer or a number over 2147483647 (max for int)
	}
	
	private void toSortingScreen() //Private command method to handle all the logic of the switching from the prompt menu to the sorting menu
	{
		this.bubbleTitle.setVisible(false); //Make the Bubble Sort Title
		this.numberPromptSubheading.setVisible(false); //Make the Number Prompt Sub-heading invisible
		this.numTextBox.setVisible(false); //Make the Number Text Box invisible
		this.numTextBox.setEnabled(false); //Disable the Number Text Box
		this.takeInputButton.setVisible(false); //Make the Take Input Button invisible
		this.takeInputButton.setEnabled(false); //Disable the Take Input Button
		
		this.bubbleInstruction1.setVisible(true); //Make Line 1 of the Bubble Sort Instructions Visible
		this.bubbleInstruction2.setVisible(true); //Make Line 2 of the Bubble Sort Instructions Visible
		
		//Declare Constants 
		final int START_X = 27; //The starting x-position of the first number
		final int START_Y = 270; //The starting y-position of the first number
		final int GAP = 20; //The gap between the numbers
		
		//Lööps
		for (int j = 0; j < this.numbersAsGraphic.length; j++) //Start the loop at 0, and iterate through every element of the array
		{
			this.numbersAsGraphic[j] = new JTextPane(); //Declare a new text pane to hold the message at whatever element j is
			this.numbersAsGraphic[j].setText(userArray[j].toString()); //Set the text at j to the index in the element array at j
			
			this.sortingTextStyle = this.numbersAsGraphic[j].getStyledDocument(); //Declare the text style for the number display at index j
			this.centerAlignment = new SimpleAttributeSet(); //Declare the attribute for the number display at index j
			StyleConstants.setAlignment(this.centerAlignment, StyleConstants.ALIGN_CENTER); //Set the attribute to a centre alignment
			this.sortingTextStyle.setParagraphAttributes(0, this.sortingTextStyle.getLength(), 
					this.centerAlignment, false); //Apply this attribute to the text style for the number display at index j
			
			//Decisions
			if (j == 0) //If it the index is 0 (first element)
			{
				this.numbersAsGraphic[j].setBounds(new Rectangle(START_X, START_Y, 90, 90)); //Set the bounds for the number display  within the JFrame
			}
			
			else //Otherwise, for every other element
			{
				this.numbersAsGraphic[j].setBounds(new Rectangle(this.numbersAsGraphic[j-1].getX() + GAP + this.numbersAsGraphic[j-1].getWidth(), 
						START_Y, 90, 90)); //Set the bounds for the number display at index j within the JFrame
			}
		
			this.userArray[j].setLocation((short)this.numbersAsGraphic[j].getX(), 
					(short)this.numbersAsGraphic[j].getY()); //Keep track of their original location
			
			//Set Number Display Attributes
			this.numbersAsGraphic[j].setForeground(this.magenta); //Set the colour of the number display at index j to magenta
			this.numbersAsGraphic[j].setBackground(this.cyan); //Set the number display at index j's background to cyan
			this.numbersAsGraphic[j].setHighlighter(null); //Do not allow the user to highlight the text for the number display at index j
			this.numbersAsGraphic[j].setEditable(false); //Do not allow the user to edit the text for the number display at index j
			this.numbersAsGraphic[j].setFont(this.numberFont); //Set the font of the number display at index j
			this.numbersAsGraphic[j].setVisible(true); //Make the number display at index visible
			this.sortingPanel.add(this.numbersAsGraphic[j]); //Add the number display at index j to the JPanel
		}
		
		this.trackIndex = 0; //Reset trackIndex to 0
		bubbleSort(); //Invoke the bubbleSort method to actually start the bubble sort
	}
	
	private void bubbleSort () //Private command method to construct the timer and set the parameters for the timer task
	{
		//Declare the Timer elements and call the sub-class BubbleMove()
		this.bubbleTimer = new Timer(); //Initialise a new Timer object
		this.bubbleTimer.schedule(new BubbleMove(), 0, SPEED_OF_SORTING); //Schedule a task called BubbleMove with no delay, and at a certain speed
		//this.bubbleTimer.scheduleAtFixedRate(new BubbleMove(), 0, SPEED_OF_SORTING);
		
		/*The comment above is another line which makes the sorting go very fast. Since this is an educational teaching tool, I scheduled it with the
		  Thread's own clock, every 1 millisecond, which goes very slowly. It goes slowly so people can actually understand how the sorting algorithm 
		  works. However, for the sake of debugging and testing, if the original task is commented out and the line below it is uncommented, it will sort 
		  much faster. This is because scheduling the timer at a fixed rate does it with the computer's clock, which will never slow down, whereas the 
		  Thread's clock may slow down, and is not always 1 thread second per real second.
		  
		  So, if you want the sorting to go faster, comment the first schedule and uncomment the second one.
		  
		  Moreover, since the task occurs every millisecond, the programs refresh rate is 1000 frames per second or 1000 Hz (at least I think)*/
	}
	
	class BubbleMove extends TimerTask //Sub-class (class within a class), that executes the task the Timer will execute every 1 millisecond 
	{
		@Override
		public void run() //When BubbleMove() is called, by default, it goes to the run() method
		{			
			highlightNumbers(); //Highlight numbers being referenced 
			delayTask(); //Potentially invoke a delay if certain conditions are satisfied
			cancelTimer(); //Potentially cancel the timer if certain conditions are satisfied
			
			//Decisions (not related to the one before). This can't be an else if statement because all three could run
			if (((Short.parseShort(numbersAsGraphic[trackIndex].getText())) > (Short.parseShort(numbersAsGraphic[trackIndex  + 1].getText()))) 
					&& (!up))  //If the number at the trackIndex is greater than the number at the next index
			{	
				moveUp(); //Move the bigger number up, and the smaller number down
			}
			
			else if (((Short.parseShort(numbersAsGraphic[trackIndex].getText())) > (Short.parseShort(numbersAsGraphic[trackIndex  + 1].getText()))) &&
					(numbersAsGraphic[trackIndex].getY() <= 215) 
					&& (numbersAsGraphic[trackIndex].getX() < userArray[trackIndex + 1].getX())) //If it is time to move it horizontally
			{
				moveAcross(); //Move the numbers across
			}
			
			else if (((Short.parseShort(numbersAsGraphic[trackIndex].getText())) > (Short.parseShort(numbersAsGraphic[trackIndex  + 1].getText())))
					&& (numbersAsGraphic[trackIndex].getX() >= userArray[trackIndex  + 1].getX()) 
					&& (numbersAsGraphic[trackIndex].getY() < userArray[trackIndex  + 1].getY())) //If it time to move down
			{
				moveDown(); //Move the bigger number down, and the smaller number up
			}
			
			else if (((Short.parseShort(numbersAsGraphic[trackIndex].getText())) > (Short.parseShort(numbersAsGraphic[trackIndex  + 1].getText()))) 
					&& (numbersAsGraphic[trackIndex].getY() >= userArray[trackIndex  + 1].getY())) //If the swap is done
			{
				unHighlightNumbers(); //Un-highlight the numbers being referenced
				swapInArray();
			}
			
			else if ((Short.parseShort(numbersAsGraphic[trackIndex].getText())) <= (Short.parseShort(numbersAsGraphic[trackIndex  + 1].getText())))
			{ //If no swap needs to happen
				unHighlightNumbers(); //Un-highlight the numbers being referenced
				noSwap();
			}
			
			else //Otherwise, do nothing, but this should never happen
			{
				System.out.println("error in TimerTask"); //Print to the console to see if something wrong occurred
			} 
			
			repaint(); //Invoke the repaint method and redraw the screen
		}
	}
	
	private void highlightNumbers() //Private command method which is an extension of the TimerTask to highlight the numbers being referenced
	{
		//Highlight numbers needed to be switched
		this.numbersAsGraphic[this.trackIndex].setForeground(Color.BLACK); //Set the colour of the first number being compared as black
		this.numbersAsGraphic[this.trackIndex].setBackground(Color.RED); //Set the background of the first number being compared from cyan to red
		this.numbersAsGraphic[this.trackIndex + 1].setForeground(Color.BLACK); //Set the colour of the second number being compared as black
		this.numbersAsGraphic[this.trackIndex + 1].setBackground(Color.RED); //Set the background of the second number being compared from cyan to red
	}
	
	private void unHighlightNumbers() //Private command method which is an extension of the TimerTask to un-highlight the numbers being referenced
	{
		//Un-highlight the numbers
		this.numbersAsGraphic[this.trackIndex].setForeground(magenta); //Set the colour of the first number back to magenta
		this.numbersAsGraphic[this.trackIndex].setBackground(cyan); //Set the background of the first number being compared from red, back to cyan
		this.numbersAsGraphic[this.trackIndex + 1].setForeground(magenta); //Set the colour of the second number back to magenta
		this.numbersAsGraphic[this.trackIndex + 1].setBackground(cyan); //Set the background of the second number being compared from red, back to cyan
	}
	
	private void delayTask() //Private command method which is an extension of the TimerTask to invoke a delay when needed
	{
		//Decisions
		if(this.first) //If it the first run of the Task
		{
			//Try and catch statement
			try //Try to invoke a 1 second delay, for the user to see what is actually being compared
			{
				Thread.sleep(DELAY); //Pause the thread based for however long the constant DELAY is
				//Thread.sleep(0);
				
				/*The comment above is another line which makes the sorting go very fast. So, if you want the sorting to go faster, comment the first 
				 delay and uncomment the second one.*/
			}
			
			catch (InterruptedException sirTheodoreIII) {} //Catch any exception and do nothing
		}
	}
	
	private void cancelTimer() //Private command method which is an extension of the TimerTask to cancel the Timer when needed
	{
		//Decisions
		if (this.finalComparisons == 8) //If swapped = false (no swaps) for 8 comparisons in a row
		{
			unHighlightNumbers(); //Un-highlight the numbers being referenced
			
			this.doneSort = true; //Set the state of the sorting being done to true
			this.tetrisThemeSong.close(); //Stop the tetris theme song
			playMario(); //Play the mario victory theme song
			repaint(); //Invoke the repaint method, and update the screen
			this.bubbleTimer.cancel(); //Cancel the timer. By this point, the array is fully sorted
		}
	}
	
	private void moveUp() //Private command method which is an extension of the TimerTask to move the bigger number up and the smaller number down
	{
		this.numbersAsGraphic[this.trackIndex].setLocation(numbersAsGraphic[this.trackIndex].getX(), 
				this.numbersAsGraphic[this.trackIndex].getY() - 1); //Move bigger number up
		
		this.numbersAsGraphic[this.trackIndex+1].setLocation(numbersAsGraphic[this.trackIndex+1].getX(), 
				this.numbersAsGraphic[this.trackIndex+1].getY()+1);//Move smaller down
		
		//Set Variables
		this.first = false; //After the very first run of the Task, set first to true so a delay only runs once
		this.swapped = true; //Set swapped to true, so the paint method can display it on the screen
		this.up = (this.numbersAsGraphic[this.trackIndex].getY() < 215); //When bigger number reaches its limit and has a y-value of 215, set up to true
	}
	
	private void moveAcross() //Private command method which is an extension of the TimerTask to move the numbers across, when a swap is needed
	{
		this.numbersAsGraphic[this.trackIndex].setLocation(this.numbersAsGraphic[this.trackIndex].getX() + 1,
				this.numbersAsGraphic[this.trackIndex].getY()); //Move bigger number right
		
		this.numbersAsGraphic[this.trackIndex+1].setLocation(this.numbersAsGraphic[this.trackIndex+1].getX() - 1,
				this.numbersAsGraphic[this.trackIndex + 1].getY()); //Move smaller left
	}
	
	private void moveDown() //Private command method which is an extension of the TimerTask to move the bigger number down and the smaller number up
	{
		this.numbersAsGraphic[this.trackIndex].setLocation(this.numbersAsGraphic[this.trackIndex].getX(),
				this.numbersAsGraphic[this.trackIndex].getY() + 1); //Move bigger number down
		
		this.numbersAsGraphic[this.trackIndex+1].setLocation(this.numbersAsGraphic[this.trackIndex+1].getX(),
				this.numbersAsGraphic[this.trackIndex+1].getY() - 1); //Move smaller up
	}
	
	private void swapInArray() //Private command method which is an extension of the TimerTask to handle the logic of swapping the elements
	{
		//Swap the actual references in the variables
		JTextPane temp = this.numbersAsGraphic[this.trackIndex + 1]; //Make a temporary variable to hold the smaller number
		this.numbersAsGraphic[this.trackIndex + 1] = this.numbersAsGraphic[this.trackIndex]; //Put the bigger number in the smaller numbers place
		this.numbersAsGraphic[this.trackIndex] = temp; //Put the smaller number in the bigger numbers place, by means of the temporary variable
		
		//Set variables
		this.up = false; //Set up to false to potentially start the first if statement
		this.first = true; //Set up to true to reset cause a delay in the next
		this.comparisons++; //Increase the number of comparisons
		this.finalComparisons = 0; //Set final comparisons to zero, since a swap occurred. Timer stops when finalComarpsions = 8
		
		//Decisions
		if (this.trackIndex < 8) //If the index is less than 8
		{
			this.trackIndex++; //Increase the index
		}
		
		else //Otherwise, if the index is 8
		{
			this.swapped = false; //Set swapped to false
			this.trackIndex = 0; //Reset the index to go back to the start
		}
	}

	private void noSwap() //Private command method which is an extension of the TimerTask to handle the logic of a no-swap
	{
		//Set variables
		this.up = false; //Set up to false to potentially start the first if statement
		this.first = true;//Set up to true to reset cause a delay in the next 
		this.comparisons++; //Increase the number of comparisons
		
		//Decisions
		if (this.trackIndex < 8) //If the index is less than 8
		{
			//Double Nested Decisions
			if ((Integer.parseInt(this.numbersAsGraphic[this.trackIndex + 1].getText())) 
					<= (Integer.parseInt(this.numbersAsGraphic[this.trackIndex + 2].getText()))) //If the next numbers are also in order
			{
				this.finalComparisons++; //Increase the final comparison counter
			}
			this.trackIndex++; //increase the index
		}
		
		else
		{
			this.swapped = false; //Set swapped to false
			this.trackIndex = 0; //Reset the index to go back to the start
			this.finalComparisons = 0; //Reset the final comparisons to 0
		}
	}
	
	public void keyPressed(KeyEvent e) //Inherited KeyListener method, to be invoked if any key is pressed by the user
	{ 
		 //Declare all variables
		 int pressedCode = e.getKeyCode(); //Integer variable to store the key code since the ENTER key does not have an ASCII code
		 
		 //Decisions
		 if (pressedCode == KeyEvent.VK_ENTER) //If the user pressed the ENTER key
		 {
			 checkIfValid(); //Check if the input is valid
		 }
		 
		 repaint(); //Invoke paint method, and update panel 
	}
	
	public void keyReleased(KeyEvent e) //Inherited, unused, to be invoked after a key stroke is released
	{ 
    	//Method is unused, so there is no code. Nevertheless, since the class implements a KeyListener, this method must be here
    } 
	
	public void keyTyped(KeyEvent e) //Inherited, unused, KeyListener method, to be invoked when a key is typed
	{
    	//Method is unused, so there is no code. Nevertheless, since the class implements a KeyListener, this method must be here
	}
	
	public void paint(Graphics g) //Inherited Paint method to paint and update the JPanel. This method is invoked numerous times when relevant
	{
		super.paint(g); //Enable panel to be painted
		g.setFont(this.subheadingFont); //Set the font of the painter to the sub-heading font
		g.setColor(this.cyan); //Set the colour of the painter to cyan
		
		//Decisions
		if (programState == 1) //If the take input screen is to be displayed
		{
			displayIntegerPrompt(g); //Display the integer prompt
		}
		
		else if (programState == 2) //Otherwise, if the sorting screen is to be displayed
		{	
			//Nested Decisions
			if (doneSort) //If the sorting is done
			{	
				g.setColor(this.magenta); //Set the colour of the painter to magenta for the done message only
				displayDoneMessage(g); //Display the end of swap message 
			}
			
			else //Otherwise, if the sorting is not done
			{
				displaySwappedStatus(g); //Display whether swapped is true or false
			}
		}
		
		else //Otherwise, if the program state is 0 (main menu)
		{
			g.drawImage(this.shakerSortImage, 40, 400, this); //Draw image
			g.drawImage(this.insertionSortImage, 835, 400, this); //Draw image
		}
	}
		
	private void displayIntegerPrompt(Graphics g) //Private command method which is an extension of the Paint method to display the integer prompts
	{
		//Paint the integer prompt
		FontMetrics promptTitleMetric = g.getFontMetrics(this.subheadingFont); //Set the font metric
        int xPrompt = this.promptArea.x + (this.promptArea.width - promptTitleMetric.stringWidth("Enter the "  
        		+ indexToString(this.trackIndex) + " Integer")) / 2; //Find x-coordinate based on the size of the string
        int yPrompt = this.promptArea.y + ((this.promptArea.height - 
        		promptTitleMetric.getHeight()) / 2) + promptTitleMetric.getAscent(); //Find the y-coordinate based on the size of the string
    	g.drawString("Enter the "  + indexToString(this.trackIndex) + " Integer", xPrompt, 
    			yPrompt); //Paint the prompt (First, Second, Third, etc.), which will now always be centred, based on the use of font metrics
	}
	
	private void displayDoneMessage(Graphics g) //Private command method which is an extension of the Paint method to display the end of swap message
	{
		this.bubbleInstruction1.setVisible(false); //Make the first line of the message to explain bubble sort invisible
		this.bubbleInstruction2.setVisible(false); //Make the second line of the message to explain bubble sort invisible
		
		//Try and Catch statement
		try //Try to invoke a 1 second delay
		{
			Thread.sleep(DELAY); //Pause the thread based for however long the constant DELAY is
		}
		
		catch (InterruptedException folabi) {} //Catch any exception and do nothing
		
		this.doneTitle.setVisible(true); //Make the end of sort title visible
    	
    	//Paint the number of comparisons
    	FontMetrics afterMessage = g.getFontMetrics(this.subheadingFont); //Set the font metric
        int xCompTitle = this.comparisonTitle.x + (this.comparisonTitle.width - 
        		afterMessage.stringWidth("Comparisons Made: " + this.comparisons)) / 2; //Find x-coordinate based on the size of the string
        int yCompTitle = this.comparisonTitle.y + ((this.comparisonTitle.height - 
        		afterMessage.getHeight()) / 2) + afterMessage.getAscent(); //Find the y-coordinate based on the size of the string
    	g.drawString("Comparisons Made: " + this.comparisons, 
    			xCompTitle, yCompTitle); //Paint the number of comparisons made, which will now always be centred, based on the use of font metrics
       
    	//Paint the time complexity of bubble sort
    	int xTimeTitle = this.timeComplexTitle.x + (this.timeComplexTitle.width - 
        		afterMessage.stringWidth("Time Complexity: O(n) = n²")) / 2; //Find x-coordinate based on the size of the string
        int yTimeTitle = this.timeComplexTitle.y + ((this.timeComplexTitle.height - 
        		afterMessage.getHeight()) / 2) + afterMessage.getAscent(); //Find the y-coordinate based on the size of the string
    	g.drawString("Time Complexity: O(n) = n²", 
    			xTimeTitle, yTimeTitle); //Paint the time complexity, which will now always be centred, based on the use of font metrics
    	
    	this.backToStart.setVisible(true); //Make the back to start button visible
    	this.backToStart.setEnabled(true); //Enable the back to start button
	}
	
	private void displaySwappedStatus(Graphics g) //Private command method which is an extension of the Paint method to show the value of swapped
	{
		//Paint the swapped variable to be true or false
    	FontMetrics swappedStatus = g.getFontMetrics(this.subheadingFont); //Set the font metric
        int xCompTitle = this.comparisonTitle.x + (this.comparisonTitle.width - 
        		swappedStatus.stringWidth("swapped =  " + this.swapped)) / 2; //Find x-coordinate based on the size of the string
        int yCompTitle = this.comparisonTitle.y + ((this.comparisonTitle.height - 
        		swappedStatus.getHeight()) / 2) + swappedStatus.getAscent(); //Find the y-coordinate based on the size of the string
    	g.drawString("swapped =  " + this.swapped, 
    			xCompTitle, yCompTitle); //Paint the swapped status, which will now always be centred, based on the use of font metrics
	}
	
	private static String indexToString(byte index) //Private method which takes the parameter of the index and returns them as ordinal words
	{
		//Declare all variables
		String elementResult; //String to be returned
		
		//Decisions
		switch (index) //Switch statement based on what the index being referenced is
		{
			case 0: //If the index is zero
				elementResult = "First"; //The element being referenced is the first one
				break;
				
			case 1: //If the index is one
				elementResult = "Second"; //The element being referenced is the second one
				break;
				
			case 2: //If the index is two
				elementResult = "Third"; //The element being referenced is the third one
				break;
				
			case 3: //If the index is three
				elementResult = "Fourth"; //The element being referenced is the fourth one
				break;
				
			case 4: //If the index is four
				elementResult = "Fifth"; //The element being referenced is the fifth one
				break;
				
			case 5: //If the index is five
				elementResult = "Sixth"; //The element being referenced is the sixth one
				break;
				
			case 6: //If the index is six
				elementResult = "Seventh"; //The element being referenced is the seventh one
				break;
				
			case 7: //If the index is seven
				elementResult = "Eighth"; //The element being referenced is the eighth one
				break;
				
			case 8: //If the index is eight
				elementResult = "Ninth"; //The element being referenced is the ninth one
				break;
				
			case 9: //If the index is nine
				elementResult = "Tenth"; //The element being referenced is the tenth one
				break;
				
			default: //If the index is anything else
				elementResult = ""; //Return empty string
				break;
		}
		
		return elementResult; //Return the result
	}
	
	private void playTetris() //Private command method to play the tetris music
	{
		//Try and Catch statement
		try //Try to start the music
		{
			this.tetrisStream = AudioSystem.getAudioInputStream(new File("tetrisTheme.wav").getAbsoluteFile()); //Get the Audio file
			this.tetrisThemeSong = AudioSystem.getClip();  //Initialise the audio clip
			this.tetrisThemeSong.open(this.tetrisStream); //Open the audio file in the clip
			this.tetrisThemeSong.start(); //Start playing the clip
		}
		
		catch (Exception tola) {} //Catch any FileIO, UnsupportedAudioFile, or LineUnavailable exceptions and do nothing
	}
	
	private void playMario() //Private command method to play the mario music
	{
		//Try and Catch statement
		try
		{
			this.superMarioStream = AudioSystem.getAudioInputStream(new File("superMarioWinTheme.wav").getAbsoluteFile()); //Get the Audio file
			this.marioWinThemeSong = AudioSystem.getClip(); //Initialise the audio clip
			this.marioWinThemeSong.open(this.superMarioStream); //Open the audio file in the clip
			this.marioWinThemeSong.start(); //Start playing the clip
		}
		
		catch (Exception baldski) {} //Catch any FileIO, UnsupportedAudioFile, or LineUnavailable exceptions and do nothing
	}
} //End of class