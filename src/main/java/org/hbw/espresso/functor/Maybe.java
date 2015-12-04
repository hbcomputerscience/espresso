package org.hbw.espresso.functor;

import java.util.function.Consumer;
import java.util.function.Function;

/**
 *
 * @author Bryan Eastwood
 * @param <T>
 */
public class Maybe<T> implements Functor<T> {

	private final T value;

	public Maybe(T x) {
		value = x;
	}

	/**
	 * Returns whether the value of the maybe is nothing
	 *
	 * @return boolean isNothing
	 */
	public boolean isNothing() {
		return value == null;
	}

	/**
	 * Returns x if value is null, otherwise returns value.
	 *
	 * @param x
	 * @return
	 */
	public T maybe(T x) {
		return value == null ? x : value;
	}

	/**
	 * Converts the maybe to a human readable string.
	 *
	 * @return String humanReadableMaybe
	 */
	@Override
	public String toString() {
		return value == null ? "Nothing" : "Just " + value.toString();
	}

	/**
	 * Takes a function that takes a parameter of the type T, which is the same
	 * type as the value of the maybe and, if the value of the maybe is not
	 * nothing, returns a new maybe containing the return value of the function
	 * called with the value of the maybe.
	 *
	 * @param <T2>
	 * @param f
	 * @return new maybe with the value of the return value of f called with the
	 * argument of the value of the maybe
	 */
	@Override
	public <T2> Maybe<T2> fmap(Function<T, T2> f) {
		return new Maybe(value == null ? value : f.apply(value));
	}

	/**
	 * Takes a consumer f and if the value is not null, calls f with the
	 * argument of the value of the maybe.
	 *
	 * @param f
	 */
	public void fmap(Consumer<T> f) {
		if (value != null) {
			f.accept(value);
		}
	}
}
