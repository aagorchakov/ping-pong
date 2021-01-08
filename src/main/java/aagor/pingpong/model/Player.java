package aagor.pingpong.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Player extends Circle {

    public static final int SIZE = 100;

    private int score;

    public Player(int x, int y) {
        super(x, y);
    }

    public int getScore() {
        return score;
    }

    public void incrementScore() {
        score++;
    }

    @JsonIgnore
    public int getSize() {
        return SIZE;
    }

}
