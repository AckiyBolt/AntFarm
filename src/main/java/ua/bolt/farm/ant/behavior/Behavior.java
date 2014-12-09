package ua.bolt.farm.ant.behavior;

import ua.bolt.farm.ant.MovementLogger;
import ua.bolt.farm.field.Field;
import ua.bolt.farm.field.entity.Cell;

/**
 * Created by ackiybolt on 09.12.14.
 */
public interface Behavior {

    Cell makeMove(Cell currentCell, Field field, MovementLogger logger);
    void doAction(Cell currentCell, Field field, MovementLogger logger);
    void logMove (Cell currentCell, MovementLogger logger);
    boolean isTimeToDie();
}
