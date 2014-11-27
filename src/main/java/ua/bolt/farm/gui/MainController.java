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
import ua.bolt.farm.field.Coordinates;
import ua.bolt.farm.field.Field;
import ua.bolt.farm.util.FieldBuilder;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    private MovementDrawer drawer;
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

    @FXML
    private TableView grid;
    @FXML
    private Canvas canvas;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        drawer = new MovementDrawer();
    }

    @FXML
    private void handleClickAction(Event event) {

        drawer.initResolution((int) canvas.getWidth(), (int) canvas.getHeight());

        try {
            if (field == null)
                field = buildField();

            ArrayList<AbstractAnt> ants = createAnts(field);
            runThreadsAndWait(ants);

            MovementLogger[] loggers = new MovementLogger[ants.size()];
            for (int i = 0; i < ants.size(); i++) {
                loggers[i] = ants.get(i).getMovementLogger();
            }

            //ObservableList<MovementLogger> data = FXCollections.observableArrayList(loggers);
            drawer.draw(canvas.getGraphicsContext2D(), field, loggers);


        } catch (Exception ex) {
            ExceptionDialogProvider.showDialog(ex, ex.getMessage());
        }
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
            start = new Coordinates(Integer.valueOf(startX.getText()), Integer.valueOf(startY.getText()));
            target = new Coordinates(Integer.valueOf(targetX.getText()), Integer.valueOf(targetY.getText()));

        } catch (NumberFormatException ex) {
            throw new NumberFormatException("Can't parse field data!");
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