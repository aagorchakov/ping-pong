package aagor.pingpong.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

public abstract class Circle {

    private int x;
    private int y;

    public Circle(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public abstract int getSize();

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    @JsonIgnore
    public int getX2() {
        return getX() + getSize();
    }

    @JsonIgnore
    public int getY2() {
        return getY() + getSize();
    }

    public void incrementX(int value) {
        x += value;
    }

    public void incrementY(int value) {
        y += value;
    }

    public boolean isOver(Circle circle) {
        return getX2() >= circle.x && x <= circle.getX2()
            && getY2() >= circle.y && y <= circle.getY2();
    }

}
