package com.mu;

import cn.hutool.core.lang.Assert;
import com.mu.entity.UserAction;
import com.mu.mapper.UserPreferenceMapper;
import com.mu.service.impl.RecommendServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.common.FastByIDMap;
import org.apache.mahout.cf.taste.impl.model.GenericDataModel;
import org.apache.mahout.cf.taste.impl.model.GenericPreference;
import org.apache.mahout.cf.taste.impl.model.GenericUserPreferenceArray;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.CachingRecommender;
import org.apache.mahout.cf.taste.impl.recommender.GenericItemBasedRecommender;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.model.PreferenceArray;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.ItemSimilarity;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author MUZUKI
 * @Classname MahoutRecommendationTest
 * @Description MySQL数据源测试
 * @Date 2023/5/21 23:17
 */

@Slf4j
@SpringBootTest
public class MahoutRecommendationTest {

    @Autowired(required = false)
    private UserPreferenceMapper userArticleMapper;

    @Test
    void fileTest() throws Exception {
        File file = new File("F:\\Dataset\\RecommendAlgorithmData\\test.dat");
        // 实例化DataModel并将数据传入其内
        DataModel dataModel = new FileDataModel(file);
        // 计算相似度
        ItemSimilarity itemSimilarity = new PearsonCorrelationSimilarity(dataModel);
        // 构建推荐器，使用基于物品的协同过滤推荐
        GenericItemBasedRecommender recommender = new GenericItemBasedRecommender(dataModel, itemSimilarity);
        List<RecommendedItem> recommendedItemList = recommender.recommendedBecause(2, 2, 2);
        // 计算用户2当前浏览的商品2，推荐2个相似的商品
        System.out.println("使用基于物品的协同过滤算法");
        System.out.println("根据用户2当前浏览的商品2，推荐2个相似的商品");
        for (RecommendedItem recommendedItem : recommendedItemList) {
            System.out.println(recommendedItem);
        }
        long start = System.currentTimeMillis();
        recommendedItemList = recommender.recommendedBecause(5, 1, 3);
        System.out.println("使用基于物品的协同过滤算法");
        System.out.println("根据用户5当前浏览的商品1，推荐3个相似的商品");
        for (RecommendedItem recommendedItem : recommendedItemList) {
            System.out.println(recommendedItem);
        }
        System.out.println(System.currentTimeMillis() - start);
    }

    /**
     * 基于用户的协同过滤算法
     * @throws Exception
     */
    @Test
    public void mysqlRecommendByUserTest() throws Exception {
        RecommendServiceImpl recommendService = new RecommendServiceImpl();
        List list = recommendService.recommendByUser(1L, 1);
        Assert.isNull(list);
        if (list==null){
            return;
        }
        list.forEach(System.out::println);
    }

    @Test
    public void mysqlRecommendByItemTest() throws Exception {
        RecommendServiceImpl recommendService = new RecommendServiceImpl();
        List list = recommendService.recommendByItem(1L, 1L,1);
        Assert.isNull(list);
        if (list==null){
            return;
        }
        list.forEach(System.out::println);
    }

    /**
     * 基于用户的协同过滤算法,PearsonCorrelationSimilarity
     * @throws Exception
     */
    @Test
    public void mysqlRecommendByUserTest2() throws Exception {
        Long userId = 1L;
        int size = 3;
        long t1=System.currentTimeMillis();
        List<UserAction> userList = userArticleMapper.getAllUserPreference();
        //创建数据模型
        DataModel model = this.createDataModel(userList);
        //计算相似度,基于用户评价的数值的相似度
        UserSimilarity similarity=new PearsonCorrelationSimilarity(model);
        double d1 = similarity.userSimilarity(userId, 2);
        double d2 = similarity.userSimilarity(userId, 3);
        log.info("用户1和用户2的相似度: " + d1);
        log.info("用户1和用户3的相似度: " + d2);
        //计算用户邻居
        UserNeighborhood neighborhood=new NearestNUserNeighborhood(size,similarity,model);
        long[] userIds = neighborhood.getUserNeighborhood(userId);
        log.info("当前用户邻居userIDs: " + Arrays.toString(userIds));
        log.info("===============使用基于用户的协同过滤算法===============");
        Recommender recommender=new CachingRecommender(new GenericUserBasedRecommender(model,neighborhood,similarity));
        // the Recommender.recommend() method's arguments: first one is the user id;
        //     the second one is the number recommended
        //推荐
        List<RecommendedItem> recommendations=recommender.recommend(userId, size);
        recommendations.forEach(System.out::println);
        log.info("done and time spend:"+(System.currentTimeMillis()-t1));
    }

    /**
     * 基于物品的协同过滤算法
     * @throws Exception
     */
    @Test
    public void mysqlRecommendByItemTest2() throws TasteException {
        Long userId = 1L;
        Long itemId = 1L;
        int size = 3;
        List<UserAction> userList = userArticleMapper.getAllUserPreference();
        //创建数据模型
        DataModel model = this.createDataModel(userList);
        ItemSimilarity itemSimilarity=new PearsonCorrelationSimilarity(model);
        double i1 = itemSimilarity.itemSimilarity(itemId, 2);
        double i2 = itemSimilarity.itemSimilarity(itemId, 3);
        log.info("商品1和商品2的相似度: " + i1);
        log.info("商品1和商品3的相似度: " + i2);
        GenericItemBasedRecommender itemRecommender = new GenericItemBasedRecommender(model, itemSimilarity);
        List<RecommendedItem> recommendedItems = itemRecommender.recommendedBecause(userId, itemId, size);

        log.info("===============使用基于物品的协同过滤算法===============");
        log.info("根据用户" + userId + "当前浏览的商品" + itemId + " ，推荐2个相似的商品");
        recommendedItems.forEach(System.out::println);
    }

    /**
     * 创建数据模型
     * @param userArticleOperations 用户行为列表
     * @return
     */
    private DataModel createDataModel(List<UserAction> userArticleOperations) {
        FastByIDMap<PreferenceArray> fastByIdMap = new FastByIDMap<>();
        Map<Integer, List<UserAction>> map = userArticleOperations.stream().collect(Collectors.groupingBy(UserAction::getUid));
        Collection<List<UserAction>> list = map.values();
        for(List<UserAction> userPreferences : list){
            GenericPreference[] array = new GenericPreference[userPreferences.size()];
            for(int i = 0; i < userPreferences.size(); i++){
                UserAction userPreference = userPreferences.get(i);
                GenericPreference item = new GenericPreference(userPreference.getUid(), userPreference.getIid(), userPreference.getValue().floatValue());
                array[i] = item;
            }
            fastByIdMap.put(array[0].getUserID(), new GenericUserPreferenceArray(Arrays.asList(array)));
        }
        return new GenericDataModel(fastByIdMap);
    }
}