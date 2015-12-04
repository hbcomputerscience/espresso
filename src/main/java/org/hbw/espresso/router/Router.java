package org.hbw.espresso.router;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.hbw.espresso.Handler;
import org.hbw.espresso.functor.Maybe;
import org.hbw.espresso.http.HttpMethod;
import org.hbw.espresso.wrappers.Request;
import org.hbw.espresso.wrappers.Response;

public class Router {

	private final List<Route> routes = new ArrayList<>();

	private final HashMap<Integer, List<Route>> errorRoutes = new HashMap<>();

	public Router() {

	}

    /**
     * Converts string to an HttpMethod.
     * @param method
     * @return 
     */
	public static Maybe<HttpMethod> toHttpMethod(String method) {
		HttpMethod httpMethod = null;

		try {
			httpMethod = HttpMethod.valueOf(method);
		} catch (IllegalArgumentException ex) {

		}

		return new Maybe<>(httpMethod);
	}

    /**
     * Defines a route to be used when an error is encountered.
     * @param code
     * @param route 
     */
	public void setErrorRoute(Integer code, Route route) {
		if (!errorRoutes.containsKey(code)) {
			errorRoutes.put(code, new ArrayList<>());
		}

		errorRoutes.get(code).add(route);
	}

    /**
     * Adds a route to the router.
     * @param route 
     */
	public void setRoute(Route route) {
		routes.add(route);
	}

	public void setRoute(HttpMethod httpMethod, String path, Handler handler) {
		setRoute(new Route(httpMethod, path, handler));
	}

    /**
     * Finds an appropriate route for an error code.
     * @param errorCode
     * @param method
     * @return 
     */
	public Maybe<Route> getErrorRoute(Integer errorCode, HttpMethod method) {
		if (errorRoutes.containsKey(errorCode)) {
			for (Route route : errorRoutes.get(errorCode)) {
				if (route.getMethod().equals(HttpMethod.ACTION) || method.equals(route.getMethod())) {
					return new Maybe(route);
				}
			}
		}

		return new Maybe(null);
	}

    /**
     * Gets a route at a url.
     * @param url
     * @param method
     * @return 
     */
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

    /**
     * Returns the result of the execution of a route.
     * @param <T>
     * @param route
     * @param url
     * @param request
     * @param response
     * @return 
     */
	public <T> Maybe<T> executeRoute(Route<T> route, String url, HttpServletRequest request, Response response) {
		return new Maybe(route.getHandler().accept(new Request(request, route.extractParams(url)), response));
	}
}
