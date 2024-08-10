package com.kabaiye.config;

import com.kabaiye.controller.AuthController;
import com.kabaiye.controller.CategoryController;
import com.kabaiye.controller.UserController;
import com.kabaiye.interceptor.AdminInterception;
import com.kabaiye.interceptor.LoginInterceptor;
import com.kabaiye.myInterface.Logging;
import com.kabaiye.myInterface.Admin;
import com.kabaiye.util.AnnotationUtil;
import com.kabaiye.util.ClassUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
@Slf4j
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    @Autowired
    private LoginInterceptor loginInterceptor;

    @Autowired
    private AdminInterception adminInterception;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        log.info("进入addInterceptors");
        String packageName = "com.kabaiye.controller";
        String[] loggingUrl = getInterceptorUrls(packageName, Logging.class);
        String[] adminUrl = getInterceptorUrls(packageName, Admin.class);
        loggingUrl = mergeStringArrays(loggingUrl, adminUrl);
        log.info("配置拦截器路径，loggingUrl:{}", Arrays.toString(loggingUrl));
        log.info("配置拦截器路径，adminUrl:{}", Arrays.toString(adminUrl));
        // 登陆校验
        registry.addInterceptor(loginInterceptor)
                .addPathPatterns(loggingUrl)
                .order(10);

        // 管理员权限校验
        registry.addInterceptor(adminInterception)
                .addPathPatterns(adminUrl)
                .order(20);
    }

    private String[] getInterceptorUrls(String packageName,
                                      Class<? extends Annotation> annotationClazz){
        List<Class<?>> classList;
        try {
            classList = ClassUtils.getClasses(packageName);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Class<?>[] controllerClasses = classList.toArray(new Class[0]);
        String[] res = null;
        try {
            res = AnnotationUtil.scanPackage(controllerClasses, annotationClazz);
        } catch (ClassNotFoundException ignored) {
        }finally {
            // 添加占位请求，避免执行默认策略（拦截所有请求）
            if(res==null || res.length == 0){
                res = new String[]{"/placeholder/placeholder"};
            }
        }
        return res;
    }

    private String[] mergeStringArrays(String[] arr1, String[] arr2) {
        // 使用 HashSet 来存放合并后的结果，自动去重
        Set<String> set = new HashSet<>(Arrays.asList(arr1));
        set.addAll(Arrays.asList(arr2));
        // 将 Set 转换回 String 数组
        return set.toArray(new String[0]);
    }
}

