package nl.gas.pork;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
	@EnableWebSocket
	public class PorkSocketConfig implements WebSocketConfigurer {
		private static Logger LOG = LoggerFactory.getLogger(PorkSocketConfig.class);
		 
	    @Bean
	    public WebSocketHandler thePorkmessageHandler() {
	        return new PorkmessageHandler();
	    }
	 
		@Override
		public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
			registry.addHandler(thePorkmessageHandler(), "/pork");
			LOG.info("Messagehandler started");
		}
	 
	}
