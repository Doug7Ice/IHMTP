package app.ihm.controllers;

import app.helpers.Choices;
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

    private Choices[][] game;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        wrk = new Worker();
        game = new Choices[3][3];
        game = wrk.fill(game);
        int row = 0;
        int column = 0;
        int compteur = 0;
        for (Node child : monTableauMorpion.getChildren()){
            if (child instanceof Label) {
                row = compteur / 3;
                column = compteur % 3;
                Label temp = (Label) child;
                System.out.println(row + " " + column);
                temp.setText(game[row][column].getValue());
                compteur++;
            }
        }
    }

    public void quitter() {
        System.out.println("app is closing !");
        System.exit(0);
    }
}
