package org.sudoku;

import javax.swing.JFrame;
import java.util.Arrays;

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
        System.out.println("Hello from GUI");
        new Cell(new int[]{1, 2}, 10);
        Solver solver = new Solver(data);
        int solvable = solver.solve();
        while (solver.hasNextLogEntry())
            System.out.println(solver.nextLogEntry());

        System.out.println(solvable);
        System.out.println(Arrays.deepToString(solver.getBoard()));
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
        return true;
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