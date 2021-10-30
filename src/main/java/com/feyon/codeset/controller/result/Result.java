package com.feyon.codeset.controller.result;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Class that can be used to load data or error message as the response body.
 * @author Feng Yong
 */
public interface Result {

    /**
     * return success result.
     * @return success result.
     */
    static Result success() {
        return new Success();
    }

    /**
     * return success result.
     * @param data response data.
     * @return success result.
     */
    static Result success(Object data) {
        return new Success(data);
    }

    /**
     * return fail result.
     * @param code error code.
     * @param message error message.
     * @return fail result.
     */
    static Result fail(int code, String message) {
        return new FailResult(code, message);
    }

    static Result fail(int code) {
        return new FailResult(code);
    }

    /**
     * return result code.
     * @return result code
     */
    int getCode();

    /**
     * return result message
     * @return result message
     */
    String getMessage();

    /**
     * Class that can be used to load data and meet a successful request.
     */
    class Success implements Result {

        private static final String DEFAULT_STATUS_MSG = "";

        @JsonInclude(JsonInclude.Include.NON_NULL)
        private Object data;


        public Success() {
        }

        public Success(Object data) {
            this.data = data;

        }

        @Override
        public int getCode() {
            return 0;
        }

        @Override
        public String getMessage() {
            return DEFAULT_STATUS_MSG;
        }

        public Object getData() {
            return data;
        }
    }


}
