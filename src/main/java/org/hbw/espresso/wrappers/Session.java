package org.hbw.espresso.wrappers;

import java.util.Enumeration;
import javax.servlet.http.HttpSession;
import org.hbw.espresso.functor.Maybe;

public class Session {
	
	private final HttpSession session;
	
	public Session(HttpSession session) {
		this.session = session;
	}
	
	public <T> Maybe<T> get(String name) {
		return new Maybe<>((T) session.getAttribute(name));
	}
	
	public <T> T get(String name, T defaultValue) {
		return (T) get(name).maybe(defaultValue);
	}
	
	public Session set(String name, Object value) {
		session.setAttribute(name, value);
		
		return this;
	}

	public long creationTime() {
		return session.getCreationTime();
	}

	public String id() {
		return session.getId();
	}

	public long lastAccessedTime() {
		return session.getLastAccessedTime();
	}

	public Session maxInactiveInterval(int i) {
		session.setMaxInactiveInterval(i);
		
		return this;
	}

	public int maxInactiveInterval() {
		return session.getMaxInactiveInterval();
	}

	public Enumeration<String> names() {
		return session.getAttributeNames();
	}

	public Session remove(String string) {
		session.removeAttribute(string);
		
		return this;
	}

	public void invalidate() {
		session.invalidate();
	}

	public boolean isNew() {
		return session.isNew();
	}
	
	public HttpSession raw() {
		return session;
	}
}
