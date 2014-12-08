package ua.bolt.farm.ant;

import ua.bolt.farm.field.Field;
import ua.bolt.farm.field.entity.Cell;

import java.util.Set;

public abstract class AbstractAnt implements Runnable {

    private volatile boolean isAlive;
    private Field field;
    private Cell currentCell;
    private MovementLogger mvLogger;
    public final String name;

    public AbstractAnt(String name, Field field) {
        this.name = name;
        this.field = field;
        this.currentCell = field.getStart();
    }

    @Override
    public void run() {
        isAlive = true;
        mvLogger = new MovementLogger(name);

        do {

            Set<Cell> nearestCells = field.getNearest(currentCell.coordinates);
            currentCell = makeMove(currentCell, nearestCells, mvLogger);

            if (isActionRequired(currentCell))
                doAction();

            if (isAimReached(currentCell))
                kill();

            incrementLogVersionIfCycleEnds();
            mvLogger.log(currentCell.coordinates);

        } while (isAlive);
    }

    private void incrementLogVersionIfCycleEnds() {
        if (!mvLogger.isEmpty() && field.getStart().equals(currentCell))
            mvLogger.makeNewSession();
    }


    protected abstract boolean isActionRequired(Cell currentCell);

    protected abstract Cell makeMove(Cell currentCell, Set<Cell> nearestCells, MovementLogger mvLogger);

    protected abstract void doAction();

    protected abstract boolean isAimReached(Cell currentCell);

    public void kill() {
        isAlive = false;
    }

    public MovementLogger getMovementLogger() {
        return this.mvLogger;
    }

    public String getName() {
        return name;
    }
}
