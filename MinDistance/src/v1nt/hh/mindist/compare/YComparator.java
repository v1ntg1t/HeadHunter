package v1nt.hh.mindist.compare;

import java.util.Comparator;

import v1nt.hh.mindist.beans.Point;

public class YComparator implements Comparator<Point> {
	public int compare(Point point1, Point point2) {
		long yDifference = point1.getY() - point2.getY();
		return (int)Math.signum(yDifference);
	}
}