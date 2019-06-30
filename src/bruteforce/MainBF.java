package bruteforce;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Scanner;

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
		myMethodName(size);
		System.out.println("\nDone!!\n");
	}

	public static void myMethodName(int size) {
		A = new double[size][2];

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
				printWriter = new PrintWriter(new FileWriter(filepath + "n=" + size + ".txt"));
				
				workMain++;
				pairs = new ClosestPair(A, printWriter);
				
				workMain++;
				workMain++;
				// calculate the closest pair in a 2D array A
				pairs.bruteForceCP(pairs.getA(), A.length, printWriter);
				
				//No work calculated
				// print closesPair
				pairs.printOutput(pairs.getClosestPair(), pairs.getMinDist(), printWriter);
				
				printWriter.println();
				printWriter.println("\nTotal work done: " + (workMain + pairs.getWork() + 1));
				
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (printWriter != null)
					printWriter.close();
			}
		} else {
			workMain++;
			pairs = new ClosestPair(A, null);
			
			workMain++;
			workMain++;
			// calculate the closest pair in a 2D array A
			pairs.bruteForceCP(pairs.getA(), A.length, null);
			
			//No work calculated
			// print closesPair
			pairs.printOutput(pairs.getClosestPair(), pairs.getMinDist(), null);
			
			System.out.println("\nTotal work done: " + (workMain + pairs.getWork() + 1));
		}
		
	}
}
