package org.hbw.espresso.router;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.hbw.espresso.http.HttpMethod;

public class StaticRoute extends Route {
    public StaticRoute(HttpMethod m, String p) {
        super(m, p, (x, y) -> {
            try {
                return new String(Files.readAllBytes(Paths.get(p)));
            } catch (IOException e) {
                return "Error 404"; // Change this soon
            }
        });
    }
}
