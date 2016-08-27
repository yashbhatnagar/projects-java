// Name:Yash Bhatnagar
// USC loginid:6814424170
// CS 455 PA3
// Spring 2016

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import javax.swing.JFrame;

/**
 * MazeViewer class
 * 
 * Program to read in and display a maze and a path through the maze. At user
 * command displays a path through the maze if there is one.
 * 
 * How to call it from the command line:
 * 
 * java MazeViewer mazeFile
 * 
 * where mazeFile is a text file of the maze. The format is the number of rows
 * and number of columns, followed by one line per row, followed by the start
 * location, and ending with the exit location. Each maze location is either a
 * wall (1) or free (0). Here is an example of contents of a file for a 3x4
 * maze, with start location as the top left, and exit location as the bottom
 * right (we count locations from 0, similar to Java arrays):
 * 
 * 3 4 0111 0000 1110 0 0 2 3
 * 
 */

public class MazeViewer {

	private static final char WALL_CHAR = '1';
	private static final char FREE_CHAR = '0';

	public static void main(String[] args) {

		String fileName = "";

		try {

			if (args.length < 1) {
				System.out.println("ERROR: missing file name command line argument");
			} else {
				fileName = args[0];

				JFrame frame = readMazeFile(fileName);

				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

				frame.setVisible(true);
			}

		} catch (FileNotFoundException exc) {
			System.out.println("File not found: " + fileName);
		} catch (IOException exc) {
			exc.printStackTrace();
		}
	}

	/**
	 * readMazeFile reads in maze from the file whose name is given and returns
	 * a MazeFrame created from it.
	 * 
	 * @param fileName
	 *            the name of a file to read from (file format shown in class
	 *            comments, above)
	 * @returns a MazeFrame containing the data from the file.
	 * 
	 * @throws FileNotFoundException
	 *             if there's no such file (subclass of IOException)
	 * @throws IOException
	 *             (hook given in case you want to do more error-checking. that
	 *             would also involve changing main to catch other exceptions)
	 */
	private static MazeFrame readMazeFile(String fileName) throws IOException {
		File inFile = new File(fileName);
		Scanner in = new Scanner(inFile);
		try {
			MazeFrame startFrame = readData(in);
			return startFrame;
		} finally {
			in.close();
		}
		
	}
	
	/**
	 * readData reads the mazeData from the file whose name is given and returns
	 * a MazeFrame created from it.
	 * 
	 * @param in	
	 *            Scanner Object used to scan the file with the given filename.
	 * @returns a MazeFrame containing the data from the file.
	 * 
	 * @throws FileNotFoundException
	 *             if there's no such file (subclass of IOException)
	 * @throws IOException
	 *             if data for rows and column values of mazes is neither a WALL_CHAR OR a FREE_CHAR.
	 * @return MazeFrame
	 * 			   for given mazeData, entryLoc, exitLoc read from the input file.
	 */
	private static MazeFrame readData(Scanner in) throws IOException {
		boolean[][] mazeData = new boolean[in.nextInt()][in.nextInt()];	//takes first two data for maze size.
		for (int row = 0; row < mazeData.length; row++) {
			String rowdata=in.next();
			for (int col = 0; col < mazeData[0].length; col++) {
				if (rowdata.charAt(col) == WALL_CHAR) {
					mazeData[row][col] = true;
				}
				else if(rowdata.charAt(col) == FREE_CHAR) {
					mazeData[row][col] = false;
				}
			    else throw new IOException();
			}
		}
		MazeCoord entryLoc = new MazeCoord(in.nextInt(), in.nextInt());	//Creates entry location as per the format.
		MazeCoord exitLoc = new MazeCoord(in.nextInt(), in.nextInt());	//Creates exit location as per the format.
		return new MazeFrame(mazeData, entryLoc, exitLoc);
	}

}
