package ua.bolt.farm.gui.drawing;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import ua.bolt.farm.ant.MovementLogger;
import ua.bolt.farm.field.Field;
import ua.bolt.farm.field.entity.Coordinates;

import java.util.Iterator;

/**
 * Created by ackiybolt on 08.12.14.
 */
public class AntDrawer extends AbstractDrawer {

    private ColorRandomizer colorRandomizer;

    public AntDrawer() {
        colorRandomizer = new ColorRandomizer();
    }

    public void draw(Field field, MovementLogger... movementLoggers) {

        for (int i = 0; i < movementLoggers.length; i++) {
            MovementLogger logger = movementLoggers[i];

            Color color = colorRandomizer.getRandomColor();

            context.setStroke(color);
            context.setFill(color);

            drawPath(context, logger);
            drawAntInfo(logger.getOwnerName(), i);
        }
    }

    private void drawAntInfo(String ownerName, int number) {

        double fontSize = context.getFont().getSize();

        context.fillText(
                number + " - " + ownerName,
                10,
                number * fontSize + fontSize);
    }

    private void drawPath(GraphicsContext context, MovementLogger logger) {

        Iterator<Coordinates> path = logger.iterator();

        Coordinates current = null;
        Coordinates previous = null;

        while(path.hasNext()) {
            current = transformCoordinates(path.next());

            if (previous != null)
                context.strokeLine(
                        previous.X,
                        previous.Y,
                        current.X,
                        current.Y);

            previous = current;
        }
    }
}
