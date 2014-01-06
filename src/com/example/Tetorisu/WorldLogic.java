package com.example.Tetorisu;

import android.graphics.Color;
import android.util.Log;

import java.util.*;

public class WorldLogic extends Logic{

    public void gameOver() {
        children.clear();
        WorldGraphics.getInstance().children.clear();
        init();
        hasTetraminoLogic = false;
    }

    private static WorldLogic ourInstance = new WorldLogic();

    public static WorldLogic getInstance() {
        return ourInstance;
    }

    private boolean hasTetraminoLogic = false;
    public boolean[][] field;
    public static int height, width;
    private ArrayList<Logic> children = new ArrayList<Logic>();

    public void addChildren(Collection<Logic> newChildren) {
        children.addAll(newChildren);
    }

    public void addTetraminoLogic(TetraminoLogic child) {
        children.add(child);
        GameScreenActivity.controlled = child;
        hasTetraminoLogic = true;
    }

    public void removeTetraminoLogic(TetraminoLogic child) {
        children.remove(child);
        GameScreenActivity.controlled = null;
        updateFieled();
    }

    @Override
    public void update() {

        Log.e("debug", "updating!" + children.size());

        if(!hasTetraminoLogic) {
            hasTetraminoLogic = true;
            TetraminoBuilder.createRandom();
        }
        Collections.sort(children);
        for (int i = 0; i < children.size(); i++) {
            children.get(i).update();
        }
        updateFieled();
        removeFullLines();
    }

    private void updateFieled() {
        for (int x = 0; x < field.length; x++) {
            for (int y = 0; y < field[0].length; y++) {
                field[x][y] = false;
            }
        }
        for (Logic child:children) {
            field[child.getX()][child.getY()] = (child.getClass() == FreeBoxLogic.class) || (child.getClass() == FixedBoxLogic.class);
        }
    }

    private void generateBounds() {
        for (int x = 0 ; x < field.length; x++) {
            int y = field[0].length - 1;
            FixedBoxLogic boundLogic = new FixedBoxLogic(x, y);
            children.add(boundLogic);
            BoxGraphics boundGraphics = new BoxGraphics(1, 1, Color.GRAY);
            boundGraphics.relatedLogic = boundLogic;
            WorldGraphics.getInstance().children.add(boundGraphics);
        }
        for (int y = 0; y < field[0].length; y++) {
            int x = 0;
            FixedBoxLogic boundLogic = new FixedBoxLogic(x, y);
            children.add(boundLogic);
            BoxGraphics boundGraphics = new BoxGraphics(1, 1, Color.GRAY);
            boundGraphics.relatedLogic = boundLogic;
            WorldGraphics.getInstance().children.add(boundGraphics);

            x = field.length - 1;
            boundLogic = new FixedBoxLogic(x, y);
            children.add(boundLogic);
            boundGraphics = new BoxGraphics(1, 1, Color.GRAY);
            boundGraphics.relatedLogic = boundLogic;
            WorldGraphics.getInstance().children.add(boundGraphics);
        }
    }

    private void removeFullLines() {
        for (int y = 0; y < field[0].length - 1; y++) {
            boolean isFull = true;
            for (int x = 1; x < field.length - 1; x++) {
                if(!field[x][y]) {
                    isFull = false;
                }
            }
            if (isFull) {
                removeLine(y);
            }
        }
    }

    private void removeLine(int y) {
        for (int x = 1; x < field.length - 1; x++) {
            FreeBoxLogic tmpLogic = new FreeBoxLogic(x, y);
            children.remove(tmpLogic);
            BoxGraphics tmpGraphics = new BoxGraphics(1, 1, 0);
            tmpGraphics.relatedLogic = tmpLogic;
            WorldGraphics.getInstance().children.remove(tmpGraphics);
            field[x][y] = false;
        }
        for (Logic child:children) {
            if (child.getClass() == FreeBoxLogic.class){
                FreeBoxLogic tmp = (FreeBoxLogic)child;
                if (tmp.y < y)
                    tmp.stopped = false;
            }
        }
    }

    private void init() {
        width = 18;
        height = 26;
        WorldGraphics.updateSize();
        field = new boolean[width][height];
        generateBounds();
        WorldGraphics.getInstance().relatedLogic = this;
    }
    private WorldLogic() {
        init();
    }
}
