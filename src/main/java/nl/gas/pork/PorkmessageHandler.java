package nl.gas.pork;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

public class PorkmessageHandler extends TextWebSocketHandler {
  	private static Logger LOG = LoggerFactory.getLogger(PorkmessageHandler.class);
    private static Map<String, WebSocketSession> sessions = Collections.synchronizedMap(new HashMap<String, WebSocketSession>());
    
    public PorkmessageHandler(){
    	LOG.info("PorkmessageHandler created");
    }
    
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
    	sessions.remove(session.getId());
        LOG.info("Connection closed, session {} removed", session.getId());
    }
 
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
    	sessions.put(session.getId(), session);
        LOG.info("Added session: {}", session.getId());
        broadcast(session, "New connection. Id: " + session.getId());
    }
 
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage textMessage) throws IOException {
        broadcast(session, textMessage.getPayload());
    }
    
    private void broadcast(WebSocketSession sessionFrom, String messageIn) throws IOException{
    	for(WebSocketSession sessionTo: sessions.values()){
    		if(!sessionFrom.getId().equals(sessionTo.getId())){//don't want to send to self
	    		String messageOut = messageIn + " to:" + sessionTo.getId() + " from:" + sessionFrom.getId();
	    		LOG.info("Sending message: {}", messageOut);
	    		sessionTo.sendMessage(new TextMessage(messageOut));
    		}
    	}
    }
 
    
}
