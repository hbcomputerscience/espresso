package org.hbw.espresso;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.hbw.espresso.logging.EspressoLogger;
import org.eclipse.jetty.server.Server;
import org.hbw.espresso.http.HttpMethod;
import org.hbw.espresso.router.Route;
import org.hbw.espresso.router.Router;
import org.hbw.espresso.router.StaticRoute;

public class Espresso {

    private final Router router = new Router();
    
    private Server server;
    
    public Espresso() {
        
    }
    
     public Espresso get(String path, Handler handler) {
        return route(HttpMethod.GET, path, handler);
    }

    public Espresso head(String path, Handler handler) {
        return route(HttpMethod.HEAD, path, handler);
    }

    public Espresso post(String path, Handler handler) {
        return route(HttpMethod.POST, path, handler);
    }

    public Espresso put(String path, Handler handler) {
        return route(HttpMethod.PUT, path, handler);
    }

    public Espresso delete(String path, Handler handler) {
        return route(HttpMethod.DELETE, path, handler);
    }

    public Espresso trace(String path, Handler handler) {
        return route(HttpMethod.TRACE, path, handler);
    }

    public Espresso options(String path, Handler handler) {
        return route(HttpMethod.OPTIONS, path, handler);
    }

    public Espresso connect(String path, Handler handler) {
        return route(HttpMethod.CONNECT, path, handler);
    }

    public Espresso patch(String path, Handler handler) {
        return route(HttpMethod.PATCH, path, handler);
    }

    public Espresso action(String path, Handler handler) {
        return route(HttpMethod.ACTION, path, handler);
    }
    
    public Espresso route(HttpMethod method, String path, Handler handler) {
        return route(new Route(method, path, handler));
    }
    
    public Espresso route(Route route) {
        router.setRoute(route);
        
        return this;
    }
    
    public Espresso staticGet(String path) {
        return staticRoute(HttpMethod.GET, path);
    }
    
    public Espresso staticHead(String path) {
        return staticRoute(HttpMethod.HEAD, path);
    }
    
    
    public Espresso staticPut(String path) {
        return staticRoute(HttpMethod.PUT, path);
    }
    
    public Espresso staticPost(String path) {
        return staticRoute(HttpMethod.POST, path);
    }
    
    public Espresso staticDelete(String path) {
        return staticRoute(HttpMethod.DELETE, path);
    }
    
    public Espresso staticTrace(String path) {
        return staticRoute(HttpMethod.TRACE, path);
    }
    
    public Espresso staticOptions(String path) {
        return staticRoute(HttpMethod.OPTIONS, path);
    }
    
    public Espresso staticConnect(String path) {
        return staticRoute(HttpMethod.CONNECT, path);
    }
    
    public Espresso staticPatch(String path) {
        return staticRoute(HttpMethod.PATCH, path);
    }
    
    public Espresso staticAction(String path) {
        return staticRoute(HttpMethod.ACTION, path);
    }
    
    public Espresso staticRoute(HttpMethod method, String path) {
        return staticRoute(new StaticRoute(method, path));
    }
    
    public Espresso staticRoute(StaticRoute route) {
        router.setRoute(route);
        
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
