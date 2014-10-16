package ua.bolt.farm.field;

public class Coordinates implements Comparable<Coordinates> {
    public final Integer X;
    public final Integer Y;

    public Coordinates(Integer x, Integer y) {
        X = x;
        Y = y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Coordinates that = (Coordinates) o;

        if (!X.equals(that.X)) return false;
        if (!Y.equals(that.Y)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = X.hashCode();
        result = 31 * result + Y.hashCode();
        return result;
    }

    @Override
    public int compareTo(Coordinates coordinates) {

        int xCompare = X < coordinates.X ? -1 : (X.equals(coordinates.X) ? 0 : 1);
        int yCompare = Y < coordinates.Y ? -1 : (Y.equals(coordinates.Y) ? 0 : 1);

        return yCompare != 0 ? yCompare : xCompare;
    }

    @Override
    public String toString() {
        return "{X=" + X + ", Y=" + Y +'}';
    }
}
