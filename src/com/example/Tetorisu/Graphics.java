package com.example.Tetorisu;

import java.util.ArrayList;

public abstract class Graphics{
    protected int z;
    protected Logic relatedLogic;
    public ArrayList<Graphics> children = new ArrayList<Graphics>();
    public void draw(int[][] field, int[][] zMask, int parentX, int parentY) {
        for (int i = 0; i < children.size(); i++) {
            children.get(i).draw(field, zMask, parentX + relatedLogic.getX(), parentY + relatedLogic.getY());
        }

    }

    @Override
    public boolean equals(Object other) {
        if (other.getClass() != getClass())
            return false;
        Graphics graphics = (Graphics) other;
        if (graphics.relatedLogic.equals(relatedLogic))
            return true;
        return false;
    }
}
