import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Fraction {
	private static final String INPUT_FILE_NAME = "in.txt";
	private static final String DATA_DELIMITER = "\\s+";
	private static final String FRACTION_PATTERN = "(-?\\d+)" + DATA_DELIMITER 
			+ "(-?\\d+)" + DATA_DELIMITER + "(\\d+)";
	private static final int DIVIDEND = 0;
	private static final int DIVIDER = 1;
	private static final int RADIX = 2;
	private static final char[] DIGITS = {'0', '1', '2', '3', '4', '5', '6', 
			'7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 
			'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 
			'x', 'y', 'z'};

	public static void main(String[] args) {
		try {
			Scanner scanner = new Scanner(new File(INPUT_FILE_NAME));
			while(scanner.hasNext()) {
				String scannedLine = scanner.nextLine().trim();
				if(scannedLine.matches(FRACTION_PATTERN)) {
					String[] fraction = scannedLine.split(DATA_DELIMITER);
					long dividend, divider, radix;
					try {
						dividend = Long.parseLong(fraction[DIVIDEND]);
						divider = Long.parseLong(fraction[DIVIDER]);
						radix = Long.parseLong(fraction[RADIX]);
					} catch(NumberFormatException e) {
						System.out.println("bad data format line");
						continue;
					}
					if(radix < 2 || radix > DIGITS.length) {
						System.out.print("radix must be 1 < radix < ");
						System.out.println(DIGITS.length);
						continue;
					}

					long integerPart = Math.abs(dividend / divider);
					long nominator = Math.abs(dividend) % Math.abs(divider);
					long denominator = Math.abs(divider);

					StringBuilder fractionPart = translateFraction(radix, 
							nominator, denominator);

					StringBuilder answer = new StringBuilder();
					if(dividend < 0 ^ divider < 0) {
						answer.append('-');
					}
					answer.append(integerPart);
					answer.append('.');
					answer.append(fractionPart);
					System.out.println(answer);
				} else {
						System.out.println("bad data format line");
					continue;
				}
			}
			scanner.close();
		} catch(FileNotFoundException exception) {
			System.out.println("File '" + INPUT_FILE_NAME + "' is not founded");
		}
	}
	
	private static StringBuilder translateFraction(long radix, 
			long nominator, long denominator) {
		StringBuilder sb = new StringBuilder();
		List<Long> savedRests = new ArrayList<Long>();
		long rest = nominator;
		savedRests.add(rest);
		while(rest != 0) {
			nominator = rest * radix;
			int digit = (int)(nominator / denominator);
			sb.append(DIGITS[digit]);
			rest = nominator % denominator;
			for(int digitPos = 0; digitPos < savedRests.size(); digitPos++) {
				if(savedRests.get(digitPos) == rest) {
					sb.insert(digitPos, '(');
					sb.append(')');
					return sb;
				}
			}
			savedRests.add(rest);
		}
		return sb;
	}
}