import java.util.Comparator;

class ComparatorY implements Comparator<Point> {
	public int compare(Point point1, Point point2) {
		return point1.getY() - point2.getY();
	}
}