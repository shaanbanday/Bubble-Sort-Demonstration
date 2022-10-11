package sorting;

import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class BinarySearch {

	public static void main(String[] args) throws IOException
	{
		String [] wordBank = new String[5757];
		Scanner takeInput = new Scanner (new FileReader("five_letter_words_sorted.txt"));
		int indexLocation = 0;
		
		for (int i = 0; i < wordBank.length; i++)
		{
			wordBank[i] = takeInput.nextLine();
		}
		
		boolean stopReading = false;
		
		while(!stopReading)
		{
			String userWord;
			boolean isFound = false;
			int comparisons = 1;
			int bottom = 0;
			int top = wordBank.length;
			
			takeInput = new Scanner(System.in);
			System.out.println("Please enter a five letter word or press q to quit: ");
			userWord = takeInput.nextLine();
			
			while ((bottom <= top) && (!(isFound)))
			{
				int middle = (bottom + top) / 2;
				
				if (wordBank[middle].equalsIgnoreCase(userWord))
				{
					isFound = true;
					indexLocation = middle;
				}
				
				else
				{
					if((wordBank[middle].compareToIgnoreCase(userWord)) < 0)
					{
						bottom = middle + 1;
					}
					
					else
					{
						top = middle - 1;
					}
					comparisons++;
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
				System.out.println("it was the " + (indexLocation + 1)  + " element in the list");
			}
			
			else
			{
				System.out.println("not a common word!");
			}
		}
		takeInput.close();
	}
}