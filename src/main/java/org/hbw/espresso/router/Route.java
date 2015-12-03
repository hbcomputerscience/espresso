package org.hbw.espresso.router;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.hbw.espresso.Handler;
import org.hbw.espresso.http.HttpMethod;

public class Route {

	private final HttpMethod method;

	private final String path;

	private final Handler handler;

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

	public List<String> extractParams(String url) {
		List<String> vars = new ArrayList();
		List<String> p1 = Arrays.asList(path.split("/"));
		List<String> p2 = Arrays.asList(url.split("/"));

		for (int i = 0; i < p1.size(); i++) {
			if (p1.get(i).length() > 0 && p1.get(i).charAt(0) == ':') {
				vars.add(p2.get(i));
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
