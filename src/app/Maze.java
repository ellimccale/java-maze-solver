package app;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import edu.princeton.cs.algs4.Graph;

/**
 * Represents a perfect maze carved on a 2D grid and stored as an undirected
 * graph.
 * <p>
 * Each cell in the maze corresponds to a vertex in the graph. A randomized
 * depth-first search algorithm (recursive backtracker) is used to generate a
 * cycle-free spanning tree that guarantees a path between any two cells.
 * <p>
 * Maze drawing and solving are delegated to MazeView and MazeSolver,
 * respectively.
 * 
 * @author Elli Steck
 */
public class Maze {

	private final int rows;
	private final int cols;

	private final boolean[][] visited;

	private final Graph graph;

	private final static int[][] DELTAS = new int[][] {
	//  {  x,  y },
		{  0,  1 }, // N
		{  1,  0 }, // E
		{  0, -1 }, // S
		{ -1,  0 }, // W
	};

	/**
	 * Constructs and generates a new maze of size {@code rows} x {@code cols}.
	 * Initializes internal structures and carves a perfect maze via recursion.
	 *
	 * @param rows the number of rows in the maze grid
	 * @param cols the number of columns in the maze grid
	 * @throws IllegalArgumentException if the values provided for {@code rows}
	 * 									or {@code cols} are less than 2.
	 */
	public Maze(int rows, int cols) throws IllegalArgumentException {
		if (rows < 2 || cols < 2)
			throw new IllegalArgumentException("rows and cols must both be ≥ 2");

		this.rows = rows;
		this.cols = cols;

		this.visited = new boolean[rows][cols];
		this.graph = new Graph(rows * cols);

		generate(0, 0);
	}

	/**
     * Recursively visits cells in random order to carve passages and add edges.
     * Implements the randomized depth-first search (recursive backtracker).
     *
     * @param row the current cell's row index
     * @param col the current cell's column index
     */
	private void generate(int row, int col) {
		visited[row][col] = true;
		int thisVertex = vertexOf(row, col);

		/*
		 * Clone a new list from the {@code DELTAS} array and shuffle it,
		 * creating a random order of directions to visit. This approach is
		 * responsible for generating unique maze designs.
		 * 
		 * NOTE: It's necessary to clone a NEW list, since it appears that
		 *       {@code Arrays.asList()} may mutate the original array.
		 *       Otherwise, the maze is generated with isolated vertices (cells
		 *       with walls on all four sides and no connected edges).
		 */

		List<int[]> directions = new ArrayList<>(Arrays.asList(DELTAS));
		Collections.shuffle(directions);

		// For each (now randomized) direction...
		for (int[] delta : directions) {

			/*
			 * Adds a delta (difference) value to the current row and column.
			 * The delta may be either -1, 0, or 1, which will correspond to
			 * the vertex directly adjacent to the current vertex, either N,
			 * S, E, or W.
			 * 
			 * For example, to visit the vertex north of the current vertex,
			 * shift up from the current row by 1 (delta 1) but stay in the same
			 * column (delta 0). The value of that vertex will ultimately be
			 * equal to that of the current vertex + the number of columns.
			 */

			int nextRow = delta[1] + row;
			int nextCol = delta[0] + col;

			/*
			 * Check if the calculated adjacent vertex is in bounds and that the
			 * program has not yet visited it. The {@code visited} check also
			 * serves as the recursion's base case.
			 */

			if (isInBounds(nextRow, nextCol) &&
				  !visited[nextRow][nextCol]) {
				graph.addEdge(thisVertex, vertexOf(nextRow, nextCol));
				generate(nextRow, nextCol);
			}
		}
	}

	/**
	 * Checks whether the specified cell coordinates lie within the maze bounds.
	 * Without verifying the boundaries of the maze, it's possible that the
	 * adjacent vertex is "outside" of the maze and shouldn't exist.
	 *
	 * @param row the row index to check
	 * @param col the column index to check
	 * @return {@code true} if (row, col) is a valid cell in the grid;
	 * 		   {@code false} otherwise
	 */
	private boolean isInBounds(int nextRow, int nextCol) {
		return 0 <= nextRow && nextRow < rows &&
			   0 <= nextCol && nextCol < cols;
	}

	/**
     * Delegates drawing of the maze walls to the MazeView class.
     */
    public void draw() {
        new MazeView(this).draw();
    }

    /**
     * Delegates solving (pathfinding) of the maze to the MazeSolver class.
     */
    public void solve() {
        new MazeSolver(this).solve();
    }

    /**
     * Converts grid coordinates to the corresponding graph vertex index.
     *
     * @param row the cell's row index
     * @param col the cell's column index
     * @return the integer vertex ID representing (row, col)
     */
    public int vertexOf(int row, int col) {
        return row * cols + col;
    }

    /**
     * Computes the row index of a given vertex in the grid.
     *
     * @param vertex the graph vertex ID
     * @return the row index corresponding to this vertex
     */
    public int rowOf(int vertex) {
        return vertex / cols;
    }

    /**
     * Computes the column index of a given vertex in the grid.
     *
     * @param vertex the graph vertex ID
     * @return the column index corresponding to this vertex
     */
    public int colOf(int vertex) {
        return vertex % cols;
    }

    /**
     * Returns the number of rows in the maze.
     *
     * @return the row count
     */
    public int getRows() {
        return rows;
    }

    /**
     * Returns the number of columns in the maze.
     *
     * @return the column count
     */
    public int getCols() {
        return cols;
    }

    /**
     * Returns the underlying graph representing maze connectivity.
     *
     * @return the Graph of maze cells and passages
     */
    public Graph getGraph() {
        return graph;
    }

}
