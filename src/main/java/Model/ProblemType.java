package Model;

public enum ProblemType {
    URBAN_PROBLEM("Urban Problem"),
    BUILDING_PROBLEM("Building Problem"),
    ENVIRONMENTAL_PROBLEM("Environmental Problem"),
    INFRASTRUCTURE_PROBLEM("Infrastructure Problem"),
    TRAFFIC_PROBLEM("Traffic Problem"),
    PUBLIC_SERVICES_PROBLEM("Public Services Problem");

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
