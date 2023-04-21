package com.mu.framework.controller;

import com.mu.framework.domain.Flink;
import com.mu.framework.service.impl.FlinkServiceImpl;
import com.mu.framework.utils.HttpUtils;
import com.mu.framework.model.JsonModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;

/**
 * @author MUZUKI
 * @Classname FlinkController
 * @Description TODO
 * @Date 2023/3/26 1:58
 */

@RestController
@RequestMapping("/flink")
public class FlinkController {
    @Autowired
    private FlinkServiceImpl flinkService;

    @RequestMapping(value = "/addFlink")
    public JsonModel addFlink(HttpServletRequest request) {
        Flink flink = HttpUtils.parseRequestToT(request,Flink.class);
        JsonModel jm = new JsonModel();
        jm.setCode(flinkService.addFlink(flink));
        return jm;
    }

    @RequestMapping(value = "/getFlink")
    public JsonModel getFlink() {
        JsonModel jm = new JsonModel();
        jm.setCode(1).setData(flinkService.getFlink());
        return jm;
    }

    @RequestMapping(value = "/deleteFlinkById")
    public JsonModel deleteFlinkById(HttpServletRequest request) {
        JsonModel jm = new JsonModel();
        int id = Integer.parseInt(request.getParameter("id"));
        jm.setCode(1).setData(flinkService.deleteFlinkById(id));
        return jm;
    }
}
