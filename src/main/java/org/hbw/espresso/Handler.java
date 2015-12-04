package org.hbw.espresso;

import org.hbw.espresso.wrappers.Response;
import org.hbw.espresso.wrappers.Request;

@FunctionalInterface
public interface Handler<T> {

	public T accept(Request req, Response res);
}
