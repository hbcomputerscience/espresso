package org.hbw.espresso;

@FunctionalInterface
public interface Handler {

	public Object accept(Request req, Response res);
}
