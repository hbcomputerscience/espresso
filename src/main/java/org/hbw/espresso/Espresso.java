package org.hbw.espresso;

import java.util.HashMap;
import java.util.function.Function;
import org.hbw.espresso.core.functor.Maybe;

public class Espresso {
    
    private HashMap<String,Route> routes;
    
    public Maybe<Route> getRoute(String path) {
        return new Maybe(routes.get(path));
    }
    
    public Maybe<String> getRouteContent(String path, Request request) {
        return getRoute(path).fmap(r -> r.getContent(request)); // Find a better way to do this
    }
    
    public DynamicRoute addRoute(String p, Function<Request,String> r) {
        DynamicRoute route = new DynamicRoute(p, r);
        routes.put(p, route);
        return route;
    }
    
    public StaticRoute addStatic(String p, String f) {
        StaticRoute route = new StaticRoute(p, f);
        routes.put(p, route);
        return route;
    }
}