package Model;

public enum ReportStatus {
    OPEN("Open"),
    IN_PROGRESS("In Progress"),
    RESOLVED("Resolved"),
    CLOSED("Closed");

    private final String description;

    // Constructor to initialize the description for each status
    ReportStatus(String description) {
        this.description = description;
    }

    // Getter to retrieve the description
    public String getDescription() {
        return description;
    }
}
