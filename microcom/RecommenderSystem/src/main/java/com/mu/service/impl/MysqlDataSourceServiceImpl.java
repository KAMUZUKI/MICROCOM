package com.mu.service.impl;

import com.mu.mapper.VlogMapper;
import com.mu.service.AbstractRecommendService;
import com.mu.service.RecommendService;
import com.mu.utils.MySQLDataSourceUtil;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import org.apache.mahout.cf.taste.impl.model.jdbc.MySQLJDBCDataModel;
import org.apache.mahout.cf.taste.model.JDBCDataModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author MUZUKI
 * @Classname MySQLDataSourseServiceImpl
 * @Description TODO
 * @Date 2023/5/23 10:00
 */

@Service
public class MysqlDataSourceServiceImpl extends AbstractRecommendService implements RecommendService {
    @Autowired
    private VlogMapper vlogMapper;

    @Override
    public List<Long> recommendByUser(Long userId, Integer size) {
        MysqlDataSource dataSource = MySQLDataSourceUtil.getDataSource();
        MySQLJDBCDataModel dataModel=new MySQLJDBCDataModel(dataSource,"recommend","uid","iid","value", "time");
        return this.absRecommendByUser(dataModel,userId,size);
    }

    @Override
    public List<Long> recommendByItem(Long userId, Long itemId, Integer size) {
        MysqlDataSource dataSource = MySQLDataSourceUtil.getDataSource();
        JDBCDataModel dataModel=new MySQLJDBCDataModel(dataSource,"recommend","uid","iid","value", "time");
        return this.absRecommendByItem(dataModel,userId,itemId,size);
    }

    public List<Long> recommendByItemWithTimeDecay(Long userId, Long itemId, Integer size) {
        MysqlDataSource dataSource = MySQLDataSourceUtil.getDataSource();
        MySQLJDBCDataModel dataModel=new MySQLJDBCDataModel(dataSource,"recommend","uid","iid","value", "time");
        return this.absRecommendByItemWithTimeDecay(vlogMapper,dataModel,userId,itemId,size);
    }

    @Override
    public List<Long> recommendByUserWithUncenteredCosine(Long userId, Integer size) {
        MysqlDataSource dataSource = MySQLDataSourceUtil.getDataSource();
        JDBCDataModel dataModel=new MySQLJDBCDataModel(dataSource,"recommend","uid","iid","value", "time");
        return this.absRecommendByUserWithUncenteredCosine(dataModel,userId,size);
    }
}
