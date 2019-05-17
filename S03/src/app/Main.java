package app;

import app.beans.Annotation;
import app.helpers.ViewLib;
import app.ihm.controllers.Controller;
import app.workers.Worker;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
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
        ctrl.stage = stage;
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Groupe 3 - Série 3: Lecteur et Annnotateur Multimedia");
        stage.show();
        stage.setOnCloseRequest(event -> {
            Alert popup = ViewLib.displayPopupConfirm("Fermer l'application", null, "Êtes-vous sûr de vouloir fermer l'application ?");
            if (popup.showAndWait().get() != ButtonType.OK) {
                event.consume();
            }
            else {
                ctrl.quitter();
            }
        });
        ///ctrl.setFullscreen();
        //Annotation a = new Annotation();
    }

}
