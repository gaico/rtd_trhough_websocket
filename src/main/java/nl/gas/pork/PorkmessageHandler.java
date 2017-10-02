package nl.gas.pork;

import java.io.IOException;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import javax.websocket.EncodeException;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;

import nl.gas.pork.model.Pork;

@ServerEndpoint(value="/pork/producer")
public class PorkEndpoint {
  
	private static Logger LOG = LoggerFactory.getLogger(PorkEndpoint.class);
    private Session session;
    private static Set<PorkEndpoint> porkers = new CopyOnWriteArraySet<PorkEndpoint>();
    
    public PorkEndpoint(){
    	LOG.info("PorkEndpoint created");
    }
    
    @OnOpen
    public void onOpen(Session session) throws IOException {
    	LOG.info("Connection open: " + session.getId());
        this.session = session;
        porkers.add(this);
    }
 
    @OnClose
    public void onClose(Session session) throws IOException {
    	LOG.info("Connection closed: " + session.getId());
    	porkers.remove(this);
    }
    
    @OnMessage
    public void onMessage(String message, Session session){
    	LOG.info("Message:" + message);
    }
 
    @OnError
    public void onError(Session session, Throwable throwable) {
    	LOG.info("Error:" + session.getId());
        
    }
 
    public static void broadcast(Pork pork) 
      throws IOException, EncodeException {
  
    	Gson g = new Gson();
    	String porkJson = g.toJson(pork);
    	
    	porkers.forEach(endpoint -> {
            synchronized (endpoint) {
                try {
                    endpoint.session.getBasicRemote().sendText(porkJson);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
