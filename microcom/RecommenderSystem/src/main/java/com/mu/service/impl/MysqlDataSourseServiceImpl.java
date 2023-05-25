package com.mu.service.impl;

import com.mu.service.RecommendService;
import com.mu.utils.MySQLDataSourceUtil;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import org.apache.mahout.cf.taste.impl.model.jdbc.MySQLJDBCDataModel;
import org.apache.mahout.cf.taste.model.JDBCDataModel;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author MUZUKI
 * @Classname MySQLDataSourseServiceImpl
 * @Description TODO
 * @Date 2023/5/23 10:00
 */

@Service
public class MysqlDataSourseServiceImpl implements RecommendService {

    @Override
    public List recommendByUser(Long userId, Integer size) throws Exception {
        MysqlDataSource dataSource = MySQLDataSourceUtil.getDataSource();
        JDBCDataModel dataModel=new MySQLJDBCDataModel(dataSource,"recommend","uid","iid","val", "time");
        return null;
    }

    @Override
    public List recommendByItem(Long userId, Long itemId, Integer size) throws Exception {
        return null;
    }

    @Override
    public List<Long> recommendByUserWithUncenteredCosine(Long userId, Integer size) throws Exception {
        return null;
    }
}
