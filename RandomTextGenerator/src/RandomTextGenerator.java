package pa4;


//Name: Yash Bhatnagar
//USC loginid: 6814424170
//CSCI455 PA2
//Spring 2016

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Random;

/**
 * class RandomTextGenerator Generates a random text with fixed number of words
 * while using a certain length of prefix to use and with the help of a given
 * source file.
 */
public class RandomTextGenerator {
	private static final long SEED_CONST = 1; // Seed Constant of Random object.
	/**
	 * Representation invariant: currPrefix is the Prefix being used to generate
	 * each next word. data is a HashMap created using the text in the source
	 * file. data.size()>currPrefix.length()>0 numWords is the number of words
	 * to be written in the output file. numWords>=0; prefixArr is a String
	 * array holding each of the words in the currPrefix as indices.
	 * prefixArr.length=number of space separated words in currPrefix. result
	 * String- holds the output text with number of words equal to the numWords
	 * when returned. Boolean debug- is true only when program being run in
	 * debugging mode.
	 */

	private String currPrefix = "";
	private HashMap<Integer, String> data;
	private Random random;
	private int numWords;
	private String[] prefixArr;
	private String result = "";
	private boolean debug = false;

	/**
	 * Constructs the RandomTextGenerator Object with desired parameters.
	 * 
	 * @param source
	 *            hashMap containing source file text as Key Values
	 * @param preFix
	 *            the Prefix String.
	 * @param numWords
	 *            total number of words to be written to the output file.
	 * @param debug
	 *            true only when program being run in debugging mode.
	 */
	public RandomTextGenerator(HashMap<Integer, String> source, String preFix, int numWords, boolean debug) {
		data = new HashMap<>(source);
		currPrefix = preFix;
		this.numWords = numWords;
		this.debug = debug;
		if (debug) {
			random = new Random(SEED_CONST);
		} else {
			random = new Random();
		}
	}

	/**
	 * Generates the random text.
	 * 
	 * @return result the output text.
	 */
	public String randomTextGenerator() {

		prefixArr = currPrefix.split("\\s");
		HashMap<Integer, String> probSuffix = new HashMap<>();
		Prefix prefix = new Prefix(prefixArr.length, data, debug);
		LinkedList<String> text = new LinkedList<>();
		for (int j = 0; j < prefixArr.length; j++) {
			text.add(prefixArr[j]);
		}
		int count = 0;
		String word1 = "";
		while (count < numWords) {
			int i = 0;
			int j = 0;
			int keyCount = 0;
			probSuffix.clear();
			while (keyCount < data.size()) {
				word1 = data.get(keyCount);
				if (word1.equals(prefixArr[i])) {
					i++;
					keyCount++;
					if (i == prefixArr.length) {
						if (keyCount < data.size()) {
							probSuffix.put(j, data.get(keyCount));
						}
						j++;
						i = 0;
					}
				} else {
					i = 0;
					keyCount++;
				}
			}
			count=getResult(probSuffix, prefix, text,count);
			
		}
		return result;
	}

	/**
	 * Creates the result String.
	 * 
	 * @param probSuffix
	 *            Hashmap with probable Suffixes as KeyValues.
	 * @param prefix
	 *            Object of Prefix class.
	 * @param text
	 *            Linked List used to modify currPrefix for each run.
	 * @param count loop variable
	 * @return count 
	 * 			updates only if a word is added to result String.
	 */
	private int getResult(HashMap<Integer, String> probSuffix, Prefix prefix, LinkedList<String> text, int count) {

		if (probSuffix.isEmpty()) {
			if (debug) {
				System.out.println("DEBUG: successors: <END OF FILE>");
			}
			currPrefix = prefix.findRandomPrefix();
			prefixArr = currPrefix.split("\\s");
			text.clear();
			for (int k = 0; k < prefixArr.length; k++) {
				text.add(prefixArr[k]);
			}
		} else {
			if (debug) {
				System.out.println("DEBUG: successors: " + probSuffix.toString());
			}
			int pick = random.nextInt(probSuffix.size());
			result += probSuffix.get(pick) + " ";
			if (debug) {
				System.out.println("DEBUG: word generated: " + probSuffix.get(pick));
			}
			text.add(probSuffix.get(pick));
			text.removeFirst();
			currPrefix = "";
			int k = 0;
			for (String p : text) {
				if (k == text.size() - 1) {
					currPrefix += p;
					k++;
				} else {
					currPrefix += p + " ";
					k++;
				}
			}
			if (debug) {
				System.out.println("prefix: " + currPrefix);
			}
			prefixArr = currPrefix.split("\\s");
			count++;
		}
		return count;
	}
}
