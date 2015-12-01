package org.hbw.espresso;

public abstract class Route {
    
    String path;
        
    public abstract String getContent(Request request);
}