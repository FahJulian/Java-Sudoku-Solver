package org.sudoku;

import javax.swing.JFrame;
import javax.swing.JPanel;

import javax.swing.event.MouseInputListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.KeyEvent;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Color;

public class GUI extends JPanel {
    /**
     * Holds the main Window and the Sudoku Grid, runs the Game Loop.
     */
    
    private static final long serialVersionUID = 1;

    private static final String TITLE = "Sudoku Solver";
    private static final int WIDTH = Cell.SIZE * 9 + 4, HEIGHT = Cell.SIZE * 9 + 4;
    private static final int FPS = 60;
    private static final int FRAME_TIME = 1000 / FPS;

    private final JFrame window;
    private final int windowYoffset;

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
                int[] pos = new int[]{col * Cell.SIZE + 2, row * Cell.SIZE + 2};
                cells[row][col] = new Cell(pos, data[row][col]);
            }
        }
        selectedCell = null;

        window = new JFrame(TITLE);
        window.setSize(new Dimension(WIDTH, HEIGHT));
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setLocationRelativeTo(null);
        window.add(this);
        window.setVisible(true);
        window.requestFocus();
        // Resize as top bar of the window get counted towards overall size
        windowYoffset = HEIGHT - window.getContentPane().getSize().height; 
        window.setSize(new Dimension(WIDTH, HEIGHT + windowYoffset));

        window.addKeyListener(new KeyListener() {
            @Override
            public void keyReleased(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_ESCAPE:
                        System.exit(1);
                        running = false;
                        break;
                    case KeyEvent.VK_ENTER:
                        System.out.println("ENTER");
                        if (selectedCell != null && isValueCorrect(selectedCell, selectedCell.getProvisionalValue()))
                            selectedCell.setValue(selectedCell.getProvisionalValue());
                        else System.out.println("INVALID");
                        break;
                    case KeyEvent.VK_1:
                        if (selectedCell != null) selectedCell.setProvisionalValue(1);
                        break;
                    case KeyEvent.VK_2:
                        if (selectedCell != null) selectedCell.setProvisionalValue(2);
                        break;
                    case KeyEvent.VK_3:
                        if (selectedCell != null) selectedCell.setProvisionalValue(3);
                        break;
                    case KeyEvent.VK_4:
                        if (selectedCell != null) selectedCell.setProvisionalValue(4);
                        break;
                    case KeyEvent.VK_5:
                        if (selectedCell != null) selectedCell.setProvisionalValue(5);
                        break;
                    case KeyEvent.VK_6:
                        if (selectedCell != null) selectedCell.setProvisionalValue(6);
                        break;
                    case KeyEvent.VK_7:
                        if (selectedCell != null) selectedCell.setProvisionalValue(7);
                        break;
                    case KeyEvent.VK_8:
                        if (selectedCell != null) selectedCell.setProvisionalValue(8);
                        break;
                    case KeyEvent.VK_9:
                        if (selectedCell != null) selectedCell.setProvisionalValue(9);
                        break;
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {}
            @Override
            public void keyTyped(KeyEvent e){}
        });

        window.addMouseListener(new MouseInputListener(){
            @Override
            public void mouseReleased(MouseEvent e) {
                selectCellAt(e.getX(), e.getY() - windowYoffset);
            }
        
            @Override
            public void mouseMoved(MouseEvent e) {}
            @Override
            public void mouseDragged(MouseEvent e) {}
            @Override
            public void mousePressed(MouseEvent e) {}
            @Override
            public void mouseExited(MouseEvent e) {}        
            @Override
            public void mouseEntered(MouseEvent e) {}
            @Override
            public void mouseClicked(MouseEvent e) {}
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

    private void setValue(Cell cell, int value) {

    }

    private boolean isValueCorrect(Cell cell, int value) {
        int col = (cell.getX() - 2) / Cell.SIZE;
        int row = (cell.getY() - 2) / Cell.SIZE;
        return solutionData[row][col] == value;
    }

    private void selectCellAt(int x, int y) {
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
            if (row % 3 == 0) {
                g.fillRect(0, row * Cell.SIZE, WIDTH, 4);
            } else {
                g.fillRect(0, row * Cell.SIZE + 1, WIDTH, 2);
            }
        }

        for (int col = 0; col <= 9; col++) {
            if (col % 3 == 0)
                g.fillRect(col * Cell.SIZE, 0, 4, HEIGHT);
            else
                g.fillRect(col * Cell.SIZE + 1, 0, 2, HEIGHT);
        }

        for (Cell[] row: cells) for(Cell c: row) c.render(g);
    }

    private static int[][] deepcopy(int[][] array) {
        int[][] newArray = new int[array.length][];
        for (int i = 0; i < array.length; i++)
            newArray[i] = array[i].clone();
        return newArray;
    }
}