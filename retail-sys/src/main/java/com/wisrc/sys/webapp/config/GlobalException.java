package com.wisrc.sys.webapp.config;


import com.wisrc.sys.webapp.utils.Result;

public class GlobalException extends Exception {

    private Result result;

    public GlobalException(Result result) {
        this.result = result;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }
}
