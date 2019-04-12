/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package morpion;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 *
 * @author leore
 */
public class Morpion extends Application {
    public BorderPane root = new BorderPane();
    public GridPane gZone = new GridPane();
    private Label[][] tLabels = new Label[3][3];
    
    @Override
    public void start(Stage primaryStage) {
        
        
        Scene scene = new Scene(root, 100, 100);
        
        primaryStage.setTitle("Morpion");
        
        for( int row=0; row<tLabels.length; row++){
            for( int col=0; col<tLabels[row].length; col++){
                int random = (int) Math.floor(Math.random() * 3) + 1;
                String lbltxt = null;
                if (random == 1){
                    lbltxt = "X";
                }
                else if (random == 2){
                    lbltxt = " ";
                }
                else{
                    lbltxt = "O";
                }
                tLabels[row][col] = new Label(lbltxt);
                gZone.add(tLabels[row][col], col, row);
            }
        }
        gZone.setPadding(new Insets(5,10,5,10));
        gZone.setVgap(8);
        gZone.setHgap(8);
        root.setCenter(gZone);
        
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
