package ua.bolt.farm.field;

import ua.bolt.farm.field.entity.Cell;
import ua.bolt.farm.field.entity.CellStatus;
import ua.bolt.farm.field.entity.Coordinates;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeMap;

public class Field {

    private TreeMap<Coordinates, Cell> field;
    private Cell start;
    private Cell food;
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
            food = cell;
        }
    }

    public Cell getStart() {
        return start;
    }

    public Cell getFood() {
        return food;
    }

    public Cell getCell(Coordinates coordinates) {
        return field.get(coordinates);
    }

    public Set<Cell> getNearest(Coordinates toCel, byte scope) {

        Set<Cell> result = new HashSet<Cell>();

        for (int x = toCel.X - scope; x <= toCel.X + scope; x++)
            for (int y = toCel.Y - scope; y <= toCel.Y + scope; y++)
                if (!(x == toCel.X && y == toCel.Y))
                    AddCellIfExist(result, x, y);

        return result;
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

    public Integer getSize() {
        return size;
    }
}
