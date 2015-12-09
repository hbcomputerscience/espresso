package org.hbw.espresso.wrappers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.hbw.espresso.functor.Maybe;

public class Request {

	private final HttpServletRequest request;

	private final List<String> params;
	
	private Maybe<Session> session = new Maybe<>(null);
	
	private final List<Cookie> cookies;
	
	private Maybe<Map<String, String>> cookieValuesMap = new Maybe<>(null);

	public Request(HttpServletRequest request, List<String> extractParams) {
		this.request = request;
		
		this.params = extractParams;
		
		this.cookies = new ArrayList<>(Arrays.asList(request.getCookies()));
	}

	public List<String> parameters() {
		return params;
	}
	
	public Session session() {
		if (session.isNothing()) {
			session = new Maybe<> (new Session(request.getSession()));
		}
		
		return session.maybe(null);
	}

	
	public Maybe<Session> session(boolean create) {
		if (session.isNothing()) {
			Maybe<HttpSession> httpSession = new Maybe<>(request.getSession(create));
			
			httpSession.fmap(httpSess -> {
				session = new Maybe<>(new Session(httpSess));
			});
		}
		return session;
	}
	
	public List<Cookie> cookies() {
		return Collections.unmodifiableList(cookies);
	}
	
	public Map<String, String> cookiesValueMap() {
		if (cookieValuesMap.isNothing()) {
			cookieValuesMap = new Maybe(new HashMap<>(cookies.size()));
			
			cookieValuesMap.fmap(map -> {
				cookies.stream().forEach(cookie -> {
					map.put(cookie.getName(), cookie.getValue());
				});
			});
		}
		
		return Collections.unmodifiableMap(cookieValuesMap.maybe(null));
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
