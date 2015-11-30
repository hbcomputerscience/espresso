package org.hbw.espresso;

import java.util.function.Function;

/**
 *
 * @author Bryan Eastwood
 */
public class DynamicRoute extends Route {
    Function<Request,String> route;
    
    @Override
    public String getContent(Request request) {
        return route.apply(request);
    }
}