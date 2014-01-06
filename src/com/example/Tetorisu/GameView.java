package com.example.Tetorisu;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class GameView extends View{
    private int[][] colorBuffers;
    private int[][] field, zMask;
    private int current = -1;

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);

        WorldGraphics.updateSize();
        Log.e("size", "" + WorldGraphics.width + " " + BoxGraphics.width + " " + WorldLogic.width);
        colorBuffers = new int[2][WorldGraphics.height * WorldGraphics.width];
    }

    protected void onDraw(Canvas canvas) {
        current = (current + 1) % 2;
        canvas.save();
        canvas.scale((1.0f * getWidth()) / WorldGraphics.width, (1.0f * getHeight()) / WorldGraphics.height);

        field = new int[WorldGraphics.width][WorldGraphics.height];
        zMask = new int[WorldGraphics.width][WorldGraphics.height];

        WorldGraphics.getInstance().draw(field, zMask, 0, 0);
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[i].length; j++) {
                int id = i + j * field.length;
                colorBuffers[current][id] = field[i][j];
            }
        }
        canvas.drawBitmap(colorBuffers[current], 0, WorldGraphics.width, 0, 0, WorldGraphics.width, WorldGraphics.height, false, null);
        canvas.restore();
        invalidate();
    }
}
