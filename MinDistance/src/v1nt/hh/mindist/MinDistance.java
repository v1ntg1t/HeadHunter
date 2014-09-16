package v1nt.hh.mindist;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import v1nt.hh.mindist.beans.Point;
import v1nt.hh.mindist.compare.XComparator;
import v1nt.hh.mindist.compare.YComparator;
import v1nt.hh.mindist.util.InputReader;

public class MinDistance {
	private static final XComparator X_COMPARATOR = new XComparator();
	private static final YComparator Y_COMPARATOR = new YComparator();
			
	public static void main(String[] args) {
		List<Point> points = InputReader.readPoints();
		if(points.size() > 1) {
			long minSquareDistance = findMinSquareDistance(points);
			double minDistance = Math.sqrt(minSquareDistance);
			System.out.println(minDistance);
		} else {
			System.out.println("Input data has less than 2 points");
		}
	}
	
	private static long findMinSquareDistance(List<Point> points) {
		Collections.sort(points, X_COMPARATOR);
		return recursiveMinSquareDistanceSearch(points);	
	}

	private static long recursiveMinSquareDistanceSearch(List<Point> points) {
		if(points.size() < 4) {
			return bruteMinSquareDistanceSearch(points);
		}
		long minSquareDistance = findWithoutBorderMinSquareDistance(points);
		List<Point> borderPoints = findBorderPoints(points, minSquareDistance);
		long borderMinSquareDistance = findBorderMinSquareDistance(
				borderPoints, minSquareDistance);
		return minSquareDistance < borderMinSquareDistance 
				? minSquareDistance : borderMinSquareDistance;
	}
	
	private static long bruteMinSquareDistanceSearch(List<Point> points) {
		long minSquareDistance = Long.MAX_VALUE;
		int pointsNumber = points.size();
		for(int i = 0; i < pointsNumber - 1; i++) {
			Point point = points.get(i);
			for(int j = i + 1; j < pointsNumber; j++) {
				long squareDistance = point.getSquareDistance(points.get(j));
				if(squareDistance < minSquareDistance) {
					minSquareDistance = squareDistance;
				}
			}
		}
		return minSquareDistance;
	}

	private static long findWithoutBorderMinSquareDistance(List<Point> points) {
		int pointsNumber = points.size();
		int mediumIndex = pointsNumber / 2;
		long leftHalfMinSquareDistance = recursiveMinSquareDistanceSearch(
				points.subList(0, mediumIndex));
		long rightHalfMinSquareDistance = recursiveMinSquareDistanceSearch(
				points.subList(mediumIndex, pointsNumber));
		return leftHalfMinSquareDistance < rightHalfMinSquareDistance 
				? leftHalfMinSquareDistance : rightHalfMinSquareDistance;
	}
	
	private static List<Point> findBorderPoints(List<Point> points, 
			long minSquareDistance) {
		int leftBorderIndex = findLeftBorderIndex(points, minSquareDistance);
		int rightBorderIndex = findRightBorderIndex(points, minSquareDistance);
		return points.subList(leftBorderIndex, rightBorderIndex + 1);
	}
	
	private static int findLeftBorderIndex(List<Point> points, 
			long minSquareDistance) {
		int mediumIndex = points.size() / 2;
		int leftBorderIndex = mediumIndex;
		long mediumX = points.get(mediumIndex).getX();
		for(int index = mediumIndex - 1; index >= 0; index--) {
			long x = points.get(index).getX();
			long squareDistance = (mediumX - x) * (mediumX - x);
			if(squareDistance < minSquareDistance) {
				leftBorderIndex = index;
			} else {
				break;
			}
		}
		return leftBorderIndex;
	}
	
	private static int findRightBorderIndex(List<Point> points, 
			long minSquareDistance) {
		int pointsNumber = points.size();
		int mediumIndex = pointsNumber / 2;
		int rightBorderIndex = mediumIndex;
		long mediumX = points.get(mediumIndex).getX();
		for(int index = mediumIndex + 1; index < pointsNumber; index++) {
			long x = points.get(index).getX();
			if( (x - mediumX) * (x - mediumX) < minSquareDistance ) {
				rightBorderIndex = index;
			} else {
				break;			
			}
		}
		return rightBorderIndex;
	}
	
	private static long findBorderMinSquareDistance(List<Point> points, 
			long minSquareDistance) {
		int pointsNumber = points.size();
		if(pointsNumber < 2) {
			return Long.MAX_VALUE;
		}
		Collections.sort(points, Y_COMPARATOR);
		long borderMinSquareDistance = Long.MAX_VALUE;
		for(int i = 1; i < pointsNumber; i++) {
			for(int j = i - 1; j >= 0; j--) {
				Point upper = points.get(i);
				Point bottom = points.get(j);
				if(upper.getY() - bottom.getY() > minSquareDistance) {
					break;
				}
				long squareDistance = upper.getSquareDistance(bottom);
				if(squareDistance < borderMinSquareDistance) {
					borderMinSquareDistance = squareDistance;
				}
			}
		}
		return borderMinSquareDistance;
	}
}
