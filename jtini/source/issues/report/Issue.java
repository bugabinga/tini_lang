package report;

import java.net.URI;

public interface Issue {
	String shortDescription();
	String longDescription();
	IssueIdentifier id();
	URI documentation();
}
