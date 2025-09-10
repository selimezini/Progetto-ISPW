package model;

public enum ReportStatus {
    APERTO("Aperto"),
    IN_PROGRESS("In corso"),
    RESOLVED("Risolto"),
    CLOSED("Chiuso");

    private final String description;


    ReportStatus(String description) {
        this.description = description;
    }


    public String getDescription() {
        return description;
    }
}
