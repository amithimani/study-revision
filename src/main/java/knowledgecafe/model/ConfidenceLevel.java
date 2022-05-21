package knowledgecafe.model;

public enum ConfidenceLevel {
    GREEN("GREEN"), RED("RED"), YELLOW("YELLOW");

    private final String name;

    private ConfidenceLevel(String value) {
        name = value;
    }

    @Override
    public String toString() {
        return name;
    }
}
