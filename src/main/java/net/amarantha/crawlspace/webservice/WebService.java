package net.amarantha.crawlspace.webservice;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.filter.LoggingFilter;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import java.net.URI;

/**
 * Created by grimalkin on 13/06/15.
 */
public class WebService {

    private static HttpServer server;

    public static HttpServer startWebService(String ip) {
        server = null;
        if ( ip!=null ) {
            System.out.println("Starting Web Service....");
            String fullUri = "http://"+ip+":8000/";
            final ResourceConfig rc = new ResourceConfig().packages("net.amarantha.crawlspace.webservice");
            rc.register(LoggingFilter.class);
            server = GrizzlyHttpServerFactory.createHttpServer(URI.create(fullUri), rc);
            System.out.println("Web Service Online @ " + fullUri);
        }
        return server;
    }

    public static void stopWebService() {
        if ( server!=null ) {
            server.shutdown();
        }
    }

}
