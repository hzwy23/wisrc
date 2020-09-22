package com.wisrc.wms.basic;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

public class WrapperedRequest extends HttpServletRequestWrapper {

    public WrapperedRequest(HttpServletRequest request) {
        super(request);
    }

}
