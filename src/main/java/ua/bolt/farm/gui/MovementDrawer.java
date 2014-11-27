package ua.bolt.farm.gui;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import ua.bolt.farm.ant.MovementLogger;
import ua.bolt.farm.field.Coordinates;
import ua.bolt.farm.field.Field;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Random;

/**
 * Created by ackiybolt on 20.11.14.
 */
public class MovementDrawer {

    private int wight;
    private int height;

    public void initResolution (int wight, int height) {

        if (wight < 0 || height < 0)
            throw new IllegalArgumentException("Wrong resolution. Must be positive");

        this.wight = wight;
        this.height = height;
    }

    public void draw (GraphicsContext gc, Field field, MovementLogger ... movementLoggers) {
        gc.clearRect(0, 0, wight, height);
        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, wight, height);
        gc.setFill(Color.BLACK);
        gc.setLineWidth(1);

        byte [] colors = resolveColors(movementLoggers.length);

        for (int i = 0; i < movementLoggers.length; i++) {
            MovementLogger logger = movementLoggers[i];
            drawPath(gc, logger, Color.rgb(
                    Math.abs(2 * colors[(i * 3)]),
                    Math.abs(2 * colors[(i * 3) + 1]),
                    Math.abs(2 * colors[(i * 3) + 2])));
        }

        Coordinates start = field.getStart().coordinates;
        Coordinates target = field.getTarget().coordinates;

        Coordinates transformedCoord = transformCoordinates(start);
        gc.fillOval(transformedCoord.X - 3, transformedCoord.Y - 3, 5, 5);
        transformedCoord = transformCoordinates(target);
        gc.fillOval(transformedCoord.X - 3, transformedCoord.Y - 3, 5, 5);

        gc.fillText("Start", start.X - 20, start.Y + 20);
        gc.fillText("Target", target.X - 20, target.Y + 20);
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

    private byte [] resolveColors(int pathsCount) {
        Random rnd = new Random(System.nanoTime());
        byte [] colors = new byte[pathsCount * 3];
        rnd.nextBytes(colors);
        return colors;
    }

    private Coordinates transformCoordinates (Coordinates coordinate) {
        return coordinate;
//        return new Coordinates(
//                (int)(coordinate.X + ((this.wight / coordinate.X)) * 0.01),
//                (int)(coordinate.Y + ((this.height / coordinate.Y)) * 0.01)
//        );
    }
}
