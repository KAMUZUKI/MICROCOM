package com.mu.controller;

import com.mu.domain.Category;
import com.mu.model.JsonModel;
import com.mu.service.impl.CategoryServiceImpl;
import com.mu.utils.HttpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author MUZUKI
 * @Classname CategoryController
 * @Description TODO
 * @Date 2023/3/26 1:49
 */

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    CategoryServiceImpl categoryService;

    private JsonModel jm = new JsonModel();

    @RequestMapping(value = "/getCategory")
    public JsonModel getCategory() {
        jm.setCode(1).setData(categoryService.getCategory());
        return jm;
    }

    @RequestMapping(value = "/addCategory")
    public JsonModel addCategory(HttpServletRequest request) {
        Category category = HttpUtils.parseRequestToT(request, Category.class);
        jm.setCode(categoryService.addCategory(category));
        return jm;
    }

    @RequestMapping(value = "/deleteCategory")
    public JsonModel deleteCategory(HttpServletRequest request) {
        int categoryId = Integer.parseInt(request.getParameter("categoryId"));
        jm.setCode(categoryService.deleteCategory(categoryId));
        return jm;
    }
}
