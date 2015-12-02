package org.hbw.espresso.router;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.hbw.espresso.Handler;
import org.hbw.espresso.Request;
import org.hbw.espresso.Response;
import org.hbw.espresso.functor.Maybe;
import org.hbw.espresso.http.HttpMethod;

public class Router {

	private final List<Route> routes = new ArrayList<>();

	private final HashMap<Integer, Route> errorRoutes = new HashMap<>();

	public Router() {

	}

	public static Maybe<HttpMethod> toHttpMethod(String method) {
		HttpMethod httpMethod = null;

		try {
			httpMethod = HttpMethod.valueOf(method);
		} catch (IllegalArgumentException ex) {

		}

		return new Maybe<>(httpMethod);
	}

	public void setErrorRoute(Integer code, Route route) {
		errorRoutes.put(code, route);
	}

	public void setRoute(Route route) {
		routes.add(route);
	}

	public void setRoute(HttpMethod httpMethod, String path, Handler handler) {
		setRoute(new Route(httpMethod, path, handler));
	}

	public Maybe<Route> getRoute(String url, Maybe<HttpMethod> method) {
		return method.fmap(m -> {
			for (Route route : routes) {
				if (m == HttpMethod.ACTION || m.equals(route.getMethod())) {
					if (route.matchRoute(url)) {
						return route;
					}
				}
			}
			
			return null;
		});
	}

	public Maybe<String> executeRoute(Maybe<Route> route, String url, HttpServletRequest request, Response response) {
		return route.fmap(r -> {
			return r.getHandler().accept(new Request(request, r.extractParams(url)), response);
		});

	}
}
