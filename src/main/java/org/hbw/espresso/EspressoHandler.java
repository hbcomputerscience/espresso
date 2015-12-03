package org.hbw.espresso;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.hbw.espresso.functor.Maybe;
import org.hbw.espresso.http.HttpMethod;
import org.hbw.espresso.router.Router;
import org.hbw.espresso.router.Route;
import org.hbw.espresso.logging.EspressoLogger;

public class EspressoHandler extends AbstractHandler {

	private final Router router;

	public EspressoHandler(Router router) {
		this.router = router;
	}

	@Override
	public void handle(String uri, org.eclipse.jetty.server.Request baseRequest, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

		EspressoLogger.info(String.format("%s %s", request.getMethod(), uri));
		
		Maybe<HttpMethod> method = Router.toHttpMethod(request.getMethod());

		Maybe<Route> route = router.getRoute(uri, method);

		if (route.isNothing()) {
			handleError(404, uri, baseRequest, request, response);
			return;
		}

		executeHandler(route, uri, baseRequest, request, response);
	}

	private void handleError(Integer errorCode, String uri, Request baseRequest, HttpServletRequest request, HttpServletResponse response) throws IOException {

		Maybe<HttpMethod> method = Router.toHttpMethod(request.getMethod());

		Maybe<Route> errorRoute = router.getErrorRoute(errorCode, method);

		if (errorRoute.isNothing()) {
			defaultErrorHandler(errorCode, uri, baseRequest, request, response);
			return;
		}

		executeHandler(errorRoute, uri, baseRequest, request, response);
	}

	private void executeHandler(Maybe<Route> route, String uri, org.eclipse.jetty.server.Request baseRequest, HttpServletRequest request, HttpServletResponse response) throws IOException {
		if (route.isNothing()) {
			throw new IllegalArgumentException("Invalid route.");
		}

		Response res = new Response(response);

		Maybe<Object> resp = router.executeRoute(route, uri, request, res);

		// Set status
		response.setStatus(res.status());

		// Set content type
		response.setContentType(res.contentType());

		// Set Headers
		res.headers().forEach(response::setHeader);

		// Set body
		if (resp.isNothing()) {
			response.getWriter().println(res.raw());
		} else {
			resp.fmap(f -> {
				try {
					renderResponse(f, res, response);
				} catch (IOException ex) {
					EspressoLogger.warn(ex);
				}
			});
		}

		baseRequest.setHandled(true);
	}

	private void defaultErrorHandler(Integer errorCode, String uri, Request baseRequest, HttpServletRequest request, HttpServletResponse response) throws IOException {
		Maybe<Route> errorRoute = new Maybe<>(new Route(HttpMethod.ACTION, uri, (req, res) -> {
			StringBuilder sb = new StringBuilder();
			sb.append(String.format("<title>Error!</title>"));
			sb.append(String.format("<h2>HTTP Error: %d</h2>", errorCode));
			sb.append("<hr>Powered by Espresso.");

			return sb.toString();
		}));

		executeHandler(errorRoute, uri, baseRequest, request, response);
	}

	private void renderResponse(Object f, Response res, HttpServletResponse response) throws IOException {
		if (f instanceof Renderable) {
			response.getWriter().write(((Renderable) f).render());
		} else if (f instanceof String) {
			response.getWriter().println(f);
		} else if (f instanceof Response) {
			response.getWriter().println(res.raw());
		} else {
			throw new IllegalArgumentException("Invalid response type");
		}
	}
}
