package app;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import debug.Debug;
import edu.princeton.cs.algs4.StdDraw;

/**
 * Entry point for the Maze application.
 * <p>
 * Parses command-line arguments to set debug mode, grid dimensions, then
 * configures the StdDraw canvas, generates a Maze, renders it, and animates
 * its solution.
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

	private static int rows, cols;

	public static void main(String[] args) {
		List<String> params = new ArrayList<>(Arrays.asList(args));

		// Enables debugging
	    if (params.remove("--debug")) {
	        Debug.ON = true;
	    }

	    // Check for valid remaining args
	    if (params.size() != 2) {
			printError(
	        		"One or more parameters is missing. Please follow the usage pattern"
	        		+ "\n    java Main.java <rows> <cols> [--debug]");
	    }

	    try {
	        rows = Integer.parseInt(params.get(0));
	        cols = Integer.parseInt(params.get(1));
	    } catch (IllegalArgumentException e) {
			printError("rows and cols must both be integers ≥ 2");
	        return;
	    }

		Maze maze;

		try {
			maze = new Maze(rows, cols);
			Debug.print(maze.getGraph().toString());
		} catch (IllegalArgumentException e) {
			printError(e);
	        return;
		}

		setupGUI();

		try {
			maze.draw();
			maze.solve();
		} catch (IllegalStateException e) {
			printError(e);
	        return;
		}
	}

	/**
	 * Prints a critical error to the console and exits the program.
	 * 
	 * @param e the exception that occurred
	 */
	private static void printError(RuntimeException e) {
		System.err.println("Error: " + e);
        System.exit(1);
	}

	/**
	 * Overloaded method.
	 * 
	 * @param e a string representing the error message
	 */
	private static void printError(String e) {
		System.err.println("Error: " + e);
        System.exit(1);
	}

	/**
	 * Initializes the StdDraw canvas with double buffering enabled, and sets
	 * the canvas scale based on the number of rows and columns in the maze.
	 */
	private static void setupGUI() {
		int height = HEIGHT;
		int width = (int) Math.round(1.0 * HEIGHT * cols / rows);

		StdDraw.setTitle("Maze");
		StdDraw.enableDoubleBuffering();
		StdDraw.setCanvasSize(width, height);

		StdDraw.setXscale(0, cols + 2);
		StdDraw.setYscale(0, rows + 2);
	}

}
