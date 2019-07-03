package bruteforce;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Scanner;

/**
 * Main class for Closest Pair Brute Force algorithm
 * @author Cristina Padro-Juarbe
 *
 */
public class MainBF {

	final static int MAX_LENGTH_ARRAY = 30;  
	final static String filepath = "./bruteforce/";
	static PrintWriter printWriter = null;
	static ClosestPair pairs = null;
	static double[][] A;
	static double randomDouble;
	static BigDecimal bd = null;
	static int workMain = 0;

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		System.out.println("Enter an integer size for a 2D array: ");
		int size = in.nextInt();
		in.close();

		System.out.println("\nStarting work. Please wait...\n");
		run(size);
		System.out.println("\nDone!!\n");
	}

	/**
	 * This method creates a random number of points of length specified by the user and calls the 'bruteforceCP' method 
	 * to compute the closest pair of points in an array using brute force.
	 * This method prints traces (if active) and prints the output to a file (if size > 30) or to the console (if size <= 30).
	 * 
	 * @param size is the length for the 2D array of points
 	 */
	public static void run(int size) {
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
				
				workMain++;		//work recorded for calling the ClosestPair constructor class
				pairs = new ClosestPair(A, printWriter);
				
				workMain++;   	//work recorded for calling the getData() method
				workMain++;		//work recorded for calling the bruteForceCP() method
				
				// calculate the closest pair in a 2D array A
				pairs.bruteForceCP(pairs.getData(), A.length, printWriter);
				
				// no work calculated for IO operations
				// print the closes pair of points found and it's distance to a file
				pairs.printOutput(pairs.getClosestPair(), pairs.getMinDist(), printWriter);				
				printWriter.println();
				
				// print the total work calculated from the work in the MainBF class and in the Closest Pair class
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
			
			workMain++;   	//work recorded for calling the getData() method
			workMain++;		//work recorded for calling the bruteForceCP() method
			
			// calculate the closest pair in a 2D array A
			pairs.bruteForceCP(pairs.getData(), A.length, null);
			
			// no work calculated for IO operations
			// print the closes pair of points found and it's distance to the terminal
			pairs.printOutput(pairs.getClosestPair(), pairs.getMinDist(), null);
			
			// print the total work calculated from the work in the MainBF class and in the Closest Pair class to the terminal
			// work recorded for calling the getWork() method
			System.out.println("\nTotal work done: " + (workMain + pairs.getWork() + 1));
		}	
	}
}
