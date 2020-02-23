package mif04.gdw.tp1.itf;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;

/**
 * Created by ecoquery on 25/09/2016.
 */
public class WebServer {

    private final static Logger LOG = LoggerFactory.getLogger(WebServer.class);
    private URI uri = null;
    private Server server;

    public URI getUri() {
        return uri;
    }

    public void start() throws Exception {
        server = new Server();
        //server.dumpStdErr();

        // HTTP connector
        ServerConnector http = new ServerConnector(server);
        http.setHost("localhost");
        http.setPort(8010);
        http.setIdleTimeout(30000);
        server.addConnector(http);

        // Enable servlet
        ServletHandler servletHandler = new ServletHandler();
        server.setHandler(servletHandler);
        servletHandler.addServletWithMapping(BlogServlet.class, "/*");

        uri = new URI("http://"+http.getHost()+":"+http.getPort()+"/");
        LOG.info("Serveur démarré à l'adresse {}", uri);
        server.start();
    }

    public void join() throws InterruptedException {
        server.join();
    }
}
