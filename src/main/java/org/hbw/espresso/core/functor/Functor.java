package org.hbw.espresso.core.functor;

import java.util.function.Function;

/**
 *
 * @author Bryan Eastwood
 * @param <T>
 */
public interface Functor<T> {
    <T2> Functor<T2> fmap(Function<T,T2> f);
}