package application.websocket;

import application.models.WebSocketService;
import com.google.gson.Gson;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import javax.naming.AuthenticationException;
import java.io.IOException;

/**
 * Created by magomed on 19.05.17.
 */
public class RegisterWebSocketHandler extends TextWebSocketHandler {
    private static final Logger LOGGER = Logger.getLogger(MyWebSocketHandler.class);
    private RemotePointService remotePointService;
    private WebSocketService webSocketService;

    @Autowired
    public RegisterWebSocketHandler(RemotePointService remotePointService1, WebSocketService webSocketService) {
        this.remotePointService = remotePointService1;
        this.webSocketService = webSocketService;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession webSocketSession) throws AuthenticationException, IOException {
        try {
            final String id = webSocketSession.getId();

            final Gson gson = new Gson();
            if (id == null) {
                throw new AuthenticationException("bad");
            }
            remotePointService.registerUser(id, webSocketSession);
//        webSocketService.sendMessageToUser(id, gson.toJson(new Message(id)));
            LOGGER.info("connection open");
        } catch (RuntimeException e){
            LOGGER.error(e.getMessage());
        }
    }

    @Override
    protected void handleTextMessage(WebSocketSession webSocketSession, TextMessage message) throws AuthenticationException, IOException {
        final String id = webSocketSession.getId();
        if (id == null ) {
            throw new AuthenticationException("bad");
        }
        remotePointService.setLogin(id, webSocketSession, message);
        LOGGER.info("send message success");
    }

    @Override
    public void handleTransportError(WebSocketSession webSocketSession, Throwable throwable) throws Exception {
        LOGGER.warn("Websocket transport problem", throwable);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession webSocketSession, CloseStatus closeStatus) throws Exception {
        final String id = webSocketSession.getId();
        remotePointService.removeUser(id);
        LOGGER.info("connection close");
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }
}