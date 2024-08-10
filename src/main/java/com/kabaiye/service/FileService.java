package com.kabaiye.service;


import com.kabaiye.exception.FileUploadException;
import org.springframework.web.multipart.MultipartFile;


public interface FileService {
    /**
     * 保存文件到服务端
     * @return 文件完整url
     */
    String saveFile(MultipartFile file, String path,String fileName) throws FileUploadException;

    /**
     * 上传文件
     * @param file
     */
    String uploadFile(MultipartFile file) throws FileUploadException;

    /**
     * 获取文件后缀
     */
    String getFileSuffix(MultipartFile file);

    /**
     * 删除文件
     */
    String deleteFile(String fullPath);
}
