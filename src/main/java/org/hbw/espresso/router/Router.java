package org.hbw.espresso.router;

import java.util.ArrayList;
import java.util.List;
import org.hbw.espresso.Handler;
import org.hbw.espresso.http.HttpMethod;

public class Router {

    private final List<Route> routes = new ArrayList<>();
    
    public Router() {
        
    }
    
    public void setRoute(Route route) {
        routes.add(route);
    }
    
    public void setRoute(HttpMethod httpMethod, String path, Handler handler) {
        setRoute(new Route(httpMethod, path, handler));
    }
    
}
