package com.mu.controller;

import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaResult;
import com.mu.entity.ApiInterface;
import com.mu.entity.User;
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
    public SaResult addApiInterface(@RequestBody ApiInterface apiInterface){
        apiInterface.setCreatedBy(StpUtil.getLoginIdAsString());
        apiInterface.setUpdatedBy(StpUtil.getLoginIdAsString());
        return SaResult.ok().setData(apiInterfaceService.save(apiInterface));
    }

    @DeleteMapping("/delete/{apiId}")
    public SaResult deleteApiInterface(@PathVariable("apiId") Integer id){
        return SaResult.ok().setData(apiInterfaceService.removeById(id));
    }

    @PostMapping("/update")
    public SaResult updateApiInterface(@RequestBody ApiInterface apiInterface){
        apiInterface.setUpdatedBy(StpUtil.getLoginIdAsString());
        return SaResult.ok().setData(apiInterfaceService.updateById(apiInterface));
    }

    @GetMapping("/search")
    public SaResult search(@RequestParam Map<String, String> map){
        return SaResult.ok().setData(apiInterfaceService.search(map));
    }
}
