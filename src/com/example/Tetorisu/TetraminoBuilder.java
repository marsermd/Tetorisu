package com.example.Tetorisu;

import android.graphics.Color;
import android.util.Log;

import java.util.Random;

public class TetraminoBuilder {
    private static Random r = new Random();
    public static void createRandom() {
        Log.e("creating", "fffuuuu");
        int figureId = r.nextInt(TetraminoShape.values().length);
        TetraminoShape newShape = TetraminoShape.values()[figureId];
        TetraminoLogic tetramino = new TetraminoLogic(newShape.mask, WorldLogic.width / 2, 2);
        WorldLogic.getInstance().addTetraminoLogic(tetramino);
        if (!tetramino.canMove(Direction.cur)) {
            WorldLogic.getInstance().gameOver();
        }
    }

    private TetraminoBuilder(){};
}
