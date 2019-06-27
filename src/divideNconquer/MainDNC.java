/**
 * 
 */
package divideNconquer;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.math.MathContext;

/**
 * @author crist
 *
 */
public class MainDNC {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		final int ONE_HUNDRED = 100;
		final int TEN_THOUSAND = 10000;
		final int FIVE_HUNDRED_THOUSAND = 500000;
		final int ONE_MILLION = 1000000;
		final int MAX_LENGTH_ARRAY = 30;
		final String filepath = "src/divideNconquer/";
		final String fileName_100 = filepath + "n=" + ONE_HUNDRED;
		final String fileName_10000 = filepath + "n=" + TEN_THOUSAND;
		final String fileName_500000 = filepath + "n=" + FIVE_HUNDRED_THOUSAND;
		final String fileName_1000000 = filepath + "n=" + ONE_MILLION;
		boolean trace_run_console = false;
		PrintWriter printWriter = null;
		ClosestPair pairs = null;	
		double[][] A;
		double randomDouble;
		BigDecimal bd = null;
		
		System.out.println("\nStarting work. Please wait...\n");

		// ------------------------------- For testing Closest Pair Brute Force with N = 100 -----------------------------

		A = new double[ONE_HUNDRED][2];
		
		//creating array of points A
		for(int index1=0; index1 < A.length; index1++) {
			for(int index2=0; index2 < 2; index2++) {
				randomDouble = Math.random() * 1000 + 0.1;
				bd = new BigDecimal(randomDouble);
				bd = bd.round(new MathContext(8));
				A[index1][index2] = bd.doubleValue();
			}
		}

		trace_run_console = A.length <= MAX_LENGTH_ARRAY? true : false;	

		if(!trace_run_console) {
			try {				
				printWriter = new PrintWriter(new FileWriter(fileName_100));
				//initialize data points
				pairs = new ClosestPair(A, printWriter);
				//Calculates the closest m <= n-1 pair of points in a 2D array A
				pairs.closestPairs(pairs.getAx(), pairs.getAy(), A.length, printWriter, "");				
				//Prints the single closest pair in A and it's respective distance
				pairs.printOutput(pairs.getClosestPair(), null, 1, printWriter);				
				//Prints the single closest pair in A and it's respective distance
				pairs.printOutput(null, pairs.getClosestMPairs(), A.length - 1, printWriter);	
				
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if(printWriter != null) printWriter.close();
			}			
		} else {
			pairs = new ClosestPair(A, null);
			//Calculates the closest m <= n-1 pair of points in a 2D array A
			pairs.closestPairs(pairs.getAx(), pairs.getAy(), A.length, null, "");				
			//Prints the single closest pair in A and it's respective distance
			pairs.printOutput(pairs.getClosestPair(), null, 1, null);				
			//Prints the single closest pair in A and it's respective distance
			pairs.printOutput(null, pairs.getClosestMPairs(), A.length - 1, null);	
			
		}

		System.out.println("\nDone!!\n");
				
	}
}
