import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Fraction {
	private static final String INPUT_FILE_NAME = "in.txt";
	private static final String DATA_DELIMITER = "\\s+";
	private static final String FRACTION_PATTERN = "(\\d+)" + DATA_DELIMITER 
			+ "(\\d+)" + DATA_DELIMITER + "(\\d+)";
	private static final int INDEX_DIVIDEND = 0;
	private static final int INDEX_DIVIDER = 1;
	private static final int INDEX_RADIX = 2;
	private static final char[] DIGITS = {'0', '1', '2', '3', '4', '5', '6', 
			'7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

	public static void main(String[] args) {
		try {
			Scanner scanner = new Scanner(new File(INPUT_FILE_NAME));
			while(scanner.hasNext()) {
				String scannedLine = scanner.nextLine().trim();
				if(scannedLine.matches(FRACTION_PATTERN)) {
					String[] fraction = scannedLine.split(DATA_DELIMITER);
					int dividend = Integer.parseInt(fraction[INDEX_DIVIDEND]);
					int divider = Integer.parseInt(fraction[INDEX_DIVIDER]);
					int radix = Integer.parseInt(fraction[INDEX_RADIX]);
					if(radix < 1 || radix > DIGITS.length) {
						continue;
					}
					int integerPart = dividend / divider;
					int nominator = dividend % divider;
					int denominator = divider;

					System.out.print(integerPart + " + " + nominator + "/" 
							+ denominator);
							
					StringBuilder digits = convertFraction(nominator, 
							denominator, radix);
					System.out.println(" in radix = " + radix + " : " 
							+ integerPart + "." + digits);
				}
			}
			scanner.close();
		} catch(FileNotFoundException exception) {
			System.out.println("File '" + INPUT_FILE_NAME + "' is not founded");
		}
	}
	
	private static StringBuilder convertFraction(int nominator, int denominator, 
			int radix) {
		StringBuilder sb = new StringBuilder('.');
		int rest = nominator;
		boolean isPeriodic = false;
		List<Integer> restDump = new ArrayList<Integer>(rest);
		while(rest != 0) {
			nominator = rest * radix;
			rest = nominator % denominator;
			for(int i = 0; i < restDump.size(); i++) {
				if(restDump.get(i) == rest) {
					char[] parenthesis = {'('};
					sb.insert(i, parenthesis, 0, 1);
					isPeriodic = true;
					break;
				}
			}
			if(isPeriodic) {
				sb.append(')');
				break;
			} else {
				int digit = nominator / denominator;
				sb.append(DIGITS[digit]);
				restDump.add(rest);
			}
		}
		return sb;
	}
}