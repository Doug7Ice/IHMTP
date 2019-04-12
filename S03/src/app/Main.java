package app;

import app.ihm.controllers.Controller;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

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
        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.setTitle("S01");
        stage.show();

        stage.setOnCloseRequest(e -> ctrl.quitter());

    }

}
