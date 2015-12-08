package org.hbw.espresso.wrappers;

import java.util.List;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.hbw.espresso.functor.Maybe;

public class Request {

	private final HttpServletRequest request;

	private final List<String> params;
	
	private final HttpSession session;

	public Request(HttpServletRequest request, List<String> extractParams) {
		this.request = request;
		
		this.params = extractParams;
		
		this.session = request.getSession();
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

	public Cookie[] cookies() {
		return request.getCookies();
	}
	
	public Maybe<String> parameter(String key) {
		return new Maybe(request.getParameter(key));
	}
}
