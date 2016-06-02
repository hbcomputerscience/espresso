package org.hbw.espresso.router;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import org.hbw.espresso.Handler;
import org.hbw.espresso.http.HttpMethod;

public class Route<T> {

	private final HttpMethod method;

	private final String path;

	private final Handler<T> handler;

	/**
	 * Tests if a url matches the route.
	 *
	 * @param url
	 * @return
	 */
	public boolean matchRoute(String url) {
		List<String> p1 = Arrays.asList(path.split("/"));
		List<String> p2 = Arrays.asList(url.split("/"));

		if (p1.size() != p2.size()) {
			return false;
		}

		for (int i = 0; i < p1.size(); i++) {
			if (!(p1.get(i).equals(p2.get(i)) || p1.get(i).charAt(0) == ':')) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Extracts the parameters supplied by the request to the server.
	 *
	 * @param url
	 * @return
	 */
	public HashMap<String, String> extractParams(String url) {
		HashMap<String, String> vars = new HashMap();
		List<String> p1 = Arrays.asList(path.split("/"));
		List<String> p2 = Arrays.asList(url.split("/"));

		for (int i = 0; i < p1.size(); i++) {
			if (p1.get(i).length() > 0 && p1.get(i).charAt(0) == ':') {
				vars.put(p1.get(i), p2.get(i));
			}
		}

		return vars;
	}

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
