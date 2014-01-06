package com.example.Tetorisu;

public enum Direction {
    up   (0, -1),
    down (0, 1),
    left (-1, 0) ,
    right(1, 0),
    cur  (0, 0);

    public final int dx;
    public final int dy;
    Direction (int dx, int dy) {
        this.dx = dx;
        this.dy = dy;
    }
}
