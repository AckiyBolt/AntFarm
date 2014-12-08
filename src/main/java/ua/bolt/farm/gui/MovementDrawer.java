package ua.bolt.farm.gui;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import ua.bolt.farm.ant.MovementLogger;
import ua.bolt.farm.field.CoordinatesCache;
import ua.bolt.farm.field.Field;
import ua.bolt.farm.field.entity.Coordinates;

import java.util.*;

/**
 * Created by ackiybolt on 20.11.14.
 */
public class MovementDrawer {

    private List<Color> colors;
    private HashSet<Color> usedColors;
    private Random rnd = new Random(System.nanoTime());

    private int canvasSize;
    private int fieldSize;

    public MovementDrawer() {

        // Create the magic cache...
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

    public void draw(Canvas canvas, Field field, MovementLogger... movementLoggers) {

        GraphicsContext gc = canvas.getGraphicsContext2D();
        canvasSize = (int) canvas.getHeight();
        fieldSize = field.getSize();


        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        gc.setFill(Color.LIGHTGRAY);
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        gc.setFill(Color.BLACK);
        gc.setLineWidth(1);

        for (int i = 0; i < movementLoggers.length; i++) {
            MovementLogger logger = movementLoggers[i];
            drawPath(gc, logger, getRandomColor());
        }
        usedColors.clear();

        Coordinates start = transformCoordinates(field.getStart().coordinates);
        Coordinates target = transformCoordinates(field.getTarget().coordinates);

        gc.fillOval(start.X - 3, start.Y - 3, 5, 5);
        gc.fillOval(target.X - 3, target.Y - 3, 5, 5);

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
            current = transformCoordinates(path.next());

            if (previous != null) {
                gc.strokeLine(
                        previous.X,
                        previous.Y,
                        current.X,
                        current.Y);
            }

            previous = current;
        }
    }

    private Coordinates transformCoordinates(Coordinates coordinate) {
        return CoordinatesCache.INSTANCE.createOrGet(
                translateCoordinate(coordinate.X),
                translateCoordinate(coordinate.Y)
        );
    }

    private Integer translateCoordinate(int coordinate) {

        double onePercentC = canvasSize / 100.0;
        double onePercentF = fieldSize / 100.0;

        double relation = onePercentC / onePercentF;

        return (int) (coordinate * relation);
    }
}
