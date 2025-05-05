package app;

import debug.Debug;
import edu.princeton.cs.algs4.BreadthFirstPaths;
import edu.princeton.cs.algs4.Graph;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdRandom;

/**
 * Performs a breadth-first search on the graph to find and animate the shortest
 * path from the starting cell to ending cell.
 * <p>
 * The solver picks the start vertex at (0, 0) and a random goal vertex in the
 * second half of the grid. It then uses
 * {@link edu.princeton.cs.algs4.BreadthFirstPaths} to compute the shortest path
 * (by number of edges) and draws the solution on the StdDraw canvas.
 *
 * @author Elli Steck
 */
public class MazeSolver {

	private final static double OFFSET = 1.5;

	private final Maze maze;
	private final Graph graph;

	private final double pointRadius;
	private final double pathsRadius;

	/**
	 * Constructs a new solver for the given Maze.
	 *
	 * @param maze the {@link Maze} instance containing the graph
	 */
	public MazeSolver(Maze maze) {
		this.maze = maze;
		this.graph = maze.getGraph();

		pointRadius = Main.HEIGHT / maze.getCols() * 0.00075;
		pathsRadius = Main.HEIGHT / maze.getCols() * 0.0002;
	}

	/**
	 * Runs the breadth-first search solver:
	 * <ol>
	 * <li>Chooses the start vertex at 0 (0, 0).
	 * <li>Chooses a random destination vertex in the second half of the grid.
	 * <li>Instantiates {@link BreadthFirstPaths} on the maze graph.
	 * <li>If a path exists, iterates over it, drawing a line between successive
	 * cell centers and pausing between steps for animation.
	 * </ol>
	 */
	public void solve() {
		int rows = maze.getRows();
		int cols = maze.getCols();

		int s = 0;
		int c = (rows * cols) / 2;
		int e = StdRandom.uniformInt(c, rows * cols);

		// Debug logs
		Debug.printf("%nStart vertex:  %d (%d, %d)", s, maze.colOf(s), maze.rowOf(s));
		Debug.printf("%nCenter vertex: %d (%d, %d)", c, maze.colOf(c), maze.rowOf(c));
		Debug.printf("%nEnd vertex:    %d (%d, %d)", e, maze.colOf(e), maze.rowOf(e));

		drawEndpoint(s, e);
		findSolution(s, e);
	}

	/**
	 * Draws endpoints for the start and end vertices on the canvas.
	 *
	 * @param s the starting vertex ID
	 * @param e the ending vertex ID
	 */
	private void drawEndpoint(int s, int e) {
		StdDraw.setPenRadius(pointRadius);
		StdDraw.setPenColor(StdDraw.BLUE);

		StdDraw.point(maze.colOf(s) + OFFSET, maze.rowOf(s) + OFFSET); // start
		StdDraw.point(maze.colOf(e) + OFFSET, maze.rowOf(e) + OFFSET); // end
	}

	/**
	 * Computes breadth-first paths from the start vertex and animates the shortest
	 * path to the end vertex.
	 *
	 * @param s the starting vertex ID
	 * @param e the ending vertex ID
	 * @throws IllegalStateException if there is no available path to the chosen end
	 *                               vertex
	 */
	private void findSolution(int s, int e) throws IllegalStateException {
		BreadthFirstPaths bfp = new BreadthFirstPaths(graph, s);

		if (!bfp.hasPathTo(e))
			throw new IllegalStateException("No path to " + e + " exists in the graph.");

		Debug.printf("%n%nPath from %d to %d:", s, e);
		Debug.printf("%n%s", bfp.pathTo(e));

		StdDraw.setPenRadius(pathsRadius);
		StdDraw.setPenColor(StdDraw.RED);

		/*
		 * Shift x and y by +1 to offset the canvas scale, and +0.5 to center the
		 * solution path within current cell.
		 */

		double x0 = OFFSET, y0 = OFFSET;

		for (int vertex : bfp.pathTo(e)) {
			double x1 = maze.colOf(vertex) + OFFSET;
			double y1 = maze.rowOf(vertex) + OFFSET;

			StdDraw.line(x0, y0, x1, y1);
			StdDraw.show();
			StdDraw.pause(250);

			x0 = x1;
			y0 = y1;
		}
	}

}
