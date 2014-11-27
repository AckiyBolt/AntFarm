package ua.bolt.farm.gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
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
            main();
        } catch (Exception ex) {
            ExceptionDialogProvider.showDialog(ex);
        }
    }

    private static final int FIELD_SIZE = 801;
    private static final int ANT_COUNT = 5;


    private static Coordinates start  = new Coordinates(30, FIELD_SIZE/4-1);
    private static Coordinates target = new Coordinates(798, FIELD_SIZE/4-1);



    public void main () throws Exception {

        Field field = buildField();
        ArrayList<DefaultAnt> ants = new ArrayList<DefaultAnt>();
        ArrayList<Thread> threads = new ArrayList<Thread>();

        for (int i = 0; i < ANT_COUNT; i++) {
            DefaultAnt ant = new DefaultAnt("murashka_" + i, field);
            ants.add(ant);

            Thread thread = new Thread(ant);
            threads.add(thread);

            thread.start();
        }

        for (Thread thread : threads)
            thread.join();

        MovementLogger[] loggers = new MovementLogger[ants.size()];
        for (int i = 0; i < ants.size(); i++) {
            loggers[i] = ants.get(i).getMovementLogger();
        }
        ObservableList<MovementLogger> data = FXCollections.observableArrayList(loggers);
        drawer.draw(canvas.getGraphicsContext2D(), field, loggers);
    }

    private Field buildField() {
        FieldBuilder builder = new FieldBuilder(FIELD_SIZE);
        builder
                .setStartCoordinates(start)
                .setTargetCoordinates(target);

        return builder.build();
    }
}