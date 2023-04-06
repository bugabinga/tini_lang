package application;

import report.IssueReporter;
import token.Tokenizer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import static java.text.MessageFormat.format;

public
class Main
{

private static final int BUFFER_SIZE                     = 512;
private static final int TOO_MANY_COMMAND_LINE_ARGUMENTS = 64;

public static
void main ( final String[] command_line_arguments )
throws
Throwable
{
  if ( command_line_arguments.length > 1 )
  {
    usage();
    throw fail( TOO_MANY_COMMAND_LINE_ARGUMENTS );
  }
  else if ( command_line_arguments.length == 1 )
  {
    final var script = Path.of( command_line_arguments[ 0 ] );
    interpret( script );
  }
  else
  {
    read_eval_print_loop();
  }
}

private static
void interpret ( final Path script_path )
throws
IOException
{
  final var bytes = Files.readAllBytes( script_path );
  evaluate( new String( bytes, StandardCharsets.UTF_8 ) );
}

private static
void read_eval_print_loop ()
throws
IOException
{
  final var input  = new InputStreamReader( System.in, StandardCharsets.UTF_8 );
  final var reader = new BufferedReader( input, BUFFER_SIZE );

  while ( true )
  {
    print( bold( green( "> " ) ) );
    final var line = reader.readLine();
    if ( line == null )
    {
      break;
    }
    evaluate( line );
  }
}

private static
void evaluate ( final String code )
{
  final var reporter  = new IssueReporter( System.err );
  final var tokenizer = new Tokenizer( reporter, code );
  final var tokens    = tokenizer.tokens();

  for ( final var token : tokens )
  {
    print( token.toString() );
  }
}

private static
void usage ()
{
  print( "Interpret a Tini language script. Java Edition." );
  print( "Usage: {0} {1}", bold( "jtini" ), italic( "[script]" ) );
}

private static
Throwable fail (
  final int code,
  final String message,
  final Object... arguments )
{
  print( System.err, message, arguments );
  return fail( code );
}

private static
Throwable fail ( final int code )
{
  System.exit( code );
  return new Throwable( "unreachable" );
}

private static
void print (
  final String message,
  Object... arguments )
{
  print( System.out, format( message, arguments ) );
}

private static
void print (
  PrintStream output,
  final String message,
  final Object... arguments )
{
  output.println( format( message, arguments ) );
}

private static
String bold ( final String text )
{
  return "\033[1m" + text + "\033[0m";
}

private static
String italic ( final String text )
{
  return "\033[3m" + text + "\033[0m";
}

private static
String underline ( final String text )
{
  return "\033[4m" + text + "\033[0m";
}

private static
String inverse ( final String text )
{
  return "\033[7m" + text + "\033[0m";
}

private static
String red ( final String text )
{
  return "\033[31m" + text + "\033[0m";
}

private static
String green ( final String text )
{
  return "\033[32m" + text + "\033[0m";
}
}
