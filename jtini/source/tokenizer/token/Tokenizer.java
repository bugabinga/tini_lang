package token;

import java.util.List;
import java.util.ArrayList;

import report.IssueReporter;

public
class Tokenizer
{

private final List< Token > tokens;

public
Tokenizer (
  final IssueReporter reporter,
  final String source_code )
{
  tokens = scanTokens( source_code );
}

public
List< Token > tokens ()
{
  return tokens;
}

private static
List< Token > scanTokens ( final String source_code )
{
  final var tokens = new ArrayList< Token >();

  int current_position    = 0;
  int current_line_number = 1;

  while ( !isEndOfFile( current_position, source_code ) )
  {
    final var token = scanToken( current_position, current_line_number, source_code );

    current_position    = token.location().end();
    current_line_number = token.location().line();

    tokens.add( token );
  }

  tokens.add( new Token( TokenType.EOF,
                         "",
                         null,
                         new Span( current_position, current_position, current_line_number ) ) );
  return tokens;
}

private static
Token scanToken (
  final int position,
  final int line_number,
  final String source_code )
{

  final int starting_position = position;

  char character = grabCharacter( source_code );

  final var token = switch ( character )
  {
    case '(' -> new Token( TokenType.LEFT_PAREN );
    default -> new Token( TokenType.ERROR )
  };

}

private static
boolean isEndOfFile (
  final int position,
  final String source_code )
{
  return position >= source_code.length();
}
}
