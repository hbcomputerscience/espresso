package org.hbw.espresso.core.functor;

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
    
    public T maybe(T x) {
        return value == null ? x : value;
    }
    
    @Override
    public String toString() {
        return value == null ? "Nothing" : "Just " + value.toString();
    }
    
    @Override
    public <T2> Maybe<T2> fmap(Function<T,T2> f) {
        return new Maybe(value == null ? value : f.apply(value));
    }
}