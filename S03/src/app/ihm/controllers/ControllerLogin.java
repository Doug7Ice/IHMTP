package app.ihm.controllers;

import app.helpers.ViewLib;
import app.workers.WorkerActiveDirectoryLDAP;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;

public class ControllerLogin {

    public Controller mainCtrl;
    private WorkerActiveDirectoryLDAP wrkLDAP;
    @FXML
    public TextField txtEmail;


    public ControllerLogin(){
        wrkLDAP = new WorkerActiveDirectoryLDAP();
    }

    public void quitter(){
        mainCtrl.quitter();
    }

    public void login(){
        System.out.println(txtEmail.getText());
        if(wrkLDAP.isProf(txtEmail.getText())){
            mainCtrl.launchProfIHM();
        }
    }

    public void loginGuest(){
        mainCtrl.launchProfIHM();
        mainCtrl.unableAnnotation();
    }


}
