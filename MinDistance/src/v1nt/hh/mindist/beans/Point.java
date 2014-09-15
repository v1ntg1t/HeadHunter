package v1nt.hh.mindist.beans;

public class Point {
	public static final int X = 0;
	public static final int Y = 1;
	
	private final long[] coordinates;

	public Point(long[] coordinates) {
		this.coordinates = coordinates;
	}
	
	public long getX() {
		return coordinates[X];
	}
	
	public long getY() {
		return coordinates[Y];
	}
	
	public long getSquareDistance(Point point) {
		long xDifference = point.getX() - coordinates[X];
		long yDifference = point.getY() - coordinates[Y];
		return xDifference * xDifference + yDifference * yDifference;
	}
}