package ua.bolt.farm.ant;

import ua.bolt.farm.ant.behavior.Behavior;
import ua.bolt.farm.field.Field;
import ua.bolt.farm.field.entity.Cell;

/**
 * Created by ackiybolt on 09.12.14.
 */
public class Ant implements Runnable {

    private volatile boolean isAlive;
    private Field field;
    private Cell currentCell;
    private MovementLogger logger;
    public final String name;
    private Behavior behavior;

    public Ant (String name, Field field, Behavior behavior) {
        this.name = name;
        this.field = field;
        this.currentCell = field.getStart();
        this.behavior = behavior;
        this.isAlive = true;
        this.logger = new MovementLogger(name);
        this.logger.log(this.currentCell.coordinates);
    }

    @Override
    public void run() {

        while (!Thread.currentThread().isInterrupted()) {
            currentCell = behavior.makeMove(currentCell, field, logger);
            behavior.logMove(currentCell, logger);
            behavior.doAction(currentCell, field, logger);

            if (behavior.isTimeToDie())
                break;
        }
    }

    public MovementLogger getMovementLogger() {
        return logger;
    }
}
