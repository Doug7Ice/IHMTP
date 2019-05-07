/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package commande;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;


/**
 *
 * @author leore
 */
public class Commande extends Application {
    public BorderPane root = new BorderPane();
    public GridPane gZone = new GridPane();
    public HBox bZone = new HBox();
    private Label lblT = new Label("< Commande >");
    private Button[][] tCmd = new Button[2][3];
    private Button btnO = new Button("OK");
    private Button btnC = new Button("Cancel");
    private CheckBox cb = new CheckBox("carte fidelité");    

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Commande 1.0");
        root.setPadding(new Insets(10));
        
        lblT.setFont(Font.font("SansSerif", FontWeight.BOLD, 20));
        lblT.setTextFill(Color.DARKGREEN);
        root.setTop(lblT);
        
        for( int row=0; row<tCmd.length; row++){
            for( int col=0; col<tCmd[row].length; col++){
                tCmd[row][col] = new Button("Cmd "+row+" /"+col);
                gZone.add(tCmd[row][col], col, row);
            }
        }
        
        gZone.setPadding(new Insets(5,10,5,10));
        gZone.setBorder(new Border(new BorderStroke( Color .RED,
                BorderStrokeStyle.SOLID,
                new CornerRadii(5),
                new BorderWidths(1),
                new Insets(20))));
        gZone.setVgap(8);
        gZone.setHgap(8);
        root.setCenter(gZone);
        
        bZone.setSpacing(18);
        bZone.setAlignment(Pos.CENTER);
        bZone.getChildren().addAll(cb,btnO, btnC);
        btnC.setDisable(true);
        btnO.setDisable(true);
        cb.setIndeterminate(false);
        root.setBottom(bZone);
        
        BorderPane.setAlignment(lblT, Pos.CENTER);
        
        Scene s = new Scene(root);
        primaryStage.setScene(s);
        primaryStage.show();

    }
    
    public static void main(String[] args){
        launch(args);
    }
}
