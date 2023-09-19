package main;

import static main.Token.Type.*;
import static main.Main.report;

class Tokenizer {

	private final String source_code;
	private final java.util.List<Token> tokens;

	private int start = 0;
	private int current = 0;
	private int line = 1;

	Tokenizer(final String source_code) {
		this.source_code = source_code;
		tokens = new java.util.ArrayList<>();
	}

	public java.util.List<Token> scanTokens() {
		while (!isEndOfFile()) {
			final var token = scanNextToken();
			if (token != null) {
				tokens.add(token);
			}
		}

		tokens.add(new Token(END_OF_FILE, null, null, line));
		return tokens;
	}

	private Token token(final Token.Type type, final Object literal) {
		final var lexeme = source_code.substring(start, current);
		return new Token(type, lexeme, literal, line);
	}

	private Token token(final Token.Type type) {
		return token(type, null);
	}

	private Token scanNextToken() {
		start = current;
		final var character = advance();
		final Token token = switch (character) {
			case '(' -> token(LEFT_PAREN);
			case ')' -> token(RIGHT_PAREN);
			case '{' -> token(LEFT_BRACE);
			case '}' -> token(RIGHT_BRACE);
			case ',' -> token(COMMA);
			case '.' -> token(DOT);
			case '-' -> token(MINUS);
			case '+' -> token(PLUS);
			case ';' -> token(SEMICOLON);
			case '*' -> token(STAR);
			case '!' -> token(advanceIf('=') ? BANG_EQUAL : BANG);
			case '=' -> token(advanceIf('=') ? EQUAL_EQUAL : EQUAL);
			case '<' -> token(advanceIf('=') ? LESS_EQUAL : LESS);
			case '>' -> token(advanceIf('=') ? GREATER_EQUAL : GREATER);
			case '/' -> {
				if (advanceIf('/')) {
					while (peek() != '\n' && !isEndOfFile()) {
						advance();
					}
					yield null;
				} else {
					yield token(SLASH);
				}
			}
			case ' ', '\r', '\t' -> null;
			case '\n' -> {
				line = line + 1;
				yield null;
			}
			case '"' -> parseString();
			case '1', '2', '3', '4', '5', '6', '7', '8', '9', '0' -> parseNumber();
			case
					'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n',
					'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
					'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N',
					'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
					'_' ->
				parseIdentifier();
			default -> {
				report(line, "unexpected character: {0}", character);
				yield null;
			}
		};

		return token;
	}

	private Token parseIdentifier() {
		while ((peek() >= 'a' && peek() <= 'z')
				|| (peek() >= 'A' && peek() <= 'Z')
				|| (peek() >= '0' && peek() <= '9')
				|| peek() == '_') {
			advance();
		}

		final var symbol = source_code.substring(start, current);
		final var type = switch (symbol) {
			case "and" -> AND;
			case "class" -> CLASS;
			case "else" -> ELSE;
			case "false" -> FALSE;
			case "for" -> FOR;
			case "fun" -> FUN;
			case "if" -> IF;
			case "nil" -> NIL;
			case "or" -> OR;
			case "print" -> PRINT;
			case "return" -> RETURN;
			case "super" -> SUPER;
			case "this" -> THIS;
			case "true" -> TRUE;
			case "var" -> VAR;
			case "while" -> WHILE;
			default -> IDENTIFIER;
		};
		return token(type);
	}

	private Token parseNumber() {
		final var number_delimiter = '.';
		var number_delimiter_parsed = false;
		while (peek() == '1' ||
				peek() == '2' ||
				peek() == '3' ||
				peek() == '4' ||
				peek() == '5' ||
				peek() == '6' ||
				peek() == '7' ||
				peek() == '8' ||
				peek() == '9' ||
				peek() == '0' ||
				peek() == number_delimiter) {

			if (peek() == number_delimiter) {
				if (number_delimiter_parsed) {
					report(line, "invalid number literal");
					return null;
				}

				number_delimiter_parsed = true;
			}

			advance();
		}

		final var number = Double.parseDouble(source_code.substring(start, current));
		return token(NUMBER, number);
	}

	private Token parseString() {
		while (peek() != '"' && !isEndOfFile()) {
			if (peek() == '\n') {
				line = line + 1;
			}
			advance();
		}

		if (isEndOfFile()) {
			report(line, "unterminated string");
			return null;
		}

		advance(); // closing "

		final var literal = source_code.substring(start + 1, current - 1);
		return token(STRING, literal);
	}

	private char peek() {
		if (isEndOfFile()) {
			return '\0';
		}
		return source_code.charAt(current);
	}

	private char peek(final int amount) {
		final var offset = current + amount;
		if (offset >= source_code.length()) {
			return '\0';
		}
		return source_code.charAt(offset);
	}

	private boolean advanceIf(final char character) {
		if (peek() != character) {
			return false;
		}
		current = current + 1;
		return true;
	}

	private char advance() {
		final var character = source_code.charAt(current);
		current = current + 1;
		return character;
	}

	private boolean isEndOfFile() {
		return current >= source_code.length();
	}
}
