package ua.bolt.farm.gui.drawing;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import ua.bolt.farm.ant.MovementLogger;
import ua.bolt.farm.field.CoordinatesCache;
import ua.bolt.farm.field.Field;
import ua.bolt.farm.field.entity.Coordinates;

/**
 * Created by ackiybolt on 08.12.14.
 */
public abstract class AbstractDrawer {

    protected int canvasSize;
    protected int fieldSize;
    protected GraphicsContext context;

    public void draw(Canvas canvas, Field field, MovementLogger... movementLoggers) {

        canvasSize = (int) canvas.getHeight();
        fieldSize = field.getSize();
        context = canvas.getGraphicsContext2D();

        draw(field, movementLoggers);
    }

    protected abstract void draw(Field field, MovementLogger ... movementLoggers);

    protected Coordinates transformCoordinates(Coordinates coordinate) {
        return CoordinatesCache.INSTANCE.createOrGet(
                translateCoordinate(coordinate.X),
                translateCoordinate(coordinate.Y)
        );
    }

    private Integer translateCoordinate(Integer coordinate) {

        double onePercentC = canvasSize / 100.0;
        double onePercentF = fieldSize / 100.0;

        double relation = onePercentC / onePercentF;

        return (int) (coordinate * relation);
    }
}
