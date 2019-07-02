/**
 * 
 */
package divideNconquer;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Scanner;

/**
 * Main class for Closest Pair Divide N Conquer algorithm
 * @author Cristina Padro-Juarbe
 *
 */
public class MainDNC {

	final static int MAX_LENGTH_ARRAY = 30;
	final static String filepath = "src/divideNconquer/";
	static PrintWriter printWriter = null;
	static ClosestPair pairs = null;
	static double[][] A;
	static double randomDouble;
	static BigDecimal bd = null;
	static int workMain = 0;

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		System.out.println("Enter an integer size for a 2D array: ");
		int nSize = in.nextInt();
		System.out.println("\nEnter an integer M : ");
		
		int mSize = in.nextInt();
		
		// get the number of closest m pair of points from the user
		while(mSize < 0 || mSize >= nSize) {
			System.out.println("M must be an integer between 1 and " + (nSize - 1));
			System.out.println("\nEnter an integer size for a 2D array: ");
			mSize = in.nextInt();
		}
		in.close();

		System.out.println("\nStarting work. Please wait...\n");
		run(nSize, mSize);
		System.out.println("\nDone!!\n");
	}

	/**
	 * This method creates a random number of points of length specified by the user and calls the 'bruteforceCP' method 
	 * to compute the closest pair of points in an array using brute force.
	 * This method prints traces (if active) and prints the output to a file (if size > 30) or to the terminal (if size <= 30).
	 * 
	 * @param size 
	 * @param sizeM
	 */
	private static void run(int size, int sizeM) {
		A = new double[size][2];

		// no work is recorder when creating the array of points
		// creating array of points A
		for (int index1 = 0; index1 < A.length; index1++) {
			for (int index2 = 0; index2 < 2; index2++) {
				randomDouble = Math.random() * 1000 + 0.1;
				
				//Big Decimal is used to use get double values for (x, y) points for up to 8 digits
				bd = new BigDecimal(randomDouble);
				bd = bd.round(new MathContext(8));
				A[index1][index2] = bd.doubleValue();
			}
		}

		// If true, print output to the console. Else, print output to a file
		boolean trace_run_console = A.length <= MAX_LENGTH_ARRAY ? true : false;

		// print output to a file
		if (!trace_run_console) {
			try {
				printWriter = new PrintWriter(new FileWriter(filepath + "n=" + size + ".txt"));
				
				workMain++;		// work recorded for calling the ClosestPair constructor class
				pairs = new ClosestPair(A, printWriter);				
				
				workMain++;   	// work recorded for calling the closestPairsDNC() method
				workMain++;   	// work recorded for calling the getDataX() method
				workMain++;	  	// work recorded for calling the getDataY() method
				
				// calculates the closest m <= n-1 pair of points in a 2D array A
				pairs.closestPairsDNC(pairs.getDataX(), pairs.getDataY(), A.length, printWriter, "");
				
				// no work calculated for IO operations
				// print the single closest pair of points found and it's distance to a file
				pairs.printOutput(pairs.getClosestPair(), null, 1, printWriter);
				
				// no work calculated for IO operations
				// prints the closest m <= n-1 pair of points and their distance
				pairs.printOutput(null, pairs.getClosestMPairs(), sizeM, printWriter);
				
				// print the total work calculated from the work in the MainBF class and in the Closest Pair class to the terminal
				// work recorded for calling the getWork() method
				printWriter.println("\nTotal work done: " + (workMain + pairs.getWork() + 1));

			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (printWriter != null)
					printWriter.close();
			}
		} else {
			workMain++;		//work recorded for calling the ClosestPair constructor class
			pairs = new ClosestPair(A, null);
			
			workMain++;	  	// work recorded for calling the closestPairsDNC() method
			workMain++;   	// work recorded for calling the getDataX() method
			workMain++;   	// work recorded for calling the getDataY() method
			
			// calculates the closest m <= n-1 pair of points in a 2D array A
			pairs.closestPairsDNC(pairs.getDataX(), pairs.getDataY(), A.length, null, "");
			
			// no work calculated for IO operations
			// print the single closest pair of points found and it's distance to the terminal
			pairs.printOutput(pairs.getClosestPair(), null, 1, null);
			
			// no work calculated for IO operations
			// prints the closest m <= n-1 pair of points and their distance to the terminal
			pairs.printOutput(null, pairs.getClosestMPairs(), sizeM, null);
			
			// print the total work calculated from the work in the MainDNC class and in the Closest Pair class to the terminal
			// work recorded for calling the getWork() method
			System.out.println("Total work done: " + (workMain + pairs.getWork() + 1));
		}		
	}
	
}
