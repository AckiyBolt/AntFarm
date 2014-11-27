package ua.bolt.farm.util;

import ua.bolt.farm.field.*;

import java.util.EnumMap;

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
                EnumMap<SmellType, Integer> smell = resolveCellSmells(cord);

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

    private EnumMap<SmellType, Integer> resolveCellSmells(Coordinates cord) {
        EnumMap<SmellType, Integer> result = new EnumMap<SmellType, Integer>(SmellType.class);

        result.put(SmellType.TARGET_SMELL, resolveSmell(cord, target));
        result.put(SmellType.NEST_SMELL, resolveSmell(cord, start));

        return result;
    }

    private Integer resolveSmell(Coordinates currentCoords, Coordinates smellSourceCoords) {
        return Math.max(Math.abs(smellSourceCoords.X - currentCoords.X), Math.abs(smellSourceCoords.Y - currentCoords.Y));
    }
}
