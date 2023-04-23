package com.mu.controller;

import cn.dev33.satoken.annotation.SaCheckRole;
import cn.dev33.satoken.util.SaResult;
import cn.hutool.extra.servlet.ServletUtil;
import com.mu.domain.Category;
import com.mu.service.impl.CategoryServiceImpl;
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

    @RequestMapping(value = "/getCategory")
    public SaResult getCategory() {
        return SaResult.ok().setData(categoryService.getCategory());
    }

    @SaCheckRole("admin")
    @RequestMapping(value = "/addCategory")
    public SaResult addCategory(HttpServletRequest request) {
        Category category = ServletUtil.toBean(request, Category.class, true);
        return SaResult.ok().setData(categoryService.addCategory(category));
    }

    @SaCheckRole("admin")
    @RequestMapping(value = "/deleteCategory")
    public SaResult deleteCategory(HttpServletRequest request) {
        int categoryId = Integer.parseInt(request.getParameter("categoryId"));
        return SaResult.ok().setData(categoryService.deleteCategory(categoryId));
    }
}
