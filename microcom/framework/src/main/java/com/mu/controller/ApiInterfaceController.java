package com.mu.controller;

import cn.dev33.satoken.util.SaResult;
import com.mu.entity.ApiInterface;
import com.mu.service.impl.ApiInterfaceServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author : MUZUKI
 * @date : 2023-10-20 16:55
 **/

@RestController
@RequestMapping("/api")
public class ApiInterfaceController {
    @Autowired
    ApiInterfaceServiceImpl apiInterfaceService;

    @GetMapping("/get")
    public SaResult getApiInterface(@RequestParam Map<String, String> map){
        return SaResult.ok().setData(apiInterfaceService.getApiInterface(map));
    }

    @PostMapping("/add")
    public SaResult addApiInterface(ApiInterface apiInterface){
        return SaResult.ok().setData(apiInterfaceService.save(apiInterface));
    }

    @DeleteMapping("/delete")
    public SaResult deleteApiInterface(Integer id){
        return SaResult.ok().setData(apiInterfaceService.removeById(id));
    }

    @PostMapping("/update")
    public SaResult updateApiInterface(ApiInterface apiInterface){
        return SaResult.ok().setData(apiInterfaceService.updateById(apiInterface));
    }
}
