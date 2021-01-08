package aagor.pingpong;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import aagor.pingpong.model.Player;
import aagor.pingpong.model.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class WebSocketHandler extends TextWebSocketHandler {

    private final Logger logger = LoggerFactory.getLogger(WebSocketHandler.class);
    private final ObjectMapper objectMapper = new ObjectMapper();

    private Game game = new Game();
    private Map<WebSocketSession, Player> players = new ConcurrentHashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws IOException {
        int playerId = players.size();
        if (playerId > 1) {
            session.close();
            return;
        }
        logger.info("Connected player id {}", playerId);
        Player player = game.addPlayer(playerId);
        players.put(session, player);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws IOException {
        logger.info("Disconnected player {}", status);
        if (players.containsKey(session)) {
            logger.info("End game {}", makeResponse());
            players = new ConcurrentHashMap<>();
            game = new Game();
        }
    }

    @Override
    public void handleTextMessage(WebSocketSession currentSession, TextMessage message) throws IOException {
        int playerY = Integer.parseInt(message.getPayload());
        Player player = players.get(currentSession);
        if (player != null) {
            player.setY(playerY);
            notifyClients();
        }
    }

    private synchronized void notifyClients() throws IOException {
        String response = makeResponse();
        for (WebSocketSession session : players.keySet()) {
            session.sendMessage(new TextMessage(response));
        }
    }

    private String makeResponse() throws JsonProcessingException {
        Response response = new Response(game.getPlayers(), game.getBall());
        return objectMapper.writeValueAsString(response);
    }

    @Scheduled(fixedDelay = 20)
    private void moveBall() throws IOException {
        if (players.size() == 2) {
            game.moveBall();
            notifyClients();
        }
    }

}
