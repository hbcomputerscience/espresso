package org.hbw.espresso.router;

import org.hbw.espresso.Handler;
import org.hbw.espresso.http.HttpMethod;

public class Route {

    private final HttpMethod method;
    
    private final String path;
    
    private final Handler handler;
    
    public Route(HttpMethod method, String path, Handler handler) {
        this.method = method;
        this.path = path;
        this.handler = handler;
    }

    public HttpMethod getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }

    public Handler getHandler() {
        return handler;
    }
}