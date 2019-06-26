package bruteforce;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.math.MathContext;

public class MainBF {

	public static void main(String[] args) {

		final int ONE_HUNDRED = 100;
		final int TEN_THOUSAND = 10000;
		final int FIVE_HUNDRED_THOUSAND = 500000;
		final int ONE_MILLION = 1000000;
		final int MAX_LENGTH_ARRAY = 30;
		final String filepath = "src/bruteforce/";
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
				//calculate the closest pair in a 2D array A 
				pairs.bruteForceCP(pairs.getA(), A.length, printWriter);	
				//print closesPair
				pairs.printOutput(pairs.getClosestPair(), pairs.getMinDist(), printWriter);

			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if(printWriter != null) printWriter.close();
			}			
		} else {
			pairs = new ClosestPair(A, null);
			//calculate the closest pair in a 2D array A 
			pairs.bruteForceCP(pairs.getA(), A.length, null);	
			//print closesPair
			pairs.printOutput(pairs.getClosestPair(), pairs.getMinDist(), null);
		}

		// --------------------------- For testing Closest Pair Brute Force with N = 100,000 ----------------------------

		//creating array of points A
		A = new double[TEN_THOUSAND][2];

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
				printWriter = new PrintWriter(new FileWriter(fileName_10000));
				//initialize data points
				pairs = new ClosestPair(A, printWriter);
				//calculate the closest pair in a 2D array A 
				pairs.bruteForceCP(pairs.getA(), A.length, printWriter);	
				//print closesPair
				pairs.printOutput(pairs.getClosestPair(), pairs.getMinDist(), printWriter);

			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if(printWriter != null) printWriter.close();
			}			
		} else {
			pairs = new ClosestPair(A, null);
			//calculate the closest pair in a 2D array A 
			pairs.bruteForceCP(pairs.getA(), A.length, null);	
			//print closesPair
			pairs.printOutput(pairs.getClosestPair(), pairs.getMinDist(), null);
		}

		// --------------------------- For testing Closest Pair Brute Force with N = 500,000 ----------------------------

		//creating array of points A
		A = new double[FIVE_HUNDRED_THOUSAND][2];

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
				printWriter = new PrintWriter(new FileWriter(fileName_500000));
				//initialize data points
				pairs = new ClosestPair(A, printWriter);
				//calculate the closest pair in a 2D array A 
				pairs.bruteForceCP(pairs.getA(), A.length, printWriter);	
				//print closesPair
				pairs.printOutput(pairs.getClosestPair(), pairs.getMinDist(), printWriter);

			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if(printWriter != null) printWriter.close();
			}			
		} else {
			pairs = new ClosestPair(A, null);
			//calculate the closest pair in a 2D array A 
			pairs.bruteForceCP(pairs.getA(), A.length, null);	
			//print closesPair
			pairs.printOutput(pairs.getClosestPair(), pairs.getMinDist(), null);
		}

		// -------------------------- For testing Closest Pair Brute Force with N = 1,000,000 ---------------------------

		//creating array of points A
		A = new double[ONE_MILLION][2];

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
				printWriter = new PrintWriter(new FileWriter(fileName_1000000));
				//initialize data points
				pairs = new ClosestPair(A, printWriter);
				//calculate the closest pair in a 2D array A 
				pairs.bruteForceCP(pairs.getA(), A.length, printWriter);	
				//print closesPair
				pairs.printOutput(pairs.getClosestPair(), pairs.getMinDist(), printWriter);

			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if(printWriter != null) printWriter.close();
			}			
		} else {
			pairs = new ClosestPair(A, null);
			//calculate the closest pair in a 2D array A 
			pairs.bruteForceCP(pairs.getA(), A.length, null);	
			//print closesPair
			pairs.printOutput(pairs.getClosestPair(), pairs.getMinDist(), null);
		}
		
		System.out.println("\nDone!!\n");
	}

}
