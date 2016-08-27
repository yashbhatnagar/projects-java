
// Name:Yash Bhatnagar
// USC loginid:6814424170
// CS 455 PA3
// Spring 2016

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JComponent;

/**
 * MazeComponent class
 * 
 * A component that displays the maze and path through it if one has been found.
 */
@SuppressWarnings("serial")
public class MazeComponent extends JComponent {

	private Maze maze;

	private static final int START_X = 10; // where to start drawing maze in
											// frame
	private static final int START_Y = 10;
	private static final int BOX_WIDTH = 20; // width and height of one maze
												// unit
	private static final int BOX_HEIGHT = 20;
	private static final int INSET = 2; // how much smaller on each side to make
										// entry/exit inner box
	private static final int FILLRECTFACTOR = 1; // +FILLRECTFACTOR is added to
													// fill the box completely
													// using fillRect().

	/**
	 * Constructs the component.
	 * 
	 * @param maze
	 *            the maze to display
	 */
	public MazeComponent(Maze maze) {
		this.maze = maze;
	}

	/**
	 * Draws the current state of maze including the path through it if one has
	 * been found.
	 * 
	 * @param g
	 *            the graphics context
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		int x = START_X;
		int y = START_Y;
		for (int row = 0; row < maze.numRows(); row++) {
			for (int col = 0; col < maze.numCols(); col++) {
				if (maze.hasWallAt(new MazeCoord(row, col))) {
					g.setColor(Color.BLACK);
				} else
					g.setColor(Color.WHITE);
				g.fillRect(x, y, BOX_WIDTH + FILLRECTFACTOR, BOX_HEIGHT + FILLRECTFACTOR);
				if (new MazeCoord(row, col).equals(maze.getEntryLoc())) {
					g.setColor(Color.YELLOW);
					g.fillRect(x + INSET, y + INSET, BOX_WIDTH - INSET * INSET + FILLRECTFACTOR,
							BOX_HEIGHT - INSET * INSET + FILLRECTFACTOR);

				}
				if (new MazeCoord(row, col).equals(maze.getExitLoc())) {
					g.setColor(Color.GREEN);
					g.fillRect(x + INSET, y + INSET, BOX_WIDTH - INSET * INSET + FILLRECTFACTOR,
							BOX_HEIGHT - INSET * INSET + FILLRECTFACTOR);
				}
				x = x + BOX_WIDTH;
			}
			x = START_X;
			y = y + BOX_HEIGHT;
		}
		drawPath(g);
		g.setColor(Color.BLACK);
		g.drawRect(START_X, START_Y, maze.numCols() * BOX_WIDTH, maze.numRows() * BOX_HEIGHT); // Boundary
																								// Box.
	}

	/**
	 * Draws the path through the maze if one has been found.
	 * 
	 * @param g
	 *            the graphics context
	 */
	private void drawPath(Graphics g) {
		int pathSize = maze.getPath().size();
		while (pathSize >= 1) {
			MazeCoord fromLoc = maze.getPath().getFirst();
			maze.getPath().removeFirst();
			MazeCoord toLoc=new MazeCoord(0, 0);
			if(!maze.getPath().isEmpty()){
			 toLoc = maze.getPath().getFirst();
			}
			else {toLoc = fromLoc;}
			g.setColor(Color.BLUE);
			g.drawLine((fromLoc.getCol() * BOX_WIDTH) + (START_X + BOX_WIDTH / 2),
					(fromLoc.getRow() * BOX_HEIGHT) + (START_Y + BOX_HEIGHT / 2),
					(toLoc.getCol() * BOX_WIDTH) + (START_X + BOX_WIDTH / 2),
					(toLoc.getRow() * BOX_HEIGHT) + (START_Y + BOX_HEIGHT / 2));	// Sizing the coordinates to the Maze size.
			pathSize = pathSize - 1;
		}
	}
}
