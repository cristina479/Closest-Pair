/**
 * 
 */
package bruteforce;

import java.awt.geom.Point2D;
import java.io.PrintWriter;

/**
 * @author crist
 *
 */
public class ClosestPair {

	private Point2D.Double[] data;
	private Point2D.Double[] closestPair;
	private double minDist = Double.MAX_VALUE;
	private static final boolean PRINT_TRACES = false;

	public ClosestPair(double[][] data, PrintWriter pw) {
		this.data = new Point2D.Double[data.length];
		this.closestPair = new Point2D.Double[2];

		if (pw != null) {
			pw.println("=================================== INPUT ===================================\n");
			pw.println("Data Size: " + data.length + "\n");

		} else {
			System.out.println("=================================== INPUT ===================================\n");
			System.out.println("Data Size: " + data.length + "\n");
		}

		for (int index1 = 0; index1 < data.length; index1++) {
			this.data[index1] = new Point2D.Double(data[index1][0], data[index1][1]);


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

		}
	}

	public void bruteForceCP(Point2D.Double[] data, int dataLength, PrintWriter pw) {
		// print trace run: the current closest pairs and their distance

		if (pw == null) {
			System.out.println("\n=================================== OUTPUT ===================================");
		} else {
			pw.println("\n=================================== OUTPUT ===================================");
		}

		// calculate the closest point to p1 using brute force
		for (int index1 = 0; index1 < dataLength - 1; index1++) {
			Point2D.Double p1 = data[index1];

			if(PRINT_TRACES) {
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
				double distance = getDistance(p1, p2);

				// update closest pairs and their distance
				if (distance < this.minDist) {
					this.minDist = distance;
					this.closestPair = new Point2D.Double[] { p1, p2 };
				}
			}

			if(PRINT_TRACES) {
				if (pw == null) {
					System.out.println(toString(this.closestPair, this.minDist));
				} else {
					pw.println(toString(this.closestPair, this.minDist));
				}
			}
		}

		if(PRINT_TRACES) {
			if (pw == null) {
				System.out.println("\n==============================================================================\n");
			} else {
				pw.println("\n==============================================================================\n");
			}
		}
	}

	public double getDistance(Point2D.Double point1, Point2D.Double point2) {
		// distance between p1 and p2
		return Math.sqrt(Math.pow(point1.getX() - point2.getX(), 2) + Math.pow(point1.getY() - point2.getY(), 2));
	}

	public Point2D.Double[] getA() {
		return this.data;
	}

	public Point2D.Double[] getClosestPair() {
		return this.closestPair;
	}

	public double getMinDist() {
		return this.minDist;
	}

	public String pointToString(Point2D.Double point) {
		return "(" + point.getX() + ", " + point.getY() + ")";
	}

	public String toString(Point2D.Double point1, Point2D.Double point2, double distance) {
		return "(" + point1.getX() + ", " + point1.getY() + ") and (" + point2.getX() + ", " + point2.getY()
		+ ")   distance: " + distance;
	}

	public String toString(Point2D.Double[] closestPair, double distance) {
		return "Closest Pairs are (" + closestPair[0].getX() + ", " + closestPair[0].getY() + ") and ("
				+ closestPair[1].getX() + ", " + closestPair[1].getY() + ") with distance: " + distance;
	}

	public void printOutput(Point2D.Double[] resultList, double distance, PrintWriter pw) {
		if (pw == null) {
			System.out.println("RESULT: " + toString(resultList, distance));
		} else {
			pw.println("RESULT: " + toString(resultList, distance));
		}
	}
}
