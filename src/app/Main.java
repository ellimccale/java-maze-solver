package app;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import debug.Debug;
import edu.princeton.cs.algs4.StdDraw;

/**
 * Entry point for the Maze application.
 * <p>
 * Parses command-line arguments to set verbose (debug) mode, grid dimensions,
 * then configures the StdDraw canvas, generates a Maze, renders it, and
 * animates its solution.
 * <p>
 * Usage:
 * <pre>
 *   java Main.java <rows> <cols> [--debug]
 * </pre>
 * where {@code rows} and {@code cols} specify the maze dimensions. The optional
 * {@code --debug} flag enables debug mode and additional logging via the
 * {@code debug.Debug} utility.
 *
 * @author Elli Steck
 */
public class Main {

	public static final int HEIGHT = 800;

	public static void main(String[] args) {
		List<String> params = new ArrayList<>(Arrays.asList(args));

		// Optionally enable debugging
	    if (params.remove("--debug")) {
	        Debug.ON = true;
	    }

	    // Check for valid remaining args
	    if (params.size() != 2) {
	        System.err.println(
	        		"Usage: java app.Main <rows> <cols> [--debug]");
	        System.exit(1);
	    }

	    int rows, cols;

	    try {
	        rows = Integer.parseInt(params.get(0));
	        cols = Integer.parseInt(params.get(1));
	    } catch (NumberFormatException e) {
	        System.err.println("Error: rows and cols must be integers.");
	        System.exit(1);
	        return; // Unreachable, but silences the compiler
	    }

	    // Validate args range
	    if (rows < 2 || cols < 2) {
	        System.err.println("Error: rows and cols must both be ≥ 2.");
	        System.exit(1);
	    }

		int height = HEIGHT;
		int width = (int) Math.round(1.0 * height * cols / rows);

		StdDraw.enableDoubleBuffering();
		StdDraw.setCanvasSize(width, height);
		StdDraw.setXscale(0, cols + 2);
		StdDraw.setYscale(0, rows + 2);

		Maze maze = new Maze(rows, cols);

		Debug.print(maze.getGraph().toString());

		maze.draw();
		maze.solve();
	}

}
