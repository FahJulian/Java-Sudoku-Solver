package org.sudoku;

import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Color;

public class GUI extends JPanel {
    private static final long serialVersionUID = 1;

    /**
     * Holds the main Window and the Sudoku Grid, runs the Game Loop.
     */
    private static final String TITLE = "Sudoku Solver";
    private static final int WIDTH = Cell.SIZE * 9 + 4, HEIGHT = Cell.SIZE * 9 + 4;
    private static final int FPS = 60;
    private static final int FRAME_TIME = 1000 / FPS;

    private final JFrame window;

    private int[][] data, initialData, solutionData;
    private boolean running, runningSimulation, simulationFinished;
    private Solver solver;
    private Cell[][] cells;
    private Cell selectedCell;

    public GUI(int[][] data) {
        this.data = deepcopy(data);
        this.initialData = deepcopy(data);
        Solver solutionSolver = new Solver(deepcopy(data), false);
        solutionSolver.solve();
        this.solutionData = solutionSolver.getBoard();
        this.solver = new Solver(deepcopy(data), true);

        cells = new Cell[9][9];
        for (int row = 0; row <= 8; row++) {
            for (int col = 0; col <= 8; col++) {
                cells[row][col] = new Cell(new int[]{row, col}, data[row][col]);
            }
        }

        window = new JFrame(TITLE);
        window.setSize(new Dimension(WIDTH, HEIGHT));
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setLocationRelativeTo(null);
        window.add(this);
        window.setVisible(true);
        window.requestFocus();
        // Resize as top bar of the window get counted towards overall size
        int extraHeight = HEIGHT - window.getContentPane().getSize().height; 
        window.setSize(new Dimension(WIDTH, HEIGHT + extraHeight));

        window.addKeyListener(new KeyListener() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_ESCAPE:
                        System.out.println("ESC");
                        running = false;
                        break;
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {}
            @Override
            public void keyTyped(KeyEvent e){}
        });
    }

    /** 
     * Start the game loop. Should be called immediately after Initialization.
     */
    public void run() {
        running = true;
        while(running) {
            long start = System.nanoTime();

            update();
            render();

            long delta = (System.nanoTime() - start) / 1000000L;
            if (delta < FRAME_TIME) {
                try {
                    Thread.sleep(FRAME_TIME - delta);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void update() {

    }

    private void render() {
        repaint();
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

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, WIDTH, HEIGHT);

        g.setColor(Color.BLACK);
        for (int row = 0; row <= 9; row++) {
            int height = row % 3 == 0 ? 4 : 2;
            g.fillRect(0, row * Cell.SIZE, WIDTH, height);
        }
        for (int col = 0; col <= 9; col++) {
            int width = col % 3 == 0 ? 4 : 2;
            g.fillRect(col * Cell.SIZE, 0, width, HEIGHT);
        }

        for (Cell[] row: cells)
            for (Cell c: row) 
                c.render();
    }

    private static int[][] deepcopy(int[][] array) {
        int[][] newArray = new int[array.length][];
        for (int i = 0; i < array.length; i++)
            newArray[i] = array[i].clone();
        return newArray;
    }
}