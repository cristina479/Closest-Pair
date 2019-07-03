/**
 * 
 */
package divideNconquer;

import java.awt.geom.Point2D;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * To print trace runs to a file (input >= 30) or to the terminal (input < 30), change PRINT_TRACE to true.
 * 
 * Class for the Closest Pair Divide N Conquer algorithm
 * @author Cristina Padro-Juarbe
 *
 */
public class ClosestPair { 

	private Point2D.Double[] dataX;				// Array of points (x, y) sorted by the x-coordinate
	private Point2D.Double[] dataY;				// Array of points (x, y) sorted by the y-coordinate
	private List<Object[]> closest; 			// Array of m <= n - 1 closest pairs of points (x, y)
	private Point2D.Double[] closestPair;		// Structure of the array: [0]: point1, [1]: point2, [2]: distance between point1 and point2 
	private double minDist = Double.MAX_VALUE;	// Simulates an infinite positive number
	private static final boolean PRINT_TRACE = false;
	static long work = 0;						// Measures the work being done by the algorithm

	/**
	 * Constructor of ClosestPair to initialize the Point2D.Double[] dataX and dataY arrays, and the Point2D.Double[] closestPair and closest arrays.
	 * This method prints the input data to a file (if size of data > 30) or to the terminal (if size of data <= 30). 
	 * 
	 * @param arrayA is an array of double precision points (x, y) randomly generated 
	 * @param pw is the Print Writer object to write output and traces to a file
	 */
	public ClosestPair(double[][] arrayA, PrintWriter pw) {
		Point2D.Double[] data = new Point2D.Double[arrayA.length];
		
		// array of points sorted in ascending order respective to the x-coordinate
		this.dataX = new Point2D.Double[arrayA.length];
		
		// array of points sorted in ascending order respective to the y-coordinate
		this.dataY = new Point2D.Double[arrayA.length];
		
		// array of points that will store the single closest pair of points in arrayA (input array of points)
		this.closestPair = new Point2D.Double[2];
		
		// array of m <= n - 1 closest pairs of points in arrayA (input array of points)
		this.closest = new ArrayList<Object[]>();

		// initialization of dataX and dataY with values of arrayA
		for(int index = 0; index < arrayA.length; index++) {
			data[index] = new Point2D.Double(arrayA[index][0], arrayA[index][1]);
			this.dataX[index] = new Point2D.Double(arrayA[index][0], arrayA[index][1]);
			this.dataY[index] = new Point2D.Double(arrayA[index][0], arrayA[index][1]);
			work++;		// work recorded for every loop iteration
		}
		work++;		// work recorded for breaking from the loop
		
		work++;		// work recorded for calling the mergeSort() method 
		mergeSort(this.dataX, this.dataX.length, "x");
		
		work++;		// work recorded for calling the mergeSort() method 
		mergeSort(this.dataY, this.dataY.length, "y");
		
		// no work calculated for IO operations
		// prints input data to a file
		if(pw != null) {
			pw.println("=================================== INPUT ===================================\n");
			pw.println("Data Size: " + this.dataX.length + "\n");
			
			pw.println("dataX: ");
			for(int index=0; index < this.dataX.length; index++) {
				pw.print("(" + this.dataX[index].getX() + ", " + this.dataX[index].getY() + ")   " );
				
				if((index + 1) % 10 == 0) {
					pw.println();
				}
			}
			pw.println();
			pw.println("dataY: ");
			for(int index=0; index < this.dataY.length; index++) {
				pw.print("(" + this.dataY[index].getX() + ", " + this.dataY[index].getY() + ")   " );
				
				if((index + 1) % 10 == 0) {
					pw.println();
				}
			}
			
			pw.println("\n\n=================================== OUTPUT ===================================\n");			
		} else {
			// print input data to the terminal
			System.out.println("\n=================================== INPUT ===================================\n");
			System.out.println("Data Size: " + this.dataX.length + "\n");
			
			System.out.println("dataX: ");
			for(int index=0; index < this.dataX.length; index++) {
				System.out.print("(" + this.dataX[index].getX() + ", " + this.dataX[index].getY() + ")   " );
				
				if((index + 1) % 10 == 0) {
					System.out.println();
				}
			}
			
			System.out.println("\n\ndataY: ");
			for(int index=0; index < this.dataY.length; index++) {
				System.out.print("(" + this.dataY[index].getX() + ", " + this.dataY[index].getY() + ")   " );
				
				if((index + 1) % 10 == 0) {
					System.out.println();
				}
			}
			System.out.println("\n\n=================================== OUTPUT ===================================\n");
		}
		
		work++;		// work recorded for returning from the ClosestPair class constructor
	}

	/**
	 * This method return the closest pair of points (x, y) in an array of points (x, y) using a divide and conquer approach. Worst-case is O(n log n).
	 * 
	 * @param dataX is an array containing points (x, y) sorted in ascending order relative to the x-coordinate
	 * @param dataY is an array containing points (x, y) sorted in ascending order relative to the y-coordinate
	 * @param size is the size of the array at the left or right side of the dataX array 
	 * @param pw is the Print Writer object to print the string values to a file
	 * @param message is a string to indicate if the closest pair of points in the left or right side of the data sorted in respect to the x-coordinate (dataX array)
	 * @return the closest pair of points (x, y) in an array of points (x, y) and their distance
	 */
	public Object[] closestPairsDNC(Point2D.Double[] dataX, Point2D.Double[] dataY, int size, PrintWriter pw, String message) {
		// base case: use brute force to get closest pair of points	
		if(size <= 3) {
			work++;		// work recorded for calling the bruteForceCP() method
			return bruteForceCP(dataX, size, pw, message);
		}
		
		// divide dataX and dataY in half into two arrays of points 
		int leftSize = size/2; 
		int rightSize = size - (size/2); 

		// get middle point in dataX
		Point2D.Double middlePoint = dataX[leftSize];

		// initializing the left and right sub-arrays of dataX
		Point2D.Double[] xLeft = new Point2D.Double[leftSize];
		Point2D.Double[] xRight = new Point2D.Double[rightSize];  

		// initializing the counters (indices) for the size of arrays xLeft and xRight
		int xLeftLength = 0, xRightLength = 0; 
		
		// initialize xLeft and xRight (each with half the points of dataX)
		for(int index = 0; index < size; index++ ) {	
			// lower half of points in dataX
			if(dataX[index] != null) {
				if(index < leftSize) {				
					xLeft[xLeftLength++] = dataX[index];			
				} else {
					// upper half of points in dataX
					xRight[xRightLength++] = dataX[index];
				}
			}
			work++;		// work recorded for every loop iteration in the outer loop
		}
		work++;		// work recorded for breaking from the inner loop
		
		// divide dataY in half into two arrays of points
		Point2D.Double[] yLeft = new Point2D.Double[leftSize];
		Point2D.Double[] yRight = new Point2D.Double[rightSize];	
		
		// initializing the counters (indices) for the size of arrays yLeft and yRight
		int yLeftLength = 0, yRightLength = 0;

		// initialize yLeft and yRight (each with half the points of dataY)
		for(int index = 0; index < size; index++ ) {			
			if(dataY[index] != null) {
				// lower half of points in dataY 
				if(yLeftLength < yLeft.length) {		
					yLeft[yLeftLength++] = dataY[index];
				} else {
					// upper half of points in dataY 
					yRight[yRightLength++] = dataY[index];	
				}
			}
			work++;		// work recorded for every loop iteration in the outer loop
		}
		work++;		// work recorded for breaking from the inner loop
		
		// no work calculated for IO operations
		// prints the points in dataX and dataY in each recursion
		if(PRINT_TRACE) {
			if(pw == null) {
				System.out.println("================== Data Size: " + size/2 + "\n");
				System.out.println("dataX: ");
				System.out.print("L: ");
				printData(xLeft, yLeftLength, pw);
				System.out.print("|  R: ");
				printData(xRight, xRightLength, pw);
				System.out.println("\n\ndataY: ");
				System.out.print("L: ");
				printData(yLeft, yLeftLength, pw);
				System.out.print("|  R: ");
				printData(yRight, yRightLength, pw);
				System.out.println("\n");
			} else {
				pw.println("================== Data Size: " + size/2 + "\n");
				pw.println("dataX: ");
				pw.print("L: ");
				printData(xLeft, xLeftLength, pw);
				pw.print("|  R: ");
				printData(xRight, xRightLength, pw);
				pw.println();
				pw.println("dataY: ");
				pw.print("L: ");
				printData(yLeft, yLeftLength, pw);
				pw.print("|  R: ");
				printData(yRight, yRightLength, pw);
				pw.println();
			}
		}
		
		work++;		// work recorded for calling the closestPairsDNC() method 

		// recursively obtain the closest pair of points in the left sub-array of dataX and their minimum distance
		Object[] temp1 = closestPairsDNC(xLeft, yLeft, leftSize, pw, " on the left side of dataX");	
		
		work++;		// work recorded for calling the closestPairsDNC() method
		
		// recursively obtain the closest pair of points in the right sub-array of dataX and their distance
		Object[] temp2 = closestPairsDNC(xRight, yRight, rightSize, pw, " on the right side of dataX");		
		
		// set the closestPair by assigning the closest pair found in the left side of dataX
		this.closestPair = (Point2D.Double[]) temp1[1];

		// update the distance between the closest pair found in the left side of dataX
		this.minDist = (double) temp1[0];

		// update closestPair and the distance between the closest pair if the right sub-array of dataX has the closest pair
		if((double) temp2[0] < this.minDist) {
			this.closestPair = (Point2D.Double[]) temp2[1];
			this.minDist = (double) temp2[0];
		}

		// Create an array to store the points in the strip. The strip consists of all the points to the left and right of dataX (divided by the middlepoint.x) 
		// and that are not farther away from the middlepoint.x than the distance of the closest pairs
		Point2D.Double[] strip = new Point2D.Double[size];
		
		// initialize the counter (index) of the strip array
		int stripLength = 0;
		
		// no work calculated for IO operations
		// prints the delimiters (upper and lower bounds) of the strip array
		if(PRINT_TRACE) {
			if(pw == null) {
				System.out.println("\n================== In Strip\n");
				System.out.println("Points in x-coordinate between " + (middlePoint.getX() - this.minDist) + " to " + middlePoint.getX() + " and " + middlePoint.getX() + " to " + (middlePoint.getX() + this.minDist) + ":\n");			
			} else {
				pw.println();
				pw.println("================== In Strip");
				pw.println();
				pw.println("Points in x-coordinate between " + (middlePoint.getX() - this.minDist) + " to " + middlePoint.getX() + " and " + middlePoint.getX() + " to " + (middlePoint.getX() + this.minDist) + ":\n");			
			}
		}

		// initialize the strip array of points (x, y)
		for(int index = 0; index < size; index++) {
			if(dataY[index] != null && Math.abs(dataY[index].getX() - middlePoint.getX()) < this.minDist) {
				strip[stripLength++] = dataY[index];
				
				// no work calculated for IO operations 
				// prints the pair of points (x, y) in the strip array
				if(PRINT_TRACE) {
					if(pw == null) {	
						System.out.print("(" + strip[stripLength-1].getX() + ", " + strip[stripLength-1].getY() + ")   " );
	
						if((index + 1) % 10 == 0) {
							System.out.println();
						}
					} else {
						pw.print("(" + strip[stripLength-1].getX() + ", " + strip[stripLength-1].getY() + ")   " );
	
						if((index + 1) % 10 == 0) {
							pw.println();
						}		
					}
				}
			}	
			work++;		// work recorded for every loop iteration	
		}
		work++;		// work recorded for breaking from the loop
		
		// no work calculated for IO operations 
		if(PRINT_TRACE) {
			if(pw == null) {
				System.out.println("\n\nPoints to be compared in the strip relative to y-axis: \n");
			} else {
				pw.println();
				pw.println();
				pw.println("Points to be compared in the strip relative to y-axis: ");
			}
		}

		// minD will store the minimum distance between the points in the strip array.
		// The largest value minD can have is the minimum distance found in dataX if no points in the strip are closer than the closest pair found in dataX
		double minD = this.minDist;
		
		// flag used for trace runs
		boolean foundAPairToCompare = false;

		// Traverse the strip array and compare the distance between each point (x, y) in the strip. 
		// At first, this nested loop suggests that the worst-case runtime is O(n * d), d being the minimum distance found in dataX.
		// However, the inner loop has an upper bound O(d) which runs in O(1) because it compares very few points (x, y) in each recursion.
		// This makes comparing the distance between each point (x, y) in the strip to have worst-case O(n).
		for(int index1 = 0; index1 < stripLength; index1++) {
			for(int index2 = index1 + 1; index2 < stripLength && (((Point2D.Double) strip[index2]).getY() - ((Point2D.Double) strip[index1]).getY()) < this.minDist; index2++) {
				foundAPairToCompare = true;
				work++;		// work recorded for calling the getDistance() method
				double distance = getDistance(strip[index2], strip[index1]);
				
				// compare the pair of points in the strip array
				if(distance < minD) {
					// update the closest pair and minimum distance between the closest pair of points
					minD = distance;
					this.closestPair = new Point2D.Double[] { strip[index2], strip[index1] };
					work++; 	// work recorded for calling the add() method. This method adds an object to index 0, which takes constant time O(1)
					closest.add(0, new Object[] { minD, this.closestPair });
					
					work++;		// work recorded for returning from the add() method
				}
				
				// no work calculated for IO operations 
				// prints the pair of points (x, y) compared in the strip array and their distance
				if(PRINT_TRACE) {
					if(pw == null) {
						System.out.println("(" + ((Point2D.Double) strip[index1]).getX() + ", " + ((Point2D.Double) strip[index1]).getY() + ") and (" + ((Point2D.Double) strip[index2]).getX() + ", " + ((Point2D.Double) strip[index2]).getY() + ") with distance: " + distance);
					} else {
						pw.println("(" + ((Point2D.Double) strip[index1]).getX() + ", " + ((Point2D.Double) strip[index1]).getY() + ") and (" + ((Point2D.Double) strip[index2]).getX() + ", " + ((Point2D.Double) strip[index2]).getY() + ") with distance: " + distance);
					}
				}
				work++;		// work recorded for every loop iteration in the inner loop	
			}
			work++;		// work recorded for breaking from the inner loop
			work++;		// work recorded for every loop iteration in the outer loop
		}
		work++;		// work recorded for breaking from the outer loop 
		
		// no work calculated for IO operations
		// prints when there are no pair of points in the strip array
		if(PRINT_TRACE) {
			if(!foundAPairToCompare) {
				if(pw == null) {
					System.out.println("None.");
				} else {
					pw.println("None.");
				}
			}
		}

		// update the distance between the closest pairs
		this.minDist = minD;
		
		// no work calculated for IO operations
		if(PRINT_TRACE) {
			if(pw == null) {
				System.out.println("----\n");
				System.out.println(toString(new Object[] { this.minDist, this.closestPair }, "") + "\n");
			} else {
				pw.println("----");
				pw.println();
				if(this.closestPair == null) {
					pw.println(toString(new Object[] { this.minDist, this.closestPair }, ""));	
				}
				pw.println(toString(new Object[] { this.minDist, this.closestPair }, ""));	
				pw.println();
			}
		}

		work++;		// work recorded for returning from the closestPairsDNC() method 
		return new Object[] { this.minDist, this.closestPair };
	}

	/**
	 * Calculates the closest pair of points (x, y) in an array of points (x, y) using brute force. Worst-case is O(1).
	 * 
	 * @param data array of points (x, y)
	 * @param size is the size of the data array
	 * @param pw is the Print Writer object to print the string values to a file
	 * @param message is a string to indicate if the closest pair of points in the left or right side of the data sorted in respect to the x-coordinate (dataX array)
	 * @return the closest pair of points in an array of points (x, y)
	 */
	public Object[] bruteForceCP(Point2D.Double[] data, int size, PrintWriter pw, String message) {
		double minD = Double.MAX_VALUE;
		Point2D.Double[] cPair = new Point2D.Double[2];
		
		// no work calculated for IO operations
		if(PRINT_TRACE) {
			if(pw != null) {
				pw.println();
			}
		}

		// compare each point (x, y) in a data array using brute force. This operation has worst-case O(1) because the data array 
		// will have at most three pair of points (x, y) to compare
		for(int index1 = 0; index1 < size - 1; index1++) {
			for(int index2 = index1 + 1; index2 < size; index2++) {
				Point2D.Double p1 = data[index1];
				Point2D.Double p2 = data[index2];

				work++;		// work recorded for calling the getDistance() method
				double distance = getDistance(p1, p2);

				// update the closest distance between two pairs
				// update the closest pairs
				if(distance < minD) {
					minD = distance;
					cPair = new Point2D.Double[] { p1, p2 };
				}

				work++; 	// work recorded for calling the add() method. This method adds an object to index 0, which takes constant time O(1)
				this.closest.add(0, new Object[] { distance, new Point2D.Double[] { p1, p2 }});

				work++;		// work recorded for returning from the add() method
				work++; 	// work recorded for every loop iteration in the inner loop
			}
			work++;		// work recorded for breaking from the inner loop
			work++;		// work recorded for every loop iteration in the outer loop
		}
		work++;		// work recorded for breaking from the outer loop
		
		// no work calculated for IO operations
		// print the closest pair of points and their distance in the data array
		if(PRINT_TRACE) {
			if(pw == null) {
				System.out.println(toString(new Object[] { minD, cPair }, message));
			} else {
				pw.println(toString(new Object[] { minD, cPair }, message));
			}
		}

		work++;		// work recorded for returning from the bruteForceCP() method
		return new Object[] { minD, cPair };
	}

	/**
	 * This method calculates the Euclidean distance between two points p1 and p2
	 * 
	 * @param p1 a double precision point (x, y)
	 * @param p2 a double precision point (x, y)
	 * @return the Euclidean distance between two points p1 and p2
	 */
	public double getDistance(Point2D.Double p1, Point2D.Double p2) {
		work++;		// work recorded for returning from the getDistance() method
		// distance between p1 and p2
		return Math.sqrt(Math.pow(p1.getX() - p2.getX(), 2) + Math.pow(p1.getY() - p2.getY(), 2));
	}
	
	/**
	 * 
	 * @return the closest pair of m points (x, y) and it's distance, where m <= n - 1
	 */
	public List<Object[]> getClosestMPairs() {
		// no work recorded because this method is only called for IO operations
		return this.closest;
	}

	/**
	 * 
	 * @return the closest pair of points (x, y) and it's distance
	 */
	public Object[] getClosestPair() {
		// no work recorded because this method is only called for IO operations
		return new Object[] { this.minDist, this.closestPair };
	}

	/**
	 * 
	 * @return the input array of points (x, y) sorted in respect to the y-coordinate
	 */
	public Point2D.Double[] getDataX() {
		work++;		// work recorded for returning from the getDataX() method
		return this.dataX;
	}

	/**
	 * 
	 * @return the input array of points (x, y) sorted in respect to the y-coordinate
	 */
	public Point2D.Double[] getDataY() {
		work++;		// work recorded for returning from the getDataY() method
		return this.dataY;
	}

	/**
	 * 
	 * @return work done in Closest Pair class
	 */
	public long getWork() {
		return ++work;		// work recorded for returning from the getWork() method
	}
	
	/**
	 * Overloaded method that returns the string value of the closest m pair of points and their distance in an array of points (x, y)
	 * 
	 * @param closestPairsList is an array of m <= n - 1 closest pairs of points and their distance
	 * @param sizeM size of m
	 * @param pw is the Print Writer object to print the string values to a file
	 * @return the string value of the closest m <= n - 1 pair of points and their distance in an array of points (x, y) 
	 */
	public String toString(List<Object[]> closestPairsList, int sizeM, PrintWriter pw) { 
		String result = "";

		// no work calculated for IO operations
		for(int index = 0; index < sizeM && index < closestPairsList.size(); index++) {
			Object[] obj = closestPairsList.get(index);
			Point2D.Double[] pairs = ((Point2D.Double[]) obj[1]);
			double distance = (double) obj[0];
			
			// prints the closest m <= n-1 pair of points and their distance
			if(pw == null) {
				result += "(" + pairs[0].getX() + ", " + pairs[0].getY() + ") and (" + pairs[1].getX() + ", " + pairs[1].getY() + ") with distance: " + distance + "\n";
			} else {
				pw.println("(" + pairs[0].getX() + ", " + pairs[0].getY() + ") and (" + pairs[1].getX() + ", " + pairs[1].getY() + ") with distance: " + distance);
			}
		}
		
		return result;
	}

	/**
	 * Overloaded method that returns the string value of the closest pair of points and their distance in an array of points (x, y)
	 * 
	 * @param closestPair is an array with three values: two Point2D.Double points and a double value of their distance.
	 * @param partition is a string to indicate if the closest pair of points in the left or right side of the data sorted in respect to the x-coordinate (dataX array)
	 * @return the string value of the closest pair of points and their distance in an array of points (x, y)
	 */
	public String toString(Object[] closestPair, String partition) {
		Point2D.Double[] pairs = ((Point2D.Double[]) closestPair[1]);
		double distance = (double) closestPair[0];
				
		return "Closest Pair" + partition + " is (" + pairs[0].getX() + ", " + pairs[0].getY() + ") and (" + pairs[1].getX() + ", " + pairs[1].getY() + ") with distance: " + distance;
	}
	
	/**
	 * This method is used to print the input data points (x, y) in the left and right sub-arrays of dataX; used for printing trace runs
	 * 
	 * @param points is a sub-array of dataX with points in the left or in the right side of dataX
	 * @param size of the points array
	 * @param pw is the Print Writer object to print the string values to a file
	 */
	private void printData(Point2D.Double[] points, int size, PrintWriter pw) {
		// no work calculated for IO operations
		if(PRINT_TRACE) {
			if(pw == null) {
				for(int index=0; index < size; index++) {
					System.out.print("(" + points[index].getX() + ", " + points[index].getY() + ")   " );
	
					if((index + 1) % 10 == 0) {
						System.out.println();
					}
				}
			} else {
				for(int index=0; index < size; index++) {				
					pw.print("(" + points[index].getX() + ", " + points[index].getY() + ")   " );
	
					if((index + 1) % 10 == 0) {
						pw.println();
					}
				}
			}
		}
	}

	/**
	 * This method prints the closest m <= n - 1 pair of points in an array of points (x, y). 
	 * To print the closest pair of points in an array of points (x, y) we use: m = 1 and closestPairsList == null.
	 * To prints the closest m <= n - 1 pair of points in an array of points (x, y) we use: m <= n - 1 and closestPair == null.
	 * 
	 * @param closestPair is an array with three two Point2D.Double points and a double value of their distance.
	 * @param closestPairsList is an array of m <= n - 1 closest pairs of points and their distance
	 * @param sizeM size of m
	 * @param pw is the Print Writer object to print the string values to a file
	 */
	public void printOutput(Object[] closestPair, List<Object[]> closestPairsList, int sizeM, PrintWriter pw) {		
		// no work calculated for IO operations
		if(PRINT_TRACE) {
			if(pw == null) {
				System.out.println("==============================================================================");
			} else {
				pw.println("==============================================================================");
			}
		}
		
		// no work calculated sorting the closest m < n - 1 pair of points since this wasn't part of the requirements of the program
		// sort closestPairs list
		if(closestPairsList != null) {
			Comparator<Object[]> compareByDist = (Object[] obj1, Object[] obj2) -> ((Double) obj1[0]).compareTo((Double) obj2[0]);
			Collections.sort(closestPairsList, compareByDist);
		}
		
		// no work calculated for IO operations
		if(pw == null) {
			if(closestPair != null && sizeM == 1) {
				// write to console
				System.out.println("RESULT: " + toString(closestPair, "") + "\n");
			} else if(closestPairsList != null && (sizeM > 0 && sizeM <= this.dataX.length - 1)) {
				// write to console
				System.out.println("RESULT: " + "Closest m = " + sizeM + " Pairs are: \n" + toString(closestPairsList, sizeM, null));
			}
		} else {
			if(closestPair != null && sizeM == 1) {
				// write to file
				pw.println("RESULT: " + toString(closestPair, ""));
				pw.println();
			} else if(closestPairsList != null && (sizeM > 0 && sizeM <= this.dataX.length - 1)) {
				// write to file
				pw.println("RESULT: " + "Closest m = " + sizeM + " Pairs are: "); 
				pw.println(toString(closestPairsList, sizeM, pw));
			}
		}
	}
	
	/**
	 * This method sorts an array in ascending order in worst-case O(n log n).
	 * 
	 * @param data is the array to be sorted
	 * @param size is the length of the array to be sorted
	 * @param s is a string used to indicate whether sort the array respective to the x or y coordinates
	 */
	private void mergeSort(Point2D.Double[] data, int size, String s) {	
		// base case; arrays of size 1 are sorted
		if(size < 2) 
			return ;
		
		int middle = size/2;
		
		// divide data array in half 
		Point2D.Double[] left = new Point2D.Double[middle];
		Point2D.Double[] right = new Point2D.Double[size - middle];
		
		// initialize left side of array
		for(int index = 0; index < middle; index++) {
			left[index] = data[index];
			work++;		// work recorded for every loop iteration
		}
		work++;		// work recorded for breaking from the loop
		
		// initialize right side of array
		for(int index = middle; index < size; index++) {
			right[index - middle] = data[index];
			work++;		// work recorded for every loop iteration
		}
		work++;		// work recorded for breaking from the loop
		
		work++;		// work recorded for calling the mergeSort() method 
		mergeSort(left, middle, s);
		
		work++;		// work recorded for calling the mergeSort() method 
		mergeSort(right, size - middle, s);
		
		work++;		// work recorded for calling the merge() method 
		merge(data, left, right, middle, size - middle, s);
		
		work++;		// work recorded for returning from the mergeSort() method
	}
	
	/**
	 * This method merges two sorted an array in ascending order in worst-case O(n).
	 * 
	 * @param data is the array of points to be sorted
	 * @param left is the half left array of data 
	 * @param right is the half right array of data
	 * @param sizeLeft is the length of the left array
	 * @param sizeRight is the length of the right array
	 * @param s is a string used to indicate whether sort the array respective to the x or y coordinates
	 */
	private void merge(Point2D.Double[] data, Point2D.Double[] left, Point2D.Double[] right, int sizeLeft, int sizeRight, String s) {
		int index1 = 0;		// index of the left array
		int index2 = 0;		// index of the right array
		int index3 = 0;		// index of the data array

		// merge the left and right arrays to the data array
		while(index1 < sizeLeft && index2 < sizeRight) {
			Point2D.Double point1 = left[index1];
			Point2D.Double point2 = right[index2];
			double value1 = 0, value2 = 0;
			
			// sort respective to x-coordinate
			if(s.equals("x")) {	
				value1 = point1.getX();
				value2 = point2.getX();
			} else if(s.equals("y")) {
				// sort respective to y-coordinate
				value1 = point1.getY();
				value2 = point2.getY();
			}
			
			// merge the left and right arrays to the data array
			if(value1 <= value2) {
				data[index3++] = left[index1++];
			} else {
				data[index3++] = right[index2++];
			}
			
			work++;		// work recorded for every loop iteration
		}
		work++;		// work recorded for breaking from the loop

		// copy any remaining points in the left side array (if any) to data
		while (index1 < sizeLeft) { 
			data[index3++] = left[index1++];  
			work++;		// work recorded for every loop iteration
		} 
		work++;		// work recorded for breaking from the loop

		// copy any remaining points in the right side array (if any) to data
		while(index2 < sizeRight) {
			data[index3++] = right[index2++];
			work++;		// work recorded for every loop iteration
		}
		work++;		// work recorded for breaking from the loop
		
		work++; 	// work recorded for returning from the merge() method
	}
	
	/**
	 * Used to sort a list of objects storing two pair of points using their distance. This method is used to sort an array of m <= n - 1 closest pairs of points
	 */
	Comparator<Object[]> compareById = new Comparator<Object[]>() {
		public int compare(Object[] obj1, Object[] obj2) {
			Double dist1 = (Double) obj1[0];
			Double dist2 = (Double) obj2[0];
			
			return dist1.compareTo(dist2);
		}
	};
	
}
