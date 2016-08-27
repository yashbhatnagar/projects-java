
// Name:Yash Bhatnagar
// USC loginid:6814424170
// CS 455 PA3
// Spring 2016

import java.util.LinkedList;

/**
 * Maze class
 * 
 * Stores information about a maze and can find a path through the maze (if
 * there is one).
 * 
 * Assumptions about structure of the maze, as given in mazeData, startLoc, and
 * endLoc (parameters to constructor), and the path: -- no outer walls given in
 * mazeData -- search assumes there is a virtual border around the maze (i.e.,
 * the maze path can't go outside of the maze boundaries) -- start location for
 * a path is maze coordinate startLoc -- exit location is maze coordinate
 * exitLoc -- mazeData input is a 2D array of booleans, where true means there
 * is a wall at that location, and false means there isn't (see public FREE /
 * WALL constants below) -- in mazeData the first index indicates the row. e.g.,
 * mazeData[row][col] -- only travel in 4 compass directions (no diagonal paths)
 * -- can't travel through walls
 */

public class Maze {

	public static final boolean FREE = false;
	public static final boolean WALL = true;
	public static final int VISITED = 1;
	public static final int EXPLORED = 2;
	private MazeCoord startLoc = null;
	private MazeCoord endLoc = null;
	private boolean[][] mazeData = null;
	private LinkedList<MazeCoord> path = null;

	/**
	 * Constructs a maze.
	 * 
	 * @param mazeData
	 *            the maze to search. See general Maze comments for what goes in
	 *            this array.
	 * @param startLoc
	 *            the location in maze to start the search (not necessarily on
	 *            an edge)
	 * @param endLoc
	 *            the "exit" location of the maze (not necessarily on an edge)
	 *            PRE: 0 <= startLoc.getRow() < mazeData.length and 0 <=
	 *            startLoc.getCol() < mazeData[0].length and 0 <=
	 *            endLoc.getRow() < mazeData.length and 0 <= endLoc.getCol() <
	 *            mazeData[0].length
	 * 
	 */
	public Maze(boolean[][] mazeData, MazeCoord startLoc, MazeCoord endLoc) {
		this.startLoc = startLoc;
		this.endLoc = endLoc;
		this.mazeData = mazeData;
		path = new LinkedList<>(); // Initialized with empty LinkedList object
	}

	/**
	 * Returns the number of rows in the maze
	 * 
	 * @return number of rows
	 */
	public int numRows() {
		return mazeData.length;
	}

	/**
	 * Returns the number of columns in the maze
	 * 
	 * @return number of columns
	 */
	public int numCols() {
		return mazeData[0].length;
	}

	/**
	 * Returns true iff there is a wall at this location
	 * 
	 * @param loc
	 *            the location in maze coordinates
	 * @return whether there is a wall here PRE: 0 <= loc.getRow() < numRows()
	 *         and 0 <= loc.getCol() < numCols()
	 */
	public boolean hasWallAt(MazeCoord loc) {
		if (mazeData[loc.getRow()][loc.getCol()] == WALL) {
			return true;
		}
		return false;
	}

	/**
	 * Returns the entry location of this maze.
	 */
	public MazeCoord getEntryLoc() {
		return startLoc;
	}

	/**
	 * Returns the exit location of this maze.
	 */
	public MazeCoord getExitLoc() {
		return endLoc;
	}

	/**
	 * Returns the path through the maze. First element is starting location,
	 * and last element is exit location. If there was not path, or if this is
	 * called before search, returns empty list.
	 * 
	 * @return the maze path
	 */
	public LinkedList<MazeCoord> getPath() {

		return path;

	}

	/**
	 * Find a path through the maze if there is one. Client can access the path
	 * found via getPath method.
	 * 
	 * @return whether path was found.
	 */
	public boolean search() {
		if (hasWallAt(startLoc) || hasWallAt(endLoc)) {
			return false;
		}
		MazeCoord currentLoc = new MazeCoord(0, 0);
		currentLoc = startLoc;
		int[][] visited = new int[mazeData.length][mazeData[0].length];
		boolean pathFound = searchR(currentLoc, visited);
		return pathFound;
	}

	/**
	 * Recursively searches the maze for free paths called by search
	 * 
	 * @param currentLoc
	 *            object of type MazeCoord the present state of currentLoc is
	 *            the location in maze coordinates
	 * @param visited
	 *            array to change state of locations. VISITED implies
	 *            location(row,column) has been encountered in the search at
	 *            least once. EXPLORED implies location(row,column) all the
	 *            neighboring locations have been Visited.
	 * @return whether path exists or not through recursive calls 
	 */
	private boolean searchR(MazeCoord currentLoc, int[][] visited) {

		if (currentLoc.equals(endLoc)) {
			path.addFirst(currentLoc);
			visited[currentLoc.getRow()][currentLoc.getCol()] = EXPLORED;
			return true;
		}
		return findPath(currentLoc, visited);
	}

	/**
	 * called by search
	 * 
	 * @param currentLoc
	 *            object of type MazeCoord the present state of currentLoc is
	 *            the location in maze coordinates
	 * @param visited
	 *            array to change state of locations. VISITED implies
	 *            location(row,column) has been encountered in the search at
	 *            least once. EXPLORED implies for the location(row,column) all the
	 *            neighboring locations have been Visited.
	 * @return whether path exists or not through recursive calls 
	 */
	private boolean findPath(MazeCoord currentLoc, int[][] visited) {
		int row = currentLoc.getRow();
		int col = currentLoc.getCol();
		if (currentLoc == startLoc) {
			visited[row][col] = VISITED;
			path.removeAll(path);
		}
		if (visited[row][col] != EXPLORED) { // Keep track of fully explored
												// locations using EXPLORED.
			path.add(currentLoc);
			visited[row][col] = VISITED; // Keep track of visited locations in
											// maze using VISITED.
			boolean done = false;
			while (!done) {
				if (row + 1 < mazeData.length && mazeData[row + 1][col] == FREE && visited[row + 1][col] != VISITED
						&& visited[row + 1][col] != EXPLORED) {
					row++;
					currentLoc = new MazeCoord(row, col); // change current
															// location.
					visited[row][col] = VISITED;
					return searchR(currentLoc, visited);
				}
				if (row - 1 >= 0 && (mazeData[row - 1][col] == FREE)
						&& (visited[row - 1][col] != VISITED && visited[row - 1][col] != EXPLORED)) {
					row--;
					currentLoc = new MazeCoord(row, col);
					visited[row][col] = VISITED;
					return searchR(currentLoc, visited);
				}
				if (col + 1 < mazeData[0].length && mazeData[row][col + 1] == FREE && visited[row][col + 1] != VISITED
						&& visited[row][col + 1] != EXPLORED) {
					col++;
					currentLoc = new MazeCoord(row, col);
					visited[row][col] = VISITED;
					return searchR(currentLoc, visited);
				}
				if (col - 1 >= 0 && mazeData[row][col - 1] == FREE && visited[row][col - 1] != VISITED
						&& visited[row][col - 1] != EXPLORED) {
					col--;
					currentLoc = new MazeCoord(row, col);
					visited[row][col] = VISITED;
					return searchR(currentLoc, visited);
				}
				done = true;
				visited[row][col] = EXPLORED;
			}
		}
		if (visited[row][col] == EXPLORED && path.size() > 2) { // If a deadlock
																// occurs where
																// there is no
																// path from
																// current
																// location.
			path.removeLast(); // need to retract path
			return searchR(path.getLast(), visited); // search for path from one
														// prior location.
		}
		path.removeAll(path); // If path not found, displays no path.
		return false;
	}
}
