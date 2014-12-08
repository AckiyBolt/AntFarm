package ua.bolt.farm.field;

import ua.bolt.farm.field.entity.Cell;
import ua.bolt.farm.field.entity.CellStatus;
import ua.bolt.farm.field.entity.Coordinates;

import java.util.*;

public class Field {

    private TreeMap<Coordinates, Cell> field;
    private Cell start;
    private Cell target;
    private Integer size;

    public Field(Integer size) {
        this.field = new TreeMap<Coordinates, Cell>();
        this.size = size;
    }

    protected void addCell(Cell cell) {

        field.put(cell.coordinates, cell);

        if (cell.status == CellStatus.START) {
            start = cell;
        }

        if (cell.status == CellStatus.TARGET) {
            target = cell;
        }
    }

    public Cell getStart() {
        return start;
    }

    public Cell getTarget() {
        return target;
    }

    public Cell getCell(Coordinates coordinates) {
        return field.get(coordinates);
    }

    public Set<Cell> getNearest(Coordinates toCel) {

        Set<Cell> result = new HashSet<Cell>();

        // up row
        AddCellIfExist(result, toCel.X - 1, toCel.Y - 1);
        AddCellIfExist(result, toCel.X, toCel.Y - 1);
        AddCellIfExist(result, toCel.X + 1, toCel.Y - 1);

        // same row
        AddCellIfExist(result, toCel.X - 1, toCel.Y);
        AddCellIfExist(result, toCel.X + 1, toCel.Y);

        // down row
        AddCellIfExist(result, toCel.X - 1, toCel.Y + 1);
        AddCellIfExist(result, toCel.X, toCel.Y + 1);
        AddCellIfExist(result, toCel.X + 1, toCel.Y + 1);

        return result;
    }

    private void AddCellIfExist(Set<Cell> resultSet, Integer x, Integer y) {

        Cell tmpCell = getCell(CoordinatesCache.INSTANCE.createOrGet(x, y));

        if (tmpCell != null)
            resultSet.add(tmpCell);
    }

    public Map<Coordinates, Cell> getMap() {
        return Collections.unmodifiableMap(field);
    }

    public Integer getSize() {
        return size;
    }
}
