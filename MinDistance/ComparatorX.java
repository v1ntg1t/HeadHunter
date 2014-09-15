import java.util.Comparator;

class ComparatorX implements Comparator<Point> {
	public int compare(Point point1, Point point2) {
		int xDifference = point1.getX() - point2.getX();
		return xDifference != 0 ? xDifference : point1.getY() - point2.getY();
	
	}
}