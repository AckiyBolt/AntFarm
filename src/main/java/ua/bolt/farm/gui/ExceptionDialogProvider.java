package ua.bolt.farm.gui;

import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Created by ackiybolt on 27.11.14.
 */
public class ExceptionDialogProvider {

    public static void showDialog (Exception ex) {

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("An unexpected error occurred!");
        alert.setHeaderText("Houston, we have a problem!");
        alert.setContentText(
                ex.getClass().getName() + " : " +
                ex.getLocalizedMessage() +  "\n" +
                ex.getStackTrace()[0]
        );

        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        ex.printStackTrace(pw);
        String exceptionText = sw.toString();

        Label label = new Label("The exception stacktrace was:");

        TextArea textArea = new TextArea(exceptionText);
        textArea.setEditable(false);
        textArea.setWrapText(true);

        textArea.setMaxWidth(Double.MAX_VALUE);
        textArea.setMaxHeight(Double.MAX_VALUE);
        GridPane.setVgrow(textArea, Priority.ALWAYS);
        GridPane.setHgrow(textArea, Priority.ALWAYS);

        GridPane expContent = new GridPane();
        expContent.setMaxWidth(Double.MAX_VALUE);
        expContent.add(label, 0, 0);
        expContent.add(textArea, 0, 1);

        alert.getDialogPane().setExpandableContent(expContent);

        alert.showAndWait();
    }

}
