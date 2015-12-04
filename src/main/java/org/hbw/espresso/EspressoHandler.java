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

	private final String version;

	public EspressoHandler(String version, Router router) {
		this.version = version;
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

		route.fmap(r -> {
			executeHandler(r, uri, baseRequest, request, response);
		});
	}

	private void handleError(Integer errorCode, String uri, Request baseRequest, HttpServletRequest request, HttpServletResponse response) throws IOException {

		Maybe<HttpMethod> method = Router.toHttpMethod(request.getMethod());

		Maybe<Route> errorRoute = router.getErrorRoute(errorCode, method);

		if (errorRoute.isNothing()) {
			defaultErrorHandler(errorCode, uri, baseRequest, request, response);
			return;
		}

		errorRoute.fmap(route -> {
			executeHandler(route, uri, baseRequest, request, response);
		});
	}

	private <T> void executeHandler(Route<T> route, String uri, org.eclipse.jetty.server.Request baseRequest, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		Response res = new Response(httpServletResponse);

		Maybe<Route<T>> resp = router.executeRoute(route, uri, httpServletRequest, res);

		if (httpServletResponse.isCommitted()) {
			baseRequest.setHandled(true);
			return;
		}

		// Set status
		httpServletResponse.setStatus(res.status());

		// Set content type
		httpServletResponse.setContentType(res.contentType());

		// Set Headers
		res.headers().forEach(httpServletResponse::setHeader);

		// Set body
		if (resp.isNothing()) {
			try {
				httpServletResponse.getWriter().println(res.body());
			} catch (IOException ex) {
				EspressoLogger.warn(ex);
			}
		} else {
			resp.fmap(f -> {
				try {
					httpServletResponse.getWriter().write(f.toString());
				} catch (IOException ex) {
					EspressoLogger.warn(ex);
				}
			});
		}

		baseRequest.setHandled(true);
	}

	private void defaultErrorHandler(Integer errorCode, String uri, Request baseRequest, HttpServletRequest request, HttpServletResponse response) throws IOException {
		Route errorRoute = new Route(HttpMethod.ACTION, uri, (req, res) -> {
			res.write("<html>");
			res.write("<title>Error!</title>");
			res.write(String.format("<h2>HTTP/1.1 Error: %d</h2>", errorCode));
			res.write(String.format("<hr> Powered by %s", version));
			res.write("</html>");

			return res;
		});

		executeHandler(errorRoute, uri, baseRequest, request, response);
	}
}
