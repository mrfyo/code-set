package com.feyon.codeset.controller.result;

import com.feyon.codeset.constants.ResultCode;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Feng Yong
 */
public class ValidationFailResult extends FailResult {

    private final List<Detail> details;

    public ValidationFailResult() {
        super(ResultCode.VALIDATION_FAIL, "params validation fail");
        this.details = new ArrayList<>(16);
    }

    public List<Detail> getDetails() {
        return details;
    }

    public static Detail detail(String name, String message) {
        return new Detail(name, message);
    }

    public static class Detail {

        private final String name;

        private final String message;

        public Detail(String name, String message) {
            this.name = name;
            this.message = message;
        }

        public String getMessage() {
            return message;
        }

        public String getName() {
            return name;
        }
    }
}
