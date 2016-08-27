package pa4;


//Name: Yash Bhatnagar
//USC loginid: 6814424170
//CSCI455 PA2
//Spring 2016

/**
 * class BadArgException Extends Exception. If command line
 * arguments(prefixLength and numWords are not entered as per the format.
 * handles the exceptions thrown.
 */
@SuppressWarnings("serial")
public class BadArgException extends Exception {

	/**
	 * If command line arguments(prefixLength and numWords are not entered as
	 * per the format. handles the exceptions thrown. Puts appropriate message
	 * to the console.
	 */
	public BadArgException() {
		System.out.println("Please follow Format: java GenText [-d] prefixLength numWords sourceFile outFile.");
		System.out.println("prefixLength>=1 and numWords>=0");
	}

	/**
	 * @param message
	 *            prints the message string to the console when called.
	 */
	public BadArgException(String message) {
		super(message);
	}

}
