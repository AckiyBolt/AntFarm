package ua.bolt.farm.view;

import ua.bolt.farm.ant.MovementLogger;
import ua.bolt.farm.field.Cell;
import ua.bolt.farm.field.Coordinates;
import ua.bolt.farm.field.Field;

import java.util.Map;

public class FieldPrinter {

    public void printSmell(Field field) {

        Map<Coordinates, Cell> fieldMap = field.getMap();

        StringBuilder result = new StringBuilder();
        Integer tempY = 0;

        for(Coordinates cord: fieldMap.keySet())
        {
            Cell cell = fieldMap.get(cord);

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

    public void printMovement(Field field, MovementLogger ... logger) {

        Map<Coordinates, Cell> fieldMap = field.getMap();

        StringBuilder result = new StringBuilder();
        Integer tempY = 0;

        for(Coordinates cord: fieldMap.keySet())
        {
            Cell cell = fieldMap.get(cord);

            if (cord.Y > tempY) {
                tempY = cord.Y;
                result.append("\n");
            }

            if (field.getStart().coordinates.equals(cord))
                result.append(" -");
            else if (field.getTarget().coordinates.equals(cord))
                result.append(" +");
            else {
                int loggerId = thereWasMove(cord, logger);
                result.append(" ");

                if (loggerId >= 0)
                    result.append(loggerId);
            }
        }

        System.out.println(result.toString());
    }

    private int thereWasMove(Coordinates cord, MovementLogger[] loggers) {
        int result = -1;

        for (int i = 0; i < loggers.length; i++)
            if (loggers[i].contains(cord))
                result = i;

        return result;
    }
}
