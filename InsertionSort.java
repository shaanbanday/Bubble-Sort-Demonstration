package sorting;

public class InsertionSort {

	@SuppressWarnings("unused")
	public static void main(String[] args) 
	{
		int [] array = {5, 3, 14, 6, 1, 4, 18, 3, 2};
		
		int comparisons = 0;
		int swaps = 0;
		
		for (int i = 0; i < array.length; i++)
		{
			int key = array [i];
			
			int j = i - 1;
			
			while ((j >= 0) && (array[j] > key))
			{
				array [j + 1] = array[j];
				printArray(array);
				swaps++;
				j--;
			}
			
			array [j + 1] = key;
			printArray(array);
			comparisons++;
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