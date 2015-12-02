package org.hbw.espresso;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.eclipse.jetty.server.Server;
import org.hbw.espresso.http.HttpMethod;
import org.hbw.espresso.logging.EspressoLogger;
import org.hbw.espresso.router.Router;

public class Espresso {

    private final Router router = new Router();
    
    private Server server;
    
    public Espresso() {
        
    }
    
     public Espresso get(String path, Handler handler) {
        router.setRoute(HttpMethod.GET, path, handler);

        return this;
    }

    public Espresso head(String path, Handler handler) {
        router.setRoute(HttpMethod.HEAD, path, handler);

        return this;
    }

    public Espresso post(String path, Handler handler) {
        router.setRoute(HttpMethod.POST, path, handler);

        return this;
    }

    public Espresso put(String path, Handler handler) {
        router.setRoute(HttpMethod.PUT, path, handler);

        return this;
    }

    public Espresso delete(String path, Handler handler) {
        router.setRoute(HttpMethod.DELETE, path, handler);

        return this;
    }

    public Espresso trace(String path, Handler handler) {
        router.setRoute(HttpMethod.TRACE, path, handler);

        return this;
    }

    public Espresso options(String path, Handler handler) {
        router.setRoute(HttpMethod.OPTIONS, path, handler);

        return this;
    }

    public Espresso connect(String path, Handler handler) {
        router.setRoute(HttpMethod.CONNECT, path, handler);

        return this;
    }

    public Espresso patch(String path, Handler handler) {
        router.setRoute(HttpMethod.PATCH, path, handler);

        return this;
    }

    public Espresso action(String path, Handler handler) {
        router.setRoute(HttpMethod.ACTION, path, handler);

        return this;
    }
    
    public void start(int port) {
        try {
            server = new Server(port);
            
            server.setHandler(new EspressoHandler(router));
            server.start();
            server.join();
        } catch (Exception ex) {
            Logger.getLogger(Espresso.class.getName()).log(Level.SEVERE, null, ex);
	    EspressoLogger.warn(ex.toString());
        }
    }
}
