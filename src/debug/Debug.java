package debug;

import java.util.Locale;

import edu.princeton.cs.algs4.StdOut;

/**
 * This class is used as a utility to print debug statements to the console
 * depending on whether or not the DEBUG flag is provided as an argument to the
 * program's entrypoint in app.Main.
 * 
 * @author Elli Steck
 */
public class Debug {

	public static boolean ON = false;

	// Don't instantiate
	private Debug() {
	}

	public static void print(Object x) {
		if (ON) StdOut.print(x);
	}

	public static void println() {
		if (ON) StdOut.println();
	}

	public static void println(Object x) {
		if (ON) StdOut.println(x);
	}

	public static void printf(String fmt, Object... args) {
		if (ON) StdOut.printf(Locale.US, fmt, args);
	}

}
