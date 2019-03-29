package app.workers;

import app.beans.Case;
import app.helpers.Choices;

import java.util.Random;

/**
 * This class is the worker of this game. It interact with the controller.
 *
 * @author Anthony Alonso Lopez
 * @version 1.0.0 First publish
 */
public class Worker implements IMineHuntModel {


    private int GRID_WIDTH;
    private int GRID_HEIGHT;
    private int MINES;
    private Case game[][];
    private int numberOfErrors = 0;
    private int caseToOpen;
    private int nbClicks = 0;

    /**
     * Initialize a new game
     * <p>
     * Reset game state (all grid's cells, error-counter, ...) and
     * place a new random arrangement of mines
     *
     * @param minesNb Number of mines
     */
    @Override
    public void initNewGame(int minesNb, int sizeGameX, int sizeGameY) {
        GRID_WIDTH = sizeGameX;
        GRID_HEIGHT = sizeGameY;
        nbClicks = 0;
        numberOfErrors = 0;
        MINES = minesNb;
        caseToOpen = GRID_WIDTH * GRID_HEIGHT - MINES;
        game = new Case[GRID_WIDTH][GRID_HEIGHT];
        for (int i = 0; i < MINES; i++) {
            placeMines();
        }
        placeBlank();
        //gameToString();
    }

    /**
     * This methods generate mines.
     */
    private void placeMines() {
        Random r = new Random();
        int x = r.nextInt(GRID_WIDTH);
        int y = r.nextInt(GRID_HEIGHT);
        if (game[x][y] == null) {
            game[x][y] = new Case(x, y, Choices.MINE_);
        } else placeMines();
    }

    private void placeBlank() {
        for (int i = 0; i < game.length; i++) {
            for (int j = 0; j < game[i].length; j++) {
                if (game[i][j] == null) {
                    game[i][j] = new Case(i, j, Choices.BLANK);
                }
            }
        }
    }

    private void gameToString() {
        for (int i = 0; i < game.length; i++) {
            System.out.print(" | ");
            for (int j = 0; j < game[i].length; j++) {
                System.out.print(game[i][j].getValue() + " | ");
            }
            System.out.println();
        }
    }

    /**
     * Number of columns in grid
     */
    @Override
    public int gridWidth() {
        return GRID_WIDTH;
    }

    /**
     * Number of rows in grid
     */
    @Override
    public int gridHeight() {
        return GRID_HEIGHT;
    }

    /**
     * Number of mines in grid
     */
    @Override
    public int mines() {
        return MINES;
    }

    /**
     * Error-counter state.
     */
    @Override
    public int errors() {
        return numberOfErrors;
    }

    /**
     * Number of mines in the neighborhood of a cell
     *
     * @param row Cell row index    [ 0..gridHeight()-1 ]
     * @param col Cell column index [ 0..gridWidth()-1  ]
     */
    @Override
    public int neighborMines(int row, int col) {
        int qty = 0;
        if (game[row][col].getValue() != Choices.MINE_) {
            if (row + 1 < game.length) {
                qty = qty + (game[row + 1][col].getValue() == Choices.MINE_ ? 1 : 0);
            }
            if (row - 1 >= 0) {
                qty = qty + (game[row - 1][col].getValue() == Choices.MINE_ ? 1 : 0);
            }
            if (col + 1 < game[0].length) {
                qty = qty + (game[row][col + 1].getValue() == Choices.MINE_ ? 1 : 0);
            }
            if (col - 1 >= 0) {
                qty = qty + (game[row][col - 1].getValue() == Choices.MINE_ ? 1 : 0);
            }
            if (row + 1 < game.length && col + 1 < game[0].length) {
                qty = qty + (game[row + 1][col + 1].getValue() == Choices.MINE_ ? 1 : 0);
            }
            if (row - 1 >= 0 && col + 1 < game[0].length) {
                qty = qty + (game[row - 1][col + 1].getValue() == Choices.MINE_ ? 1 : 0);
            }
            if (row - 1 >= 0 && col - 1 >= 0) {
                qty = qty + (game[row - 1][col - 1].getValue() == Choices.MINE_ ? 1 : 0);
            }
            if (row + 1 < game.length && col - 1 >= 0) {
                qty = qty + (game[row + 1][col - 1].getValue() == Choices.MINE_ ? 1 : 0);
            }
        }
        return qty;
    }

    /**
     * Return true if cell has been opened (clicked)
     *
     * @param row Cell row index    [ 0..gridHeight()-1 ]
     * @param col Cell column index [ 0..gridWidth()-1  ]
     */
    @Override
    public boolean isOpen(int row, int col) {
        return game[row][col].isOpen();
    }

    /**
     * Return true if cell has been flagged (supposed mine position)
     *
     * @param row Cell row index    [ 0..gridHeight()-1 ]
     * @param col Cell column index [ 0..gridWidth()-1  ]
     */
    @Override
    public boolean isFlagged(int row, int col) {
        return game[row][col].isFlag();
    }

    /**
     * Check if game is over.
     * Return true only if all non-mined cells have been opened (clicked)
     *
     * @return game-over state
     */
    @Override
    public boolean isGameOver() {
        return (caseToOpen + errors() - nbClicks) == 0;
    }

    /**
     * Open a cell (user clicked on it).
     * <p>
     * No effect if cell is already open.
     * <p>
     * If a mine is in the cell, the internal error-counter is incremented.
     *
     * @param row Cell row index    [ 0..gridHeight()-1 ]
     * @param col Cell column index [ 0..gridWidth()-1  ]
     * @return true if a mine was in the cell (error-counter incremented)
     */
    @Override
    public boolean open(int row, int col) {
        boolean ok = false;
        if (!isOpen(row, col)) {
            game[row][col].setOpen(true);
            if (game[row][col].getValue() == Choices.MINE_) numberOfErrors++;
            ok = true;
        }
        return ok;
    }

    /**
     * Set flag state of a cell
     *
     * @param row   Cell row index    [ 0..gridHeight()-1 ]
     * @param col   Cell column index [ 0..gridWidth()-1  ]
     * @param state Flag-state of the cell
     */
    @Override
    public void setFlagState(int row, int col, boolean state) {
        if (!isOpen(row, col)) {
            game[row][col].setFlag(state);
        }
    }

    @Override
    public Case[][] getGame() {
        return game;
    }

    @Override
    public void addClick() {
        nbClicks++;
    }

    public int getNbClicks() {
        return nbClicks;
    }

    public void removeOneCaseToOpen() {
        caseToOpen--;
    }
}
