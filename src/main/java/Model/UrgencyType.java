package Model;

public enum UrgencyType {

    LOW("Bassa urgenza"),
    MEDIUM("Media urgenza"),
    HIGH("Alta urgenza"),
    CRITICAL("Urgenza critica");


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
