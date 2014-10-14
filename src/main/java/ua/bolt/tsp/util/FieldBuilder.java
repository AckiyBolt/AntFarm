package ua.bolt.tsp.util;

import ua.bolt.tsp.model.field.Cell;
import ua.bolt.tsp.model.field.CellStatus;
import ua.bolt.tsp.model.field.Coordinates;
import ua.bolt.tsp.model.field.Field;

public class FieldBuilder {

    private Field field;
    private int size;
    private Coordinates start;
    private Coordinates target;

    public FieldBuilder(int size) {
        this.field = new Field();
        this.size = size;
    }

    public FieldBuilder setStartCoordinates (Coordinates coordinates){
        this.start = coordinates;
        return this;
    }

    public FieldBuilder setTargetCoordinates (Coordinates coordinates){
        this.target = coordinates;
        return this;
    }

    public Field build () {

        if (size <= 1 || start == null || target == null)
            throw new IllegalStateException("There is wrong field settings.");

        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {

                Coordinates cord = new Coordinates(x, y);
                CellStatus status = resolveCellStatus(cord);
                Integer smell = resolveSmell(cord);

                field.addCell(new Cell(cord, status, smell));
            }
        }

        return field;
    }

    private CellStatus resolveCellStatus(Coordinates cord) {

        CellStatus result;

        if (cord.equals(target))
            result = CellStatus.TARGET;

        else if (cord.equals(start))
            result = CellStatus.START;

        else
            result = CellStatus.DEFAULT;

        return result;
    }

    private Integer resolveSmell(Coordinates cord) {
        return Math.max(Math.abs(target.X - cord.X), Math.abs(target.Y - cord.Y));
    }
}
