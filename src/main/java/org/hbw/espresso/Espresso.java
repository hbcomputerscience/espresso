package org.hbw.espresso;

import org.hbw.espresso.logging.EspressoLogger;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.session.HashSessionIdManager;
import org.eclipse.jetty.server.session.HashSessionManager;
import org.eclipse.jetty.server.session.SessionHandler;
import org.eclipse.jetty.util.log.Log;
import org.hbw.espresso.http.HttpMethod;
import org.hbw.espresso.router.Route;
import org.hbw.espresso.router.Router;
import org.hbw.espresso.router.StaticRoute;

public class Espresso {

	private final Router router = new Router();

	private final String version = "Espresso v0.0.1a";

	private Server server;

	private Boolean started = false;

	private final Object lock = new Object();

	public Espresso() {

	}

	// Handler Routes
	public Espresso get(String path, Handler handler) {
		return route(HttpMethod.GET, path, handler);
	}

	public Espresso head(String path, Handler handler) {
		return route(HttpMethod.HEAD, path, handler);
	}

	public Espresso post(String path, Handler handler) {
		return route(HttpMethod.POST, path, handler);
	}

	public Espresso put(String path, Handler handler) {
		return route(HttpMethod.PUT, path, handler);
	}

	public Espresso delete(String path, Handler handler) {
		return route(HttpMethod.DELETE, path, handler);
	}

	public Espresso trace(String path, Handler handler) {
		return route(HttpMethod.TRACE, path, handler);
	}

	public Espresso options(String path, Handler handler) {
		return route(HttpMethod.OPTIONS, path, handler);
	}

	public Espresso connect(String path, Handler handler) {
		return route(HttpMethod.CONNECT, path, handler);
	}

	public Espresso patch(String path, Handler handler) {
		return route(HttpMethod.PATCH, path, handler);
	}

	public Espresso action(String path, Handler handler) {
		return route(HttpMethod.ACTION, path, handler);
	}

	public Espresso route(HttpMethod method, String path, Handler handler) {
		return route(new Route(method, path, handler));
	}

	// Static Routes
	public Espresso get(String path) {
		return route(HttpMethod.GET, path);
	}

	public Espresso head(String path) {
		return route(HttpMethod.HEAD, path);
	}

	public Espresso put(String path) {
		return route(HttpMethod.PUT, path);
	}

	public Espresso post(String path) {
		return route(HttpMethod.POST, path);
	}

	public Espresso delete(String path) {
		return route(HttpMethod.DELETE, path);
	}

	public Espresso trace(String path) {
		return route(HttpMethod.TRACE, path);
	}

	public Espresso options(String path) {
		return route(HttpMethod.OPTIONS, path);
	}

	public Espresso connect(String path) {
		return route(HttpMethod.CONNECT, path);
	}

	public Espresso patch(String path) {
		return route(HttpMethod.PATCH, path);
	}

	public Espresso action(String path) {
		return route(HttpMethod.ACTION, path);
	}

	public Espresso route(HttpMethod method, String path) {
		return route(new StaticRoute(method, path));
	}

	public Espresso route(Route route) {
		router.setRoute(route);

		return this;
	}

	// Handler Error Routes
	public Espresso get(Integer errorCode, Handler handler) {
		return errorRoute(errorCode, HttpMethod.GET, handler);
	}

	public Espresso head(Integer errorCode, Handler handler) {
		return errorRoute(errorCode, HttpMethod.HEAD, handler);
	}

	public Espresso post(Integer errorCode, Handler handler) {
		return errorRoute(errorCode, HttpMethod.POST, handler);
	}

	public Espresso put(Integer errorCode, Handler handler) {
		return errorRoute(errorCode, HttpMethod.PUT, handler);
	}

	public Espresso delete(Integer errorCode, Handler handler) {
		return errorRoute(errorCode, HttpMethod.DELETE, handler);
	}

	public Espresso trace(Integer errorCode, Handler handler) {
		return errorRoute(errorCode, HttpMethod.TRACE, handler);
	}

	public Espresso options(Integer errorCode, Handler handler) {
		return errorRoute(errorCode, HttpMethod.OPTIONS, handler);
	}

	public Espresso connect(Integer errorCode, Handler handler) {
		return errorRoute(errorCode, HttpMethod.CONNECT, handler);
	}

	public Espresso patch(Integer errorCode, Handler handler) {
		return errorRoute(errorCode, HttpMethod.PATCH, handler);
	}

	public Espresso action(Integer errorCode, Handler handler) {
		return errorRoute(errorCode, HttpMethod.ACTION, handler);
	}

	public Espresso errorRoute(Integer errorCode, HttpMethod method, Handler handler) {
		return errorRoute(errorCode, new Route(method, "", handler));
	}

	// Static Error Routes
	public Espresso get(Integer errorCode, String path) {
		return errorRoute(errorCode, new StaticRoute(HttpMethod.GET, path));
	}

	public Espresso head(Integer errorCode, String path) {
		return errorRoute(errorCode, new StaticRoute(HttpMethod.HEAD, path));
	}

	public Espresso put(Integer errorCode, String path) {
		return errorRoute(errorCode, new StaticRoute(HttpMethod.PUT, path));
	}

	public Espresso post(Integer errorCode, String path) {
		return errorRoute(errorCode, new StaticRoute(HttpMethod.POST, path));
	}

	public Espresso delete(Integer errorCode, String path) {
		return errorRoute(errorCode, new StaticRoute(HttpMethod.DELETE, path));
	}

	public Espresso trace(Integer errorCode, String path) {
		return errorRoute(errorCode, new StaticRoute(HttpMethod.TRACE, path));
	}

	public Espresso options(Integer errorCode, String path) {
		return errorRoute(errorCode, new StaticRoute(HttpMethod.OPTIONS, path));
	}

	public Espresso connect(Integer errorCode, String path) {
		return errorRoute(errorCode, new StaticRoute(HttpMethod.CONNECT, path));
	}

	public Espresso patch(Integer errorCode, String path) {
		return errorRoute(errorCode, new StaticRoute(HttpMethod.PATCH, path));
	}

	public Espresso action(Integer errorCode, String path) {
		return errorRoute(errorCode, new StaticRoute(HttpMethod.ACTION, path));
	}

	public Espresso errorRoute(Integer errorCode, HttpMethod method, String path) {
		return errorRoute(errorCode, new StaticRoute(method, path));
	}

	public Espresso errorRoute(Integer errorCode, Route route) {
		router.setErrorRoute(errorCode, route);

		return this;
	}

	public void start(int port) {
		synchronized (lock) {
			if (started) {
				EspressoLogger.warn("The server has already been started.");
				return;
			}
		}

		EspressoLogger.initialize(System.out);
		EspressoLogger.getInstance().fmap(f -> {
			//Log.setLog(f);
		});

		try {
			server = new Server(port);

			HashSessionIdManager sessionIdManager = new HashSessionIdManager();
			server.setSessionIdManager(sessionIdManager);

			HashSessionManager manager = new HashSessionManager();
			SessionHandler sessions = new SessionHandler(manager);

			EspressoHandler handler = new EspressoHandler(version, router);

			handler.setHandler(sessions);

			server.setHandler(handler);
			server.start();

			synchronized (lock) {
				started = true;
			}
			server.join();

		} catch (Exception ex) {
			EspressoLogger.warn(ex.toString());
		}
	}

	public void stop() {
		synchronized (lock) {
			if (!started) {
				EspressoLogger.warn("The server has not been started.");
				return;
			}
		}

		try {
			server.stop();
		} catch (Exception ex) {
			EspressoLogger.warn(ex.toString());
		}
	}
}
