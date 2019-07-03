/**
 * 
 */
package bruteforce;

import java.awt.geom.Point2D;
import java.io.PrintWriter;

/**
 * To print trace runs to a file (input >= 30) or to the terminal (input < 30), change PRINT_TRACE to true.
 * 
 * Class for Closest Pair Brute Force algorithm
 * @author Cristina Padro-Juarbe
 *
 */
public class ClosestPair {

	private Point2D.Double[] data;							// Array of points (x, y) 
	private Point2D.Double[] closestPair;					// Structure of the array: [0]: point1, [1]: point2, [2]: distance between point1 and point2 
	private double minDist = Double.MAX_VALUE;				// Simulates an infinite positive number
	private static final boolean PRINT_TRACE = false;
	static long work = 0;									// Measures the work being done by the algorithm								

	/**
	 * Constructor of ClosestPair to initialize the Point2D.Double[] data array and the Point2D.Double[] closestPair array.
	 * This method prints the input data to a file (if size of data > 30) or to the terminal (if size of data <= 30). 
	 * 
	 * @param data is an array of double precision points (x, y) randomly generated 
	 * @param pw is the Print Writer object to write output and traces to a file
	 */
	public ClosestPair(double[][] data, PrintWriter pw) {
		this.data = new Point2D.Double[data.length];
		this.closestPair = new Point2D.Double[2];

		// no work calculated for IO operations
		// prints input data to a file
		if (pw != null) {
			pw.println("=================================== INPUT ===================================\n");
			pw.println("Data Size: " + data.length + "\n");

		} else {
			System.out.println("=================================== INPUT ===================================\n");
			System.out.println("Data Size: " + data.length + "\n");
		}

		// initialization of the data array with values of a 2D array 'data'
		for (int index1 = 0; index1 < data.length; index1++) {
			this.data[index1] = new Point2D.Double(data[index1][0], data[index1][1]);

			// no work calculated for IO operations
			if (pw == null) {
				System.out.print(pointToString(this.data[index1]) + "   ");

				if ((index1 + 1) % 10 == 0) {
					System.out.println();
				}
			} else {
				pw.print(pointToString(this.data[index1]) + "   ");

				if ((index1 + 1) % 10 == 0) {
					pw.println();
				}
			}
			work++;		// work recorded for every loop iteration		
		}
		work++;		// work recorded for breaking from the loop
		work++;		// work recorded for returning from the ClosestPair class constructor
	}

	/**
	 * Calculates the closest pair of points (x, y) in an array of points (x, y) using brute force. Worst-case is O(n).
	 * 
	 * @param data array of points (x, y)
	 * @param dataLength is the size of the data array
	 * @param pw is the Print Writer object to print the string values to a file
	 */
	public void bruteForceCP(Point2D.Double[] data, int dataLength, PrintWriter pw) {
		// no work calculated for IO operations
		if (pw == null) {
			System.out.println("\n=================================== OUTPUT ===================================");
		} else {
			pw.println("\n=================================== OUTPUT ===================================");
		}

		// compare each point (x, y) in a data array using brute force. This operation has worst-case O(n^2).
		for (int index1 = 0; index1 < dataLength - 1; index1++) {
			Point2D.Double p1 = data[index1];

			// no work calculated for IO operations
			if(PRINT_TRACE) {
				if (pw == null) {
					System.out.println("\n================== index = " + index1 + "\n");
					System.out.println("Comparing all points with point (" + p1.getX() + ", " + p1.getY() + ")");
				} else {
					pw.println("\n================== index = " + index1 + "\n");
					pw.println("Comparing all points with point (" + p1.getX() + ", " + p1.getY() + ")");
				}
			}

			for (int index2 = index1 + 1; index2 < dataLength; index2++) {
				Point2D.Double p2 = data[index2];
				work++;		// work recorded for calling the getDistance() method
				double distance = getDistance(p1, p2);

				// update the closest distance between two pairs
				// update the closest pairs
				if (distance < this.minDist) {
					this.minDist = distance;
					this.closestPair = new Point2D.Double[] { p1, p2 };
				}
				work++;		// work recorded for every loop iteration in the inner loop
			}
			work++;		// work recorded for breaking from the inner loop

			// no work calculated for IO operations
			if(PRINT_TRACE) {
				if (pw == null) {
					System.out.println(toString(this.closestPair, this.minDist));
				} else {
					pw.println(toString(this.closestPair, this.minDist));
				}
			}
			work++;		// work recorded for every loop iteration in the outer loop
		}
		work++;		// work recorded for breaking from the outer loop

		// no work calculated for IO operations
		if(PRINT_TRACE) {
			if (pw == null) {
				System.out.println("\n==============================================================================\n");
			} else {
				pw.println("\n==============================================================================\n");
			}
		}
		
		work++;		// work recorded for returning from the bruteForceCP() method
	}

	/**
	 * This method calculates the Euclidean distance between two points p1 and p2
	 * 
	 * @param point1 a double precision point (x, y)
	 * @param point2 a double precision point (x, y)
	 * @return the Euclidean distance between two points point1 and point2
	 */
	public double getDistance(Point2D.Double point1, Point2D.Double point2) {
		work++;		// work recorded for returning from the getDistance() method
		// distance between point1 and point2
		return Math.sqrt(Math.pow(point1.getX() - point2.getX(), 2) + Math.pow(point1.getY() - point2.getY(), 2));
	}

	/**
	 * 
	 * @return the input array of points (x, y) 
	 */
	public Point2D.Double[] getData() {
		work++;		// work recorded for returning from the getData() method
		return this.data;
	}

	/**
	 * 
	 * @return
	 */
	public Point2D.Double[] getClosestPair() {
		return this.closestPair;
	}

	/**
	 * 
	 * @return returns the distance between the closest pair of points
	 */
	public double getMinDist() {
		// no work recorded because this method is only called for IO operations
		return this.minDist;
	}

	/**
	 * 
	 * @return work done in Closest Pair class
	 */
	public long getWork() {
		return ++work;		// work recorded for returning from the getWork() method
	}
	
	/**
	 * 
	 * @param point a double precision point (x, y)
	 * @return the string value of point (x, y)
	 */
	public String pointToString(Point2D.Double point) {
		return "(" + point.getX() + ", " + point.getY() + ")";
	}

	/**
	 * Overloaded method that returns the string value of two pair of points and their distance
	 * 
	 * @param point1 a double precision point (x, y)
	 * @param point2 a double precision point (x, y)
	 * @param distance is the distance between point1 and point2
	 * @return the string value of points point1 and point2 with their distance
	 */
	public String toString(Point2D.Double point1, Point2D.Double point2, double distance) {
		return "(" + point1.getX() + ", " + point1.getY() + ") and (" + point2.getX() + ", " + point2.getY() + ")   distance: " + distance;
	}

	/**
	 * Overloaded method that returns the string value of the closest pair of points and their distance in an array of points (x, y)
	 * 
	 * @param closestPair is an array with two Point2D.Double points
	 * @param distance the distance between the two points in closestPair
	 * @return the string value of the closest pair of points and their distance in an array of points (x, y)
	 */
	public String toString(Point2D.Double[] closestPair, double distance) {
		return "Closest Pairs are (" + closestPair[0].getX() + ", " + closestPair[0].getY() + ") and (" + closestPair[1].getX() + ", " + closestPair[1].getY() + ") with distance: " + distance;
	}

	/**
	 * 
	 * @param resultList the array containing the closest pair of points (x, y) in an array of points (x, y)
	 * @param distance the distance between the closest pair of points
	 * @param pw is the Print Writer object to print the string values to a file
	 */
	public void printOutput(Point2D.Double[] resultList, double distance, PrintWriter pw) {
		if (pw == null) {
			System.out.println("RESULT: " + toString(resultList, distance));
		} else {
			pw.println("RESULT: " + toString(resultList, distance));
		}
	}
}
