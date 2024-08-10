package com.kabaiye.service.impl;

import cn.hutool.core.util.RandomUtil;
import com.kabaiye.exception.FileUploadException;
import com.kabaiye.service.FileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@Slf4j
public class FileServiceImpl implements FileService {
    @Override
    public String saveFile(MultipartFile file, String path, String fileName) throws FileUploadException {
        String intactUri="";
        try {
            // 获取上传的目录绝对路径(编译路径）
            ClassPathResource classPathResource = new ClassPathResource("static");
            String classesPath = classPathResource.getURL().getPath() + path;
            classesPath = classesPath.replaceAll("/D:","");
            // 建classesPath文件夹
            Path uploadPath = Paths.get(classesPath);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            // 文件相对项目路径
            intactUri = path + fileName;

            log.info("FileService.saveFile: 完整的文件相对路径：{}",intactUri);

            // 保存文件到绝对路径
            Files.copy(file.getInputStream(), uploadPath.resolve(fileName));
            if (Files.exists(uploadPath.resolve(fileName))) {
                log.info("文件已成功保存: {}", uploadPath.resolve(fileName));
            } else {
                log.error("文件保存失败: 文件不存在于指定位置");
            }

            try {
                // 获取源代码目录下对应路径
                String resourcePath = System.getProperty("user.dir") + "\\src\\main\\resources\\static";
                resourcePath = resourcePath
                        .replaceAll("D:","")
                        .replaceAll("\\\\","/") + path;
                // 建resourcePath文件夹
                Path uploadPath2 = Paths.get(resourcePath);
                if (!Files.exists(uploadPath2)) {
                    Files.createDirectories(uploadPath2);
                }
                // 复制文件到源代码路径
                Files.copy(file.getInputStream(), uploadPath2.resolve(fileName));
            }catch (Exception ignored){} // 报错说明是上线环境，没有项目源代码
        } catch (IOException e) {
            e.printStackTrace();
            throw new FileUploadException("文件上传失败");
        }
        // 返回uri
        return intactUri;
    }

    @Override
    public String uploadFile(MultipartFile file) throws FileUploadException {
        String suffix = getFileSuffix(file);
        if(suffix==null){
            return null;
        }
        String path = "/file/otherFile/";
        String fileName = "file_"+ RandomUtil.randomString(15)+"."+suffix;
        return saveFile(file,path,fileName);
    }

    @Override
    public String getFileSuffix(MultipartFile file){
        String originalFilename = file.getOriginalFilename();
        if (originalFilename == null) {
            return null;
        }
        // 2.2 获取后缀
        return originalFilename.substring(originalFilename.lastIndexOf(".")+1);
    }

    @Override
    public String deleteFile(String fullPath) {
        // 获取上传的目录绝对路径(编译路径）
        ClassPathResource classPathResource = new ClassPathResource("static");
        if(fullPath.charAt(0)!='/'){
            fullPath = "/" + fullPath;
        }
        String classesPath = null;
        try {
            classesPath = classPathResource.getURL().getPath() + fullPath;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        // 建classesPath文件夹
        Path uploadPath = Paths.get(classesPath);
        if (!Files.exists(uploadPath)) {
            return null;
        }
        // 删除文件
        try {
            Files.delete(uploadPath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return "ok";
    }
}
