import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MinDistance {
	private static final String INPUT_FILE_NAME = "in.txt";
	private static final String COORDINATES_DELIMITER = "\\s+";
	private static final String POINT_PATTERN = 
			"(\\d+)" + COORDINATES_DELIMITER + "(\\d+)";
	
	public static void main(String[] args) {
		List<Point> points = initializePoints();
		if(points.size() > 1) {
			System.out.println(findMinDistance(points));
		} else {
			System.out.println("Input data has less than 2 points");
		}
	}
	
	private static List<Point> initializePoints() {
		List<Point> points = new ArrayList<Point>();
		try {
			Scanner scanner = new Scanner(new File(INPUT_FILE_NAME));
			while(scanner.hasNext()) {
				String point = scanner.nextLine().trim();
				int[] coordinates = parseCoordinates(point);
				if(coordinates != null) {
					points.add(new Point(coordinates));
				}
			}
			scanner.close();
		} catch(FileNotFoundException exception) {
			System.out.println("File '" + INPUT_FILE_NAME + "' is not founded");
		}
		return points;
	}
	
	private static int[] parseCoordinates(String point) {
		if(point.matches(POINT_PATTERN)) {
			String[] coordinates = point.split(COORDINATES_DELIMITER);
			int x = Integer.parseInt(coordinates[Const.INDEX_X]);
			int y = Integer.parseInt(coordinates[Const.INDEX_Y]);
			int[] parsedCoordinates = {x, y};
			return parsedCoordinates;
		}
		return null;
	}

	private static double findMinDistance(List<Point> points) {
		int minSquareDistance = Integer.MAX_VALUE;
		for(int i = 0; i < points.size() - 1; i++) {
			Point point = points.get(i);
			for(int j = i + 1; j < points.size(); j++) {
				int squareDistance = point.getSquareDistance(points.get(j));
				if(squareDistance < minSquareDistance) {
					minSquareDistance = squareDistance;
				}
			}
		}
		return Math.sqrt(minSquareDistance);
	}
}
