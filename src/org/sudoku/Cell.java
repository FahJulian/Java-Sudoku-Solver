package org.sudoku;

import java.awt.Color;

public class Cell {
    /**
     * One Cell of the GUI's Sudoku bord.
     */
    public static final int SIZE = 50;

    private int[] pos;
    private int value, provisionalValue;
    private Color color;

    public Cell(int[] pos, int value) {
        this.pos = new int[2];
        this.pos[0] = pos[0] * SIZE;
        this.pos[1] = pos[1] * SIZE;
    }

    public void render() {
        
    }

    public void setValue() {

    }

    public void setValue(int value) {

    }

    public void setProvisionalValue(int value) {

    }
}
