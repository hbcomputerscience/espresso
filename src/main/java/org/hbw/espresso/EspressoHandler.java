package org.hbw.espresso;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.hbw.espresso.router.Route;
import org.hbw.espresso.router.Router;

public class EspressoHandler extends AbstractHandler {

	private final Router router;

	public EspressoHandler(Router router) {
		this.router = router;
	}

	@Override
	public void handle(String uri, org.eclipse.jetty.server.Request baseRequest, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		response.setContentType("text/html;charset=utf-8");
		response.setStatus(HttpServletResponse.SC_OK);
		response.getWriter().println("<h1>Hello World</h1>");
		baseRequest.setHandled(true);
	}

	private void handleRoute(Route route, org.eclipse.jetty.server.Request baseRequest, HttpServletRequest request, HttpServletResponse response) throws IOException {

	}
}
