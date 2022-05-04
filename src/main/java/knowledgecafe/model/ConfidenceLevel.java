package knowledgecafe.model;

public enum ConfidenceLevel {
    LIGHTGREEN("LightGreen"), GREEN("Green"), RED("RED"), ORANGE("ORANGE");

    private final String name;

    private ConfidenceLevel(String value) {
        name = value;
    }

    @Override
    public String toString() {
        return name;
    }
}
