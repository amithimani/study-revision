package knowledgecafe.model;

public enum Subject {
    MATHS1("Maths 1"), MATHS2("Maths 2"), CHEMISTRY1("Chemistry 1"), PHYSICS1("Physics 1"),
    PHYSICS2("Physics 2"), CHEMISTRY2("Chemistry 2");

    private final String name;

    private Subject(String value) {
        name = value;
    }

    @Override
    public String toString() {
        return name;
    }
}
