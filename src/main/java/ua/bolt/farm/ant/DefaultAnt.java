package ua.bolt.farm.ant;

import ua.bolt.farm.field.Cell;
import ua.bolt.farm.field.Field;
import ua.bolt.farm.field.SmellType;

import java.util.*;

public class DefaultAnt extends AbstractAnt {

    private Cell target;
    private Cell start;
    private final Random RND = new Random(System.nanoTime());

    public DefaultAnt(String name, Field field) {
        super(name, field);
        this.target = field.getTarget();
        this.start = field.getStart();
    }

    @Override
    protected boolean isAimReached(Cell currentCell) {
        return currentCell.equals(target);
    }

    @Override
    protected void doAction() {
        System.out.println("I did it!");

        target = start;
    }

    @Override
    protected boolean isActionRequired(Cell currentCell) {
        return currentCell.equals(target);
    }

    @Override
    protected Cell makeMove(Cell currentCell, Set<Cell> nearestCells, MovementLogger mvLogger) {

        Cell result = null;

        Set<Cell> availableForMoveCells = filterNearestCells(nearestCells, mvLogger);

        result = decideWhereToMove(availableForMoveCells);

        return result;
    }

    private Set<Cell> filterNearestCells(Set<Cell> nearestCells, MovementLogger mvLogger) {
        Set<Cell> result = new HashSet<Cell>();

        for (Cell cell : nearestCells)
            if (!mvLogger.containsInCurrentSession(cell.coordinates))
                result.add(cell);

        return result;
    }

    public Cell decideWhereToMove(Set<Cell> nearestToCurrentPosition) {

        Cell result = null;

        List<Cell> variants = rateNextMoves(nearestToCurrentPosition);

        result = variants.get(genRnd(variants.size()));

        return result;
    }

    public List<Cell> rateNextMoves(Set<Cell> nearestToCurrentPosition) {

        List<Cell> result = new ArrayList<Cell>();
        Integer lowestSmell = Integer.MAX_VALUE;

        for (Cell cell : nearestToCurrentPosition) {


            Integer currentCellSmell = cell.smells.get(start.equals(target) ? SmellType.NEST_SMELL : SmellType.TARGET_SMELL);

            if (currentCellSmell < lowestSmell) {
                result.clear();
                lowestSmell = currentCellSmell;
            }

            if (currentCellSmell.equals(lowestSmell))
                result.add(cell);
        }

        return result;
    }


    public int genRnd(int max) {
        return RND.nextInt(max);
    }
}
