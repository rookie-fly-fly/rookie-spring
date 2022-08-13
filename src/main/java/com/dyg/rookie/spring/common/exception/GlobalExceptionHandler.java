package com.dyg.rookie.spring.common.exception;

import com.dyg.rookie.spring.common.constants.CommonErrorCodeEnum;
import com.dyg.rookie.spring.common.resp.CallResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

/**
 * GlobalExceptionHandler 类是 全局异常处理类 所有的异常都会被此类捕获，并返回统一的错误信息 对业务异常/参数校验进行单独处理
 * 如有需要,通过 @ExceptionHandler 注解捕获其他异常
 *
 * @author rookie-spring
 * @date 2022/6/24 16:43
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    /**
     * 参数校验异常捕获处理 对方法参数校验异常处理方法(前端提交的方式为json格式出现异常时会被该异常类处理)
     * json格式提交时，spring会采用json数据的数据转换器进行处理
     * （进行参数校验时错误是抛出MethodArgumentNotValidException异常）
     *
     * @param ex 异常信息
     * @return 返回响应
     */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public CallResponse<Void> methodArgumentNotValidExceptionHandler(BindException ex, HttpServletRequest request) {
        log.error(
                "MethodArgumentNotValidException Business-Uri:{},Exception=Cause:{},Message:{},QueryString:{},Param:{}",
                request.getRequestURI(), ex.getCause(), ex.getMessage(), request.getQueryString(),
                getRequestParam(request)
        );
        BindingResult bindingResult = ex.getBindingResult();
        String errorMessage = Optional.ofNullable(bindingResult.getFieldError()).map(FieldError::getDefaultMessage)
                .orElse("请求参数错误");
        return CallResponse.error(errorMessage);
    }

    /**
     * 参数校验异常捕获处理
     *
     * @param ex 异常信息
     * @return 返回响应
     */
    @ExceptionHandler(value = ConstraintViolationException.class)
    public CallResponse<Void> constraintViolationExceptionHandler(ConstraintViolationException ex,
                                                                  HttpServletRequest request) {
        log.error("ConstraintViolationException Business-Uri:{},Exception=Cause:{},Message:{},QueryString:{},Parm:{}",
                request.getRequestURI(), ex.getCause(), ex.getMessage(), request.getQueryString(),
                getRequestParam(request)
        );
        return CallResponse.error(ex.getMessage());
    }

    /**
     * 业务异常捕捉处理
     *
     * @param ex 异常信息
     * @return 返回响应
     */
    @ExceptionHandler(value = BusinessRuntimeException.class)
    public CallResponse<Void> businessHandler(BusinessRuntimeException ex, HttpServletRequest request) {
        log.error("BusinessRuntimeException Business-Uri:{},Exception=Cause:{},Message:{},QueryString:{},Parm:{}",
                request.getRequestURI(), ex.getCause(), ex.getMessage(), request.getQueryString(),
                getRequestParam(request)
        );
        return CallResponse.error(ex.getCode(), ex.getMessage());
    }

    /**
     * 全局异常捕捉处理-兜底异常处理
     *
     * @param ex 异常信息
     * @return 返回响应
     */
    @ExceptionHandler(value = Exception.class)
    public CallResponse<Void> errorHandler(Exception ex, HttpServletRequest request) {
        log.error("Exception Exception-Uri:{},Exception=Cause:{},Message:{},QueryString:{},Parm:{}",
                request.getRequestURI(), ex.getCause(), ex.getMessage(), request.getQueryString(),
                getRequestParam(request), ex
        );
        return CallResponse.error(CommonErrorCodeEnum.SYSTEM_ERROR.getMsg());
    }

    /**
     * 获取post请求
     *
     * @param request HttpServletRequest
     * @return String
     */
    private String getRequestParam(HttpServletRequest request) {
        String line;
        StringBuilder sb = new StringBuilder();
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(request.getInputStream(), StandardCharsets.UTF_8))) {
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            log.error("getRequestParam 获取POST请求异常:{}", e);
        }
        return sb.toString();
    }
}
