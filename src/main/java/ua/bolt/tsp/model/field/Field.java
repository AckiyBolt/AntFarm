package ua.bolt.tsp.model.field;

import java.util.*;

public class Field {

    private Map<Coordinates, Cell> field;
    private LinkedHashMap<Coordinates, Move> movingHistory;
    private Cell start;
    private Cell target;

    public Field() {
        this.field = new TreeMap<Coordinates, Cell>();
        this.movingHistory = new LinkedHashMap<Coordinates, Move>();
    }

    public void addCell (Cell cell) {

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

    public Cell getCell (Coordinates coordinates) {
        return field.get(coordinates);
    }

    public List<Cell> getNearest(Coordinates toCel) {

        LinkedList<Cell> result = new LinkedList<Cell>();
        
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

        return Collections.unmodifiableList(result);
    }

    private void AddCellIfExist(List<Cell> toList, Integer x, Integer y) {
        
        Cell tmpCell = getCell(new Coordinates(x, y));
        
        if (tmpCell != null)
            toList.add(tmpCell);
    }

    public void logMove(Coordinates cord, Move move) {
        movingHistory.put(cord, move);
    }

    public void printSmell() {

        StringBuilder result = new StringBuilder();
        Integer tempY = 0;

        for (Coordinates cord : field.keySet()) {

            Cell cell = field.get(cord);

            if (cord.Y > tempY) {
                tempY = cord.Y;
                result.append("\n");
            }

            Integer smell = cell.smell;

            if (smell < 10) {
                result.append(" ");
            }

            result.append(smell);

            result.append(" ");
        }

        System.out.println( result.toString());
    }

    public void printPath() {

        StringBuilder result = new StringBuilder();
        Integer tempY = 0;

        for (Coordinates cord : field.keySet()) {

            Cell cell = field.get(cord);

            if (cord.Y > tempY) {
                tempY = cord.Y;
                result.append("\n");
            }

            switch (cell.status) {
                case START:
                   result.append(" -");
                   break;

                case TARGET:
                    result.append(" +");
                    break;

                default:
                    Move historyMove = movingHistory.get(cell.coordinates);

                    if (historyMove != null) {
                        result.append(historyMove);
                    } else {
                        result.append("  ");
                    }
                    break;
            }

            result.append(" ");
        }

        System.out.println( result.toString());
    }
}
