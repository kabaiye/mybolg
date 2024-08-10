package com.kabaiye;

import jakarta.servlet.MultipartConfigElement;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@Slf4j
@SpringBootApplication
public class MyMainApplication{
    public static void main(String[] args) {
        SpringApplication.run(MyMainApplication.class,args);
        log.info("项目在 http://localhost:8080/ 启动");
    }
}
