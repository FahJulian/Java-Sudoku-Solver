package org.sudoku;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;

public class Cell {
    /**
     * Part of the GUI's Sudoku board.
     */

    public static final int SIZE = 50;

    private int[] pos;
    private int value, provisionalValue;
    private boolean selected;
    private Color color;

    public Cell(int[] pos, int value) {
        this.pos = pos;
        this.value = value;
        this.provisionalValue = 0;
        this.color = Color.BLACK;
    }
    
    public void render(Graphics g) {
        if (selected) {
            g.setColor(Color.RED);
            g.fillRect(pos[0], pos[1], SIZE, SIZE);
            g.setColor(Color.WHITE);
            g.fillRect(pos[0] + 2, pos[1] + 2, SIZE - 4, SIZE - 4);
        }

        if (value != 0) {
            g.setColor(Color.BLACK);
            g.setFont(new Font("Arial", 1, 40));
            g.drawString(String.valueOf(value), pos[0] + 10, pos[1] + 40);
        } else if (provisionalValue != 0) {
            g.setColor(Color.DARK_GRAY);
            g.setFont(new Font("Arial", 1, 25));
            g.drawString(String.valueOf(provisionalValue), pos[0] + 20 / 4, pos[1] + 27);
        }
    }

    public boolean contains(int x, int y) {
        return (pos[0] <= x && x < pos[0] + SIZE && pos[1] <= y && y < pos[1] + SIZE);
    }

    public void setValue() {

    }

    public void select() {
        this.selected = true;
    }

    public void unselect() {
        this.selected = false;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setProvisionalValue(int value) {
        this.provisionalValue = value;
    }

    public int getProvisionalValue() {
        return provisionalValue;
    }

    public int getX() {
        return this.pos[0];
    }

    public int getY() {
        return this.pos[1];
    }
}
