package org.hbw.espresso;

import org.hbw.espresso.core.functor.Maybe;
import java.util.HashMap;

public class Request {
    
    public String path;
    private HashMap<String, String> params;
    
    public Maybe<String> getParam(String param) {
        return new Maybe(params.get(param));
    }
}