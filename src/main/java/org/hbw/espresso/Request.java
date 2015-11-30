package org.hbw.espresso;

import org.hbw.espresso.core.functor.Maybe;
import java.util.HashMap;

/**
 *
 * @author Bryan Eastwood
 */
public class Request {
    HashMap<String, String> params;
    
    public Maybe<String> getParam(String param) {
        return new Maybe(params.get(param));
    }
}