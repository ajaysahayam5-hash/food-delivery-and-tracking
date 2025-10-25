package com.fooddelivery.exception;

import com.fooddelivery.dto.ApiResponse;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * Wraps every /api/** response into standard {success, data, message} envelope.
 * Why: capstone non-negotiable (Section 4.1) requires one consistent JSON structure.
 * Frontend Axios unwraps .data automatically so pages keep using r.data.
 */
@RestControllerAdvice
public class ApiResponseAdvice implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
                                  Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                  ServerHttpRequest request, ServerHttpResponse response) {
        String path = request.getURI().getPath();
        if (!path.startsWith("/api/")) {
            return body;
        }
        if (body instanceof ApiResponse) {
            return body;
        }
        if (body instanceof String) {
            return body;
        }
        return ApiResponse.ok(body, "OK");
    }
}
