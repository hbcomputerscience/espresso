package org.hbw.espresso;

import java.io.IOException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.hbw.espresso.functor.Maybe;
import org.hbw.espresso.http.HttpMethod;
import org.hbw.espresso.router.Router;
import org.hbw.espresso.logging.EspressoLogger;
import org.hbw.espresso.router.Route;

public class EspressoHandler extends AbstractHandler {

	private final Router router;

	public EspressoHandler(Router router) {
		this.router = router;
	}

	@Override
	public void handle(String uri, org.eclipse.jetty.server.Request baseRequest, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		
		Response res = new Response(response);
		
		Maybe<HttpMethod> method = Router.toHttpMethod(request.getMethod());
		
		Maybe<Route> route = router.getRoute(uri, method);
		
		if (route.isNothing()) {
			EspressoLogger.info(String.format("404: %s %s request unhandled.", request.getMethod(), uri));
			return;
		}
		
        Maybe<String> resp = router.executeRoute(route, uri, request, res);
        
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
                    response.getWriter().println(f);
                } catch (IOException ex) {
                    Logger.getLogger(EspressoHandler.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
        }
        
        baseRequest.setHandled(true);
	}
}
