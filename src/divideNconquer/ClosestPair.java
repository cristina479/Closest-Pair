/**
 * 
 */
package divideNconquer;

import java.awt.geom.Point2D;
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

	private Point2D.Double[] dataX;
	private Point2D.Double[] dataY;
	private List<Object[]> closest; 	//0: P1, 1: P2, 2: distance 
	private Point2D.Double[] closestPair;
	private double minDist = Double.MAX_VALUE;

	public ClosestPair(double[][] a, PrintWriter pw) {
		Point2D.Double[] A = new Point2D.Double[a.length];
		this.dataX = new Point2D.Double[a.length];
		this.dataY = new Point2D.Double[a.length];
		this.closestPair = new Point2D.Double[2];
		this.closest = new ArrayList<Object[]>();

		for(int i = 0; i < a.length; i++) {
			A[i] = new Point2D.Double(a[i][0], a[i][1]);
			this.dataX[i] = new Point2D.Double(a[i][0], a[i][1]);
			this.dataY[i] = new Point2D.Double(a[i][0], a[i][1]);
		}

		//sort dataX in ascending order relative to the x-coordinates
		Arrays.sort(this.dataX, new Comparator<Point2D.Double>() {
			public int compare(Point2D.Double p1, Point2D.Double p2) {			
				if (p1.getX() < p2.getX()) 			
					return -1;

				if (p1.getX() > p2.getX()) 
					return 1;

				return 0;
			}
		});

		//sort dataY in ascending order relative to the y-coordinates
		Arrays.sort(this.dataY, new Comparator<Point2D.Double>() {
			public int compare(Point2D.Double p1, Point2D.Double p2) {			
				if (p1.getY() < p2.getY()) 			
					return -1;

				if (p1.getY() > p2.getY()) 
					return 1;

				return 0;
			}
		});
		
		if(pw != null) {
			pw.println("=================================== INPUT ===================================\n");
			pw.println("Data Size: " + this.dataX.length + "\n");
			
			pw.println("dataX: ");
			for(int i=0; i < this.dataX.length; i++) {
				pw.print("(" + this.dataX[i].getX() + ", " + this.dataX[i].getY() + ")   " );
				
				if((i + 1) % 10 == 0) {
					pw.println();
				}
			}
			
			pw.println("\n\ndataY: ");
			for(int i=0; i < this.dataY.length; i++) {
				pw.print("(" + this.dataY[i].getX() + ", " + this.dataY[i].getY() + ")   " );
				
				if((i + 1) % 10 == 0) {
					pw.println();
				}
			}
			
			pw.println("\n\n=================================== OUTPUT ===================================\n");	
			
		} else {
			System.out.println("\n=================================== INPUT ===================================\n");
			System.out.println("Data Size: " + this.dataX.length + "\n");
			
			System.out.println("dataX: ");
			for(int i=0; i < this.dataX.length; i++) {
				System.out.print("(" + this.dataX[i].getX() + ", " + this.dataX[i].getY() + ")   " );
				
				if((i + 1) % 10 == 0) {
					System.out.println();
				}
			}
			
			System.out.println("\n\ndataY: ");
			for(int i=0; i < this.dataY.length; i++) {
				System.out.print("(" + this.dataY[i].getX() + ", " + this.dataY[i].getY() + ")   " );
				
				if((i + 1) % 10 == 0) {
					System.out.println();
				}
			}
			
			System.out.println("\n\n=================================== OUTPUT ===================================\n");
		}
	}

	public Object[] closestPairs(Point2D.Double[] Ax, Point2D.Double[] Ay, int n, PrintWriter pw, String partition) {

		if(n <= 3) {		
			return bruteForceCP(Ax, n, pw, partition);
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
		
		if(pw == null) {
			System.out.println("================== Data Size: " + n/2 + "\n");
			System.out.println("dataX: ");
			System.out.print("L: ");
			printData(xLeft, pw);
			System.out.print("|  R: ");
			printData(xRight, pw);
			System.out.println("\n\ndataY: ");
			System.out.print("L: ");
			printData(yLeft, pw);
			System.out.print("|  R: ");
			printData(yRight, pw);
			System.out.println("\n");
		} else {
			pw.println("================== Data Size: " + n/2 + "\n");
			pw.println("dataX: ");
			pw.print("L: ");
			printData(xLeft, pw);
			pw.print("|  R: ");
			printData(xRight, pw);
			pw.println("\n\ndataY: ");
			pw.print("L: ");
			printData(yLeft, pw);
			pw.print("|  R: ");
			printData(yRight, pw);
			pw.println("\n");
		}

		//recursively obtain the closest pair and minimum distance on the left side of middlePoint.x
		Object[] temp1 = closestPairs(xLeft, yLeft, n/2, pw, " on the left side of dataX");

		//recursively obtain the closest pair and minimum distance of the right side of middlePoint.x
		Object[] temp2 = closestPairs(xRight, yRight, n - (n/2), pw, " on the right side of dataX");

		
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
		
		if(pw == null) {
			System.out.println("\n================== In Strip\n");
			System.out.println("Points in x-coordinate between " + (middlePoint.getX() - this.minDist) + " to " + middlePoint.getX() + " and " + middlePoint.getX() + " to " + (middlePoint.getX() + this.minDist) + "\n");			
		} else {
			pw.println("\n================== In Strip\n");
			pw.println("Points in x-coordinate between " + (middlePoint.getX() - this.minDist) + " to " + middlePoint.getX() + " and " + middlePoint.getX() + " to " + (middlePoint.getX() + this.minDist) + "\n");			
		}

		//initialize the array of points in the strip
		for(int i = 0; i < n; i++) {
			if(Math.abs(Ay[i].getX() - middlePoint.getX()) < this.minDist) {
				strip[stripLength++] = Ay[i];
				
				if(pw == null) {	
					System.out.print("(" + strip[stripLength-1].getX() + ", " + strip[stripLength-1].getY() + ")   " );

					if((i + 1) % 10 == 0) {
						System.out.println();
					}
				} else {
					pw.print("(" + strip[stripLength-1].getX() + ", " + strip[stripLength-1].getY() + ")   " );

					if((i + 1) % 10 == 0) {
						pw.println();
					}		
				}
			}
		}
		
		if(pw == null) {
			System.out.println("\n\nPoints to be compared in the strip relative to y-axis: \n");
		} else {
			pw.println("\n\nPoints to be compared in the strip relative to y-axis: \n");
		}

		//minD will store the minimum distance between the points in the strip. The largest value minD can have is this.minDist if no points in the strip are closer than the closest pair
		//we found in the left or right of the middlepoint.x
		double minD = this.minDist;
		boolean foundAPairToCompare = false;

		//traverse the strip and compare the distance between each point. At most, the inner loop will run at most 6 times. Thus, this is O(n).
		for(int i = 0; i < stripLength; i++) {
			for(int j = i + 1; j < stripLength && (((Point2D.Double) strip[j]).getY() - ((Point2D.Double) strip[i]).getY()) < this.minDist; j++) {
				foundAPairToCompare = true;
				double d = getDistance(strip[j], strip[i]);
				
				if(d < minD) {
					minD = d;
					this.closestPair = new Point2D.Double[] {strip[j], strip[i]};
					closest.add(new Object[] {minD, this.closestPair});
				}
				
				if(pw == null) {
					System.out.println("(" + ((Point2D.Double) strip[i]).getX() + ", " + ((Point2D.Double) strip[i]).getY() + ") and (" + ((Point2D.Double) strip[j]).getX() + ", " + ((Point2D.Double) strip[j]).getY() + ") with distance: " + d);
				} else {
					pw.println("(" + ((Point2D.Double) strip[i]).getX() + ", " + ((Point2D.Double) strip[i]).getY() + ") and (" + ((Point2D.Double) strip[j]).getX() + ", " + ((Point2D.Double) strip[j]).getY() + ") with distance: " + d);
				}
			}
		}
		
		if(!foundAPairToCompare) {
			if(pw == null) {
				System.out.println("None.");
			} else {
				pw.println("None.");
			}
		}

		//update the distance between the closest pairs
		this.minDist = minD;
		
		if(pw == null) {
			System.out.println("----\n");
			System.out.println(toString(new Object[] { this.minDist, this.closestPair }, "") + "\n");
		} else {
			pw.println("----\n");
			pw.println(toString(new Object[] { this.minDist, this.closestPair }, "") + "\n");			
		}

		return new Object[] {this.minDist, this.closestPair};
	}

	private void printData(Point2D.Double[] points, PrintWriter pw) {
		if(pw == null) {
			for(int i=0; i < points.length; i++) {
				System.out.print("(" + points[i].getX() + ", " + points[i].getY() + ")   " );

				if((i + 1) % 10 == 0) {
					System.out.println();
				}
			}
		} else {
			for(int i=0; i < points.length; i++) {
				pw.print("(" + points[i].getX() + ", " + points[i].getY() + ")   " );

				if((i + 1) % 10 == 0) {
					pw.println();
				}
			}
		}
	}

	public Object[] bruteForceCP(Point2D.Double[] A, int n, PrintWriter pw, String partition) {
		double minD = Double.MAX_VALUE;
		Point2D.Double[] cPair = new Point2D.Double[2];

		for(int i = 0; i < n - 1; i++) {
			for(int j = i + 1; j < n; j++) {
				Point2D.Double p1 = A[i];
				Point2D.Double p2 = A[j];

				double distance = getDistance(p1, p2);
				if(distance < minD) {
					minD = distance;
					cPair = new Point2D.Double[] { p1, p2 };
				}
				
				this.closest.add(new Object[] { distance, new Point2D.Double[] { p1, p2 }});
			}
			
			if(pw == null) {
				System.out.println(toString(new Object[] { minD, cPair }, partition));
			} else {
				
				pw.println(toString(new Object[] { minD, cPair }, partition));
			}
		}

		return new Object[] { minD, cPair };
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
		return this.dataX;
	}

	public Point2D.Double[] getAy() {
		return this.dataY;
	}

	public String toString(List<Object[]> closestPairsList, int m) { 
		String result = "Closest m = " + m + " Pairs are: \n";

		for(int i = 0; i < m && i < closestPairsList.size(); i++) {
			Object[] obj = closestPairsList.get(i);
			Point2D.Double[] pairs = ((Point2D.Double[]) obj[1]);
			double distance = (double) obj[0];
			result += "(" + pairs[0].getX() + ", " + pairs[0].getY() + ") and (" + pairs[1].getX() + ", " + pairs[1].getY() + ") with distance: " + distance + "\n";
		}

		return result;
	}

	public String toString(Object[] closestPair, String partition) {
		Point2D.Double[] pairs = ((Point2D.Double[]) closestPair[1]);
		double distance = (double) closestPair[0];
				
		return "Closest Pair" + partition + " is (" + pairs[0].getX() + ", " + pairs[0].getY() + ") and (" + pairs[1].getX() + ", " + pairs[1].getY() + ") with distance: " + distance;
	}

	public void printOutput(Object[] closestPair, List<Object[]> closestPairsList, int m, PrintWriter pw) {
		if(pw == null) {
			System.out.println("==============================================================================");
		} else {
			pw.println("==============================================================================");
		}
		
		if(pw == null) {
			if(closestPair != null && m == 1) {
				//write to console
				System.out.println("RESULT: " + toString(closestPair, "") + "\n");
			} else if(closestPairsList != null && (m > 0 && m <= this.dataX.length - 1)) {
				//write to console
				System.out.println("RESULT: " + toString(closestPairsList, m));
			}
		} else {
			if(closestPair != null && m == 1) {
				//write to file
				pw.print("RESULT: " + toString(closestPair, "") + "\n");
			} else if(closestPairsList != null && (m > 0 && m <= this.dataX.length - 1)) {
				//write to file
				pw.print("RESULT: " + toString(closestPairsList, m));
			}
		}
	}
	
}
