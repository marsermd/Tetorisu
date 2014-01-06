package com.example.Tetorisu;

public class FreeBoxLogic extends Logic{
    boolean stopped = false;
    public boolean canMove(Direction direction) {
        return !WorldLogic.getInstance().field[x + direction.dx][y + direction.dy];
    }

    public void move(Direction direction) {
        x += direction.dx;
        y += direction.dy;
    }

    public void setX(int x) {
        this.x = x;
    }
    public void setY(int y) {
        this.y = y;
    }

    @Override
    public void update() {
        if (WorldLogic.getInstance().field[x][y + 1]) {
            stopped = true;
        }
        if (!stopped) {
            boolean tmp = WorldLogic.getInstance().field[x][y];
            WorldLogic.getInstance().field[x][y] = false;
            y++;
            WorldLogic.getInstance().field[x][y] = tmp;
        }
    }

    public FreeBoxLogic(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
