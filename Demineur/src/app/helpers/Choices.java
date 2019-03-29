package app.helpers;

public enum Choices {
     MINE_(-1), BLANK(6);

    private int value;
    Choices(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}