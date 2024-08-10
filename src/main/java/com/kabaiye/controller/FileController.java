package com.kabaiye.controller;

import com.kabaiye.entity.vo.R;
import com.kabaiye.exception.FileUploadException;
import com.kabaiye.myInterface.Admin;
import com.kabaiye.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class FileController {
    @Autowired
    private FileService fileService;
    /**
     * 上传文件
     * 请求方法：POST
     * 请求地址：/file/upload
     * 请求数据格式：multipart/form-data
     * 请求参数：file：文件，必传
     * 需要access_token： 是
     * 需要管理员权限： 是
     * 接口说明：文件大小不能超过5MB，上传成功后返回文件url。
     */
    @Admin
    @PostMapping("//file/upload") // 加两个/是因为前端写错了，懒得查位置了
    public R uploadFile(@RequestParam("file")MultipartFile file){
        try {
            return R.ok(fileService.uploadFile(file));
        } catch (FileUploadException e) {
            return R.error("文件上传失败，FileUploadException");
        }
    }

    /**
     * 删除文件
     * 请求方法：DELETE
     * 请求地址：/file/delete
     * 请求参数：fullPath ：文件url
     * 需要access_token： 是
     * 需要管理员权限： 是
     * 接口说明：删除已上传的文件
     * 删除成功:
     */
    @Admin
    @PostMapping("/file/delete")
    public R deleteFile(@RequestParam("fullPath")String fullPath){
        String msg = fileService.deleteFile(fullPath);
        if(msg==null){
            return R.error("删除失败，IO错误");
        }
        return R.ok(null);
    }

    /**
     * page文件
     */
    @Admin
    @GetMapping("/file/page")
    public R pageFile(@RequestParam("size") String size){
        return R.ok(null);
    }
}
