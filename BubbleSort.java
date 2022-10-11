package sorting;
public class BubbleSort 
{
	public static void main(String[] args) 
	{
		float [] floatBank = {10, 9, 8, 7, 6, 5, 4, 3, 2, 1};
		boolean swapped = true;
		int comparisons = 0;

		while(swapped)
		{
			swapped = false;
			for (int i = 1; i < floatBank.length; i++)
			{
				if (floatBank[i-1] > floatBank[i])
				{
					float temp = floatBank[i];
					floatBank[i] = floatBank[i-1];
					floatBank[i-1] = temp;
					swapped = true;
				}
				comparisons++;
			}
		}

		for (int j = 0; j < floatBank.length; j++)
		{
			System.out.print(floatBank[j] + ", ");
		}
		System.out.println(comparisons);
	}
}