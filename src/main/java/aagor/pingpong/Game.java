package aagor.pingpong;

import aagor.pingpong.model.Ball;
import aagor.pingpong.model.Player;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Game {

    private final int width = 1000;
    private final int height = 700;
    private final Ball ball = new Ball(width / 2, height / 2);
    private final List<Player> players = new CopyOnWriteArrayList<>();


    public Player addPlayer(int id) {
        int margin = 1;
        int x = id == 0 ? margin : width - margin - Player.SIZE;
        Player player = new Player(x, height / 2);
        players.add(player);
        return player;
    }

    public Ball getBall() {
        return ball;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void moveBall() {
        Ball ball = getBall();
        if (ball.getX() <= 0) {
            players.get(1).incrementScore();
            ball.setSpeedX(Math.abs(ball.getSpeedX()));
        } else if (ball.getX2() >= width) {
            players.get(0).incrementScore();
            ball.setSpeedX(Math.abs(ball.getSpeedX()) * -1);
        }
        if (ball.getY() <= 0) {
            ball.setSpeedY(Math.abs(ball.getSpeedY()));
        } else if (ball.getY2() >= height) {
            ball.setSpeedY(Math.abs(ball.getSpeedY()) * -1);
        }

        for (Player player : players) {
            if (ball.isOver(player)) {
                ball.hitPlayer();
            }
        }

        ball.incrementX(ball.getSpeedX());
        ball.incrementY(ball.getSpeedY());
    }

}
