package pa4;


//Name: Yash Bhatnagar
//USC loginid: 6814424170
//CSCI455 PA2
//Spring 2016
import java.util.HashMap;
import java.util.Random;

/**
 * class Prefix Generates a Random Prefix to be used to generate text given a
 * fixed Prefix length and using a given source file stored as a Hash-map.
 */
public class Prefix {

	private static final long SEED_CONST = 1; // Seed Constant of Random object.
	/**
	 * Representation invariant: prefixLength is integer and is initialized to
	 * zero. source is a HashMap created using the text in the source file.
	 * source.size()>prefixLength>0 Boolean debug is true only when program
	 * being run in debugging mode.
	 */

	private int prefixLen = 0;
	private HashMap<Integer, String> source;
	private Random random;
	private boolean debug = false;

	/**
	 * Creates the Prefix object with desired initializations.
	 * 
	 * @param prefixLength
	 *            Required Length of prefix.
	 * @param hashMap
	 *            contains the text of Source File as KeyValues.
	 * @param debug
	 *            true only when debugging mode is turned on.
	 */
	public Prefix(int prefixLength, HashMap<Integer, String> hashMap, boolean debug) {
		prefixLen = prefixLength;
		source = new HashMap<>(hashMap);
		this.debug = debug;
		if (debug) {
			random = new Random(SEED_CONST); // Constant Seed.
		} else {
			random = new Random();
		}
	}

	/**
	 * Chooses a new initial prefix from the source.
	 * 
	 * @return String-preString--> Random Prefix
	 */
	public String findRandomPrefix() {
		int startKey = random.nextInt(source.size() - 2);
		String preString = "";
		for (int i = 0; i < prefixLen; i++) {
			if (i == prefixLen - 1) {
				preString += source.get(startKey + i);
			} else {
				preString += source.get(startKey + i) + " ";
			}
		}
		// currKey=startKey+prefixLen;
		if (debug) {
			System.out.println("chose a new initial prefix: " + preString);
		}
		return preString;
	}

}
