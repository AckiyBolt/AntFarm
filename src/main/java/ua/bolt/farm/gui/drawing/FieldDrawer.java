package ua.bolt.farm.gui.drawing;

import javafx.scene.paint.Color;
import ua.bolt.farm.ant.MovementLogger;
import ua.bolt.farm.field.Field;

/**
 * Created by ackiybolt on 08.12.14.
 */
public class FieldDrawer extends AbstractDrawer {

    public void draw(Field field, MovementLogger... movementLoggers) {
        context.clearRect(0, 0, canvasSize, canvasSize);
        context.setFill(Color.LIGHTGRAY);
        context.fillRect(0, 0, canvasSize, canvasSize);
        context.setFill(Color.BLACK);
        context.setLineWidth(1);
    }
}
