package app.ihm.controllers;

import app.beans.Case;
import app.helpers.Choices;
import app.helpers.DateTimeLib;
import app.helpers.ViewLib;
import app.ihm.views.DemineurIHM;
import app.workers.IMineHuntModel;
import app.workers.Worker;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.WindowEvent;

/**
 * This class is the controller of this game. It interact with the view and the worker
 *
 * @author Anthony Alonso Lopez
 * @version 1.0.0 First publish
 */
public class Controller {
    private boolean isShowAllMineToggle;
    private IMineHuntModel wrk;
    private DemineurIHM ihm;
    private int gridGameSizeX = 10;
    private int gridGameSizeY = 10;
    private int mineQuantity = 10;

    private GridPane gridGame;
    private int buttonSizeX = 35;
    private int buttonSizeY = 35;

    /**
     * This method is call on init of the game, it link the controller to the worker (threw IMineHuntModel)
     */
    public void initialize(DemineurIHM demineurIHM) {
        ihm = demineurIHM;
        ihm.changeSizeWindows();
        gridGame = ihm.getGridButtons();

        wrk = new Worker();
        startGame();
    }

    /**
     * This method init the game.
     */
    private void startGame() {
        isShowAllMineToggle = false;
        //Clear the Vbox from the gridgame
        ihm.clearGameGrid();
        //Call worker to create a new game
        wrk.initNewGame(mineQuantity, gridGameSizeX, gridGameSizeY);
        //set text field errors to 0
        ihm.setTxtNbErrors(wrk.errors() + "");
        //set text field clicks to 0
        ihm.setTxtNbClicks(wrk.getNbClicks() + "");
        //create grid and display it
        createGameGrid();
        DateTimeLib.chronoReset();
    }

    /**
     * This method create the game grid and display it. It will display it in the Vbox.
     */
    private void createGameGrid() {
        //Get the Game game from the worker
        Case[][] theGame = wrk.getGame();
        //Loop insert row
        for (int i = 0; i < theGame.length; i++) {
            //Loop insert column
            for (int j = 0; j < theGame[i].length; j++) {
                //Create the button
                Button btn = new Button();
                //Set a default background
                setButtonBackgroundDefault(btn);
                //Set MouseEvent Listener link to gameButtonEventListener
                btn.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> gameButtonEventListener(event, btn));
                //Set size of button min
                btn.setMinSize(buttonSizeX, buttonSizeY);
                //Set size of button max
                btn.setMaxSize(buttonSizeX, buttonSizeY);
                //Add a case to the grid with the button
                gridGame.addColumn(j, btn);
            }
        }
        //Set alignment to the grid
        gridGame.setAlignment(Pos.CENTER);
        //Set a gap between horizontal node (Button)
        gridGame.setHgap(4);
        //Set a gap between vertical node (Button)
        gridGame.setVgap(4);
        //Set visible lines between row and column
        gridGame.setVisible(true);
    }

    private void showAllZero(int x, int y) {
        Button btn = getNodeFromGridPane(x, y);
        Case selectedCase = wrk.getGame()[x][y];
        if (!selectedCase.isOpen() && !selectedCase.isFlag() && selectedCase.getValue() != Choices.MINE_) {
            wrk.open(x, y);

            setButtonBackgroundNormal(btn);
            int nbMine = wrk.neighborMines(x, y);
            btn.setText(nbMine + " ");
            if (nbMine == 0) {
                if (x + 1 < getGridGameSizeX() && y + 1 < getGridGameSizeY()) {
                    if (!wrk.isOpen(x + 1, y + 1) && !wrk.isFlagged(x + 1, y + 1)) {
                        showAllZero(x + 1, y + 1);
                        wrk.removeOneCaseToOpen();
                    }
                }
                if (x - 1 >= 0 && y - 1 >= 0) {
                    if (!wrk.isOpen(x - 1, y - 1) && !wrk.isFlagged(x - 1, y - 1)) {
                        showAllZero(x - 1, y - 1);
                        wrk.removeOneCaseToOpen();
                    }
                }
                if (x + 1 < getGridGameSizeX()) {
                    if (!wrk.isOpen(x + 1, y) && !wrk.isFlagged(x + 1, y)) {
                        showAllZero(x + 1, y);
                        wrk.removeOneCaseToOpen();
                    }
                }
                if (x - 1 >= 0) {
                    if (!wrk.isOpen(x - 1, y) && !wrk.isFlagged(x - 1, y)) {
                        showAllZero(x - 1, y);
                        wrk.removeOneCaseToOpen();
                    }
                }
                if (x + 1 < getGridGameSizeX() && y - 1 >= 0) {
                    if (!wrk.isOpen(x + 1, y - 1) && !wrk.isFlagged(x + 1, y - 1)) {
                        showAllZero(x + 1, y - 1);
                        wrk.removeOneCaseToOpen();
                    }
                }
                if (x - 1 >= 0 && y + 1 < getGridGameSizeY()) {
                    if (!wrk.isOpen(x - 1, y + 1) && !wrk.isFlagged(x - 1, y + 1)) {
                        showAllZero(x - 1, y + 1);
                        wrk.removeOneCaseToOpen();
                    }
                }
                if (y + 1 < getGridGameSizeY()) {
                    if (!wrk.isOpen(x, y + 1) && !wrk.isFlagged(x, y + 1)) {
                        showAllZero(x, y + 1);
                        wrk.removeOneCaseToOpen();
                    }
                }
                if (y - 1 >= 0) {
                    if (!wrk.isOpen(x, y - 1) && !wrk.isFlagged(x, y - 1)) {
                        showAllZero(x, y - 1);
                        wrk.removeOneCaseToOpen();
                    }
                }
            }
        }
    }

    /**
     * This method is called when the game is quit.
     */
    public void quitCall(WindowEvent e) {
        Alert popup = ViewLib.displayPopupConfirm("Fermer l'application", null, "Êtes-vous sûr de vouloir fermer l'application ?");
        if (popup.showAndWait().get() != ButtonType.OK) {
            e.consume();
        }
    }

    /**
     * This method listener trigger when the user press the ShowMines button, it will reveal in color the position of all the mines.
     */
    public void btnShowMinesHit() {
        Case[][] theGame = wrk.getGame();
        for (int i = 0; i < theGame.length; i++) {
            for (int j = 0; j < theGame[i].length; j++) {
                if (theGame[i][j].getValue() == Choices.MINE_ && !theGame[i][j].isOpen() && !theGame[i][j].isFlag()) {
                    Button selected = getNodeFromGridPane(i, j);
                    if (selected != null) {
                        if (isShowAllMineToggle) setButtonBackgroundDefault(selected);
                        else setButtonBackgroundShowMine(selected);
                    }
                }
            }
        }
        isShowAllMineToggle = !isShowAllMineToggle;
    }

    /**
     * This method is used in btnShowMinesHit, it will get the button ask with the param of position in array.
     *
     * @param col int contain the column position
     * @param row int contain the row position
     * @return null if no Button found, else the Button asked.
     */
    private Button getNodeFromGridPane(int row, int col) {
        for (Node node : gridGame.getChildren()) {
            if (GridPane.getRowIndex(node) == row && GridPane.getColumnIndex(node) == col) {
                return (Button) node;
            }
        }
        return null;
    }

    private void gameButtonEventListener(MouseEvent event, Button btn) {
        if (!wrk.isGameOver()) {
            if (event.getButton() == MouseButton.SECONDARY) {
                gameButtonSecondary(btn);
            } else if (event.getButton() == MouseButton.PRIMARY) {
                gameButtonPrimary(btn);
            }
        }
    }

    private void gameButtonSecondary(Button btn) {
        int y = gridGame.getColumnIndex(btn);
        int x = gridGame.getRowIndex(btn);
        Case selectedCase = wrk.getGame()[x][y];
        if (wrk.isFlagged(x, y) && !wrk.isOpen(x, y)) {
            if (isShowAllMineToggle && selectedCase.getValue() == Choices.MINE_) setButtonBackgroundShowMine(btn);
            else setButtonBackgroundDefault(btn);
        } else if (!wrk.isOpen(x, y)) {
            BackgroundImage bImage = new BackgroundImage(new
                    Image("/ressources/images/flag.jpg", buttonSizeX, buttonSizeY, false, true),
                    BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                    BackgroundSize.DEFAULT);
            btn.setBackground(new Background(bImage));
        }
        wrk.setFlagState(x, y, !wrk.isFlagged(x, y));
    }

    private void gameButtonPrimary(Button btn) {
        int y = gridGame.getColumnIndex(btn);
        int x = gridGame.getRowIndex(btn);
        Case selectedCase = wrk.getGame()[x][y];
        if (wrk.isFlagged(x, y)) {
            setButtonBackgroundDefault(btn);
            wrk.setFlagState(x, y, false);
        } else {
            if (selectedCase.getValue() == Choices.MINE_ && !selectedCase.isOpen()) {
                if (wrk.getNbClicks() == 0) {
                    startGame();
                    return;
                }
                wrk.addClick();
                wrk.open(x, y);
                setButtonBackgroundMine(btn);
                ihm.setTxtNbErrors(wrk.errors() + "");

            } else if (selectedCase.getValue() == Choices.BLANK && !selectedCase.isOpen()) {
                wrk.addClick();
                showAllZero(x, y);
            }
            //Win Check
            if (wrk.isGameOver()) {
                if (wrk.errors() > 0)
                    ViewLib.displayPopup("Fin du jeu !", "Vos avez fait " + wrk.errors() + " erreur(s) et joué pendant " + DateTimeLib.chronoStringElapsedTime() + " secondes", "Vous pouvez relancer une partie avec le bouton ou changer les variables dans le menu jeu", Alert.AlertType.WARNING);
                else
                    ViewLib.displayPopup("Fin du jeu !", "Vos avez fait aucune erreur !!! Vous avez joué pendant " + DateTimeLib.chronoStringElapsedTime() + " secondes", "Vous pouvez relancer une partie avec le bouton ou changer les variables dans le menu jeu", Alert.AlertType.INFORMATION);
            }
        }
        ihm.setTxtNbClicks(wrk.getNbClicks() + "");
    }

    private void setButtonBackgroundDefault(Button btn) {
        btn.setBackground(new Background(new BackgroundFill(Color.rgb(40, 80, 70), CornerRadii.EMPTY, Insets.EMPTY)));
    }

    private void setButtonBackgroundMine(Button btn) {
        BackgroundImage bImage = new BackgroundImage(
                new Image("/ressources/images/mine.png", buttonSizeX, buttonSizeY, false, true),
                BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);
        btn.setBackground(new Background(bImage));
    }

    private void setButtonBackgroundNormal(Button btn) {
        btn.setBackground(new Background(new BackgroundFill(Color.rgb(220, 220, 220), CornerRadii.EMPTY, Insets.EMPTY)));
    }

    private void setButtonBackgroundShowMine(Button btn) {
        btn.setBackground(new Background(new BackgroundFill(Color.rgb(255, 0, 0), CornerRadii.EMPTY, Insets.EMPTY)));
    }


    private int getGridGameSizeX() {
        return gridGameSizeX;
    }

    public void setGridGameSizeX(int gridGameSizeX) {
        this.gridGameSizeX = gridGameSizeX;
    }

    private int getGridGameSizeY() {
        return gridGameSizeY;
    }

    public void setGridGameSizeY(int gridGameSizeY) {
        this.gridGameSizeY = gridGameSizeY;
    }


    public void setMineQuantity(int mineQuantity) {
        this.mineQuantity = mineQuantity;
    }
}