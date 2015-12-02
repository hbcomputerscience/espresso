package org.hbw.espresso;

import javax.servlet.http.HttpServletResponse;

public class Response {

    private final HttpServletResponse response;
    
    private String contentType = "text/html;charset=utf-8";
    
    private Integer status = 200;
    
	public Response(HttpServletResponse response) {
        this.response = response;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
    
    public String getRaw() {
        return "test";
    }
}
