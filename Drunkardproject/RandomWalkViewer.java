package drunkardprj;


import java.util.Scanner;
import javax.swing.JFrame;

//Name: Yash Bhatnagar
//USC loginid: 6814424170
//CS 455 PA1
//Spring 2016

/**
 * RandomWalkViewer:        Main class for viewing the Random walk.
 * Displays the overall Random Path traversed by the Drunkard for given number of steps.  
 * @author yash bhatnagar
 */
public class RandomWalkViewer {

	private static Scanner in;
	private static final int FRAME_WIDTH = 400;										// This constant can be changed to change the Frame width.
	private static final int FRAME_HEIGHT = 400;									// This constant can be changed to change the Frame Height.
	private static JFrame frame;
	private static ImPoint imP1;
	private static RandomWalkComponent component;

	/**
	 * Defines the Result viewer "Random Walk" JFrame and takes user command line input for Number of Steps.
	 * @param args not used
	 */
	public static void main(String[] args) {
		
		frame = new JFrame();														// Instancing default JFrame Object.
		frame.setSize(FRAME_WIDTH,FRAME_HEIGHT);
		frame.setTitle("Random Walk");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		boolean validation = false;
		in = new Scanner(System.in);
		int numberOfSteps=0;
		while(!validation){		
			System.out.print("Enter number of steps: ");
			numberOfSteps = in.nextInt();
			in.nextLine();
			if(numberOfSteps>0){
				validation=true;
			}
			else{
				System.out.println("ERROR: Number entered must be greater than 0.");
			}
			}
		imP1 = new ImPoint((FRAME_WIDTH/2),(FRAME_HEIGHT/2));						// Instancing the ImPoint object with Starting location as parameters.
		component = new RandomWalkComponent(imP1, numberOfSteps);					// Instancing the RandomWalkComponent Object
																					// with ImPoint Object and numberOfSteps as parameters.
		frame.add(component);
		frame.setVisible(true);
		}
	
}
