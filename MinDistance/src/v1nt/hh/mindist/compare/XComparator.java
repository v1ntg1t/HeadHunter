package v1nt.hh.mindist.compare;

import java.util.Comparator;

import v1nt.hh.mindist.beans.Point;

public class XComparator implements Comparator<Point> {
	public int compare(Point point1, Point point2) {
		long xDifference = point1.getX() - point2.getX();
		if(xDifference != 0) {
			return (int)Math.signum(xDifference);
		} else {
			long yDifference = point1.getY() - point2.getY();
			return (int)Math.signum(yDifference);
		}
	}
}