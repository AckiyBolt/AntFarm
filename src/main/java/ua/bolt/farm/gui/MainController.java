package ua.bolt.farm.gui;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import ua.bolt.farm.ant.AbstractAnt;
import ua.bolt.farm.ant.DefaultAnt;
import ua.bolt.farm.ant.MovementLogger;
import ua.bolt.farm.field.CoordinatesCache;
import ua.bolt.farm.field.Field;
import ua.bolt.farm.field.FieldBuilder;
import ua.bolt.farm.field.entity.Coordinates;
import ua.bolt.farm.gui.drawing.AbstractDrawer;
import ua.bolt.farm.gui.drawing.AdditionalInfoDrawer;
import ua.bolt.farm.gui.drawing.AntDrawer;
import ua.bolt.farm.gui.drawing.FieldInfoDrawer;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    private List<AbstractDrawer> drawers;
    private Field field;

    @FXML
    private TextField antsCount;
    @FXML
    private TextField fieldSize;
    @FXML
    private TextField startX;
    @FXML
    private TextField startY;
    @FXML
    private TextField targetX;
    @FXML
    private TextField targetY;

    private String fieldSizeStr;
    private String startXStr;
    private String startYStr;
    private String targetXStr;
    private String targetYStr;

    @FXML
    private TableView grid;
    @FXML
    private Canvas canvas;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // init drawers
        drawers = new ArrayList<>();
        drawers.add(new FieldInfoDrawer());
        drawers.add(new AntDrawer());
        drawers.add(new AdditionalInfoDrawer());
    }

    @FXML
    private void handleClickAction(Event event) {

        try {
            if (fieldChanged()) {
                field = buildField();
            }

            ArrayList<AbstractAnt> ants = createAnts(field);
            runThreadsAndWait(ants);

            MovementLogger[] loggers = new MovementLogger[ants.size()];
            for (int i = 0; i < ants.size(); i++) {
                loggers[i] = ants.get(i).getMovementLogger();
            }

            //ObservableList<MovementLogger> data = FXCollections.observableArrayList(loggers);
            for(AbstractDrawer drawer : drawers)
                drawer.draw(canvas, field, loggers);

        } catch (Exception ex) {
            ExceptionDialogProvider.showDialog(ex, ex.getMessage());
        }
    }

    private boolean fieldChanged() {
        boolean result = field == null;

        if (!fieldSize.getText().equals(fieldSizeStr)) {
            fieldSizeStr = fieldSize.getText();
            result = true;
        }
        if (!startX.getText().equals(startXStr)) {
            startXStr = startX.getText();
            result = true;
        }
        if (!startY.getText().equals(startYStr)) {
            startYStr = startY.getText();
            result = true;
        }
        if (!targetX.getText().equals(targetXStr)) {
            targetXStr = targetX.getText();
            result = true;
        }
        if (!targetY.getText().equals(targetYStr)) {
            targetYStr = targetY.getText();
            result = true;
        }
        return result;
    }

    private void runThreadsAndWait(ArrayList<AbstractAnt> ants) throws InterruptedException {

        ArrayList<Thread> threads = new ArrayList<Thread>();

        for (AbstractAnt ant : ants) {
            Thread thread = new Thread(ant);
            threads.add(thread);

            thread.start();
        }

        for (Thread thread : threads)
            if (!thread.isInterrupted())
                thread.join();
    }

    private Field buildField() {

        Integer size = null;
        Coordinates start = null;
        Coordinates target = null;

        try {
            size = Integer.valueOf(fieldSize.getText());

            Integer sx = Integer.valueOf(startX.getText());
            Integer sy = Integer.valueOf(startY.getText());
            Integer tx = Integer.valueOf(targetX.getText());
            Integer ty = Integer.valueOf(targetY.getText());

            if (sx < 0 && sy < 0 && tx < 0 && ty < 0) throw new IllegalArgumentException("Field data must be positive!");

            start = CoordinatesCache.INSTANCE.createOrGet(sx, sy);
            target = CoordinatesCache.INSTANCE.createOrGet(tx, ty);

        } catch (NumberFormatException ex) {
            throw new IllegalArgumentException("Can't parse field data!");
        } catch (IllegalArgumentException ex) {
            throw ex;
        }

        FieldBuilder builder = new FieldBuilder(size);
        builder
                .setStartCoordinates(start)
                .setTargetCoordinates(target);

        return builder.build();
    }

    private ArrayList<AbstractAnt> createAnts(Field field) {

        ArrayList<AbstractAnt> result = new ArrayList<AbstractAnt>();
        Integer count = 0;

        try {
            count = Integer.valueOf(antsCount.getText());

        } catch (NumberFormatException ex) {
            throw new NumberFormatException("Can't parse count of ants!");
        }

        for (int i = 0; i < count; i++) {
            DefaultAnt ant = new DefaultAnt("murashka_" + i, field);
            result.add(ant);
        }

        return result;
    }
}