package ua.bolt.farm.gui.drawing;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import ua.bolt.farm.ant.MovementLogger;
import ua.bolt.farm.field.Field;
import ua.bolt.farm.field.entity.Coordinates;

import java.util.*;

/**
 * Created by ackiybolt on 08.12.14.
 */
public class AntDrawer extends AbstractDrawer {

    private List<Color> colors;
    private HashSet<Color> usedColors;

    private Random rnd = new Random(System.nanoTime());

    public AntDrawer() {
        // Create the magic cache of rainbow...
        java.lang.reflect.Field[] fields = Color.class.getFields();
        ArrayList<Color> colors = new ArrayList<Color>(fields.length);

        for (java.lang.reflect.Field field : fields) {
            try {
                Object obj = field.get(Color.class);
                if (obj instanceof Color) {
                    Color color = (Color) obj;
                    if (color.isOpaque())
                        colors.add(color);
                }

            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        this.colors = Collections.<Color>unmodifiableList(colors);
        this.usedColors = new HashSet<Color>(colors.size());
    }

    public void draw(Field field, MovementLogger... movementLoggers) {

        usedColors.clear();

        for (int i = 0; i < movementLoggers.length; i++) {
            MovementLogger logger = movementLoggers[i];

            Color color = getRandomColor();

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

    private Color getRandomColor() {

        if (usedColors.size() > colors.size() / 1.4)
            usedColors.clear();

        Color chosenColor = colors.get(rnd.nextInt(colors.size()));
        if (usedColors.contains(chosenColor))
            return getRandomColor();
        else
            usedColors.add(chosenColor);
        return chosenColor;
    }

    private void drawPath(GraphicsContext context, MovementLogger logger) {

        Iterator<Coordinates> path = logger.getLastSession().iterator();

        Coordinates current = null;
        Coordinates previous = null;

        while (path.hasNext()) {
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
