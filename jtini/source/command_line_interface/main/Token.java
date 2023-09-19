package main;

record Token(Type token_type, String lexeme, Object literal, int line) {

	@Override
	public String toString() {
		return java.text.MessageFormat.format("TKN[ {0} ] ( {1} ) ''{2}''", token_type, lexeme, literal);
	}

	enum Type {
		// single char tokens
		LEFT_PAREN,
		RIGHT_PAREN,
		LEFT_BRACE,
		RIGHT_BRACE,
		COMMA,
		DOT,
		MINUS,
		PLUS,
		SEMICOLON,
		SLASH,
		STAR,

		// single or dubbel char tokens
		BANG,
		BANG_EQUAL,
		EQUAL,
		EQUAL_EQUAL,
		GREATER,
		GREATER_EQUAL,
		LESS,
		LESS_EQUAL,

		// literals
		IDENTIFIER,
		STRING,
		NUMBER,

		// keyword
		AND,
		CLASS,
		ELSE,
		FALSE,
		FOR,
		FUN,
		IF,
		NIL,
		OR,
		PRINT,
		RETURN,
		SUPER,
		THIS,
		TRUE,
		VAR,
		WHILE,

		// sentinel
		END_OF_FILE,
	}
}
