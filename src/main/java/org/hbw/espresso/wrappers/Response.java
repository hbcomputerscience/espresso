package org.hbw.espresso.wrappers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import org.hbw.espresso.functor.Maybe;
import org.hbw.espresso.logging.EspressoLogger;

public class Response {

	private final HttpServletResponse response;

	private String contentType = "text/html;charset=utf-8";

	private Integer status = 200;
	
	private final List<Cookie> cookies;

	private final Map<String, String> headers = new HashMap<>();
	
	private StringBuilder buffer = new StringBuilder();

	public Response(HttpServletResponse response, Cookie[] cookies) {
		this.response = response;
		this.cookies = new ArrayList<>(Arrays.asList(cookies));
	}

	public String contentType() {
		return contentType;
	}

	public Response contentType(String contentType) {
		this.contentType = contentType;

		return this;
	}

	public Integer status() {
		return status;
	}

	public Response status(Integer status) {
		this.status = status;

		return this;
	}

	public Map<String, String> headers() {
		return headers;
	}

	public Response header(String key, String value) {
		headers.put(key, value);

		return this;
	}
	
	public List<Cookie> cookies() {
		return cookies;
	}
	
	public Maybe<Cookie> cookie(String name) {
		for(Cookie cookie : cookies) {
			if (cookie.getName().equals(name)) {
				return new Maybe(cookie);
			}
		}
		
		return new Maybe(null);
	}

	public Cookie cookie(String name, String value) {
		Cookie cookie = new Cookie(name, value);
		
		cookie(cookie);
		
		return cookie;
	}
	
	public Response cookie(Cookie cookie) {
		cookies.add(cookie);
		
		return this;
	}
	
	public Response deleteCookie(Cookie cookie) {
		return deleteCookie(cookie.getName());
	} 
	
	public Response deleteCookie(String name) {
		for(int i = 0; i < cookies.size(); i++) {
			if (cookies.get(i).getName().equals(name)) {
				cookies.remove(i);
			}
		}
		
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
	
	@Override
	public String toString() {
		return body();
	}
}
