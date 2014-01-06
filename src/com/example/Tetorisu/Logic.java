package com.example.Tetorisu;

public abstract class Logic implements Comparable<Logic>{
    public int getX() {
        return x;
    }
    protected int x;

    public int getY() {
        return y;
    }
    protected int y;

    abstract public void update();

    @Override
    public int compareTo(Logic logic) {
        if(logic.y > y)
            return 1;
        if(logic.y < y)
            return -1;
        if(logic.x < x)
            return -1;
        if(logic.x > x)
            return 1;
        if (logic.getClass() != getClass()) {
            if (logic.hashCode() > hashCode())
                return -1;
            if (logic.hashCode() < hashCode())
                return 1;
        }
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean equals(Object other) {
        if (other.getClass() != getClass())
            return false;

        Logic logic = (Logic) other;

        if (logic.getX() != x)
            return false;
        if (logic.getY() != y)
            return false;

        return true;
    }
}
