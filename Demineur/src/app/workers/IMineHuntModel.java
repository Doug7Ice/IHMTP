package app.workers;

import app.beans.Case;
import app.helpers.Choices;

/**
 * Cette interface définit les services "métier" de l'application.
 *
 * @author Anthony Alonso Lopez & HEIA
 * @version 1.0.0 First publish
 */

public interface IMineHuntModel {

    /**
     * Initialize a new game
     *
     * Reset game state (all grid's cells, error-counter, ...) and
     * place a new random arrangement of mines
     *
     * @param minesNb Number of mines
     */
    void initNewGame(int minesNb, int gameSizeX, int gameSizeY);

    /**
     * Number of columns in grid
     */
    int gridWidth();

    /**
     * Number of rows in grid
     */
    int gridHeight();

    /**
     * Number of mines in grid
     */
    int mines();

    /**
     * Error-counter state.
     */
    int errors();

    /**
     * Number of mines in the neighborhood of a cell
     *
     * @param row Cell row index    [ 0..gridHeight()-1 ]
     * @param col Cell column index [ 0..gridWidth()-1  ]
     */
    int neighborMines(int row, int col);

    /**
     * Return true if cell has been opened (clicked)
     *
     * @param row Cell row index    [ 0..gridHeight()-1 ]
     * @param col Cell column index [ 0..gridWidth()-1  ]
     */
    boolean isOpen(int row, int col);

    /**
     * Return true if cell has been flagged (supposed mine position)
     *
     * @param row Cell row index    [ 0..gridHeight()-1 ]
     * @param col Cell column index [ 0..gridWidth()-1  ]
     */
    boolean isFlagged(int row, int col);

    /**
     * Check if game is over.
     * Return true only if all non-mined cells have been opened (clicked)
     *
     * @return game-over state
     */
    boolean isGameOver();

    /**
     * Open a cell (user clicked on it).
     *
     * No effect if cell is already open.
     *
     * If a mine is in the cell, the internal error-counter is incremented.
     *
     * @param  row   Cell row index    [ 0..gridHeight()-1 ]
     * @param  col   Cell column index [ 0..gridWidth()-1  ]
     * @return true if a mine was in the cell (error-counter incremented)
     */
    boolean open(int row, int col);

    /**
     * Set flag state of a cell
     *
     * @param row   Cell row index    [ 0..gridHeight()-1 ]
     * @param col   Cell column index [ 0..gridWidth()-1  ]
     * @param state Flag-state of the cell
     */
    void setFlagState(int row, int col, boolean state);

    Case[][] getGame();

    void addClick();
    int getNbClicks();
    void removeOneCaseToOpen();
}