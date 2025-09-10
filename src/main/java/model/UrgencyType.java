package model;

public enum UrgencyType {

    LOW("Bassa urgenza"),
    MEDIUM("Media urgenza"),
    HIGH("Alta urgenza"),
    CRITICAL("Urgenza critica");


    private final String description;


    UrgencyType(String description) {
        this.description = description;
    }


    public String getDescription() {
        return description;
    }

}
