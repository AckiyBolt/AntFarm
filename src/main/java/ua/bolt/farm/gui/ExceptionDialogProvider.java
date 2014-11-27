package ua.bolt.farm.gui;

import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Created by ackiybolt on 27.11.14.
 */
public class ExceptionDialogProvider {

    public static void showDialog(Exception ex) {
        showDialog(ex, null);
    }

    public static void showDialog(Exception ex, String message) {

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("An unexpected error occurred!");
        alert.setHeaderText(message == null ? "Houston, we have a problem!" : message);
        alert.setContentText(
                ex.getClass().getName() + " : " +
                        ex.getLocalizedMessage() + "\n" +
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
