import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class MinDistance {
	private static final String INPUT_FILE_NAME = "in.txt";
	private static final String COORDINATES_DELIMITER = "\\s+";
	private static final String POINT_PATTERN = 
			"(-?\\d+)" + COORDINATES_DELIMITER + "(-?\\d+)";
	
	public static void main(String[] args) {
		long startTime = System.currentTimeMillis();
		List<Point> points = initializePoints();
		long endTime = System.currentTimeMillis();

		if(points.size() > 1) {
			startTime = System.currentTimeMillis();
			System.out.println(Math.sqrt(bruteFindMinSquareDistance(points)));
			endTime = System.currentTimeMillis();
			System.out.println("time: " + (endTime - startTime) + " mseconds");
			
			startTime = System.currentTimeMillis();
			System.out.println(Math.sqrt(fastFindMinSquareDistance(points)));
			endTime = System.currentTimeMillis();
			System.out.println("time: " + (endTime - startTime) + " mseconds");
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
	
	private static long bruteFindMinSquareDistance(List<Point> points) {
		long minSquareDistance = Long.MAX_VALUE;
		for(int i = 0; i < points.size() - 1; i++) {
			Point point = points.get(i);
			for(int j = i + 1; j < points.size(); j++) {
				long squareDistance = point.getSquareDistance(points.get(j));
				if(squareDistance < minSquareDistance) {
					minSquareDistance = squareDistance;
				}
			}
		}
		return minSquareDistance;
	}

	private static double fastFindMinSquareDistance(List<Point> points) {
		Collections.sort(points);
		return recur(points);	
	}
	
	private static long recur(List<Point> points) {
		long minSquareDistance = Long.MAX_VALUE;
		int pointsNumber = points.size();
		if(pointsNumber < 4) {
			return bruteFindMinSquareDistance(points);
		}
		int mediumIndex = pointsNumber / 2;
		long leftMin = recur(points.subList(0, mediumIndex));
		long rightMin = recur(points.subList(mediumIndex, pointsNumber));
		return leftMin < rightMin ? leftMin : rightMin;
	}
}
