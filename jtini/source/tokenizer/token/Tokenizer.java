package token;

import java.util.List;
import java.util.ArrayList;

import report.IssueReporter;

public class Tokenizer {

	private final List<Token> tokens;

	public Tokenizer( final IssueReporter reporter, final String source_code ) {
		tokens = new ArrayList<>();
	}

	public List<Token> tokens() {
		return tokens;
	} 
}
