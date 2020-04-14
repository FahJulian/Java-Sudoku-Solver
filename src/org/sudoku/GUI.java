package org.sudoku;

import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Color;
import java.awt.Font;

public class GUI extends JPanel {
    /**
     * Holds the main Window and the Sudoku Grid, runs the Game Loop.
     */
    
    private static final long serialVersionUID = 1;

    private static final String TITLE = "Sudoku Solver";
    private static final int TIMESTAMP_HEIGHT = 50;
    private static final int TIMESTAMP_FONTSIZE = 25;
    private static final int BORDER_THICKNESS = 4;
    private static final int WIDTH = Cell.SIZE * 9 + BORDER_THICKNESS, 
        HEIGHT = Cell.SIZE * 9 + BORDER_THICKNESS + TIMESTAMP_HEIGHT;
    private static final int FPS = 120;
    private static final int FRAME_TIME = 1000 / FPS;

    /** 
     * Offset that needs to be put to many y values related to the JFrame 
     * because of a problem with the title bar of the JFrame Window.
    */
    static final int windowYOffset = 25;

    private final JFrame window;

    private boolean running, runningSimulation;

    private int[][] data, solutionData;
    private Solver solver;
    private Cell[][] cells;
    private Cell selectedCell;

    private String timestamp;
    private int numericTimestamp;
    private final long start;
    
    public GUI(int[][] data) {
        this.data = deepcopy(data);
        Solver solutionSolver = new Solver(deepcopy(data), false);
        solutionSolver.solve();
        this.solutionData = solutionSolver.getBoard();

        // Init cell grid
        cells = new Cell[9][9];
        for (int row = 0; row <= 8; row++) {
            for (int col = 0; col <= 8; col++) {
                int x = col * Cell.SIZE + 2, 
                    y = row * Cell.SIZE + 2;
                cells[row][col] = new Cell(x, y, row, col, data[row][col]);
            }
        }
        selectedCell = null;

        // Init timestamp variables
        timestamp = "0:00";
        numericTimestamp = 0;
        start = System.nanoTime();

        // Init window
        window = new JFrame(TITLE);
        window.setSize(new Dimension(WIDTH, HEIGHT + windowYOffset));
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setLocationRelativeTo(null);
        window.add(this);
        window.setVisible(true);
        window.requestFocus();
        InputManager im = new InputManager(this);
        window.addKeyListener(im);
        window.addMouseListener(im);
    }

    /** Start the game loop. Should be called immediately after Initialization. */
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

    /** If there is a cell at the given coordinates, select it. */
    void selectCellAt(int x, int y) {
        if (selectedCell != null) selectedCell.unselect();
        for (Cell[] row: cells)
            for(Cell c: row)
                if (c.contains(x, y)) {
                    selectedCell = c;
                    selectedCell.select();
                    return;
                }
        selectedCell = null;
    }

    /** Run the solving algorithm simulation. */
    void startSimulation() {
        runningSimulation = true;
        this.solver = new Solver(deepcopy(this.data), true);
        this.solver.solve();
    }

    /** Select the cell right to the current selected cell. */
    void selectRight() {
        if (selectedCell == null) return;
        selectCellAt(selectedCell.x + Cell.SIZE, selectedCell.y);
    }
    /** Select the cell left to the current selected cell. */
    void selectLeft() {
        if (selectedCell == null) return;
        selectCellAt(selectedCell.x - Cell.SIZE, selectedCell.y);
    }
    /** Select the cell above to the current selected cell. */
    void selectAbove() {
        if (selectedCell == null) return;
        selectCellAt(selectedCell.x, selectedCell.y - Cell.SIZE);
    }
    /** Select the cell below to the current selected cell. */
    void selectBelow() {
        if (selectedCell == null) return;
        selectCellAt(selectedCell.x, selectedCell.y + Cell.SIZE);
    }
    /** If a cell is selected, redirect the value to that cell. */
    void setProvisionalValue(int value) {
        if (selectedCell == null) return;
        selectedCell.setProvisionalValue(value);
    }

    /** If the current selected cell's provisional value is correct, confirm it. */
    void confirmProvisionalIfValid() {
        if (selectedCell == null) return;
        int value = selectedCell.getProvisionalValue();
        if (isValueCorrect(selectedCell, value)){
            selectedCell.setValue(value);
            data[selectedCell.row][selectedCell.col] = value;
        }
    }

    private void update() {
        if (runningSimulation) {
            if (solver.hasNextLogEntry()) {
                LogEntry logEntry = solver.nextLogEntry();
                Cell cell = cells[logEntry.pos[0]][logEntry.pos[1]];
                if (logEntry.eventType == "PUT") {
                    cell.setTextColor(Color.GREEN);
                    cell.setValue(logEntry.value);
                } else if (logEntry.eventType == "INVALID") {
                    cell.setTextColor(Color.ORANGE);
                    cell.setValue(logEntry.value);
                } else if (logEntry.eventType == "REMOVE") {
                    cell.setTextColor(Color.RED);
                }
            }
        }

        // Update timestamp
        int delta = (int)((System.nanoTime() - start) / 1000000000);

        if (delta > numericTimestamp) {
            numericTimestamp = delta;
            String minutes = String.valueOf(numericTimestamp / 60);
            String seconds = numericTimestamp % 60 > 9 ? String.valueOf(numericTimestamp % 60) : "0" + String.valueOf(numericTimestamp % 60);
            timestamp = String.format("%s:%s", minutes, seconds);
        }
    }

    private void render() {
        repaint();
    }

    private boolean isValueCorrect(Cell cell, int value) {
        return solutionData[cell.row][cell.col] == value;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, 
            RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);

        // Draw Background
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, WIDTH, HEIGHT);

        // Draw Grid
        g2d.setColor(Color.BLACK);
        for (int row = 0; row <= 9; row++) {
            if (row % 3 == 0) {
                g2d.fillRect(0, row * Cell.SIZE, WIDTH, 4);
            } else {
                g2d.fillRect(0, row * Cell.SIZE + 1, WIDTH, 2);
            }
        }
        for (int col = 0; col <= 9; col++) {
            if (col % 3 == 0)
                g2d.fillRect(col * Cell.SIZE, 0, 4, Cell.SIZE * 9 + 4);
            else
                g2d.fillRect(col * Cell.SIZE + 1, 0, 2, Cell.SIZE * 9 + 4);
        }

        // Draw cells
        for (Cell[] row: cells) for (Cell c: row) c.render(g2d);

        // Bottom right timestamp
        Font f = new Font("Arial", 1, TIMESTAMP_FONTSIZE);
        int string_width = g2d.getFontMetrics(f).stringWidth(timestamp);
        g2d.setFont(f);
        g2d.drawString(timestamp, WIDTH - (string_width + 15), HEIGHT - 15);
    }

    private static int[][] deepcopy(int[][] array) {
        int[][] newArray = new int[array.length][];
        for (int i = 0; i < array.length; i++)
            newArray[i] = array[i].clone();
        return newArray;
    }
}
