package org.hbw.espresso.router;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.hbw.espresso.http.HttpMethod;
import java.net.URI;
import java.net.URISyntaxException;
import org.hbw.espresso.logging.EspressoLogger;

public class StaticRoute extends Route {

	public StaticRoute(HttpMethod m, String p) {
		super(m, p, (x, y) -> {
			try {
                            return new String(Files.readAllBytes(Paths.get(new URI(p).normalize())));
			} catch (IOException e) {
                            return "Error 404"; // Change this soon
			} catch (URISyntaxException e) {
                            EspressoLogger.warn("Possible directory traversal attack.");
                            return "An error occured";
                        }
		});
	}
}
