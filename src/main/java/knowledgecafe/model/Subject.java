package knowledgecafe.model;

public enum Subject {
    MATHS1("MATHS1"), MATHS2("MATHS2"), CHEMISTRY1("CHEMISTRY1"), PHYSICS1("PHYSICS1"),
    PHYSICS2("PHYSICS2"), CHEMISTRY2("CHEMISTRY2");

    private final String name;

    private Subject(String value) {
        name = value;
    }

    @Override
    public String toString() {
        return name;
    }
}
