package org.hbw.espresso;

import java.util.ArrayList;
import java.util.List;

public class Request {
    private List<String> params = new ArrayList();
    
    public List<String> getParams() {
        return params;
    }
    
    public Request(List<String> ps) {
        params = ps;
    }
}