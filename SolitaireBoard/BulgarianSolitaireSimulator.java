import java.util.Scanner;

// Name: Yash Bhatnagar
// USC loginid: 6814424170
// CSCI455 PA2
// Spring 2016

/**
 * Class BulgarianSolitaireSimulator
 * This simulates the Bulgarian Solitare for any initial configuration provided.
 * if run without program arguments it outputs Bulgarian SOlitare results with a random initial configuration. 
 * Program argument '-u' is used if the user wants to provide the initial configuration.
 * Program argument '-s' is used to wait after generating each configuration for the user to press return key.
 * With '-s' it uses the random initial configuration. 
 */

public class BulgarianSolitaireSimulator {

	// private static
	/**
	 * Simulates one round of Bulgarian Solitare and takes user command line input for Initial configuration if '-u' i.e userConfig argument provided.
	 * @param args: -u  takes user command line input for Initial configuration.
	 * 				-s	wait after generating each configuration for the user to press return key.
	 * 	  Not Provided	outputs Bulgarian SOlitare results with a random initial configuration.			
	 */	
	public static void main(String[] args) {
		SolitaireBoard solitareBoard = null;
		String numberString = null;
		Scanner in=null;
		boolean singleStep = false;
		boolean userConfig = false;
		for (int i = 0; i < args.length; i++) {
			if (args[i].equals("-u")) {
				userConfig = true;
			} else if (args[i].equals("-s")) {
				singleStep = true;
			}
		}
		if (args.length == 0) {
			solitareBoard = new SolitaireBoard();
			printResult(solitareBoard, "Default",in);
		}
		if (userConfig==true&&singleStep==true){
			solitareBoard = new SolitaireBoard(formatCheck(solitareBoard, numberString, in));
			printResult(solitareBoard, "Single Step", in);
		}
		else if (userConfig==true){
			userConfig(solitareBoard, numberString, in);
		}
		else if (singleStep==true){
			singleStep(solitareBoard, numberString, in);
		}
	}
	
	/**
	 * Takes user command line input for Initial configuration if '-u' i.e userConfig argument provided and displays results.
	 * @param solitareBoard object of SolitareBoard class.
	 * @param numberString to hold Initial configuration string.	
	 */
	private static void userConfig(SolitaireBoard solitareBoard, String numberString, Scanner in) {
		solitareBoard = new SolitaireBoard(formatCheck(solitareBoard, numberString, in));
		printResult(solitareBoard, "User Config", in);
	}
	
	/**
	 * Does the behavior required when '-s' argument is called for i.e wait after generating each configuration for the user to press return key.
	 * @param solitareBoard, object of SolitareBoard class.
	 * @param numberString, to hold Initial configuration string.	
	 * @param in, Scanner class object.
	 */
	private static void singleStep(SolitaireBoard solitareBoard, String numberString, Scanner in) {

		solitareBoard = new SolitaireBoard();
		printResult(solitareBoard, "Single Step", in);
	}
	
	/**
	 * Takes the numberString String input from the user and checks it by using isValidConfigString() method of SolitareBoard class.
	 * Returns a String iff it passes the validation. 
	 * @param solitareBoard, object of SolitareBoard class.
	 * @param numberString, to hold Initial configuration string.	
	 * @param in, Scanner class object.
	 */
	private static String formatCheck(SolitaireBoard solitareBoard, String numberString, Scanner in) {
		in = new Scanner(System.in);
		boolean pass = false;
		System.out.println("Number of total cards is 45");
		System.out.println("You will be entering the initial configuration of the cards (i.e., how many in each pile.)");
		System.out.println("Please enter a space-separated list of positive integers followed by newline: ");
		while (!pass) {
			numberString = in.nextLine();
			if (SolitaireBoard.isValidConfigString(numberString)) {

				pass = true;
			} else {
				System.out.println("ERROR: Each pile must have at least one card and the total number of cards must be 45");
				System.out.println("Please enter a space-separated list of positive integers followed by newline: ");
			}
		}
		return numberString;
	}
	
	/**
	 * Prints the output correctly with desired constraints for both type of arguments provided.
	 * @param solitareBoard, object of SolitareBoard class.
	 * @param numberString, to hold Initial configuration string.	
	 * @param in, Scanner class object.
	 */
	private static void printResult(SolitaireBoard solitareBoard, String argument, Scanner in) {
		System.out.println("Initial configuration: " + solitareBoard.configString());
		int n = 0;
		while (!solitareBoard.isDone()) {
			solitareBoard.playRound();
			n++;
			System.out.println("[" + n + "] Current configuration: " + solitareBoard.configString());
			if (argument.equals("Single Step")) {					//Has to wait for user to press return key.
				in = new Scanner(System.in);
				System.out.print("<Type return to continue>");
				in.nextLine();
			}
		}
		System.out.println("Done!");
	}


}

// <add private static methods here>
