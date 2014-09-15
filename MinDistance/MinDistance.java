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
/*		
			startTime = System.currentTimeMillis();
			long minSquareDistance = bruteFindMinSquareDistance(points);
			System.out.println(minSquareDistance);
			System.out.println(Math.sqrt(minSquareDistance));
			endTime = System.currentTimeMillis();
			System.out.println("time: " + (endTime - startTime) + " mseconds");
*/			
			startTime = System.currentTimeMillis();
			double fastMinSquareDistance = fastFindMinSquareDistance(points);
			System.out.println(Math.sqrt(fastMinSquareDistance));
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
		Collections.sort(points, new ComparatorX());
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
		long min = leftMin < rightMin ? leftMin : rightMin;
		
		int mediumX = points.get(mediumIndex).getX();
		int leftBorderPointIndex = mediumIndex;
		int rightBorderPointIndex = mediumIndex;
		for(int index = mediumIndex - 1; index >= 0; index--) {
			int x = points.get(index).getX();
			long squareDistance = (long)(mediumX - x) * (long)(mediumX - x);
			if(squareDistance < min) {
				leftBorderPointIndex = index;
			} else {
				break;
			}
		}
		for(int index = mediumIndex + 1; index < pointsNumber; index++) {
			int x = points.get(index).getX();
			long squareDistance = (long)(x - mediumX) * (long)(x - mediumX);
			if(squareDistance < min) {
				rightBorderPointIndex = index;
			} else {
				break;			
			}
		}
		
		long borderMin = findBorderMin(
				points.subList(leftBorderPointIndex, rightBorderPointIndex + 1),
				min
		);
		
/*		
		long borderMin = bruteFindMinSquareDistance(
				points.subList(
						leftBorderPointIndex, rightBorderPointIndex + 1
				)
		);
*/
		return min < borderMin ? min : borderMin;
	}
	
	private static long findBorderMin(List<Point> points, long min) {
		long borderMin = Long.MAX_VALUE;
		int pointsNumber = points.size();
		if(pointsNumber > 1) {
			Collections.sort(points, new ComparatorY());
			for(int i = 1; i < pointsNumber; i++) {
				for(int j = i - 1; j >= 0; j--) {
					Point upperPoint = points.get(i);
					Point bottomPoint = points.get(j);
					if(upperPoint.getY() - bottomPoint.getY() > min) {
						break;
					}
					long squareDistance = upperPoint.getSquareDistance(bottomPoint);
					if(squareDistance < borderMin) {
						borderMin = squareDistance;
					}
				}
			}
		}
		return borderMin;
	}
}
