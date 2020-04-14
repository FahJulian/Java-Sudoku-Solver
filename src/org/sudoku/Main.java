package org.sudoku;

public class Main {

    static int[][] board = new int[][]{
        {2, 0, 0,  0, 0, 0,  0, 4, 5},
        {5, 0, 0,  8, 0, 0,  0, 0, 0},
        {0, 0, 0,  0, 0, 0,  0, 7, 1},

        {4, 6, 0,  0, 1, 2,  7, 0, 0},
        {0, 7, 0,  0, 6, 0,  0, 9, 0},
        {0, 0, 0,  0, 0, 3,  4, 2, 0},

        {0, 0, 8,  0, 9, 6,  0, 0, 0},
        {0, 4, 5,  3, 7, 1,  0, 0, 2},
        {0, 0, 9,  0, 0, 0,  5, 0, 7}
    };

    public static void main(String[] args) {
        new GUI(board).run();
    }
}