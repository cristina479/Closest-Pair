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
 * @author crist
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

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		System.out.println("Enter an integer size for a 2D array: ");
		int nSize = in.nextInt();
		System.out.println("\nEnter an integer M : ");
		
		int mSize = in.nextInt();
		while(mSize < 0 || mSize >= nSize) {
			System.out.println("M must be an integer between 1 and " + (nSize - 1));
			System.out.println("\nEnter an integer size for a 2D array: ");
			mSize = in.nextInt();
		}
		in.close();

		System.out.println("\nStarting work. Please wait...\n");
		myMethodName(nSize, mSize);
		System.out.println("\nDone!!\n");
	}

	private static void myMethodName(int n, int m) {
		A = new double[n][2];

		// creating array of points A
		for (int index1 = 0; index1 < A.length; index1++) {
			for (int index2 = 0; index2 < 2; index2++) {
				randomDouble = Math.random() * 1000 + 0.1;
				bd = new BigDecimal(randomDouble);
				bd = bd.round(new MathContext(8));
				A[index1][index2] = bd.doubleValue();
			}
		}

		boolean trace_run_console = A.length <= MAX_LENGTH_ARRAY ? true : false;

		if (!trace_run_console) {
			try {
				printWriter = new PrintWriter(new FileWriter(filepath + "n=" + n + ".txt"));
				// initialize data points
				pairs = new ClosestPair(A, printWriter);
				// Calculates the closest m <= n-1 pair of points in a 2D array A
				pairs.closestPairs(pairs.getAx(), pairs.getAy(), A.length, printWriter, "");
				// Prints the single closest pair in A and it's respective distance
				pairs.printOutput(pairs.getClosestPair(), null, 1, printWriter);
				// Prints the single closest pair in A and it's respective distance
				pairs.printOutput(null, pairs.getClosestMPairs(), m, printWriter);

			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (printWriter != null)
					printWriter.close();
			}
		} else {
			pairs = new ClosestPair(A, null);
			// Calculates the closest m <= n-1 pair of points in a 2D array A
			pairs.closestPairs(pairs.getAx(), pairs.getAy(), A.length, null, "");
			// Prints the single closest pair in A and it's respective distance
			pairs.printOutput(pairs.getClosestPair(), null, 1, null);
			// Prints the single closest pair in A and it's respective distance
			pairs.printOutput(null, pairs.getClosestMPairs(), m, null);
		}
	}
}
