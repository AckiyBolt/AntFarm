package ua.bolt.farm.ant;

import ua.bolt.farm.field.Cell;
import ua.bolt.farm.field.Field;

import java.util.Set;

public abstract class AbstractAnt implements Runnable {

    private volatile boolean isAlive;
    private Field field;
    private Cell currentCell;
    private MovementLogger mvLogger;
    public final String name;

    public AbstractAnt(String name,  Field field) {
        this.name = name;
        this.field = field;
        this.currentCell = field.getStart();
    }

    @Override
    public void run() {
        isAlive = true;
        mvLogger = new MovementLogger();

        while (isAlive) {

            switch (makeMoveOrDoAction(currentCell)) {

                case MOVE:
                    Set<Cell> nearestCells = field.getNearest(currentCell.coordinates);
                    currentCell = makeMove(currentCell, nearestCells, mvLogger);
                    mvLogger.log(currentCell.coordinates);
                    break;

                case ACTION:
                    doAction();
                    break;

                default:
                    break;
            }

            incrementLogVersionIfCycleEnds();
            if (isAimReached(currentCell))
                kill();
        }
    }

    private void incrementLogVersionIfCycleEnds() {
        if (!mvLogger.isEmpty() && field.getStart().equals(currentCell))
            mvLogger.incrementVersion();
    }




    protected abstract Behavior makeMoveOrDoAction(Cell currentCell);
    protected abstract Cell makeMove(Cell currentCell, Set<Cell> nearestCells, MovementLogger mvLogger);
    protected abstract void doAction();
    protected abstract boolean isAimReached(Cell currentCell);

    public void kill() {
        isAlive = false;
    }

    public MovementLogger getMovementLogger() {
        return this.mvLogger;
    }

    protected enum Behavior {
        MOVE, ACTION;
    }
}
