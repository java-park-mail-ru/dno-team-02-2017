package application.websocket;

import application.mehanica.GameSessions;
import application.mehanica.MehanicsExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import org.apache.log4j.Logger;

import javax.naming.AuthenticationException;

/**
 * Created by magomed on 19.04.17.
 */
public class MyWebSocketHandler extends TextWebSocketHandler {
    private static final Logger LOGGER = Logger.getLogger(MyWebSocketHandler.class);
    private RemotePointService remotePointService;
    private MehanicsExecutor mehanicsExecutor;

    @Autowired
    public MyWebSocketHandler(RemotePointService remotePointService, MehanicsExecutor mehanicsExecutor) {
        this.remotePointService = remotePointService;
        this.mehanicsExecutor = mehanicsExecutor;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession webSocketSession) throws AuthenticationException {
        LOGGER.info("connection open");
        remotePointService.registerUser(webSocketSession);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws AuthenticationException {
        LOGGER.info("");
    }

    @Override
    public void handleTransportError(WebSocketSession webSocketSession, Throwable throwable) throws Exception {
        LOGGER.warn("Websocket transport problem", throwable);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession webSocketSession, CloseStatus closeStatus) throws Exception {
        LOGGER.info("connection close");
        remotePointService.removeUser(webSocketSession.getId());
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }
}