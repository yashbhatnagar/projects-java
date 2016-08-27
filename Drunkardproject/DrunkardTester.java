import java.lang.Math;

//Name: Yash Bhatnagar
//USC loginid: 6814424170
//CS 455 PA1
//Spring 2016

/**
 *Drunkard Tester
 * console-based test program for Drunkard class.
    Uses hard-coded data; prints expected and actual output.
 * @author yash bhatnagar
 */
public class DrunkardTester {

	/**
	 * Test Driver for Drunkard class.
	 * @param args not used.
	 */
	public static void main(String[] args) {	
		DrunkardTest(new ImPoint(200,200), 5);
		DrunkardTest(new ImPoint(300,300), 1);
		DrunkardTest(new ImPoint(100,100), 10);
		}
	
	/**
	 * Test Driver for Drunkard class.
	 * @param args not used.
	 */
	private static void DrunkardTest(ImPoint imP, int theStepSize) {
		Drunkard drunkard = new Drunkard(imP,theStepSize);
		
		System.out.println("Testing with location=("+imP.getX()+","+imP.getY()+") And Step Size="+theStepSize);
		System.out.println("Testing getCurrentLoc...");
		System.out.println("Current Location [expected: "+ imP+"]");
		System.out.println("Current Location: "+drunkard.getCurrentLoc());
		
		System.out.println();
		System.out.println("Testing takeStep...");
		System.out.println("Drunkard starts at=(" +imP.getX()+","+imP.getY()+")"
			+ " And Step Size is " + theStepSize);
		
		for (int i=0;i<10;i++){
			
			int oldX=drunkard.getCurrentLoc().getX();							// X-Coordinate Before taking Step
			int oldY=drunkard.getCurrentLoc().getY();							// Y-Coordinate Before taking Step
			
			drunkard.takeStep();
		
			if ((drunkard.getCurrentLoc().getX()==oldX)&&
					(Math.abs((drunkard.getCurrentLoc()).getY()-oldY)==theStepSize)){
				System.out.println("took Step to ("+(drunkard.getCurrentLoc()).getX()+","
					+(drunkard.getCurrentLoc()).getY()+")"+ " SUCCEEDED");
			}
			else if((drunkard.getCurrentLoc().getY()==oldY)&&
					(Math.abs((drunkard.getCurrentLoc()).getX()-oldX)==theStepSize)){
				System.out.println("took Step to ("+(drunkard.getCurrentLoc()).getX()+","
					+(drunkard.getCurrentLoc()).getY()+")"+ " SUCCEEDED");
			}
			else System.out.println("took Step to ("+(drunkard.getCurrentLoc()).getX()+","
					+(drunkard.getCurrentLoc()).getY()+")"+ " Failed: Not a valid step");
		}
		
		System.out.println();
		
		
	}
}
