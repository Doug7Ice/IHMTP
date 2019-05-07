package app;

import app.beans.Annotation;
import app.ihm.controllers.Controller;
import app.workers.Worker;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.Timestamp;

/**
 * @author Anthony Alonso Lopez
 */
public class Main extends Application {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ihm/views/View.fxml"));
        Parent root = loader.load();
        // Récupére le controleur
        Controller ctrl = loader.getController();
        ctrl.stage = stage;
        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.setTitle("S03");
        stage.show();
    }

}
