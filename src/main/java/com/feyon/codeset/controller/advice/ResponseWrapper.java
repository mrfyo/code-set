package com.feyon.codeset.controller.advice;


import com.feyon.codeset.controller.result.Result;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * Class that can be used to convert POJO to {@link Result}
 *
 * @author Feng Yong
 */
@RestControllerAdvice(basePackages = "com.feyon.codeset")
public class ResponseWrapper implements ResponseBodyAdvice<Object> {

    /**
     * 如果响应体对象不是{@link Result}，则返回 {@code true}
     *
     * @param returnType    返回类型
     * @param converterType 转换器类型
     * @return 如果响应体对象不是{@link JsonResult}，则返回 {@code true}
     */
    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        return !Result.class.isAssignableFrom(returnType.getParameterType());
    }

    /**
     * 包装响应体对象
     *
     * @param body                  响应体对象
     * @param returnType            响应体类型
     * @param selectedContentType   被选择内容类型
     * @param selectedConverterType 被选择的转换器类型
     * @param request               the current request
     * @param response              the current response
     * @return {@link Result}
     */
    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        return Result.success(body);
    }
}
