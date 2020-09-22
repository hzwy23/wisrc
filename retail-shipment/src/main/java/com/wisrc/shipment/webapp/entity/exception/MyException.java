package com.wisrc.shipment.webapp.entity.exception;

public class MyException extends RuntimeException {
    private int errorCode;
    private String message;
    private Throwable cause;

    public MyException(int errorCode, String message, Throwable cause) {
        this.errorCode = errorCode;
        this.message = message;
        this.cause = cause;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public Throwable getCause() {
        return cause;
    }

    public void setCause(Throwable cause) {
        this.cause = cause;
    }
}
