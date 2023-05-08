package com.mu.controller;

import cn.dev33.satoken.util.SaResult;
import com.mu.service.impl.UploadServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author : MUZUKI
 * @date : 2023-03-22 15:17
 * 文件上传控制器
 **/

@Slf4j
@RestController
@CrossOrigin
@RequestMapping("/upload/")
public class UploadController {

    @Autowired
    private UploadServiceImpl uploadService;

    /**
     * 上传图片
     * @param file 文件
     */
    @PostMapping("image")
    public SaResult uploadImage(@RequestPart(value = "file") final MultipartFile file){
        String url=uploadService.uploadImage(file);
        log.info("返回地址：【{}】",url);
        if (url==null){
            return SaResult.ok("图片上传失败");
        }
        return SaResult.ok("图片上传成功").setData(url);
    }
}