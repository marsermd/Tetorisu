package com.example.Tetorisu;


public enum TetraminoShape {
    I(0, 0, 0, 0,
      0, 0, 0, 0,
      1, 1, 1, 1,
      0, 0, 0, 0),
    O(0, 0, 0, 0,
      0, 1, 1, 0,
      0, 1, 1, 0,
      0, 0, 0, 0),
    T(0, 0, 0, 0,
      0, 0, 1, 0,
      0, 1, 1, 1,
      0, 0, 0, 0),
    J(0, 0, 0, 0,
      0, 1, 0, 0,
      0, 1, 1, 1,
      0, 0, 0, 0),
    L(0, 0, 0, 0,
      0, 0, 0, 0,
      0, 1, 1, 1,
      0, 1, 0, 0),
    S(0, 0, 0, 0,
      0, 0, 1, 1,
      0, 1, 1, 0,
      0, 0, 0, 0),
    Z(0, 0, 0, 0,
      1, 1, 0, 0,
      0, 1, 1, 0,
      0, 0, 0, 0);

    public final boolean[][] mask = new boolean[4][4];
    TetraminoShape(int... mask) {
        for (int x = 0; x < this.mask.length; x++) {
            for (int y = 0; y < this.mask[0].length; y++) {
                this.mask[x][y] = mask[x * this.mask.length + y] == 1;
            }
        }
    }
}
