package report;

import java.io.PrintStream;
import java.nio.file.Path;

public class IssueReporter {

	private final PrintStream sink;

	public IssueReporter( final PrintStream sink ) {
		this.sink = sink;
	}

	public void present(
		final Issue issue,
		final Path source_file,
		final int start_location,
		final int end_location,
		final String message ) {
		sink.printf("%s: %s%n", issue.id(), issue.shortDescription() );
		//TODO: if verbose, print long description?
		sink.printf( "%s: %s%n", source_file, message );
		//TODO: show fancy pants spans 
	}
}
