package com.mu.framework.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mu.framework.domain.Flink;
import com.mu.framework.mapper.FlinkMapper;
import com.mu.framework.service.FlinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

/**
* @author MUZUKI
* @description 针对表【flink】的数据库操作Service实现
* @createDate 2023-03-05 19:20:49
*/
@Service
public class FlinkServiceImpl extends ServiceImpl<FlinkMapper, Flink> implements FlinkService {
    @Autowired(required = false)
    private FlinkMapper flinkMapper;

    /**
     * 添加友链
     * @param flink
     */
    public int addFlink(Flink flink){
        return flinkMapper.insert(flink);
    }

    /**
     * 获取所有开启的友情链接
     */
    public List<Flink> getFlink(){
        QueryWrapper<Flink> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("status", 1);
        return flinkMapper.selectList(queryWrapper);
    }

    /**
     * 删除友链
     */

    public int deleteFlinkById(int id){
        return flinkMapper.deleteById(id);
    }

    /**
     * 修改密码
     */
    public void alterPassword(String oldPassword, String newPassword, String confirmPassword){}
}




