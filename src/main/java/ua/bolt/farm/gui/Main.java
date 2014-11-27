package ua.bolt.farm.gui;

import javafx.application.Application;
import javafx.stage.Stage;
import ua.bolt.farm.gui.SatgeImpl;

public class Main extends Application {

    @Override
    public void start ( Stage stage )
            throws Exception {

        SatgeImpl.setStage(stage);
        SatgeImpl.MAIN_INSTANCE.showScene();
    }

    public static void main ( String[] args ) {
        launch( args );
    }
}