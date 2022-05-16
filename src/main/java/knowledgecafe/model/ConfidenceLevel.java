package knowledgecafe.model;

public enum ConfidenceLevel {
    LIGHTGREEN("Confident"), GREEN("Very Confident"), RED("Not Confident"), ORANGE("Little Confident");

    private final String name;

    private ConfidenceLevel(String value) {
        name = value;
    }

    @Override
    public String toString() {
        return name;
    }
}
