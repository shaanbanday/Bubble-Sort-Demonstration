package sorting;

import java.util.Scanner;
import java.io.*;
import java.io.IOException;

public class sequentialSearch {

	public static void main(String[] args) throws IOException
	{
		Scanner takeInput = new Scanner(System.in);
		boolean stopReading = false;
		
		while(!stopReading)
		{
			takeInput = new Scanner(System.in);
			String userWord;
			int comparisons = 1;
			
			System.out.println("Please enter a five letter word or press q to quit: ");
			
			userWord = takeInput.nextLine();
			
			takeInput = new Scanner (new FileReader("five_letter_words.txt"));
			
			boolean isFound = false;
			
			while((takeInput.hasNext()) && (!(isFound)))
			{
				if (takeInput.next().equals(userWord))
				{
					isFound = true;
				}
				else 
				{
					comparisons ++;
				}
			}
			
			
			if (userWord.equalsIgnoreCase("q"))
			{
				System.out.println("Program terminated");
				stopReading = true;
			}
			
			else if(isFound)
			{
				System.out.println("found it!");
				System.out.println("It took " + comparisons + " comparison(s) to find it");
			}
			
			else
			{
				System.out.println("not a common word!");
				System.out.println("There are " + (comparisons - 1) + " words in the list");
			}
		}
		takeInput.close();
	}
}
