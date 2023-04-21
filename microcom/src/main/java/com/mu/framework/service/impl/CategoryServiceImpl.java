package com.mu.framework.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mu.framework.domain.Category;
import com.mu.framework.mapper.CategoryMapper;
import com.mu.framework.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

/**
* @author MUZUKI
* @description 针对表【category】的数据库操作Service实现
* @createDate 2023-03-05 19:20:49
*/
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {
    @Autowired(required = false)
    private CategoryMapper categoryMapper;

    /**
     * 获取所有分类
     */
    public List<Category> getCategory(){
        return categoryMapper.selectList(null);
    }

    /**
     * 添加分类
     */
    public int addCategory(Category category){
        return categoryMapper.insert(category);
    }

    /**
     * 删除分类
     */
    public int deleteCategory(int categoryId){
        return categoryMapper.deleteById(categoryId);
    }
}




