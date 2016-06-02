package org.hbw.espresso;

import org.hbw.espresso.wrappers.Response;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.eclipse.jetty.server.session.SessionHandler;
import org.hbw.espresso.functor.Maybe;
import org.hbw.espresso.http.HttpMethod;
import org.hbw.espresso.router.Router;
import org.hbw.espresso.router.Route;
import org.hbw.espresso.logging.EspressoLogger;
import org.hbw.espresso.wrappers.Request;

public class EspressoHandler extends SessionHandler {

	private final Router router;

	private final String version;

	public EspressoHandler(String version, Router router) {
		this.version = version;
		this.router = router;
	}

	@Override
	public void doHandle(String uri, org.eclipse.jetty.server.Request baseRequest, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

		EspressoLogger.info(String.format("%s %s", request.getMethod(), uri));

		Maybe<HttpMethod> method = Router.toHttpMethod(request.getMethod());

		method.fmap(m -> {
			Maybe<Route> route = router.getRoute(uri, m);

			if (route.isNothing()) {
				try {
					handleError(404, uri, baseRequest, request, response);
				} catch (IOException ex) {
					Logger.getLogger(EspressoHandler.class.getName()).log(Level.SEVERE, null, ex);
				}
				return;
			}

			route.fmap(r -> {
				executeHandler(r, uri, baseRequest, request, response);
			});
		});
	}

	private void handleError(Integer errorCode, String uri, org.eclipse.jetty.server.Request baseRequest, HttpServletRequest request, HttpServletResponse response) throws IOException {

		Maybe<HttpMethod> method = Router.toHttpMethod(request.getMethod());

		method.fmap(m -> {
			Maybe<Route> errorRoute = router.getErrorRoute(errorCode, m);

			if (errorRoute.isNothing()) {
				defaultErrorHandler(errorCode, uri, baseRequest, request, response);
				return;
			}

			errorRoute.fmap(route -> {
				executeHandler(route, uri, baseRequest, request, response);
			});
		});

	}

	private <T> void executeHandler(Route<T> route, String uri, org.eclipse.jetty.server.Request baseRequest, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {

		Request req = new Request(httpServletRequest, route.extractParams(uri));
		Response res = new Response(httpServletResponse);

		Maybe<T> resp = router.executeRoute(route, uri, req, res);

		if (httpServletResponse.isCommitted()) {
			baseRequest.setHandled(true);
			return;
		}

		if (res.contentType().isNothing()) {
			httpServletResponse.setContentType(Response.defaultContentType());
		}

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

	private void defaultErrorHandler(Integer errorCode, String uri, org.eclipse.jetty.server.Request baseRequest, HttpServletRequest request, HttpServletResponse response) {
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
