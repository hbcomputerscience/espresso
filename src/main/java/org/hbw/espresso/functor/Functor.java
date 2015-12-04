package org.hbw.espresso.functor;

import java.util.function.Function;

/**
 *
 * @author Bryan Eastwood
 * @param <T>
 */
public interface Functor<T> {

	/**
	 * 
	 * @param <T2>
	 * @param f
	 * @return 
	 */
	<T2> Functor<T2> fmap(Function<T, T2> f);
}
