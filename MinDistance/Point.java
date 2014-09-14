class Point implements Comparable<Point> {
	private final int x;
	private final int y;
	
	Point(int[] coordinates) {
		x = coordinates[Const.INDEX_X];
		y = coordinates[Const.INDEX_Y];
	}
	
	int getX() {
		return x;
	}
	
	int getY() {
		return y;
	}
	
	long getSquareDistance(Point point) {
		return (point.getX() - x) * (point.getX() - x) 
				+ (point.getY() - y) * (point.getY() - y);
	}
	
	public int compareTo(Point point) {
		int xDifference = x - point.getX();
		return xDifference != 0 ? xDifference : y - point.getY();
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(x);
		sb.append("\t");
		sb.append(y);
		return sb.toString();
	}
}