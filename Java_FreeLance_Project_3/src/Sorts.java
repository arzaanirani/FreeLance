import java.util.Random;

public class Sorts {

	private static final Random random = new Random();
	
	public static void main(String[] args) {
		System.out.println("                 DATA A   |  DATA B |   DATA C   |   DATA D   |   DATA E   |"); //Print out the header
		sortAlgorithmSequence(new SelectionSort()); //Selection sort sequence
		sortAlgorithmSequence(new InsertionSort()); //Insertion sort sequence
		sortAlgorithmSequence(new HeapSort()); //Heap sort sequence
		sortAlgorithmSequence(new MergeSort()); //Merge sort sequence
		sortAlgorithmSequence(new QuickSort()); //Quick sort sequence
	}
	
	/** Goes through the sequence that sorts different sizes of arrays.
	 * 
	 * @param alg The algorithm to sort with
	 */
	public static void sortAlgorithmSequence(ISortingAlgorithm alg) {
		String data = alg + ": "; //The string that will be printed
		Tuple t = sort(alg, 100); //Sort with 100 random elements
		data += "(" + t.a + ", " + t.b + ") | "; //Add comparisons and sort to string
		t = sort(alg, 1000); //Sort with 1000 random elements
		data += "(" + t.a + ", " + t.b + ") | "; //Add comparisons and sort to string
		t = sort(alg, 10000); //Sort with 10000 random elements
		
		int[] array = new int[10001]; //create a new array to hold 10001 elements (from 0 to 10000)
		for (int i = 10000; i >= 0; i--) { //Go through each element
			array[i] = i; //Add in decreasing order
		}
		t = sort(alg, array); //Sort the array
		data += "(" + t.a + ", " + t.b + ") | "; //Add comparisons and sort to string
 		
		array = new int[10001]; //Create a new array to hold 10001 elements  (from 0 to 10000)
		int j = 0;
		for (int i = 0; i <= 5000; i++) { //Go through the numbers 0-5000
			if (i % 2 == 1) { //Add to the array only if it's odd
				array[j] = i; //Set in the position of j. This way no elements are skipped in the array
				j++; //Inc j
			}
		}
		for (int i = 0; i <= 5000; i++) { //Go through the numbers 0-5000
			if (i % 2 == 0) { //Add to the array only if it's even
				array[j] = i; //Set in the position of j. This way no elements are skipped in the array
				j++; //Inc j
			}
		}
		t = sort(alg, array); //Sort the array
		data += "(" + t.a + ", " + t.b + ") | "; //Add comparisons and sort to string
		System.out.println(data); //Print out the full string
	}
	
	/**
	 * 
	 * @param alg The algorithm to sort
	 * @param randomNumbers The amount of random numbers to generate
	 * @return A tuple holding the number of comparisons and swaps
	 */
	public static Tuple sort(ISortingAlgorithm alg, int randomNumbers) {
		int[] array = new int[randomNumbers]; //Create a new array to hold the number of random numbers
		for (int i = 0; i < randomNumbers; i++) { //Go through the specified amount of numbers
			array[i] = random.nextInt(randomNumbers); //Add a random number
		}
		return alg.sort(array); //Sort it with the algorithm
	}
	
	/** Sorts an array with a specific array
	 * 
	 * @param alg The algorithm to sort with
	 * @param array The array to sort
	 * @return The tuple holding the number of comparisons and swaps
	 */
	public static Tuple sort(ISortingAlgorithm alg, int[] array) {
		return alg.sort(array); //Sort and return the tuple
	}
	
}

/** 
 * Holds two values. Used to return two variables in a method
 */
class Tuple {
	
	/** The two variables. Public for ease of access. */
	public int a, b;
	
	/** Creates the tuple to hold the values
	 * 
	 * @param a The first variable
	 * @param b The second variable
	 */
	public Tuple(int a, int b) {
		this.a = a;
		this.b = b;
	}
	
}

/** 
 * Use of inheritance makes the ease of incorporating and using different sorting algorithms easier and cleaner.
 */
interface ISortingAlgorithm {
	
	Tuple sort(int[] array);
	
}

class SelectionSort implements ISortingAlgorithm {

	@Override
	public Tuple sort(int[] array) {
		int comparisons = 0, swaps = 0;
		for (int i = 0; i < array.length - 1; i++) {
            int index = i;
            for (int j = i + 1; j < array.length; j++) {
            	comparisons++;
                if (array[j] < array[index])
                    index = j;
            }
            
            swaps++;
            int smallerNumber = array[index];
            array[index] = array[i];
            array[i] = smallerNumber;
        }
		return new Tuple(comparisons, swaps);
	}
	
	@Override
	public String toString() {
		return "Selection Sort";
	}
	
}

class InsertionSort implements ISortingAlgorithm {

	@Override
	public Tuple sort(int[] array) {
		int comparisons = 0, swaps = 0;
		int n = array.length;
        for (int j = 1; j < n; j++) {
            int key = array[j];
            int i = j-1;
            while ( (i > -1) && ( array [i] > key)) { //comparison
            	comparisons++;
            	swaps++;
                array [i+1] = array [i]; //swap
                i--;
            }
        	swaps++;
            array[i+1] = key; //swap
        }
        return new Tuple(comparisons, swaps);
	}
	
	@Override
	public String toString() {
		return "Insertion Sort";
	}
	
}

class HeapSort implements ISortingAlgorithm {

	public int heapSize, comparison, swaps;
	
	@Override
	public Tuple sort(int[] array) {
		heapSize = array.length;
		heapSort(array);
		return new Tuple(comparison, swaps);
	}
	
	public int left(int i) {
        return 2*i+1;
    }

    public int right(int i) {
        return 2*i+2;
    }
    
    public void buildMaxHeap(int array[]) {
        heapSize = array.length;
        for(int i=array.length/2; i>=0;i--) {
            maxHeapify(array, i);
        }
    }

    public void maxHeapify(int array[],int i)
    {
        int l = left(i);
        int r = right(i);
        int largestElementIndex = -1;
        
        comparison++;
        if(l<heapSize && array[l]>array[i]) { //comparisons
            largestElementIndex = l;
        } else {
            largestElementIndex=i;
        }
        
        comparison++;
        if(r<heapSize && array[r]>array[largestElementIndex]) { //comparison
            largestElementIndex = r;
        }

        comparison++;
        if(largestElementIndex!=i) { //comparison
        	//swap
        	swaps++;
            int temp = array[i];
            array[i]=array[largestElementIndex];
            array[largestElementIndex]=temp;
            maxHeapify(array, largestElementIndex);
        }
    }

    public void heapSort(int array[]) {
        buildMaxHeap(array);
        for(int i=array.length-1;i>=0;i--) {
        	swaps++;
            int temp = array[0]; //swap
            array[0]=array[i];
            array[i]=temp;

            heapSize  = heapSize-1;
            maxHeapify(array,0);
        }
    }
	
	@Override
	public String toString() {
		return "Heap Sort";
	}
	
}

class MergeSort implements ISortingAlgorithm {

	private int[] array;
    private int[] tempArray;
	private int length, comparisons, swaps;
    
	@Override
	public Tuple sort(int[] array) {
        this.array = array;
        this.length = array.length;
        this.tempArray = new int[length];
        mergeSort(0, length - 1);
        return new Tuple(comparisons, swaps);
	}

	private void mergeSort(int lowerIndex, int higherIndex) {
        if (lowerIndex < higherIndex) {
            int middle = lowerIndex + (higherIndex - lowerIndex) / 2;
            mergeSort(lowerIndex, middle);
            mergeSort(middle + 1, higherIndex);
            mergeParts(lowerIndex, middle, higherIndex);
        }
    }
 
    private void mergeParts(int lowerIndex, int middle, int higherIndex) {
 
        for (int i = lowerIndex; i <= higherIndex; i++) {
            tempArray[i] = array[i];
        }
        int i = lowerIndex;
        int j = middle + 1;
        int k = lowerIndex;
        while (i <= middle && j <= higherIndex) {
        	comparisons++;
            if (tempArray[i] <= tempArray[j]) { //comparison
            	swaps++;
                array[k] = tempArray[i]; //swap
                i++;
            } else {
            	swaps++;
                array[k] = tempArray[j]; //swap
                j++;
            }
            k++;
        }
        while (i <= middle) {
        	swaps++;
            array[k] = tempArray[i]; //swap
            k++;
            i++;
        }
    }
	
	@Override
	public String toString() {
		return "Merge Sort";
	}
	
}

class QuickSort implements ISortingAlgorithm {

	private int[] array;
	private int length, comparisons, swaps;
	
	@Override
	public Tuple sort(int[] array) {
        this.array = array;
        length = array.length;
        quickSort(0, length - 1);
        return new Tuple(comparisons, swaps);
    }
 
    private void quickSort(int lowerIndex, int higherIndex) {
        int i = lowerIndex;
        int j = higherIndex;
        
        int pivot = array[lowerIndex+(higherIndex-lowerIndex)/2];
        while (i <= j) {
            while (array[i] < pivot) {
                i++;
            }
            while (array[j] > pivot) {
                j--;
            }
            if (i <= j) { //comparison
            	comparisons++;
                exchangeNumbers(i, j);
                i++;
                j--;
            }
        }
        if (lowerIndex < j)
            quickSort(lowerIndex, j);
        if (i < higherIndex)
            quickSort(i, higherIndex);
    }
 
    private void exchangeNumbers(int i, int j) {
    	swaps++;
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }
	
	@Override
	public String toString() {
		return "Quick Sort";
	}
	
}