package aagor.pingpong.model;

import java.util.List;

public class Response {

    public Response(List<Player> players, Ball ball) {
        this.players = players;
        this.ball = ball;
    }

    private final List<Player> players;
    private final Ball ball;

    public List<Player> getPlayers() {
        return players;
    }

    public Ball getBall() {
        return ball;
    }

}
