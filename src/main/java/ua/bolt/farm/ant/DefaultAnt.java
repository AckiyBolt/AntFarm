package ua.bolt.farm.ant;

import ua.bolt.farm.field.Cell;
import ua.bolt.farm.field.Field;

import java.util.*;

public class DefaultAnt extends AbstractAnt {

    private Cell target;
    private final Random RND = new Random(System.nanoTime());

    public DefaultAnt(String name, Field field) {
        super(name, field);
        this.target = field.getTarget();
    }

    @Override
    protected boolean isAimReached(Cell currentCell) {
        return currentCell.equals(target);
    }

    @Override
    protected void doAction() {
        System.out.println("I did it!");
    }

    @Override
    protected Behavior makeMoveOrDoAction(Cell currentCell) {
        return currentCell.equals(target) ? Behavior.ACTION : Behavior.MOVE;
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

        for (Cell cell: nearestCells)
            if (!mvLogger.contains(cell.coordinates))
                result.add(cell);

        return result;
    }

    public Cell decideWhereToMove (Set<Cell> nearestToCurrentPosition) {

        Cell result = null;

        List<Cell> variants = rateNextMoves(nearestToCurrentPosition);

        result = variants.get(genRnd(variants.size()));

        return result;
    }

    public List<Cell> rateNextMoves (Set<Cell> nearestToCurrentPosition) {

        List<Cell> result = new ArrayList<Cell>();
        Integer lowestSmell = Integer.MAX_VALUE;

        for (Cell cell : nearestToCurrentPosition) {

            if (cell.smell < lowestSmell) {
                result.clear();
                lowestSmell = cell.smell;
            }

            if (cell.smell.equals(lowestSmell))
                result.add(cell);
        }

        return result;
    }


    public int genRnd (int max) {
        return RND.nextInt(max);
    }
}
