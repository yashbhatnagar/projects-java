package pa4;


//Name: Yash Bhatnagar
//USC loginid: 6814424170
//CSCI455 PA2
//Spring 2016
import java.util.HashMap;
import java.util.Scanner;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

/**
 * class GenText Takes the command line arguments as specified and writes the
 * random text generated to a given output file.
 */
public class GenText {

	private static final int LINE_WIDTH = 80; // can be used to set
												// the req. Line width
												// in terms of charachters.
	private static final int BEGIN_INDEX = 0; // can be used to set
												// the index of character to
												// begin with.
	private static final int ARGLENGTH_W_DEBUG_OFF = 4;

	/**
	 * @param args
	 *            command line arguments. format - java GenText [-d]
	 *            prefixLength numWords sourceFile outFile
	 * @throws BadArgException
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner in = new Scanner(System.in);
		try {
			checkArg(args, in);
		} catch (BadArgException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Checks the Command Line Arguments, uses Prefix and RandomTextGenartor
	 * Objects to write the random text to the output file.
	 * 
	 * @param args
	 *            command line arguments.
	 * @param in
	 *            Scanner Object
	 * @throws BadArgException
	 */
	private static void checkArg(String[] args, Scanner in) throws BadArgException {
		int argCount = 0;
		Boolean debug = false;
		if (args.length < ARGLENGTH_W_DEBUG_OFF) {
			throw new BadArgException();
		}
		if (args[0].equals("-d") || args[0].equals("-D")) {
			debug = true;
			argCount++;
		}
		int prefixLength = 0;
		int numWords = 0;
		in = new Scanner(args[argCount]);
		checkDigit(in);
		prefixLength = Integer.parseInt(args[argCount]);
		argCount++;
		in = new Scanner(args[argCount]);
		checkDigit(in);
		numWords = Integer.parseInt(args[argCount]);
		argCount++;
		if (prefixLength < 1 || numWords < 0) {
			throw new BadArgException();
		}
		File sourceFile = new File(args[argCount]);
		if (args.length - 1 == argCount) { // If output file or input file not
											// provided.
			throw new BadArgException();
		}
		argCount++;
		File outFile = new File(args[argCount]);
		try {
			in = new Scanner(sourceFile);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		HashMap<Integer, String> hashMap = new HashMap<>();
		populateMap(in, hashMap, prefixLength);
		Prefix prefix = new Prefix(prefixLength, hashMap, debug);
		RandomTextGenerator ranText = new RandomTextGenerator(hashMap, prefix.findRandomPrefix(), numWords, debug);
		String result = ranText.randomTextGenerator();
		writeToFile(outFile, result);
	}

	/**
	 * Populates the HashMap created using text in Source File. Also checks for
	 * prefixLength constraints.
	 * 
	 * @param in
	 *            Scanner Object
	 * @param hashMap
	 *            hasMap object to be populated.
	 * @param prefixLength
	 *            Length of the prefix String.
	 * @throws BadArgException
	 */
	private static void populateMap(Scanner in, HashMap<Integer, String> hashMap, int prefixLength)
			throws BadArgException {
		int i = 0;
		while (in.hasNext()) {
			hashMap.put(i, in.next());
			i++;
		}
		if (prefixLength >= hashMap.size()) {
			throw new BadArgException("prefixLength is greater than numOfWords in source file.");
		}
		in.close();

	}

	/**
	 * Checks if the scanned input is a number.
	 * 
	 * @param in
	 *            Scanner Object.
	 * @throws BadArgException
	 */
	private static void checkDigit(Scanner in) throws BadArgException {
		while (in.hasNext()) {
			if (!in.hasNext("\\d*")) {
				throw new BadArgException();
			}
			in.next();
		}

	}

	/**
	 * Writes the text as per the prescribed LINE_WIDTH and output file.
	 * 
	 * @param outFile
	 *            File in which the output is to be written.
	 * @param result
	 *            the result String which is to be written to the output file.
	 */
	private static void writeToFile(File outFile, String result) {
		try {
			FileWriter fout = new FileWriter(outFile);
			BufferedWriter bw = new BufferedWriter(fout);
			int traversed = LINE_WIDTH;
			int begin = BEGIN_INDEX;
			boolean space = false;
			String lineText = "";
			while (traversed <= result.length()) {
				if (result.charAt(begin) == ' ') {
					begin++;
					traversed++;
				}
				if (result.charAt(traversed - 1) == ' ') {
					traversed--;
					space = true;
				}
				lineText = result.substring(begin, traversed);
				if (result.charAt(traversed - 1) != ' ' && !space && result.charAt(traversed) != ' ') {
					traversed = lineText.lastIndexOf(' ');
					lineText = lineText.substring(0, traversed);
					traversed += begin;
				}
				bw.write(lineText);
				bw.newLine();
				begin = traversed + 1;
				traversed = begin + LINE_WIDTH;
				lineText = "";
				space = false;
			}
			if (begin != result.length()) {
				lineText = result.substring(begin);
				bw.write(lineText);
			}
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
