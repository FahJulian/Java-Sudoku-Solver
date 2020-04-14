package org.sudoku;

import java.util.Queue;
import java.util.Arrays;
import java.util.LinkedList;

public class Solver {
    /** 
     * Responsible for solving sudoku bords and logging the process 
    */

    private int[][] board;
    private boolean log;
    private Queue<LogEntry> logQueue;

    public Solver(int[][] board, boolean log) {
        this.log = log;
        this.board = board;
        this.logQueue = new LinkedList<LogEntry>();
    }

    public int solve() {
        int[] pos = nextEmptyPos();
        if (Arrays.equals(pos, new int[]{ -1, -1 })) return 1;  // No empty pos -> Board solved

        int row = pos[0], col = pos[1];
        for (int num = 1; num < 10; num++) {

            if (this.isNumValidAtPos(pos, num)) {
                if (log) log("PUT", pos, num);
                board[row][col] = num;
                int solution = solve();

                if (solution != -1)
                    return 1;
                else
                    board[row][col] = 0;
            } else
            if (log) log("INVALID", pos, num);
        }
        if (log) log("REMOVE", pos, 0);
        return -1;
    }
    
    public LogEntry nextLogEntry() {
        return logQueue.poll();
    }

    public boolean hasNextLogEntry() {
        return logQueue.peek() != null;
    }
    
    private void log(String eventType, int[] pos, int value) {
        logQueue.offer(new LogEntry(eventType, pos, value));
    }
    
    private int[] nextEmptyPos() {
        for (int row = 0; row <= 8; row++)
            for (int col = 0; col <= 8; col++)
                if (this.board[row][col] == 0) 
                    return new int[]{row, col};

        return new int[]{-1, -1};     // No empy pos
    }
    
    private boolean isNumValidAtPos(int[] pos, int num) {
        for (int row = 0; row <= 8; row++)
            for (int col = 0; col <=8; col++) {
                if (row == pos[0] && col == pos[1]) continue;
                boolean relevant = false;

                // Check same row or col
                if (row == pos[0] || col == pos[1]) relevant = true;
                // Check same 3x3 field
                if ((row / 3 == pos[0] / 3) && (col / 3 == pos[1] / 3)) relevant = true;

                if (relevant && num == this.board[row][col])
                    return false;
            }
        return true;
    }

    public int[][] getBoard() {
        return this.board;
    }
}