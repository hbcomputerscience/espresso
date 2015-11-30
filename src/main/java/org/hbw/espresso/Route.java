package org.hbw.espresso;

/**
 *
 * @author Bryan Eastwood
 */
public abstract class Route {
    String path;
    
    public abstract String getContent(Request request);
}