package org.hbw.espresso;

@FunctionalInterface
public interface Handler {
    
    public String accept(Request req, Response res);
}
