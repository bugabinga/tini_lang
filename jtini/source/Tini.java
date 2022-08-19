package net.bugabinga.tini;

import static java.text.MessageFormat.format;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.util.List;

public class Tini {

	private static final int NOT_ENOUGH_COMMAND_LINE_ARGUMENTS = 64;

	public static void main(final String[] command_line_arguments) throws Throwable {
		if (command_line_arguments.length > 1) {
			usage();
			throw exit(NOT_ENOUGH_COMMAND_LINE_ARGUMENTS);
		}
		else if (command_line_arguments.length == 1){
			final var script = Path.of(command_line_arguments[0]);
			interpret(script);
		}
		else {
			read_eval_print_loop();
		}
	}

	private static final void interpret(final Path script_path){
		//TODO
	}

	private static final void read_eval_print_loop(){
		//TODO
	}

	private static final void usage(){
		print("Interpret a Tini language script. Java Edition.");
		print("Usage: {0} {1}", bold("jtini"), italic("[script]"));
	}

	private static final Throwable fail(final int code, final String message, final Object... arguments) {
		print(System.err, message, arguments);
		return fail(code);
	}

	private static final Throwable fail(final int code){
		System.exit(code);
		return new Throwable("unreachable");
	}

	private static final void print(final String message, Object... arguments){
		print(format(System.out, message, arguments));
	}

	private static final void print(PrintStream output, final String message, final Object... arguments){
		output.println(format(message, arguments));
	}

	private static final String bold(final String text){
		return "\033[1m" + text + "\033[0m";
	}

	private static final String italic(final String text){
		return "\033[3m" + text + "\033[0m";
	}

	private static final String underline(final String text){
		return "\033[4m" + text + "\033[0m";
	}

	private static final String inverse(final String text){
		return "\033[7m" + text + "\033[0m";
	}

	private static final String red(final String text){
		return "\033[31m" + text + "\033[0m";
	}

	private static final String green(final String text){
		return "\033[32m" + text + "\033[0m";
	}
}
