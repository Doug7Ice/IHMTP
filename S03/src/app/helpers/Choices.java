package app.helpers;

public enum Choices {
    X("X"), O("O"), N(" ");

    private String value;
    Choices(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
