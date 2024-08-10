package com.kabaiye.util;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class AnnotationUtil {

    /**
     * 获取带有 annotationClazz(@Logging或@Admin)注解的Handel的完整请求路径列表。
     * @param clazz 要检查的类
     * @return 完整请求路径列表
     */
    private static List<String> getFullAnnotationPaths(Class<?> clazz,
                                                   Class<? extends Annotation> annotationClazz) {
        List<String> fullRequestPaths = new ArrayList<>();

        // 获取类上的 RequestsMapping 注解
        RequestMapping requestsMapping = clazz.getAnnotation(RequestMapping.class);
        String classLevelPath = "";
        if (requestsMapping != null) {
            classLevelPath = requestsMapping.value()[0];
        }

        // 获取类中所有带有 annotationClazz 注解的方法
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            if (method.isAnnotationPresent(annotationClazz)) {
                // 获取方法上的 GetMapping、PostMapping、PutMapping 或 DeleteMapping 注解value
                for(String fullPath : getFullPathList(method)){
                    fullRequestPaths.add(classLevelPath+fullPath);
                }
            }
        }

        return fullRequestPaths;
    }

    private static String[] getFullPathList(Method method) {
        String[] methodLevelPathList = {};
        if (method.isAnnotationPresent(GetMapping.class)) {
            methodLevelPathList = method.getAnnotation(GetMapping.class).value();
        } else if (method.isAnnotationPresent(PostMapping.class)) {
            methodLevelPathList = method.getAnnotation(PostMapping.class).value();
        } else if (method.isAnnotationPresent(PutMapping.class)) {
            methodLevelPathList = method.getAnnotation(PutMapping.class).value();
        } else if (method.isAnnotationPresent(DeleteMapping.class)) {
            methodLevelPathList = method.getAnnotation(DeleteMapping.class).value();
        }
        // 拼接完整的请求路径
        return methodLevelPathList;
    }

    /**
     * 遍历指定包下的所有类，并获取带有指定注解的方法的完整请求路径列表。
     * @param annotationClazz 注解类型
     * @return 所有类的完整请求路径列表
     */
    public static <A extends Annotation> String[] scanPackage(Class<?>[] classList,Class<A> annotationClazz) throws ClassNotFoundException {
        List<String> allUrls = new ArrayList<>();
        for(Class<?> clazz:classList){
            List<String> urls = AnnotationUtil.getFullAnnotationPaths(clazz,annotationClazz);
            allUrls.addAll(urls);
        }
        return allUrls.toArray(new String[0]);
    }
}
