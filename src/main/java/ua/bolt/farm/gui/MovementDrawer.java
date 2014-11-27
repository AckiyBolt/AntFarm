package ua.bolt.farm.gui;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import ua.bolt.farm.ant.MovementLogger;
import ua.bolt.farm.field.Coordinates;
import ua.bolt.farm.field.Field;

import java.util.*;

/**
 * Created by ackiybolt on 20.11.14.
 */
public class MovementDrawer {

    private int wight;
    private int height;
    private List<Color> colors;
    private HashSet<Color> usedColors;
    private Random rnd = new Random(System.nanoTime());

    public void initResolution(int wight, int height) {

        if (wight < 0 || height < 0)
            throw new IllegalArgumentException("Wrong resolution. Must be positive");

        this.wight = wight;
        this.height = height;

        // Create the magic cache...
        java.lang.reflect.Field[] fields = Color.class.getFields();
        ArrayList<Color> colors = new ArrayList<Color>(fields.length + fields.length / 4);

        for (java.lang.reflect.Field field: fields) {
            try {
                Object obj = field.get(Color.class);
                if ( obj instanceof Color)
                    colors.add((Color)obj);

            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        this.colors = Collections.<Color>unmodifiableList(colors);
        this.usedColors = new HashSet<Color>(colors.size());
    }

    public void draw(GraphicsContext gc, Field field, MovementLogger... movementLoggers) {
        gc.clearRect(0, 0, wight, height);
        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, wight, height);
        gc.setFill(Color.BLACK);
        gc.setLineWidth(1);

        for (int i = 0; i < movementLoggers.length; i++) {
            MovementLogger logger = movementLoggers[i];
            drawPath(gc, logger, getRandomColor());
        }
        usedColors.clear();

        Coordinates start = field.getStart().coordinates;
        Coordinates target = field.getTarget().coordinates;

        Coordinates transformedCoord = transformCoordinates(start);
        gc.fillOval(transformedCoord.X - 3, transformedCoord.Y - 3, 5, 5);
        transformedCoord = transformCoordinates(target);
        gc.fillOval(transformedCoord.X - 3, transformedCoord.Y - 3, 5, 5);

        gc.fillText("Start", start.X - 20, start.Y + 20);
        gc.fillText("Target", target.X - 20, target.Y + 20);
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

    private void drawPath(GraphicsContext gc, MovementLogger logger, Color color) {

        Iterator<Coordinates> path = logger.getLastSession().iterator();

        Coordinates current = null;
        Coordinates previous = null;

        gc.setStroke(color);

        while (path.hasNext()) {
            current = path.next();

            if (previous != null) {
                gc.strokeLine(previous.X, previous.Y, current.X, current.Y);
            }

            previous = current;
        }
    }

    private Coordinates transformCoordinates(Coordinates coordinate) {
        return coordinate;
//        return new Coordinates(
//                (int)(coordinate.X + ((this.wight / coordinate.X)) * 0.01),
//                (int)(coordinate.Y + ((this.height / coordinate.Y)) * 0.01)
//        );
    }
}
