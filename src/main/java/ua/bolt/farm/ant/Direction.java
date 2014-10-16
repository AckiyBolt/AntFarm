package ua.bolt.farm.ant;

public enum Direction {
    N("⇑"),
    S("⇓"),
    W ("⇐"),
    E("⇒"),
    NW("⇖"),
    NE("⇗"),
    SW("⇙"),
    SE("⇘");

    private final String asString;

    private Direction(String asString) {
        this.asString = asString;
    }

    @Override
    public String toString() {
        return asString;
    }
}
