/*
Java™ Project: ICS4U
Package: sorting
Class: Element
Programmer: Shaan Banday

Date Created: Thursday, April 7th, 2022.
Date Completed: Friday, April 8th, 2022.

Description: The following program/class defines the Element object that the SortingDemonstration class references to store the values of the user
inputed number and their coordinates on the JFrame. As a result, the object has 3 components: a short to represent the value of the number, and two 
shorts to represent the x and y positions. The Element has methods, which includes the constructor which only takes the number as the parameter. It
also has a setLocation() method to keep track of the x and y positions, get method for each instance of the class, and a overwritten toString method.
*/

package sorting; //Launch the class from this package named "sorting"

public class Element //The name of the class that defines the object being referenced by the "SortingDemonstration" class
{
	//Declare all variables
	private short number; //Short to hold user inputed number. Since the bounds of the number is -100 to 1000, then a 64-bit int is not needed
	private short x; //Short to hold the x-position. Since the JFrame is only 1150 pixels wide, a 32-bit int is not needed and a 16-bit short works
	private short y; //Short to hold the y-position. Since the JFrame is only 700 pixels wide, a 32-bit int is not needed and a 16-bit short works
	
	public Element(short setNum) //Constructor which initialises the object by taking in one input for the user inputed number
	{
		this.number = setNum; //Set the number in the Element class to whatever is passed to the constructor
	}
	
	public void setLocation (short xCord, short yCord) //Public method which sets the location of the number on the screen by taking the coordinates
	{
		this.x = xCord; //Set x to the first parameter
		this.y = yCord; //Set y to the second parameter
	}
	
	public short getValue() //Public method if user wants the number after it has been initialised, which returns it as a short
	{
		return this.number; //Return the instance of the class
	}
	
	public short getX() //Public method if user wants the x-coordinate after it has been initialised, which returns it as a short
	{
		return this.x; //Return the instance of the class
	}
	
	public short getY() //Public method if user wants the y-coordinate after it has been initialised, which returns it as a short
	{
		return this.y; //Return the instance of the class
	}
	
	public String toString() //Public method if user wants the number as a String
	{ 
		return Short.toString(this.number); //Return the instance of the class, and parse it to a String
	}	
}