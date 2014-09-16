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
	private static final int DIVIDEND_INDEX = 0;
	private static final int DIVIDER_INDEX = 1;
	private static final int RADIX_INDEX = 2;
	private static final int DATA_INDEXES = 3;
	private static final char[] DIGITS = {'0', '1', '2', '3', '4', '5', '6', 
			'7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 
			'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 
			'x', 'y', 'z'};

	public static void main(String[] args) {
		try {
			Scanner scanner = new Scanner(new File(INPUT_FILE_NAME));
			while(scanner.hasNext()) {
				String scannedLine = scanner.nextLine().trim();
				long[] data = parse(scannedLine);
				if(data == null) {
					continue;
				}
				System.out.println(buildFraction(data));
			}
			scanner.close();
		} catch(FileNotFoundException exception) {
			System.out.println("File '" + INPUT_FILE_NAME + "' is not founded");
		}
	}
	
	private static long[] parse(String dataLine) {
		if(!dataLine.matches(FRACTION_PATTERN)) {
			System.out.println("bad data format line");
			return null;
		}
		String[] data = dataLine.split(DATA_DELIMITER);
		long[] parsedData = new long[DATA_INDEXES];
		try {
			parsedData[DIVIDEND_INDEX] = Long.parseLong(data[DIVIDEND_INDEX]);
			parsedData[DIVIDER_INDEX] = Long.parseLong(data[DIVIDER_INDEX]);
			if(parsedData[DIVIDER_INDEX] == 0) {
				System.out.println("bad divider equals 0");
				return null;
			}
			parsedData[RADIX_INDEX] = Long.parseLong(data[RADIX_INDEX]);
			if(parsedData[RADIX_INDEX] < 2 
					|| parsedData[RADIX_INDEX] > DIGITS.length) {
				System.out.print("bad radix, can be 1 < radix <= ");
				System.out.println(DIGITS.length);
				return null;
			}
			return parsedData;
		} catch(NumberFormatException e) {
			System.out.println("bad data format line");
			return null;
		}
	}
	
	private static StringBuilder buildFraction(long[] data) {
		long dividend = data[DIVIDEND_INDEX];
		long divider = data[DIVIDER_INDEX];
		long radix = data[RADIX_INDEX];
		StringBuilder fraction = new StringBuilder();
		boolean isNegative = dividend < 0 ^ divider < 0;
		if(isNegative) {
			fraction.append('-');
		}
		long integerPart = Math.abs(dividend / divider);
		fraction.append(translateIntPart(radix, integerPart));
		long nominator = Math.abs(dividend % divider);
		long denominator = Math.abs(divider);
		fraction.append(translateFractionPart(radix, nominator, denominator));
		return fraction;
	}
	
	private static StringBuilder translateIntPart(long radix, long intPart) {
		StringBuilder sb = new StringBuilder();
		while(radix < intPart) {
			int digit = (int)(intPart % radix);
			sb.insert(0, DIGITS[digit]);
			intPart /= radix;
		}
		sb.insert(0, DIGITS[(int)intPart]);
		return sb;
	}
	
	private static StringBuilder translateFractionPart(long radix, 
			long nominator, long denominator) {
		StringBuilder sb = new StringBuilder();
		List<Long> savedRests = new ArrayList<Long>();
		long rest = nominator;
		if(rest != 0) {
			sb.append('.');
		}
		while(rest != 0) {
			savedRests.add(rest);
			nominator = rest * radix;
			int digit = (int)(nominator / denominator);
			sb.append(DIGITS[digit]);
			rest = nominator % denominator;
			for(int digitPos = 0; digitPos < savedRests.size(); digitPos++) {
				if(savedRests.get(digitPos) == rest) {
					sb.insert(digitPos + 1, '(');
					sb.append(')');
					return sb;
				}
			}
		}
		return sb;
	}
}