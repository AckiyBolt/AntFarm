package ua.bolt.tsp.model.field;

public class Cell {

    public final Coordinates coordinates;
    public final CellStatus status;
    public final Integer smell;

    public Cell(Coordinates coordinates, CellStatus status, Integer smell) {
        this.coordinates = coordinates;
        this.status = status;
        this.smell = smell;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Cell cell = (Cell) o;

        if (!coordinates.equals(cell.coordinates)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return coordinates.hashCode();
    }

    @Override
    public String toString() {
        return coordinates.toString();
    }
}
