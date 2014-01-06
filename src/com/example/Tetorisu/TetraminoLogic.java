package com.example.Tetorisu;

import android.graphics.Color;
import android.util.Log;

import java.util.ArrayList;

public class TetraminoLogic extends Logic implements Controllable{

    private boolean hasControll;
    public TetraminoLogic(boolean[][] mask, int x, int y) {
        hasControll = true;
        this.x = x;
        this.y = y;
        shape = new FreeBoxLogic[4][4];
        for (int i = 0; i < shape.length; i++) {
            for (int j = 0; j < shape[0].length; j++) {
                if (mask[i][j]) {
                    shape[i][j] = new FreeBoxLogic(0, 0);
                    BoxGraphics tmp = new BoxGraphics(1, 1, Color.GREEN);
                    tmp.relatedLogic = shape[i][j];
                    WorldGraphics.getInstance().children.add(tmp);
                }
            }
        }
        applyPositionToBoxes();
    }

    private FreeBoxLogic[][] shape;

    @Override
    public void update() {
        if (!canMove(Direction.down)) {
            stop();
            removeControl();
            return;
        }

        for (int i = 0; i < shape.length; i++) {
            for (int j = 0; j < shape[0].length; j++) {
                if (shape[i][j] != null)
                    shape[i][j].update();
            }
        }
        y++;
    }

    private void stop() {
        for (int i = 0; i < shape.length; i++) {
            for (int j = 0; j < shape[0].length; j++) {
                if (shape[i][j] != null) {
                    shape[i][j].stopped = true;
                }
            }
        }
        ArrayList<Logic> toEmit = new ArrayList<Logic>();
        for (int i = 0; i < shape.length; i++) {
            for (int j = 0; j < shape[0].length; j++) {
                if (shape[i][j] != null) {
                    toEmit.add(shape[i][j]);
                }
            }
        }
        WorldLogic.getInstance().addChildren(toEmit);
        WorldLogic.getInstance().removeTetraminoLogic(this);
    }
    private void removeControl() {
        TetraminoBuilder.createRandom();
        hasControll = false;
    }

    boolean canMove (Direction direction) {
        for (int i = 0; i < shape.length; i++) {
            for (int j = 0; j < shape[0].length; j++) {
                if (shape[i][j] != null && !shape[i][j].canMove(direction)) {
                    return false;
                }
            }
        }
        return true;
    }

    private void Move(Direction direction) {
        if (!canMove(direction))
            return;
        for (int i = 0; i < shape.length; i++) {
            for (int j = 0; j < shape[0].length; j++) {
                if (shape[i][j] != null)
                    shape[i][j].move(direction);
            }
        }
        x += direction.dx;
        y += direction.dy;
    }

    private boolean canRotate() {
        for (int dx = 0; dx < shape.length; dx++) {
            for (int dy = 0; dy < shape[0].length; dy++) {
                if (WorldLogic.getInstance().field[x + dx - 1][y + dy - 1]) {
                    return false;
                }
            }
        }
        return true;
    }

    private void RotateLeft() {
        if(!canRotate())
            return;
        for (int i = 0; i < shape.length; i++) {
            for (int j = 0; j < shape[0].length / 2; j++) {
                FreeBoxLogic tmp = shape[i][shape[0].length - j - 1];
                shape[i][shape[0].length - j - 1] = shape[i][j];
                shape[i][j] = tmp;
            }
        }
        for (int i = 0; i < shape.length; i++) {
            for (int j = 0; j < i; j++) {
                FreeBoxLogic tmp = shape[j][i];
                shape[j][i] = shape[i][j];
                shape[i][j] = tmp;
            }
        }
        for (int i = 0; i < shape.length; i++) {
            String s = "";
            for (int j = 0; j < shape[0].length; j++) {
                s += shape[i][j]!=null ? "1" : "0";
            }
            Log.e("rotation", s);
        }

        Log.e("up", "switched boxes");
        applyPositionToBoxes();
    }

    public void applyPositionToBoxes() {
        for (int i = 0; i < shape.length; i++) {
            for (int j = 0; j < shape[0].length; j++) {
                if (shape[i][j] != null) {
                    shape[i][j].setX(x + i - 1);
                    shape[i][j].setY(y + j - 1);
                }
            }
        }
        Log.e("boxes", "applyed");
    }

    @Override
    public void controlLeft() {
        Log.e("swipe", "left");
        Move(Direction.left);
    }

    @Override
    public void controlRight() {
        Move(Direction.right);
        Log.e("swipe", "right");
    }

    @Override
    public void controlUp() {
        RotateLeft();
        Log.e("swipe", "up");
    }

    @Override
    public void controlDown() {
        Log.e("swipe", "down");
        while (hasControll)
            update();
        //removeControl();
    }
}
