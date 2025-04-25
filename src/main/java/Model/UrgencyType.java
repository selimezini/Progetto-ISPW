package Model;

public enum UrgencyType {

    LOW("Low urgency"),
    MEDIUM("Medium urgency"),
    HIGH("High urgency"),
    CRITICAL("Critical urgency");

    private final String description;

    // Il costruttore viene invocato per ciascun valore dell'enum
    UrgencyType(String description) {
        this.description = description;
    }

    // Metodo per ottenere la descrizione associata al livello di urgenza
    public String getDescription() {
        return description;
    }

}
