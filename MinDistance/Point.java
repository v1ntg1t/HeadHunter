class Point {
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
	
	int getSquareDistance(Point point) {
		return (point.getX() - x) * (point.getX() - x) 
				+ (point.getY() - y) * (point.getY() - y);
	}
}