package org.hbw.espresso;

import java.util.function.Function;

public class DynamicRoute extends Route {
    
    Function<Request,String> route;
    
    @Override
    public String getContent(Request request) {
        return route.apply(request);
    }
    
    public DynamicRoute(String p, Function<Request,String> r) {
        path = p;
        route = r;
    }
}