package org.hbw.espresso.wrappers;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import org.hbw.espresso.functor.Maybe;
import org.hbw.espresso.logging.EspressoLogger;

public class Response {

	private final HttpServletResponse response;

	private static final String defaultContentType = "text/html;charset=utf-8";

	private StringBuilder buffer = new StringBuilder();

	public Response(HttpServletResponse response) {
		this.response = response;
	}

	public Response contentType(String contentType) {
		response.setContentType(contentType);

		return this;
	}

	public Response status(Integer status) {
		response.setStatus(status);

		return this;
	}

	public Response header(String key, String value) {
		response.setHeader(key, value);

		return this;
	}

	public Cookie cookie(String name, String value) {
		Cookie cookie = new Cookie(name, value);

		cookie(cookie);

		return cookie;
	}

	public Response cookie(Cookie cookie) {
		response.addCookie(cookie);

		return this;
	}

	public Response deleteCookie(Cookie cookie) {
		return deleteCookie(cookie.getName());
	}

	public Response deleteCookie(String name) {
		cookie(name, "").setMaxAge(0);

		return this;
	}

	public Response write(Object o) {
		buffer.append(o);

		return this;
	}

	public Response clear() {
		buffer = new StringBuilder();

		return this;
	}

	public Response body(String body) {
		buffer = new StringBuilder(body);

		return this;
	}

	public String body() {
		return buffer.toString();
	}

	public Response redirect(String url) {
		try {
			response.sendRedirect(url);
		} catch (IOException ex) {
			EspressoLogger.warn(ex);
		}

		return this;
	}

	public HttpServletResponse raw() {
		return response;
	}

	public Maybe<String> contentType() {
		return new Maybe<>(response.getContentType());
	}

	public static String defaultContentType() {
		return defaultContentType;
	}

	@Override
	public String toString() {
		return body();
	}
}
