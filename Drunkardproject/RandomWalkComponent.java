package drunkardprj;


import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import javax.swing.JComponent;

//Name: Yash Bhatnagar
//USC loginid: 6814424170
//CS 455 PA1
//Spring 2016

/**
 * RandomWalkComponent class  extends JComponent class 
 * Mostly draws the random path that the drunkard traverse over the JFrame with the help of JComponent class.
 * @author yash bhatnagar
 */
public class RandomWalkComponent extends JComponent{

	private static final long serialVersionUID = 1L;
	private static final int STEP_SIZE = 5;														 // Constant for the individual Step Size.
	private ImPoint imP;
	private int numberOfSteps;
	
	/**
	 * Creates a RandomWalkComponent Object with given ImPoint Object
	 * and total number of Steps the Drunkard has to take.
	 * Number Of Steps are declared in the RandomWalkViewer Class by the End-User.
	 */
	public RandomWalkComponent(ImPoint imP, int numberOfSteps){
		this.imP=imP;
		this.numberOfSteps=numberOfSteps;
	}
	
	/**
	 * Overrides the paintComponent method of JComponent class
	 * to draw the overall path traversed by the Drunkard as lines originating from 
	 * the starting location(before taking the step) to the end location(after taking one steps). 
	 * @param graphics object of Graphics class.
	 */
	public void paintComponent(Graphics graphics){
		Graphics2D graphics2D = (Graphics2D) graphics;											// Instancing the Graphics2D object for doing 2D drawing.
		int count=0;																			// Keeping count of number of steps taken, Initialized to zero.
		
		while(count<numberOfSteps){
		Drunkard drunkboy=new Drunkard(imP,STEP_SIZE);											// Instancing an Object of Drunkard class.
		Point2D.Double from = 
				new Point2D.Double(drunkboy.getCurrentLoc().getX(),drunkboy.getCurrentLoc().getY());
		
		drunkboy.takeStep();																	// Call to the takeStep() method using Drunkard class object.	
		
		Point2D.Double to = 
				new Point2D.Double(drunkboy.getCurrentLoc().getX(),drunkboy.getCurrentLoc().getY());
		
		Line2D.Double segment = new Line2D.Double(from, to);
		
		graphics2D.draw(segment);																// Call to draw method of Graphics2D class for drawing the segment
		
		imP=drunkboy.getCurrentLoc();															// Changing the Start location for next iteration 
																								// to be the end location for this one.
		
		count+=1;
	 }
	 }
	}
