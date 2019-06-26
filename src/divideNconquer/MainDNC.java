/**
 * 
 */
package divideNconquer;

/**
 * @author crist
 *
 */
public class MainDNC {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		//initializing A for testing 
		double[][] A = new double[8][2];
		A[0][0] = 5;
		A[0][1] = 13;		
		A[1][0] = 26;
		A[1][1] = 1;
		A[2][0] = 9;
		A[2][1] = 7;
		A[3][0] = 8;
		A[3][1] = 12;
		A[4][0] = 0;
		A[4][1] = 2;
		A[5][0] = 19;
		A[5][1] = 16;
		A[6][0] = 6;
		A[6][1] = 17; 
		A[7][0] = 4;
		A[7][1] = 20;

		ClosestPair pairs = new ClosestPair(A);

		//Calculates the closest m <= n-1 pairs in A 
		pairs.closestPairs(pairs.getAx(), pairs.getAy(), A.length);
		
		//Prints the single closest pair in A and it's respective distance
		pairs.printOutput(pairs.getClosestPair(), null, 1);
		
		//Prints the single closest pair in A and it's respective distance
		pairs.printOutput(null, pairs.getClosestMPairs(), A.length - 1);
				
	}
}
