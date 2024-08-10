package com.kabaiye.config;

import com.kabaiye.entity.vo.R;
import com.kabaiye.exception.FileUploadException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(FileUploadException.class)
    public R handlerFileUploadException(FileUploadException ex){
        return R.error("Global：文件上传失败");
    }

    @ExceptionHandler(ClassNotFoundException.class)
    public R classNotFoundException(ClassNotFoundException ex){
        return R.error("获取拦截器需要拦截的控制器路径失败 in InterceptorConfig");
    }
}
