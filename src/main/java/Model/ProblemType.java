package Model;

public enum ProblemType {
    URBAN_PROBLEM("Problema urbano"),
    BUILDING_PROBLEM("Problema edilizio"),
    ENVIRONMENTAL_PROBLEM("Problema ambientale"),
    INFRASTRUCTURE_PROBLEM("Problema infrastrutturale"),
    TRAFFIC_PROBLEM("Problema di traffico"),
    PUBLIC_SERVICES_PROBLEM("Problema dei servizi pubblici");


    private final String description;

    // Constructor to associate a description with each problem type
    ProblemType(String description) {
        this.description = description;
    }

    // Getter to retrieve the description of the problem type
    public String getDescription() {
        return description;
    }


}
