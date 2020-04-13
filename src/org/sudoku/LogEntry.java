package org.sudoku;

public class LogEntry {
    /** 
     * Data structure to store the process of solving a sudoku bord
    */

    public final String eventType;
    public final int[] pos;
    public final int value;

    public LogEntry(String eventType, int[] pos, int value) {
        this.eventType = eventType;
        this.pos = pos;
        this.value = value;
    }
}
