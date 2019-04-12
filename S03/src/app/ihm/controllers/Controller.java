package app.ihm.controllers;

import app.ihm.models.ViewModel;
import app.workers.Worker;
import app.workers.WorkerItf;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Anthony Alonso Lopez
 */
public class Controller implements Initializable {


    private WorkerItf wrk;
    private ViewModel model;
    @FXML
    private GridPane monTableauMorpion;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        wrk = new Worker();
    }

    public void quitter() {
        System.out.println("app is closing !");
        System.exit(0);
    }
}
