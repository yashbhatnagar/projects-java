import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

// Name: Yash Bhatnagar
// USC loginid: 6814424170
// CSCI455 PA2
// Spring 2016

/*
   class SolitaireBoard
   The board for Bulgarian Solitaire.  You can change what the total number of cards is for the game
   by changing NUM_FINAL_PILES, below.  Don't change CARD_TOTAL directly, because there are only some values
   for CARD_TOTAL that result in a game that terminates.
   (See comments below next to named constant declarations for more details on this.)
 */

public class SolitaireBoard {

	public static final int NUM_FINAL_PILES = 9;
	// number of piles in a final configuration
	// (note: if NUM_FINAL_PILES is 9, then CARD_TOTAL below will be 45)

	public static final int CARD_TOTAL = NUM_FINAL_PILES * (NUM_FINAL_PILES + 1) / 2;
	// bulgarian solitaire only terminates if CARD_TOTAL is a triangular number.
	// see: http://en.wikipedia.org/wiki/Bulgarian_solitaire for more details
	// the above formula is the closed form for 1 + 2 + 3 + . . . +
	// NUM_FINAL_PILES

	/**
	 * Representation invariant: 
	 * nCurrPiles is the number of piles currently present in the cardPiles[] array. 
	 * 1<=nCurrPiles<=cardPiles.length.
	 * If nCurrPiles>=1, the cards are in cardPiles[0] through
	   cardPiles[nCurrPiles-1]. 
	 * 0<cardPiles[i]<=CARD_TOTAL; here 'i' is any index from 0 to nCurrPiles-1.
	 * Sum of the values of cardPiles[] array is always equal to the CARD_TOTAL. 
	 * cardPiles.length<=CARD_TOTAL. 
	 */
	private String numberString = null;
	private int[] cardPiles = null;
	private int nCurrPiles = 0;
	private static Scanner in = null;
	private Random random=new Random();
	
	/**
	 * Creates a solitaire board with the given configuration. 
	 * @param
	 * PRE: SolitaireBoard.isValidConfigString(numberString)
	 */
	public SolitaireBoard(String numberString) {
		this.numberString = numberString;
		cardPiles = new int[CARD_TOTAL];
		in = new Scanner(numberString);
		while (in.hasNextInt()) {
			cardPiles[nCurrPiles] = in.nextInt();
			nCurrPiles++;
		}

		assert isValidSolitaireBoard(); 
	}

	/**
	 * Creates a solitaire board with a random initial configuration.
	 */
	public SolitaireBoard() {
		int cardTotal=CARD_TOTAL;
		numberString="";
		while(cardTotal!=0){
			int nextPile=1+random.nextInt(cardTotal);			//Taking random numbers while maintaining Total
			numberString=numberString+(nextPile+" ");
			cardTotal-=nextPile;	
		}			
		numberString = numberString.substring(0,numberString.length()-1);	//Removing Ending Space.
		cardPiles = new int[CARD_TOTAL];
		in = new Scanner(numberString);
		while (in.hasNextInt()) {
			cardPiles[nCurrPiles] = in.nextInt();
			nCurrPiles++;
		}
		assert isValidConfigString(numberString);
		assert isValidSolitaireBoard();
	}

	/**
	 * Plays one round of Bulgarian solitaire. Updates the configuration
	 * according to the rules of Bulgarian solitaire: Takes one card from each
	 * pile, and puts them all together in a new pile. The old piles that are
	 * left will be in the same relative order as before, and the new pile will
	 * be at the end.
	 */
	public void playRound() {
		int count = 0;
		for (int i = 0; i < nCurrPiles; i++) {
			cardPiles[i]--;						//Subtracting one card from each pile.
			count++;
			if (cardPiles[i] == 0) {			//pile after subtracting one becomes zero.
				for (int j = i; j < nCurrPiles - 1; j++) {
					cardPiles[j] = cardPiles[j + 1];	//Move each next pile left by one
					if (j == nCurrPiles - 2) {
						cardPiles[j + 1] = 0;			//Remove the previously last pile.
					}
				}
				nCurrPiles--;				//Reduce number of piles.
				i--;
			}
		}
		cardPiles[nCurrPiles] = count;		//Putting all the cards subtracted to last pile.
		nCurrPiles++;						
		assert isValidSolitaireBoard();
	}

	/**
	 * Returns true iff the current board is at the end of the game. That is,
	 * there are NUM_FINAL_PILES piles that are of sizes 1, 2, 3, . . . ,
	 * NUM_FINAL_PILES, in any order.
	 */

	public boolean isDone() {
		boolean done = true;
		if (nCurrPiles == NUM_FINAL_PILES) {
			for (int i = 0; i < nCurrPiles - 1; i++) {
				if (!repeatCheck(cardPiles[i]) || (cardPiles[NUM_FINAL_PILES + 1] != 0)) {
					done = false;							//Final Config: Distinct digits only.
				}
			}
		} else if (nCurrPiles != NUM_FINAL_PILES) {			//End with exactly final num of piles.
			done = false;
		}
		assert isValidSolitaireBoard();
		return done; 
	}

	/**
	 * Returns current board configuration as a string with the format of a
	 * space-separated list of numbers with no leading or trailing spaces. The
	 * numbers represent the number of cards in each non-empty pile.
	 */
	public String configString() {
		assert isValidSolitaireBoard();
		return (Arrays.toString(cardPiles).replaceAll(" 0", " ").replaceAll("\\[", "").replaceAll("\\]", "")
				.replaceAll(",", ""));
	}

	/**
	 * Returns true iff configString is a space-separated list of numbers that
	 * is a valid Bulgarian solitaire board with card total
	 * SolitaireBoard.CARD_TOTAL
	 */
	public static boolean isValidConfigString(String configString) {

		in = new Scanner(configString);
		int[] piles = new int[CARD_TOTAL];
		int countPiles = 0;
		
		while (in.hasNext()) {
			if(in.hasNext("\\d*")){					//only digits accepted.
			piles[countPiles] = in.nextInt();
			countPiles++;
			}
			else {
				return false;
			}
			
		}
		boolean result = isValidConfiguration(piles, countPiles);
		return result;
	}

	/**
	 * Returns true iff the solitaire board data is in a valid state (See
	 * representation invariant comment for more details.)
	 */
	private boolean isValidSolitaireBoard() {
		// isValidConfiguration()
		boolean result = isValidConfiguration(cardPiles, nCurrPiles);
		return result; 

	}
	/**
	 * Returns true iff the solitaire board configuration is in a valid state (See
	 * representation invariant comment for more details.)
	 * @param piles: is the array containing the piles of cards as index values.
	 * @param numPiles: is the number of piles in the game currently.
	 */
	private static boolean isValidConfiguration(int[] piles, int numPiles) {
		boolean fail = false;
		if (numPiles > 0 && numPiles <= CARD_TOTAL) {
			int j = 0;
			int sum = 0;
			while (j < numPiles) {					
				if (piles[j] < 1) {				//number of cards in each pile should be non-negative.
					return fail;
				}
				sum += piles[j];
				j++;
			}
			if (sum != CARD_TOTAL) {			//Sum of all the cards=Card total.
				return fail;
			}
		}
		if (piles[0] <= 0||numPiles<=0) {		//atleast one card and one pile;
			return fail;
		}
		return !fail;
	}
	/**
	 * Returns true iff the number of cards in each pile doesn't repeat in the final configuration.
	 * @param cardValue: is the number of cards in a pile.
	 */
	private boolean repeatCheck(int cardValue) {
		int countEqual = 0;
		boolean unique = false;
		for (int i = 0; i < nCurrPiles - 1; i++) {
			if (cardValue == cardPiles[i]) {
				countEqual++;
			}
		}
		if (countEqual < 2) {							//implies not repeated.
			unique = true;
		}
		return unique;
	}
	// <add any additional private methods here>

}
