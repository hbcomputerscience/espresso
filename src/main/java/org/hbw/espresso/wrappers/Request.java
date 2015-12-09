package org.hbw.espresso.wrappers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.hbw.espresso.functor.Maybe;

public class Request {

	private final HttpServletRequest request;

	private final List<String> params;
	
	private final HttpSession session;
	
	private final List<Cookie> cookies;

	public Request(HttpServletRequest request, List<String> extractParams) {
		this.request = request;
		
		this.params = extractParams;
		
		this.session = request.getSession();
		
		this.cookies = new ArrayList<>(Arrays.asList(request.getCookies()));
	}

	public List<String> parameters() {
		return params;
	}
	
	public HttpSession session(String key, Object value) {
		session.setAttribute(key, value);
		
		return session;
	}
	
	public Object session(String key) {
		return session.getAttribute(key);
	}
	
	public HttpSession session() {
		return session;
	}

	public List<Cookie> cookies() {
		return cookies;
	}
	
	public Maybe<Cookie> cookie(String name) {
		for(Cookie cookie : cookies) {
			if (cookie.getName().equals(name)) {
				return new Maybe<>(cookie);
			}
		}
		
		return new Maybe<>(null);
	}
	
	public Maybe<String> parameter(String key) {
		return new Maybe(request.getParameter(key));
	}
}
