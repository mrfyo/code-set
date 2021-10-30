package com.feyon.codeset.controller.advice;

import com.feyon.codeset.controller.result.Result;
import com.feyon.codeset.constants.ResultCode;
import com.feyon.codeset.controller.result.ValidationFailResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

/**
 * @author Feng Yong
 */
@RestControllerAdvice
public class ExceptionControllerAdvice {

    private final Logger log = LoggerFactory.getLogger(ExceptionControllerAdvice.class);

    /**
     * 拦截 校验异常
     */
    @ExceptionHandler(ConstraintViolationException.class)
    Result onConstraintValidationException(ConstraintViolationException e) {
        ValidationFailResult errors = new ValidationFailResult();
        for (ConstraintViolation<?> violation : e.getConstraintViolations()) {
            String field = violation.getPropertyPath().toString();
            String message = violation.getMessage();
            errors.getDetails().add(new ValidationFailResult.Detail(field, message));
        }
        return errors;
    }

    /**
     * 拦截 参数绑定异常
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    Result onMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        ValidationFailResult errors = new ValidationFailResult();
        for (FieldError fieldError : e.getBindingResult().getFieldErrors()) {
            String field = fieldError.getField();
            String message = fieldError.getDefaultMessage();
            errors.getDetails().add(new ValidationFailResult.Detail(field, message));
        }
        return errors;
    }

    /**
     * 拦截 通用异常
     */
    @ExceptionHandler
    Result onUnknownException(Exception e) {
        log.warn("errorType: {}, errorMessage: {}", e.getClass(), e.getMessage());
        e.printStackTrace();
        return Result.fail(ResultCode.SYSTEM_ERROR, "服务器异常");
    }
}
