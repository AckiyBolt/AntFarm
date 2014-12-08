package ua.bolt.farm.field.entity;

import java.util.EnumMap;

public class Cell {

    public final Coordinates coordinates;
    public final CellStatus status;
    public final EnumMap<SmellType, Integer> smells;

    public Cell(Coordinates coordinates, CellStatus status, EnumMap<SmellType, Integer> smells) {
        this.coordinates = coordinates;
        this.status = status;
        this.smells = smells;
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
