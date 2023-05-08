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
     *
     * @param file 文件
     */
    @PostMapping("image")
<<<<<<< HEAD
    public SaResult uploadImage(@RequestPart(value = "file") final MultipartFile file){
        String url=uploadService.uploadImage(file);
        log.info("返回地址：【{}】",url);
=======
    public SaResult uploadImage(@RequestPart MultipartFile file) {
        String url = uploadService.uploadImage(file);
        log.info("返回地址：【{}】", url);
>>>>>>> da0ff57595972358aeb325d866c6732d053409b1
        return SaResult.ok(url);
    }

    @PostMapping("test")
    public SaResult test(@RequestParam("image") MultipartFile file) {
        log.info(file.getOriginalFilename());
        return SaResult.ok().setMsg("test");
    }

    @PostMapping("datatest")
    public SaResult test1(@RequestParam("name") String name) {
        log.info(name);
        return SaResult.ok().setMsg("datatest");
    }
}