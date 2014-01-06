package com.example.Tetorisu;

public class WorldGraphics extends Graphics{
    private static WorldGraphics ourInstance = new WorldGraphics();
    public static int width, height;

    public static WorldGraphics getInstance() {
        return ourInstance;
    }

    public static void updateSize() {
        width = WorldLogic.width * BoxGraphics.width;
        height = WorldLogic.height * BoxGraphics.height;
    }

    private WorldGraphics() {

    }
}
