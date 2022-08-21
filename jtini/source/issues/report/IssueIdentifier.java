package report;

public record IssueIdentifier ( int number ) {
	@Override
	public String toString() {
		return "ISSUE-" + number;
	}
}
