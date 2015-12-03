package org.hbw.espresso;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;

public class Response {

	private final HttpServletResponse response;

	private String contentType = "text/html;charset=utf-8";

	private Integer status = 200;

	private final Map<String, String> headers = new HashMap<>();
	
	private StringBuilder buffer = new StringBuilder();

	public Response(HttpServletResponse response) {
		this.response = response;
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
		throw new UnsupportedOperationException("Redirect not implemented");
	}
}
