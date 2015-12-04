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
	 * Returns false if the Maybe is Nothing, otherwise returns true
	 *
	 * @return boolean isNothing
	 */
	public boolean isNothing() {
		return value == null;
	}

	/**
	 * Returns x if the value is null, otherwise returns the non-encapsulated value;
	 *
	 * @param x
	 * @return
	 */
	public T maybe(T x) {
		return value == null ? x : value;
	}

	/**
	 * toString method
	 *
	 * @return String humanReadableMaybe
	 */
	@Override
	public String toString() {
		return value == null ? "Nothing" : "Just " + value.toString();
	}

	/**
	 * Returns the result of a function called on the value encapsulated in Maybe.
     * fmap(f, Maybe x) = Maybe f (x)
     * fmap(f, Nothing) = Nothing
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
