package ua.bolt.tsp.model.field;

public enum Move {
    LEFT ("←"),
    RIGHT("→"),
    UP("↑"),
    DOWN("↓"),
    STAY("0");

    private final String asString;

    private Move (String asString) {
        this.asString = asString;
    }

    @Override
    public String toString() {
        return asString;
    }
}
