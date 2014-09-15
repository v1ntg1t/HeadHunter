public class InputGenerator {
	private static final int POINTS_NUMBER = 10;
	
	private static int generateRandom(int min, int max) {
		return (int)Math.round(Math.random() * Math.abs(max - min) + min);
	}
	
	public static void main(String[] args) {
		for(int i = 0; i < POINTS_NUMBER; i++) {
			System.out.print(generateRandom(-POINTS_NUMBER, POINTS_NUMBER));
			System.out.print("\t");
			System.out.println(generateRandom(-POINTS_NUMBER, POINTS_NUMBER));
		}
	}
}