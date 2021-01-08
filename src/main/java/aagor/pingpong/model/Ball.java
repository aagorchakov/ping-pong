package aagor.pingpong.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Random;

public class Ball extends Circle {

    private static final Random random = new Random();

    public static final int SIZE = 70;

    private int speedX;
    private int speedY;

    public Ball(int x, int y) {
        super(x, y);
        speedX = 2;
        speedY = 2;
    }

    @JsonIgnore
    public int getSize() {
        return SIZE;
    }

    @JsonIgnore
    public int getSpeedX() {
        return speedX;
    }

    public void setSpeedX(int speedX) {
        this.speedX = speedX;
    }

    @JsonIgnore
    public int getSpeedY() {
        return speedY;
    }

    public void setSpeedY(int speedY) {
        this.speedY = speedY;
    }

    public void hitPlayer() {
        setSpeedX(getSpeedX() * -1);
        setSpeedY(getSpeedY() * -1);
        int addX = random.nextInt(2);
        int addY = random.nextInt(2);
        if (getSpeedX() > 0) {
            setSpeedX(getSpeedX() + addX);
        } else {
            setSpeedX(getSpeedX() - addX);
        }
        if (getSpeedY() > 0) {
            setSpeedY(getSpeedY() + addY);
        } else {
            setSpeedY(getSpeedY() - addY);
        }
        if (getSpeedX() >= SIZE / 2) {
            setSpeedX(2);
        }
        if (getSpeedY() >= SIZE / 2) {
            setSpeedY(2);
        }
    }

}
