package model;

public enum ReportStatus {
    APERTO("Aperto"),
    IN_PROGRESS("In corso"),
    RESOLVED("Risolto"),
    CLOSED("Chiuso");

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
