package com.example.Tetorisu;

public class BoxGraphics extends Graphics{
    public static int height = 16, width = 16;
    private int color;
    @Override
    public void draw(int[][] field, int[][] zMask, int parentX, int parentY) {
        int globalX = parentX + relatedLogic.getX();
        int globalY = parentY + relatedLogic.getY();

        for (int dx = 0; dx < width; dx++) {
            for (int dy = 0; dy < height; dy++) {
                if (zMask[globalX * width + dx][globalY * height + dy] <= z) {
                    field[globalX * width + dx][globalY * height + dy] = color;
                }
            }
        }
    }

    public BoxGraphics(int width, int height, int color) {
        height = 16;
        width = 16;
        this.color = color;
        z = 1;
    }
}
