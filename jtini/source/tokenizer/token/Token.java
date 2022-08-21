package token;

public record Token(
	TokenType type,
	String lexeme,
	Object literal,
	Span location ) {

	@Override
	public String toString() {
		final var builder = new StringBuilder();
		builder.append(type());
		builder.append(' ');
		builder.append('{');
		builder.append('\n');
		builder.append('\t');
		builder.append("lexeme: ");
		builder.append(lexeme());
		builder.append('\t');
		builder.append("literal: ");
		builder.append(literal());
		builder.append('\n');
		builder.append('}');
		return builder.toString();
	}
}
