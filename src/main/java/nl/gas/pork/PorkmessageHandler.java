package nl.gas.pork;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.google.gson.Gson;

import nl.gas.pork.generator.PorkMessageGenerator;
import nl.gas.pork.model.PorkMessage;
import nl.gas.pork.model.SensorOutput;

public class PorkmessageHandler extends TextWebSocketHandler {
  	private static Logger LOG = LoggerFactory.getLogger(PorkmessageHandler.class);
    private static Map<String, WebSocketSession> sessions = Collections.synchronizedMap(new HashMap<String, WebSocketSession>());
    private Gson gson = new Gson();
    
    public PorkmessageHandler(){
    	LOG.info("PorkmessageHandler created");
    }
    
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
    	sessions.remove(session.getId());
    	LOG.info("Connection closed, session {} removed", session.getId());
    	if(sessions.size() == 0){
    		//stopBroadcast();
    	}
    }
 
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
    	sessions.put(session.getId(), session);
    	LOG.info("Added session: {}", session.getId());
        if (sessions.size() == 1){
    		startBroadcast();
    	}
    }
 
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage textMessage) throws IOException {
        //broadcast(session, textMessage.getPayload());
    }
    
    protected void startBroadcast() throws IOException, InterruptedException{
    	for(int i=0; i< 100; i++){
    		broadcast(PorkMessageGenerator.generatePorkMessage(i, new Date()));
    		Thread.sleep( PorkMessageGenerator.generateRandomNum(800, 3000) );
    	}
    }
    
    private void broadcast(PorkMessage porkMessage) throws IOException{
    	for(WebSocketSession sessionTo: sessions.values()){
    		String messageOut = gson.toJson(porkMessage);
    		LOG.info("Sending message: {}", messageOut);
    		sessionTo.sendMessage(new TextMessage(messageOut));
    		
    	}
    }
 
    
}
