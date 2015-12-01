package org.hbw.espresso.router;

import java.util.ArrayList;
import java.util.List;
import org.hbw.espresso.Handler;
import org.hbw.espresso.functor.Maybe;
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
    
    public Maybe<Route> getRoute(String url) {
        for(Route route : routes) {
            if(route.matchRoute(url)) {
                return new Maybe(route);
            }
        }
        return new Maybe(null);
    }
    
    // public String executeRoute(String url) {
    //     Maybe<List<String>> params = this.getRoute(url).fmap(r -> r.extractParams(url));
    // }
    
}
