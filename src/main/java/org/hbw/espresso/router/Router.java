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

	private final HashMap<Integer, List<Route>> errorRoutes = new HashMap<>();

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
		if (!errorRoutes.containsKey(code)) {
			errorRoutes.put(code, new ArrayList<>());
		}

		errorRoutes.get(code).add(route);
	}

	public void setRoute(Route route) {
		routes.add(route);
	}

	public void setRoute(HttpMethod httpMethod, String path, Handler handler) {
		setRoute(new Route(httpMethod, path, handler));
	}

	public Maybe<Route> getErrorRoute(Integer errorCode, Maybe<HttpMethod> method) {
		return method.fmap(m -> {
			if (errorRoutes.containsKey(errorCode)) {
				for (Route route : errorRoutes.get(errorCode)) {
					if (route.getMethod().equals(HttpMethod.ACTION) || m.equals(route.getMethod())) {
						return route;
					}
				}
			}

			return null;
		});
	}

	public Maybe<Route> getRoute(String url, Maybe<HttpMethod> method) {
		return method.fmap(m -> {
			for (Route route : routes) {
				if (route.getMethod().equals(HttpMethod.ACTION) || m.equals(route.getMethod())) {
					if (route.matchRoute(url)) {
						return route;
					}
				}
			}

			return null;
		});
	}

	public <T> Maybe<Route<T>> executeRoute(Route<T> route, String url, HttpServletRequest request, Response response) {
		return new Maybe(route.getHandler().accept(new Request(request, route.extractParams(url)), response));
	}
}
