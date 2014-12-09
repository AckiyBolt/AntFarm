package ua.bolt.farm.ant.behavior;

import ua.bolt.farm.ant.MovementLogger;
import ua.bolt.farm.field.Field;
import ua.bolt.farm.field.entity.Cell;
import ua.bolt.farm.field.entity.SmellType;

import java.util.*;

/**
 * Created by ackiybolt on 09.12.14.
 */
public class ScoutBehavior implements Behavior {

    static final byte VISION_SCOPE = 1;
    private boolean aimReached;
    private SmellType aimSmell;

    public ScoutBehavior() {
        aimSmell = SmellType.FOOD_SMELL;
    }

    @Override
    public Cell makeMove(Cell currentCell, Field field, MovementLogger logger) {

        Cell result = currentCell;

        Set<Cell> nearestCells = field.getNearest(currentCell.coordinates, VISION_SCOPE);
        filterNearestCells(nearestCells, logger);
        List<Cell> variants = rateNextMoves(nearestCells);

        result = variants.get(genRnd(variants.size()));

        return result;
    }

    private void filterNearestCells(Set<Cell> nearestCells, MovementLogger mvLogger) {
        Iterator<Cell> iterator = nearestCells.iterator();

        while (iterator.hasNext()) {
            if (mvLogger.containsInCurrentSession(iterator.next().coordinates))
                iterator.remove();
        }
    }

    public List<Cell> rateNextMoves(Set<Cell> nearestToCurrentPosition) {

        List<Cell> result = new ArrayList<Cell>();
        Integer lowestSmell = Integer.MAX_VALUE;

        for(Cell cell : nearestToCurrentPosition) {
            Integer currentCellSmell = cell.smells.get(aimSmell);
            if (currentCellSmell < lowestSmell) {
                result.clear();
                lowestSmell = currentCellSmell;
            }

            if (currentCellSmell.equals(lowestSmell))
                result.add(cell);
        }

        return result;
    }

    @Override
    public void doAction(Cell currentCell, Field field, MovementLogger logger) {

        if (currentCell.equals(field.getStart())) {
            System.out.println("I did it");
            aimReached = true;
        }

        if (aimSmell.equals(SmellType.FOOD_SMELL) &&
                currentCell.equals(field.getFood())) {
            aimSmell = SmellType.NEST_SMELL;
            logger.makeNewSession();
        }

    }

    @Override
    public void logMove(Cell currentCell, MovementLogger logger) {
        logger.log(currentCell.coordinates);
    }

    @Override
    public boolean isTimeToDie() {
        return aimReached;
    }

    public int genRnd(int max) {
        return new Random().nextInt(max);
    }
}
