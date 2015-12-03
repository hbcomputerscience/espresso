package org.hbw.espresso;

@FunctionalInterface
public interface Handler<T> {

	public T accept(Request req, Response res);
}
