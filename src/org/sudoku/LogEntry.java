package org.sudoku;

import java.util.Arrays;

public class LogEntry {
    /** 
     * Data structure to store the process of solving a sudoku board
    */

    public final String eventType;
    public final int[] pos;
    public final int value;

    public LogEntry(String eventType, int[] pos, int value) {
        this.eventType = eventType;
        this.pos = pos;
        this.value = value;
    }

    public String toString() {
        return String.format("%s at %s", eventType, Arrays.toString(pos)) + (value != 0 ? String.format("at %d", value) : "");
    }
}
