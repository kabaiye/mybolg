package com.kabaiye.util;

import java.io.File;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Optional;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class ClassUtils {

    /**
     * 获取某个包下的所有类。
     *
     * @param packageName 包名
     * @return 包含类的集合
     * @throws IOException 如果发生 I/O 错误
     */
    public static List<Class<?>> getClasses(String packageName) throws IOException {
        List<Class<?>> classes = new ArrayList<>();
        Enumeration<URL> resources = Thread.currentThread().getContextClassLoader().getResources(packageName.replace(".", "/"));
        while (resources.hasMoreElements()) {
            URL resource = resources.nextElement();
            if ("file".equals(resource.getProtocol())) {
                loadClassesFromDirectory(new File(resource.getFile()), packageName, classes);
            } else if ("jar".equals(resource.getProtocol())) {
                loadClassesFromJar(resource, packageName, classes);
            }
        }
        return classes;
    }

    private static void loadClassesFromDirectory(File directory, String packageName, List<Class<?>> classes) {
        File[] files = directory.listFiles(file -> file.isDirectory() || file.getName().endsWith(".class"));
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    loadClassesFromDirectory(file, packageName + "." + file.getName(), classes);
                } else {
                    String className = packageName + '.' + file.getName().substring(0, file.getName().length() - 6);
                    classes.add(loadClass(className));
                }
            }
        }
    }

    private static void loadClassesFromJar(URL jarUrl, String packageName, List<Class<?>> classes) throws IOException {
        JarURLConnection jarURLConnection = (JarURLConnection) jarUrl.openConnection();
        JarFile jarFile = jarURLConnection.getJarFile();
        Enumeration<JarEntry> jarEntries = jarFile.entries();
        while (jarEntries.hasMoreElements()) {
            JarEntry jarEntry = jarEntries.nextElement();
            String jarEntryName = jarEntry.getName();
            if (jarEntryName.endsWith(".class") && jarEntryName.startsWith(packageName.replace(".", "/"))) {
                String className = jarEntryName.substring(0, jarEntryName.lastIndexOf(".")).replace("/", ".");
                classes.add(loadClass(className));
            }
        }
    }

    /**
     * 加载类。
     *
     * @param className 类名
     * @return 加载的类
     */
    private static Class<?> loadClass(String className) {
        try {
            return Class.forName(className, true, getClassLoader());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取当前线程上下文类加载器。
     *
     * @return 类加载器
     */
    private static ClassLoader getClassLoader() {
        return Optional.ofNullable(Thread.currentThread().getContextClassLoader())
                .orElseGet(ClassLoader::getSystemClassLoader);
    }
}