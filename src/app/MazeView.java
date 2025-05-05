package app;

import debug.Debug;
import edu.princeton.cs.algs4.Graph;
import edu.princeton.cs.algs4.StdDraw;

/**
 * Renders the walls of a {@link Maze} onto an StdDraw canvas based on the
 * underlying graph structure. For each grid cell (vertex), MazeView will draw
 * the four surrounding walls, except where the graph has an edge.
 *
 * @author Elli Steck
 */
public class MazeView {

	private final static double OFFSET = 1;

	private final Maze maze;
	private final Graph graph;

	/**
	 * Constructs a new MazeView for the given Maze.
	 *
	 * @param maze the Maze instance to render
	 */
	public MazeView(Maze maze) {
		this.maze = maze;
		this.graph = maze.getGraph();
	}

	/**
	 * Draws all walls of the maze onto the StdDraw canvas. For each cell, checks
	 * its four neighbor directions and draws a wall line segment whenever there is
	 * no graph edge in that direction.
	 * 
	 * @throws IllegalStateException if the number of vertices in the graph is not
	 *                               equal to the number of cells in the grid
	 */
	public void draw() throws IllegalStateException {
		int rows = maze.getRows();
		int cols = maze.getCols();

		if (graph.V() != rows * cols)
			throw new IllegalStateException(
					"The number of vertices in this graph is not equal to the number of cells in the grid.");

		for (int vertex = 0; vertex < graph.V(); vertex++) {
			// Shift x and y by +1 to offset the canvas scale
			double x = maze.colOf(vertex) + OFFSET;
			double y = maze.rowOf(vertex) + OFFSET;

			/*
			 * The StdDraw canvas is built on Cartestian coordinates. (0, 0) is bottom-left,
			 * so vertex 0 is also bottom-left. The final vertex will be at the top-right
			 * corner of the canvas.
			 */

			if (Debug.ON) {
				StdDraw.setPenColor(StdDraw.CYAN);
				drawNWall(x, y);
				drawEWall(x, y);
				drawSWall(x, y);
				drawWWall(x, y);
			}

			StdDraw.setPenColor(StdDraw.BLACK);

			if (wall(vertex, vertex + cols))
				drawNWall(x, y);
			if (wall(vertex, vertex + 1))
				drawEWall(x, y);
			if (wall(vertex, vertex - cols))
				drawSWall(x, y);
			if (wall(vertex, vertex - 1))
				drawWWall(x, y);
		}

		StdDraw.show();
	}

	/**
	 * Returns {@code true} if there is a wall between vertices v and w (i.e., no
	 * edge in the graph), {@code false} if an edge exists (passage).
	 *
	 * @param v a vertex ID in the maze graph
	 * @param w the neighboring vertex ID to check against
	 * @return {@code true} if no graph edge exists (draw wall); {@code false}
	 *         otherwise
	 */
	private boolean wall(int v, int w) {
		for (int adj : graph.adj(v)) {
			if (adj == w)
				return false;
		}
		return true;
	}

	/**
	 * Draws the northern wall of a cell, given x and y coordinates.
	 * 
	 * @param x the horizontal position
	 * @param y the vertical position
	 */
	private static void drawNWall(double x, double y) {
		StdDraw.line(x, y + 1, x + 1, y + 1);
	}

	/**
	 * Draws the eastern wall of a cell, given x and y coordinates.
	 * 
	 * @param x the horizontal position
	 * @param y the vertical position
	 */
	private static void drawEWall(double x, double y) {
		StdDraw.line(x + 1, y + 1, x + 1, y);
	}

	/**
	 * Draws the southern wall of a cell, given x and y coordinates.
	 * 
	 * @param x the horizontal position
	 * @param y the vertical position
	 */
	private static void drawSWall(double x, double y) {
		StdDraw.line(x + 1, y, x, y);
	}

	/**
	 * Draws the western wall of a cell, given x and y coordinates.
	 * 
	 * @param x the horizontal position
	 * @param y the vertical position
	 */
	private static void drawWWall(double x, double y) {
		StdDraw.line(x, y, x, y + 1);
	}

}
