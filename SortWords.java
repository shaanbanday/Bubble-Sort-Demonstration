package sorting;

import java.io.FileReader;
import java.io.PrintWriter;
import java.io.FileWriter;
import java.util.Scanner;
import java.io.IOException;

public class SortWords {

	public static void main(String[] args) throws IOException
	{
		String [] wordBank = new String[5757];
		Scanner takeInput = new Scanner (new FileReader("five_letter_words.txt"));
		
		for (int i = 0; i < wordBank.length; i++)
		{
			wordBank[i] = takeInput.nextLine();
		}
		
		takeInput.close();
		
		boolean swapped = true;
		
		while(swapped)
		{
			swapped = false;
			
			for (int i = 1; i < wordBank.length; i++)
			{
				if ((wordBank[i].compareToIgnoreCase(wordBank[i-1])) > 0)
				{
					String temp = wordBank[i];
					wordBank[i] = wordBank[i-1];
					wordBank[i-1] = temp;
					
					swapped = true;
				}
			}
		}
		
		PrintWriter writeToFile = new PrintWriter(new FileWriter ("empty_file.txt"));
		
		for (int i = 0; i < wordBank.length; i++)
		{
			writeToFile.println(wordBank[i]);
		}
		
		writeToFile.close();
	}
}