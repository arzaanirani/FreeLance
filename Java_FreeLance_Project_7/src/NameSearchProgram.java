import java.io.*;
import java.util.ArrayList;

import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader.Array;

public class NameSearchProgram {
	public static final int MAX_NAMES = 90000;
	public static int         numNames;
	public static String[]    names = new String[MAX_NAMES]; 

	// BubbleSort algorithm to sort all names by their length
	public static void bubbleSortByLength() {
		 //... Complete this ...
				int n = names.length;
				System.out.println(names.length);
				for (int c = 0; c < ( n - 1 ); c++) {
					for (int d = 0; d < n - c - 1; d++) {
						if (names[d+1] !=null && names[d].length() > names[d+1].length()) /* For descending order use < */
						{
							String temp = names[d];
							names[d]   = names[d+1];
							names[d+1] = temp;
						}
					}
				}
	}

	// BucketSort algorithm to sort all names by their length
	public static void bucketSortByLength() {
		// ... Complete this .
		int N = names.length;
		if( N <= 0 )
			return;                                   // Case of empty names
		System.out.println(names.length);
		int min = names[0].length();
		int max = min;
		for( int i = 1; i < N; i++ )                // Find the minimum and maximum
		{
			if(names[i] ==null )
			{
				continue;
			}
			if(  names[i].length() > max )
				max = names[i].length();
			else if(names[i].length() < min )
				min = names[i].length();
		}
		int bucket[] = new int[max-min+1];          // Create buckets
		ArrayList<String> temp_bucket[] = new ArrayList[max-min+1];
		for( int i = 0; i < N; i++ )                // "Fill" buckets
		{	
			if(names[i] ==null )
			{
				continue;
			}
			bucket[names[i].length()-min]++;                   // by counting each datum
			if(temp_bucket[names[i].length()-min] ==null)
			{
				temp_bucket[names[i].length()-min] = new ArrayList<>();
			}
			temp_bucket[names[i].length()-min].add(names[i]);			
		}
		int i = 0;                                  
		for( int b = 0; b < bucket.length; b++ )    // "Empty" buckets
			for( int j = 0; j < bucket[b]; j++ )      // back into names
			{
				names[i++] = temp_bucket[b].get(j);                     // by creating one per count
			}
	}

	// This method loads the names from the file
	public static void loadData(String dataFile) {
		try { 
			BufferedReader   in  = new BufferedReader(new FileReader(dataFile)); 

			// Read each name into the names
			numNames = 0;
			String line = in.readLine();
			while(line != null) {
				names[numNames++] = line;
				line = in.readLine();
			}
			in.close(); 
		} catch (FileNotFoundException e) { 
			System.out.println("Error: Cannot open file for reading"); 
			System.exit(-1);
		} catch (IOException e) { 
			System.out.println("Error: Cannot read from file"); 
			System.exit(-1);
		} 
	}

	public static void saveData(String dataFile) {
		try { 
			PrintWriter   out  = new PrintWriter(new FileWriter(dataFile)); 

			for (int i=0; i<numNames; i++)
				out.println(names[i]);
			out.close(); 
		} catch (FileNotFoundException e) { 
			System.out.println("Error: Cannot open file for writing"); 
			System.exit(-1);
		} catch (IOException e) { 
			System.out.println("Error: Cannot write to file"); 
			System.exit(-1);
		} 
	}

	public static void main(String [] args) {
		// Load up the data and bubble sort the names in increasing order of their length
		loadData("surnames.txt");
		System.out.println("Bubble Sorting ...");
		long start = System.currentTimeMillis();
		bubbleSortByLength();
		System.out.println("Bubble Sort Took: " + (System.currentTimeMillis()-start)/1000.0f + " seconds");
		System.out.println("Writing to file ...");
		saveData("surnamesBubbleSorted.txt");
		System.out.println("Done.");

		// Load up the data and bucket sort the names in increasing order of their length
		loadData("surnames.txt");
		System.out.println("Bucket Sorting ...");
		start = System.currentTimeMillis();
		bucketSortByLength();
		System.out.println("Bucket Sort Took: " + (System.currentTimeMillis()-start)/1000.0f + " seconds");
		System.out.println("Writing to file ...");
		saveData("surnamesBucketSorted.txt");
		System.out.println("Done.");
	}
}
