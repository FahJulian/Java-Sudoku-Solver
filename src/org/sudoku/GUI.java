package org.sudoku;

import javax.swing.JFrame;

import org.sudoku.Cell;

public class GUI {
    /**
     * Holds the main Window and the Sudoku Grid, runs the Game Loop.
     */

    private JFrame window;

    private int[][] data, initialData, solutionData;
    private boolean running, runningSimulation, simulationFinished;
    private Cell[][] cells;
    private Cell selectedCell;

    // TODO: Add some kind of anonymous key/mouse listener

    public GUI(int[][] data) {

    }

    /** 
     * Start the game loop. Should be called immediately after Initialization.
     */
    public void run() {

    }

    private void update() {

    }

    private void render() {

    }

    private void setValue(int row, int col, int value) {

    }

    private boolean isValueCorrect(int row, int col, int value) {

    }

    private void selectCellAt(int row, int col) {

    }

    private void confirmProvisional() {

    }

    private void startSimulation() {

    }

    private void cancelSimulation() {

    }
}