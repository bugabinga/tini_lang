package main;

interface Main {

	static int EXIT_INVALID_INPUT = 64;
	static int EXIT_INVALID_CODE = 65;

	static void main(final String[] command_line_arguments)
			throws Throwable {
		dbg("START!");

		if (command_line_arguments.length > 1) {
			println_error("Usage: jtini <path to script>");
			throw exit(EXIT_INVALID_INPUT);
		} else if (command_line_arguments.length == 1) {
			final var had_error = run(command_line_arguments[0]);
			if (had_error) {
				throw exit(EXIT_INVALID_CODE);
			}
		} else {
			prompt();
		}

		dbg("END!");
	}

	static boolean run(final String script_path)
			throws java.io.IOException {
		final var path = java.nio.file.Path.of(script_path);
		final var script = java.nio.file.Files.readString(path, java.nio.charset.StandardCharsets.UTF_8);
		return execute(script);
	}

	static boolean execute(final String source_code) {
		final var tokenizer = new Tokenizer(source_code);
		final var tokens = tokenizer.scanTokens();

		tokens
				.stream()
				.map(Token::toString)
				.forEach(Main::dbg);

		return true;
	}

	static void prompt()
			throws java.io.IOException {
		final var input = new java.io.InputStreamReader(System.in);
		final var buffered_input = new java.io.BufferedReader(input);

		var alive = true;
		while (alive) {
			print(" ");
			final var line = buffered_input.readLine();
			if (line == null) {
				alive = false;
			} else {
				execute(line);
				// NOTE: ignoring the error state
			}
		}
	}

	static Throwable exit(final int status) {
		dbg("EXIT");
		System.exit(status);
		// dead code for fake control flow
		return new Throwable();
	}

	static void dbg(final String message, final Object... arguments) {
		if (arguments.length > 0)
			System.out.println("[DEBUG] " + java.text.MessageFormat.format(message, arguments));
		else
			System.out.println("[DEBUG] " + message);
	}

	static void println(final String message, final Object... arguments) {
		if (arguments.length > 0)
			System.out.println(java.text.MessageFormat.format(message, arguments));
		else
			System.out.println(message);
	}

	static void print(final String message, final Object... arguments) {
		if (arguments.length > 0)
			System.out.print(java.text.MessageFormat.format(message, arguments));
		else
			System.out.print(message);
	}

	static void println_error(final String message, final Object... arguments) {
		if (arguments.length > 0)
			System.err.println(java.text.MessageFormat.format(message, arguments));
		else
			System.err.println(message);
	}

	static void report(final int line,
			final String message,
			final Object... arguments) {
		report(line, "chunk", message, arguments);
	}

	static void report(final int line,
			final String location,
			final String message,
			final Object... arguments) {

		final var message_with_arguments = java.text.MessageFormat.format(message, arguments);

		println_error("[TROUBLE] in {1} on line {0}: {2}", line, location, message_with_arguments);
	}

}
