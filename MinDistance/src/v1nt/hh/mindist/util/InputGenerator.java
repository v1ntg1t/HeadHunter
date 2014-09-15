package v1nt.hh.mindist.util;

public class InputGenerator {
	public static void main(String[] args) {
		try {
			int pointsNumber = Integer.parseInt(args[0]);
			for(int i = 0; i < pointsNumber; i++) {
				System.out.print(randomInt(-pointsNumber, pointsNumber));
				System.out.print("\t\t\t");
				System.out.println(randomInt(-pointsNumber, pointsNumber));
			}
		} catch(NumberFormatException e) {
			System.out.println("Argument 'points number' has wrong format!");
			System.out.println("It must be 'int' type!");
		}
	}
	
	private static int randomInt(int min, int max) {
		return (int)Math.round( min + Math.random() * Math.abs(max - min) );
	}
}