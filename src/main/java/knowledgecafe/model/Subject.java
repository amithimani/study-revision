package knowledgecafe.model;

public enum Subject {
    MATHS2("MATHS2") ,MATHS1("MATHS1"), PHYSICS("PHYSICS"), CHEMISTRY("CHEMISTRY");

    private final String name;

    private Subject(String value) {
        name = value;
    }

    @Override
    public String toString() {
        return name;
    }
}
