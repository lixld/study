package com.lixl.study.beautyController.exception;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lixl.study.beautyController.Response;
import com.lixl.study.beautyController.exception.business.APIException;
import com.lixl.study.beautyController.exception.common.ErrorCodeEum;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@RestControllerAdvice(basePackages = "com.lixl.study.service")
public class ControllerExceptionAdvice implements ResponseBodyAdvice {
    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        boolean assignableFrom = returnType.getParameterType().isAssignableFrom(Response.class);
        return !assignableFrom;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        Response value = Response.succ(body);
        boolean isStringResult = returnType.getParameterType().equals(String.class);
        if (isStringResult) {
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                String s = objectMapper.writeValueAsString(value);
                return s;
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                throw new APIException(ErrorCodeEum.PARAM_ERROR, e.getMessage());
            }
        }
        return value;
    }

    @ExceptionHandler({BindException.class})
    public Response BindExceptionHandler(BindException e) {
        ObjectError objectError = e.getBindingResult().getAllErrors().get(0);
        Response<Object> fail = Response.fail(ErrorCodeEum.PARAM_ERROR.getCode(), objectError.getDefaultMessage());
        return fail;
    }
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public Response methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        ObjectError objectError = e.getBindingResult().getAllErrors().get(0);
        Response<Object> fail = Response.fail(ErrorCodeEum.PARAM_ERROR.getCode(), objectError.getDefaultMessage());
        return fail;
    }

    @ExceptionHandler(APIException.class)
    public Response APIExceptionHandler(APIException apiException) {
        int code = apiException.getCode();
        String message = apiException.getMessage();
        Response<Object> fail = Response.fail(code, message);
        return fail;
    }

    /**
     * 兜底
     *
     * @param exception
     * @return
     */
    @ExceptionHandler(Exception.class)
    public Response APIExceptionHandler(Exception exception) {
        exception.printStackTrace();
        Response<Object> fail = Response.fail(exception.getMessage());
        return fail;
    }
}
