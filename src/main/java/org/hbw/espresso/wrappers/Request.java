package org.hbw.espresso.wrappers;

import java.util.List;
import javax.servlet.http.HttpServletRequest;

public class Request {

	private final HttpServletRequest request;

	private final List<String> params;
	
	private final Session session;

	public Request(HttpServletRequest request, List<String> extractParams) {
		this.request = request;
		
		this.params = extractParams;
		
		this.session = new Session(request.getSession());
	}

	public List<String> params() {
		return params;
	}
	
	public Session session() {
		return session;
	}
}
