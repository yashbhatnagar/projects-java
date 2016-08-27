package drunkardprj;


import java.util.Random;

// Name: Yash Bhatnagar
// USC loginid: 6814424170
// CS 455 PA1
// Spring 2016

/**
 * Drunkard class
 * Represents a "drunkard" doing a random walk on a grid.
 * @author yash bhatnagar
*/
public class Drunkard {

    // add private instance variables here:
	private ImPoint startLoc;
	private int theStepSize;
	
	Random random = new Random(); // Instancing the Random class Object 

    /**
       Creates drunkard with given starting location and distance
       to move in a single step.
       @param startLoc starting location of drunkard
       @param theStepSize size of one step in the random walk
    */
    public Drunkard(ImPoint startLoc, int theStepSize) {
    	this.startLoc=startLoc;
    	this.theStepSize=theStepSize;
   }


    /**
       Takes a step of length step-size (see constructor) in one of
       the four compass directions.  Changes the current location of the
       drunkard.
    */
    public void takeStep() {
    	
    	int direction= 1+ random.nextInt(4);
    	if(direction==1){
    		startLoc=startLoc.translate(theStepSize, 0);
    		
    	}
    	else if(direction==2){
    		startLoc=startLoc.translate(0, theStepSize);
    		
    	}
    	else if(direction==3){
    		startLoc=startLoc.translate(-theStepSize, 0);
    		
    	}
    	else if(direction==4){
    		startLoc=startLoc.translate(0, -theStepSize);
    	}
    }


    /**
       gets the current location of the drunkard.
       @return an ImPoint object representing drunkard's current location
    */
    public ImPoint getCurrentLoc() {
    	return startLoc;  
    }

}

