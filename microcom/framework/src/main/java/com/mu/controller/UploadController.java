package com.mu.controller;

import cn.dev33.satoken.util.SaResult;
import com.mu.service.UploadService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author : MUZUKI
 * @date : 2023-03-22 15:17
 * 文件上传控制器
 **/

@Slf4j
@RestController
@RequestMapping("/upload/")
public class UploadController {

    @Autowired
    private UploadService uploadService;

    /**
     * 上传图片
     * @param file 文件
     */
    @PostMapping("image")
    public SaResult uploadImage(@RequestPart MultipartFile file){
        String url=uploadService.uploadImage(file);
        log.info("返回地址：【{}】",url);
        return SaResult.ok(url);
    }

    @PostMapping("test")
    public SaResult test(@RequestParam("image") MultipartFile file){
        return SaResult.ok().setMsg("test");
    }
}