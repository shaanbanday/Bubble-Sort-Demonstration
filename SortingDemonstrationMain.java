/*
Java™ Project: ICS4U
Package: sorting
Class: SortingDemonstrationMain
Programmer: Shaan Banday

Date Created: Thursday, April 7th, 2022.
Date Completed: Friday, April 8th, 2022.

Description: The following program/class has a main method, and creates the GUI object by calling the SortingDemonstration class. Since the main 
method is always ran first, and since the SortingDemonstration class does not have a main method, then there needs to be one to create the object. 
Otherwise, the code would run, but not do anything, since Eclipse wouldn't know which method to run first. So this main method has to be here.
*/

package sorting; //Launch the class from this package named "sorting"

public class SortingDemonstrationMain //The name of the class that will reference the "SortingDemonstration" class.
{
	@SuppressWarnings("unused") //Suppress any warnings of unused objects
	public static void main(String[] args) //Main method which creates the SortingDemonstration object as a GUI
	{
		SortingDemonstration sortingGUI = new SortingDemonstration(); //Create the SortingDemonstration object as a new GUI
	}
} //End of class