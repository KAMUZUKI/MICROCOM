package com.mu;

import com.mu.service.impl.RecommendServiceImpl;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.recommender.GenericItemBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.similarity.ItemSimilarity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @author MUZUKI
 * @Classname Test
 * @Description TODO
 * @Date 2023/5/21 23:17
 */

public class MahoutRecommendationTest {
    @Test
    void fileTest() {
        File file = new File("F:\\Dataset\\RecommendAlgorithmData\\test.dat");
        // 实例化DataModel并将数据传入其内
        DataModel dataModel = null;
        try {
            dataModel = new FileDataModel(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 计算相似度
        ItemSimilarity itemSimilarity = null;
        try {
            itemSimilarity = new PearsonCorrelationSimilarity(dataModel);
        } catch (TasteException e) {
            e.printStackTrace();
        }

        // 构建推荐器，使用基于物品的协同过滤推荐
        GenericItemBasedRecommender recommender = new GenericItemBasedRecommender(dataModel, itemSimilarity);

        List<RecommendedItem> recommendedItemList = null;
        try {
            // 计算用户2当前浏览的商品2，推荐2个相似的商品
            recommendedItemList = recommender.recommendedBecause(2, 2, 2);
        } catch (TasteException e) {
            e.printStackTrace();
        }

        System.out.println("使用基于物品的协同过滤算法");
        System.out.println("根据用户2当前浏览的商品2，推荐2个相似的商品");
        for (RecommendedItem recommendedItem : recommendedItemList) {
            System.out.println(recommendedItem);
        }
        long start = System.currentTimeMillis();
        try {
            recommendedItemList = recommender.recommendedBecause(5, 1, 3);
        } catch (TasteException e) {
            e.printStackTrace();
        }

        System.out.println("使用基于物品的协同过滤算法");
        System.out.println("根据用户5当前浏览的商品1，推荐3个相似的商品");
        for (RecommendedItem recommendedItem : recommendedItemList) {
            System.out.println(recommendedItem);
        }
        System.out.println(System.currentTimeMillis() - start);
    }

    @Test
    public void mysqlRecommendByUserTest() throws Exception {
        RecommendServiceImpl recommendService = new RecommendServiceImpl();
        List list = recommendService.recommendByUser(1L, 3);
        list.forEach(System.out::println);
    }

    @Test
    public void mysqlRecommendByItemTest() throws Exception {
        RecommendServiceImpl recommendService = new RecommendServiceImpl();
        List list = recommendService.recommendByUser(1L, 3);
        list.forEach(System.out::println);
    }
}