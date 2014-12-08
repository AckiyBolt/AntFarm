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
        Coordinates target = transformCoordinates(field.getTarget().coordinates);

        context.fillOval(start.X - 3, start.Y - 3, 5, 5);
        context.fillOval(target.X - 3, target.Y - 3, 5, 5);

        context.fillText("Start", start.X - 20, start.Y + 20);
        context.fillText("Target", target.X - 20, target.Y + 20);
    }
}
