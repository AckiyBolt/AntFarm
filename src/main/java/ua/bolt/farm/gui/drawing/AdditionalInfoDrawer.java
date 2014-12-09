package ua.bolt.farm.gui.drawing;

import javafx.scene.paint.Color;
import ua.bolt.farm.ant.MovementLogger;
import ua.bolt.farm.field.Field;
import ua.bolt.farm.field.entity.Coordinates;

/**
 * Created by ackiybolt on 08.12.14.
 */
public class AdditionalInfoDrawer extends AbstractDrawer {

    @Override
    public void draw( Field field, MovementLogger... movementLoggers) {

        context.setFill(Color.BLACK);

        Coordinates start = transformCoordinates(field.getStart().coordinates);
        Coordinates food = transformCoordinates(field.getFood().coordinates);

        context.fillOval(start.X - 3, start.Y - 3, 5, 5);
        context.fillOval(food.X - 3, food.Y - 3, 5, 5);

        context.fillText("Start", start.X - 20, start.Y + 20);
        context.fillText("Target", food.X - 20, food.Y + 20);
    }
}
