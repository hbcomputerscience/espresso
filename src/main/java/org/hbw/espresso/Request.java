package org.hbw.espresso;

import java.util.List;
import javax.servlet.http.HttpServletRequest;

public class Request {

	private final HttpServletRequest request;

	private final List<String> params;

	public Request(HttpServletRequest request, List<String> extractParams) {
		this.request = request;
		this.params = extractParams;
	}

	public List<String> getParams() {
		return params;
	}
}
