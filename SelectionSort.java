package sorting;

public class SelectionSort {

	@SuppressWarnings("unused")
	public static void main(String[] args) 
	{
		int timesSwapped = 0;
		
		int [] array = {9, 21, 17, 1, 15, 3};
		
		for (int i = 0; i < array.length; i++)
		{
			int lowestIndex = i;
			
			for (int j = i + 1; j < array.length; j++)
			{
				if (array[j] < array[lowestIndex])
				{
					timesSwapped++;
					lowestIndex = j;
				}
				else {}
				
			}
			printArray(array);
			int temp = array[lowestIndex];
			array[lowestIndex] = array[i];
			array[i] = temp;
		}
	}
	
	private static void printArray(int [] arr)
	{
		for (int l = 0; l < arr.length; l++)
		{
			System.out.print(arr[l] + ", ");
		}
		System.out.println();
	}
}