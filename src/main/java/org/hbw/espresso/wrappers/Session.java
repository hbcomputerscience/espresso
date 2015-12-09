package org.hbw.espresso.wrappers;

import javax.servlet.http.HttpSession;

public class Session {
	
	private final HttpSession session;
	
	public Session(HttpSession session) {
		this.session = session;
	}
}
