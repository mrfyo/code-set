package com.feyon.codeset.controller.result;

/**
 * @author Feng Yong
 */
class FailResult implements Result {

    private Integer code;

    private String message;

    public FailResult() {
    }

    public FailResult(int code) {
        this(code, null);
    }

    public FailResult(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
