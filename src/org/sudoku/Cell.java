package org.sudoku;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;

public class Cell {
    /**
     * Part of the GUI's Sudoku board.
     */

    public static final int SIZE = 50;

    final int x, y, row, col;
    private int value, provisionalValue;
    private boolean selected;
    private Color textColor;

    public Cell(int x, int y, int row, int col, int value) {
        this.x = x;
        this.y = y;
        this.row = row;
        this.col = col;
        this.value = value;
        this.provisionalValue = 0;
        this.textColor = Color.BLACK;
        this.selected = false;
    }
    
    public void render(Graphics g) {
        if (selected) {
            g.setColor(Color.RED);
            g.fillRect(x, y, SIZE, SIZE);
            g.setColor(Color.WHITE);
            g.fillRect(x + 2, y + 2, SIZE - 4, SIZE - 4);
        }

        if (value != 0) {
            g.setColor(textColor);
            g.setFont(new Font("Arial", 1, 40));
            g.drawString(String.valueOf(value), x + 10, y + 40);
        } else if (provisionalValue != 0) {
            g.setColor(Color.DARK_GRAY);
            g.setFont(new Font("Arial", 1, 25));
            g.drawString(String.valueOf(provisionalValue), x + 20 / 4, y + 27);
        }
    }

    public boolean contains(int x, int y) {
        return (x <= this.x && x < this.x + SIZE && y <= this.y && y < this.y + SIZE);
    }

    public void select() {
        this.selected = true;
    }
    public void unselect() {
        this.selected = false;
    }

    public void setTextColor(Color color) {
        textColor = color;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public void setProvisionalValue(int value) {
        this.provisionalValue = value;
    }

    public int getProvisionalValue() {
        return provisionalValue;
    }
}
