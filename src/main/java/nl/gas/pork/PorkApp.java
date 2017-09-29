package nl.gas.pork;

import java.util.Arrays;

import javax.servlet.ServletContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.web.context.ServletContextAware;

@SpringBootApplication
public class PorkApp 
{
	private static Logger LOG = LoggerFactory.getLogger(PorkApp.class);
	
	public static void main( String[] args )
    {
        SpringApplication.run(PorkApp.class, args);
		LOG.info("Porkapp Start: " + Arrays.toString(args));
    }
	
	@Bean
    public ServletContextAware endpointExporterInitializer(final ApplicationContext applicationContext) {
        return new ServletContextAware() {
            @Override
            public void setServletContext(ServletContext servletContext) {
            	org.springframework.web.socket.server.standard.ServerEndpointExporter exporter = 
            			new org.springframework.web.socket.server.standard.ServerEndpointExporter();
                exporter.setApplicationContext(applicationContext);
                try {
					exporter.afterPropertiesSet();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }

        };
    }
}
