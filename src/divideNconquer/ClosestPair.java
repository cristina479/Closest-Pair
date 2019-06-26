/**
 * 
 */
package divideNconquer;

import java.awt.geom.Point2D;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * @author crist
 *
 */
public class ClosestPair { 

	private static final int MAX_LENGTH = 30;
	private static final String filepath = "src/divideNconquer/closestPairs-BruteForce.txt";
	private Point2D.Double[] Ax;
	private Point2D.Double[] Ay;
	private List<Object[]> closest; 	//0: P1, 1: P2, 2: distance 
	private Point2D.Double[] closestPair;
	private double minDist = Double.MAX_VALUE;

	public ClosestPair(double[][] a) {
		Point2D.Double[] A = new Point2D.Double[a.length];
		this.Ax = new Point2D.Double[a.length];
		this.Ay = new Point2D.Double[a.length];
		this.closestPair = new Point2D.Double[2];
		this.closest = new ArrayList<Object[]>();

		for(int i = 0; i < a.length; i++) {
			A[i] = new Point2D.Double(a[i][0], a[i][1]);
			this.Ax[i] = new Point2D.Double(a[i][0], a[i][1]);
			this.Ay[i] = new Point2D.Double(a[i][0], a[i][1]);
		}

		//sort Ax in ascending order relative to the x-coordinates
		Arrays.sort(this.Ax, new Comparator<Point2D.Double>() {
			//@Override
			public int compare(Point2D.Double p1, Point2D.Double p2) {			
				if (p1.getX() < p2.getX()) 			
					return -1;

				if (p1.getX() > p2.getX()) 
					return 1;

				return 0;
			}
		});

		//sort Ay in ascending order relative to the y-coordinates
		Arrays.sort(this.Ay, new Comparator<Point2D.Double>() {
			//@Override
			public int compare(Point2D.Double p1, Point2D.Double p2) {			
				if (p1.getY() < p2.getY()) 			
					return -1;

				if (p1.getY() > p2.getY()) 
					return 1;

				return 0;
			}
		});

		//		System.out.println("A:");
		//		for(int i=0; i < A.length; i++) {
		//			System.out.println("(" + A[i].getX() + ", " + A[i].getY() + ")");
		//		}
		//		
		//		System.out.println("\nAx:");
		//		for(int i=0; i < this.Ax.length; i++) {
		//			System.out.println("(" + this.Ax[i].getX() + ", " + this.Ax[i].getY() + ")");
		//		}
		//		
		//		System.out.println("\nAy:");
		//		
		//		for(int i = 0; i < this.Ay.length; i++) {
		//			System.out.println("(" + this.Ay[i].getX() + ", " + this.Ay[i].getY() + ")");
		//		}
		//		
		//		System.out.println();
	}

	public Object[] closestPairs(Point2D.Double[] Ax, Point2D.Double[] Ay, int n) {

		if(n <= 3) {		
			return bruteForceCP(Ax, n);
		}

		//get middle point
		Point2D.Double middlePoint = Ax[n/2];

		//divide Ax in half into two arrays of points
		Point2D.Double[] xLeft = new Point2D.Double[n/2];
		Point2D.Double[] xRight = new Point2D.Double[n - (n/2)];

		//might not be needed
		int xLeftLength = 0, xRightLength = 0; 

		//initialize xLeft and xRight (each with half the points of Ax)
		for(int i = 0; i < n; i++ ) {
			if(i < n/2) {
				xLeft[xLeftLength++] = Ax[i];
			} else {
				xRight[xRightLength++] = Ax[i];
			}
		}

		//divide Ax in half into two arrays of points
		Point2D.Double[] yLeft = new Point2D.Double[n/2];
		Point2D.Double[] yRight = new Point2D.Double[n - (n/2)];

		//might not be needed
		int yLeftLength = 0, yRightLength = 0;

		//initialize yLeft and yRight (each with half the points of Ay)
		for(int i = 0; i < n; i++ ) {
			if(Ay[i].getX() < middlePoint.getX()) {
				yLeft[yLeftLength++] = Ay[i];
			} else {
				yRight[yRightLength++] = Ay[i];
			}
		}

		//recursively obtain the closest pair and minimum distance on the left side of middlePoint.x
		Object[] temp1 = closestPairs(xLeft, yLeft, n/2);

		//recursively obtain the closest pair and minimum distance of the right side of middlePoint.x
		Object[] temp2 = closestPairs(xRight, yRight, n - (n/2));

		//initialize closestPair by assigning the closest pair found in the left side of middlePoint.x
		this.closestPair = (Point2D.Double[]) temp1[1];

		//update the distance of the closest pair found in the left side of middlePoint.x
		this.minDist = (double) temp1[0];

		//update closestPair and distance in the case where the right side of middlePoint.x has the closest pair and minimum distance
		if((double) temp2[0] < this.minDist) {
			this.closestPair = (Point2D.Double[]) temp2[1];
			this.minDist = (double) temp2[0];
		}

		//create an array to store the points in the strip. The strip consists of all the points to the left and right of the middlepoint.x 
		//and that are not farther away from the middlepoint.x than the distance of the closest pairs
		Point2D.Double[] strip = new Point2D.Double[n];
		int stripLength = 0;

		//initialize the array of points in the strip
		for(int i = 0; i < n; i++) {
			if(Math.abs(Ay[i].getX() - middlePoint.getX()) < this.minDist) {
				strip[stripLength++] = Ay[i];
			}
		}

		//minD will store the minimum distance between the points in the strip. The largest value minD can have is this.minDist if no points in the strip are closer than the closest pair
		//we found in the left or right of the middlepoint.x
		double minD = this.minDist;

		//traverse the strip and compare the distance between each point. At most, the inner loop will run at most 6 times. Thus, this is O(n).
		for(int i = 0; i < stripLength; i++) {
			for(int j = i + 1; j < stripLength && (((Point2D.Double) strip[j]).getY() - ((Point2D.Double) strip[i]).getY()) < this.minDist; j++) {
				double d = getDistance(strip[j], strip[i]);
				if(d < minD) {
					minD = d;
					this.closestPair = new Point2D.Double[] {strip[j], strip[i]};
					closest.add(new Object[] {minD, this.closestPair});
				}
			}
		}

		//update the distance between the closest pairs
		this.minDist = minD;

		return new Object[] {this.minDist, this.closestPair};
	}

	public Object[] bruteForceCP(Point2D.Double[] A, int n) {
		double minD = Double.MAX_VALUE;
		Point2D.Double[] cPair = new Point2D.Double[2];

		for(int i = 0; i < n - 1; i++) {
			for(int j = i + 1; j < n; j++) {
				Point2D.Double p1 = A[i];
				Point2D.Double p2 = A[j];

				double distance = getDistance(p1, p2);
				if(distance < minD) {
					minD = distance;
					cPair = new Point2D.Double[] {p1, p2};
				}
				this.closest.add(new Object[] {distance, new Point2D.Double[] {p1, p2}});
			}
		}

		return new Object[] {minD, cPair};
	}

	public double getDistance(Point2D.Double p1, Point2D.Double p2) {
		//distance between p1 and p2
		return Math.sqrt(Math.pow(p1.getX() - p2.getX(), 2) + Math.pow(p1.getY() - p2.getY(), 2));
	}

	public List<Object[]> getClosestMPairs() {
		return this.closest;
	}

	public Object[] getClosestPair() {
		return new Object[] {this.minDist, this.closestPair};
	}

	public double getMinDist() {
		return this.minDist;
	}

	public Point2D.Double[] getAx() {
		return this.Ax;
	}

	public Point2D.Double[] getAy() {
		return this.Ay;
	}

	public String toString(List<Object[]> closestPairsList, int m) { 
		String result = "Closest m Pairs are: \n";

		for(int i = 0; i < m && i < closestPairsList.size(); i++) {
			Object[] obj = closestPairsList.get(i);
			Point2D.Double[] pairs = ((Point2D.Double[]) obj[1]);
			double distance = (double) obj[0];
			result += "(" + pairs[0].getX() + ", " + pairs[0].getY() + ") and (" + pairs[1].getX() + ", " + pairs[1].getY() + ") with distance: " + distance + "\n";
		}

		return result;
	}

	public String toString(Object[] closestPair, int m) {
		Point2D.Double[] pairs = ((Point2D.Double[]) closestPair[1]);
		double distance = (double) closestPair[0];
				
		return "\nClosest Pair is (" + pairs[0].getX() + ", " + pairs[0].getY() + ") and (" + pairs[1].getX() + ", " + pairs[1].getY() + ") with distance: " + distance + "\n";
	}

	public void printOutput(Object[] closestPair, List<Object[]> closestPairsList, int m) {
		if(closestPair != null && this.Ax.length <= ClosestPair.MAX_LENGTH) {
			if(m == 1) {
				//print to console 
				System.out.println(toString(closestPair, m));
			}
		} else if(closestPairsList != null && closestPairsList.size() <= ClosestPair.MAX_LENGTH) {
			if(m > 0 && m <= this.Ax.length - 1) {
				//print to console 
				System.out.println(toString(closestPairsList, m));
			}
		} else {
			FileWriter fileWriter = null;
			PrintWriter printWriter = null;
			try {
				fileWriter = new FileWriter(ClosestPair.filepath);
				printWriter = new PrintWriter(fileWriter);
				
				if(closestPair != null && m == 1) {
					//write to file
					printWriter.print(toString(closestPair, m));
				} else if(closestPairsList != null && (m > 0 && m <= this.Ax.length - 1)) {
					//write to file
					printWriter.print(toString(closestPairsList, m));
				}
				
				printWriter.flush();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				if(printWriter != null) {
					printWriter.close();
				}	
			}
		}
	}
	
}
