/**
 * 
 */
package divideNconquer;

import java.awt.geom.Point2D;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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
	private static final boolean PRINT_TRACES = false;

	public ClosestPair(double[][] a, PrintWriter pw) {
		Point2D.Double[] data = new Point2D.Double[a.length];
		this.dataX = new Point2D.Double[a.length];
		this.dataY = new Point2D.Double[a.length];
		this.closestPair = new Point2D.Double[2];
		this.closest = new ArrayList<Object[]>();

		for(int index = 0; index < a.length; index++) {
			data[index] = new Point2D.Double(a[index][0], a[index][1]);
			this.dataX[index] = new Point2D.Double(a[index][0], a[index][1]);
			this.dataY[index] = new Point2D.Double(a[index][0], a[index][1]);
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
	}

	public Object[] closestPairs(Point2D.Double[] dataX, Point2D.Double[] dataY, int size, PrintWriter pw, String message) {

		if(size <= 3) {		
			return bruteForceCP(dataX, size, pw, message);
		}

		//get middle point
		Point2D.Double middlePoint = dataX[size/2];

		//divide Ax in half into two arrays of points
		Point2D.Double[] xLeft = new Point2D.Double[size/2];
		Point2D.Double[] xRight = new Point2D.Double[size - (size/2)];

		//might not be needed
		int xLeftLength = 0, xRightLength = 0; 

		//initialize xLeft and xRight (each with half the points of Ax)
		for(int index = 0; index < size; index++ ) {
			if(index < size/2) {
				xLeft[xLeftLength++] = dataX[index];
			} else {
				xRight[xRightLength++] = dataX[index];
			}
		}

		//divide Ax in half into two arrays of points
		Point2D.Double[] yLeft = new Point2D.Double[size/2];
		Point2D.Double[] yRight = new Point2D.Double[size - (size/2)];

		//might not be needed
		int yLeftLength = 0, yRightLength = 0;

		//initialize yLeft and yRight (each with half the points of Ay)
		for(int index = 0; index < size; index++ ) {
			if(dataY[index].getX() < middlePoint.getX()) {
				yLeft[yLeftLength++] = dataY[index];
			} else {
				yRight[yRightLength++] = dataY[index];
			}
		}
		
		if(PRINT_TRACES) {
			if(pw == null) {
				System.out.println("================== Data Size: " + size/2 + "\n");
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
				pw.println("================== Data Size: " + size/2 + "\n");
				pw.println("dataX: ");
				pw.print("L: ");
				printData(xLeft, pw);
				pw.print("|  R: ");
				printData(xRight, pw);
				pw.println();
				pw.println("dataY: ");
				pw.print("L: ");
				printData(yLeft, pw);
				pw.print("|  R: ");
				printData(yRight, pw);
				pw.println();
			}
		}

		//recursively obtain the closest pair and minimum distance on the left side of middlePoint.x
		Object[] temp1 = closestPairs(xLeft, yLeft, size/2, pw, " on the left side of dataX");

		//recursively obtain the closest pair and minimum distance of the right side of middlePoint.x
		Object[] temp2 = closestPairs(xRight, yRight, size - (size/2), pw, " on the right side of dataX");
		
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
		Point2D.Double[] strip = new Point2D.Double[size];
		int stripLength = 0;
		
		if(PRINT_TRACES) {
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

		//initialize the array of points in the strip
		for(int index = 0; index < size; index++) {
			if(Math.abs(dataY[index].getX() - middlePoint.getX()) < this.minDist) {
				strip[stripLength++] = dataY[index];
				
				if(PRINT_TRACES) {
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
		}
		
		if(PRINT_TRACES) {
			if(pw == null) {
				System.out.println("\n\nPoints to be compared in the strip relative to y-axis: \n");
			} else {
				pw.println();
				pw.println();
				pw.println("Points to be compared in the strip relative to y-axis: ");
			}
		}

		//minD will store the minimum distance between the points in the strip. The largest value minD can have is this.minDist if no points in the strip are closer than the closest pair
		//we found in the left or right of the middlepoint.x
		double minD = this.minDist;
		boolean foundAPairToCompare = false;

		//traverse the strip and compare the distance between each point. At most, the inner loop will run at most 6 times. Thus, this is O(n).
		for(int index1 = 0; index1 < stripLength; index1++) {
			for(int index2 = index1 + 1; index2 < stripLength && (((Point2D.Double) strip[index2]).getY() - ((Point2D.Double) strip[index1]).getY()) < this.minDist; index2++) {
				foundAPairToCompare = true;
				double distance = getDistance(strip[index2], strip[index1]);
				
				if(distance < minD) {
					minD = distance;
					this.closestPair = new Point2D.Double[] {strip[index2], strip[index1]};
					closest.add(new Object[] { minD, this.closestPair });
				}
				
				if(PRINT_TRACES) {
					if(pw == null) {
						System.out.println("(" + ((Point2D.Double) strip[index1]).getX() + ", " + ((Point2D.Double) strip[index1]).getY() + ") and (" + ((Point2D.Double) strip[index2]).getX() + ", " + ((Point2D.Double) strip[index2]).getY() + ") with distance: " + distance);
					} else {
						pw.println("(" + ((Point2D.Double) strip[index1]).getX() + ", " + ((Point2D.Double) strip[index1]).getY() + ") and (" + ((Point2D.Double) strip[index2]).getX() + ", " + ((Point2D.Double) strip[index2]).getY() + ") with distance: " + distance);
					}
				}
			}
		}
		
		if(PRINT_TRACES) {
			if(!foundAPairToCompare) {
				if(pw == null) {
					System.out.println("None.");
				} else {
					pw.println("None.");
				}
			}
		}
		

		//update the distance between the closest pairs
		this.minDist = minD;
		
		if(PRINT_TRACES) {
			if(pw == null) {
				System.out.println("----\n");
				System.out.println(toString(new Object[] { this.minDist, this.closestPair }, "") + "\n");
			} else {
				pw.println("----");
				pw.println();
				pw.println(toString(new Object[] { this.minDist, this.closestPair }, ""));	
				pw.println();
			}
		}

		return new Object[] { this.minDist, this.closestPair };
	}

	private void printData(Point2D.Double[] points, PrintWriter pw) {
		if(PRINT_TRACES) {
			if(pw == null) {
				for(int index=0; index < points.length; index++) {
					System.out.print("(" + points[index].getX() + ", " + points[index].getY() + ")   " );
	
					if((index + 1) % 10 == 0) {
						System.out.println();
					}
				}
			} else {
				for(int index=0; index < points.length; index++) {
					pw.print("(" + points[index].getX() + ", " + points[index].getY() + ")   " );
	
					if((index + 1) % 10 == 0) {
						pw.println();
					}
				}
			}
		}
	}

	public Object[] bruteForceCP(Point2D.Double[] data, int size, PrintWriter pw, String message) {
		double minD = Double.MAX_VALUE;
		Point2D.Double[] cPair = new Point2D.Double[2];
		
		if(PRINT_TRACES) {
			if(pw != null) {
				pw.println();
			}
		}

		for(int index1 = 0; index1 < size - 1; index1++) {
			for(int index2 = index1 + 1; index2 < size; index2++) {
				Point2D.Double p1 = data[index1];
				Point2D.Double p2 = data[index2];

				double distance = getDistance(p1, p2);
				if(distance < minD) {
					minD = distance;
					cPair = new Point2D.Double[] { p1, p2 };
				}
				
				this.closest.add(new Object[] { distance, new Point2D.Double[] { p1, p2 }});
			}
		}
		
		if(PRINT_TRACES) {
			if(pw == null) {
				System.out.println(toString(new Object[] { minD, cPair }, message));
			} else {
				pw.println(toString(new Object[] { minD, cPair }, message));
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

	public String toString(List<Object[]> closestPairsList, int sizeM, PrintWriter pw) { 
		String result = "";

		for(int index = 0; index < sizeM && index < closestPairsList.size(); index++) {
			Object[] obj = closestPairsList.get(index);
			Point2D.Double[] pairs = ((Point2D.Double[]) obj[1]);
			double distance = (double) obj[0];
			
			if(pw == null) {
				result += "(" + pairs[0].getX() + ", " + pairs[0].getY() + ") and (" + pairs[1].getX() + ", " + pairs[1].getY() + ") with distance: " + distance + "\n";
			} else {
				pw.println("(" + pairs[0].getX() + ", " + pairs[0].getY() + ") and (" + pairs[1].getX() + ", " + pairs[1].getY() + ") with distance: " + distance);
			}
		}

		return result;
	}

	public String toString(Object[] closestPair, String partition) {
		Point2D.Double[] pairs = ((Point2D.Double[]) closestPair[1]);
		double distance = (double) closestPair[0];
				
		return "Closest Pair" + partition + " is (" + pairs[0].getX() + ", " + pairs[0].getY() + ") and (" + pairs[1].getX() + ", " + pairs[1].getY() + ") with distance: " + distance;
	}

	public void printOutput(Object[] closestPair, List<Object[]> closestPairsList, int sizeM, PrintWriter pw) {
		
		if(PRINT_TRACES) {
			if(pw == null) {
				System.out.println("==============================================================================");
			} else {
				pw.println("==============================================================================");
			}
		}
		
		//sort closestPairs list
		if(closestPairsList != null) {
			Comparator<Object[]> compareByDist = (Object[] obj1, Object[] obj2) -> ((Double) obj1[0]).compareTo((Double) obj2[0]);
			Collections.sort(closestPairsList, compareByDist);
		}
		
		if(pw == null) {
			if(closestPair != null && sizeM == 1) {
				//write to console
				System.out.println("RESULT: " + toString(closestPair, "") + "\n");
			} else if(closestPairsList != null && (sizeM > 0 && sizeM <= this.dataX.length - 1)) {
				//write to console
				System.out.println("RESULT: " + "Closest m = " + sizeM + " Pairs are: \n" + toString(closestPairsList, sizeM, null));
			}
		} else {
			if(closestPair != null && sizeM == 1) {
				//write to file
				pw.println("RESULT: " + toString(closestPair, ""));
				pw.println();
			} else if(closestPairsList != null && (sizeM > 0 && sizeM <= this.dataX.length - 1)) {
				//write to file
				pw.println("RESULT: " + "Closest m = " + sizeM + " Pairs are: "); 
				pw.println(toString(closestPairsList, sizeM, pw));
			}
		}
	}
	
	Comparator<Object[]> compareById = new Comparator<Object[]>() {
		@Override
		public int compare(Object[] obj1, Object[] obj2) {
			Double dist1 = (Double) obj1[0];
			Double dist2 = (Double) obj2[0];
			
			return dist1.compareTo(dist2);
		}
	};
	
}
