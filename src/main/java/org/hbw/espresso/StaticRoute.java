package org.hbw.espresso;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class StaticRoute extends Route {
    
    public String file;
    
    @Override
    public String getContent(Request request) {
        try {
            return new String(Files.readAllBytes(Paths.get(file)));
        } catch (IOException e) {
            return "Error 404: Page not found"; // MUST find a better way to do this
        }
    }
    
    public StaticRoute(String p, String f) {
        path = p;
        file = f;
    }
}