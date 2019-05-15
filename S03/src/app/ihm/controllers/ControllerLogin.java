package app.ihm.controllers;

import app.helpers.ViewLib;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class ControllerLogin {

    public Controller mainCtrl;

    public void quitter(){
        mainCtrl.quitter();
    }

    public void login(){
        mainCtrl.launchProfIHM();
    }


}
