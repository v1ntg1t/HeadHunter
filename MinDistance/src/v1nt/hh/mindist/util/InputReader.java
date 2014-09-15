package v1nt.hh.mindist.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import v1nt.hh.mindist.beans.Point;

public class InputReader {
	private static final long MAX_COORDINATE_ABSOLUTE_VALUE = (long)1.0e9;
	private static final String INPUT_FILE_NAME = "res/in.txt";
	private static final String COORDINATES_DELIMITER = "\\s+";
	private static final String POINT_PATTERN = 
			"(-?\\d+)" + COORDINATES_DELIMITER + "(-?\\d+)";

	public static List<Point> readPoints() {
		List<Point> points = new ArrayList<Point>();
		try {
			Scanner scanner = new Scanner(new File(INPUT_FILE_NAME));
			while(scanner.hasNext()) {
				String point = scanner.nextLine().trim();
				long[] coordinates = parseCoordinates(point);
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
	
	private static long[] parseCoordinates(String point) {
		if(point.matches(POINT_PATTERN)) {
			String[] coordinates = point.split(COORDINATES_DELIMITER);
			try {
				long x = Long.parseLong(coordinates[Point.X]);
				if(Math.abs(x) > MAX_COORDINATE_ABSOLUTE_VALUE) {
					return null;
				}
				long y = Long.parseLong(coordinates[Point.Y]);
				if(Math.abs(y) > MAX_COORDINATE_ABSOLUTE_VALUE) {
					return null;
				}
				long[] parsedCoordinates = {x, y};
				return parsedCoordinates;
			} catch(NumberFormatException e) {
				return null;
			}
		}
		return null;
	}
	
}