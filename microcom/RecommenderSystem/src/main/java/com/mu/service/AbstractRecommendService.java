package com.mu.service;

import com.mu.entity.UserAction;
import com.mu.exception.RecommendException;
import com.mu.extra.PreferenceWithTimestamp;
import com.mu.extra.TimeDecayRescorer;
import com.mu.mapper.VlogMapper;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.common.FastByIDMap;
import org.apache.mahout.cf.taste.impl.model.GenericDataModel;
import org.apache.mahout.cf.taste.impl.model.GenericPreference;
import org.apache.mahout.cf.taste.impl.model.GenericUserPreferenceArray;
import org.apache.mahout.cf.taste.impl.model.jdbc.MySQLJDBCDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.CachingRecommender;
import org.apache.mahout.cf.taste.impl.recommender.GenericItemBasedRecommender;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.UncenteredCosineSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.model.PreferenceArray;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.IDRescorer;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.ItemSimilarity;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * @author MUZUKI
 * @Classname AbstractRecommendServiceImpl
 * @Description 推荐服务抽象类，提供一些公共方法
 * @Date 2023/5/25 15:59
 */

public abstract class AbstractRecommendService {
    /**
     * 基于用户的协同过滤算法
     *
     * @param userId 用户ID
     * @param size   推荐数量
     * @return 推荐列表
     */
    protected List<Long> absRecommendByUser(DataModel model, Long userId, Integer size) {
        List<RecommendedItem> recommendedItems;
        try {
            UserSimilarity similarity = new PearsonCorrelationSimilarity(model);
            //明确与给定用户最相似的一组用户
            UserNeighborhood neighborhood = new NearestNUserNeighborhood(3, similarity, model);
            Recommender recommender = new CachingRecommender(new GenericUserBasedRecommender(model, neighborhood, similarity));
            recommendedItems = recommender.recommend(userId, size);
        }catch (TasteException e){
            throw new RecommendException("recommendByUser:推荐失败" + e);
        }
        return recommendedItems.stream().map(RecommendedItem::getItemID).collect(Collectors.toList());
    }

    /**
     * 基于用户的协同过滤算法,根据时间衰减
     * @param model 数据模型
     * @param userId 用户ID
     * @param itemId 商品ID
     * @param size 推荐数量
     * @return 推荐列表
     */
    protected List<Long> absRecommendByItemWithTimeDecay(VlogMapper vlogMapper,MySQLJDBCDataModel model, Long userId, Long itemId, Integer size){
        List<RecommendedItem> recommendedItems;
        try {
            // 创建相似度计算器
            PearsonCorrelationSimilarity similarity = new PearsonCorrelationSimilarity(model);
            // 创建推荐器
            GenericItemBasedRecommender recommender = new GenericItemBasedRecommender(model, similarity);
            // 创建IDRescorer
            IDRescorer rescorer = new TimeDecayRescorer(vlogMapper);
            // 为用户推荐物品
            recommendedItems = recommender.recommend(userId, size, rescorer);
        }catch (TasteException e){
            throw new RecommendException("recommendByUser:推荐失败" + e);
        }
        return recommendedItems.stream().map(RecommendedItem::getItemID).collect(Collectors.toList());
    }

    /**
     * 基于物品的协同过滤算法
     *
     * @param userId 用户ID
     * @param itemId 商品ID
     * @param size   推荐数量
     * @return 推荐列表
     */
    protected List<Long> absRecommendByItem(DataModel model, Long userId, Long itemId, Integer size) {
        List<RecommendedItem> recommendedItems;
        //获取用户相似程度
        try {
            ItemSimilarity itemSimilarity = new PearsonCorrelationSimilarity(model);
            //获取用户邻居
            GenericItemBasedRecommender itemRecommender = new GenericItemBasedRecommender(model, itemSimilarity);
            recommendedItems = itemRecommender.recommendedBecause(userId, itemId, size);
        }catch (TasteException e){
            throw new RecommendException("recommendByItem:推荐失败" + e);
        }
        return recommendedItems.stream().map(RecommendedItem::getItemID).collect(Collectors.toList());
    }

    /**
     * 基于物品的协同过滤算法,批量推荐
     *
     * @param userId 用户ID
     * @param itemIdList 商品ID集合
     * @param size   推荐数量
     * @return 推荐集合
     */
    protected List<Long> absBatchRecommendByItem(DataModel model, Long userId, List<Long> itemIdList, Integer size){
        Map<Long,Float> itemHashMap = new ConcurrentHashMap<>();
        //获取用户相似程度
        try{
            ItemSimilarity itemSimilarity = new PearsonCorrelationSimilarity(model);
            //获取用户邻居
            GenericItemBasedRecommender itemRecommender = new GenericItemBasedRecommender(model, itemSimilarity);
            itemIdList.forEach(
                    itemId -> {
                        try {
                            List<RecommendedItem> recommendedItems = itemRecommender.recommendedBecause(userId, itemId, size);
                            recommendedItems.forEach(
                                    item -> itemHashMap.put(item.getItemID(), item.getValue())
                            );
                        } catch (TasteException e) {
                            throw new RecommendException("batchRecommendByItem:批量推荐失败" + e);
                        }
                    }
            );
        } catch (TasteException e) {
            throw new RecommendException("batchRecommendByItem:批量推荐失败" + e);
        }
        return itemHashMap.entrySet().stream()
                .sorted(Map.Entry.<Long, Float>comparingByValue().reversed())
                .limit(size)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    /**
     * 基于用户的协同过滤算法,基于余弦相似度
     *
     * @param userId 用户ID
     * @return 推荐列表
     */
    protected List<Long> absRecommendByUserWithUncenteredCosine(DataModel model, Long userId, Integer size) {
        List<RecommendedItem> recommendedItems;
        try {
            //获取用户相似程度
            UserSimilarity similarity = new UncenteredCosineSimilarity(model);
            //获取用户邻居
            UserNeighborhood userNeighborhood = new NearestNUserNeighborhood(3, similarity, model);
            //构建推荐器
            Recommender recommender = new GenericUserBasedRecommender(model, userNeighborhood, similarity);
            //推荐个数
            recommendedItems = recommender.recommend(userId, size);
        }catch (TasteException e){
            throw new RecommendException("recommendByUserWithUncenteredCosine:推荐失败" + e);
        }
        return recommendedItems.stream().map(RecommendedItem::getItemID).collect(Collectors.toList());
    }

    /**
     * 创建数据模型
     *
     * @param userArticleOperations 用户行为列表
     * @return 数据模型
     */
    protected DataModel createDataModel(List<UserAction> userArticleOperations) {
        FastByIDMap<PreferenceArray> fastByIdMap = new FastByIDMap<>();
        Map<Integer, List<UserAction>> map = userArticleOperations.stream().collect(Collectors.groupingBy(UserAction::getUid));
        Collection<List<UserAction>> list = map.values();
        for (List<UserAction> userPreferences : list) {
            PreferenceWithTimestamp[] array = new PreferenceWithTimestamp[userPreferences.size()];
            for (int i = 0; i < userPreferences.size(); i++) {
                UserAction userPreference = userPreferences.get(i);
                PreferenceWithTimestamp item = new PreferenceWithTimestamp(userPreference.getUid(), userPreference.getIid(), userPreference.getValue().floatValue(),userPreference.getTime());
                array[i] = item;
            }
            fastByIdMap.put(array[0].getUserID(), new GenericUserPreferenceArray(Arrays.asList(array)));
        }
        return new GenericDataModel(fastByIdMap);
    }
}
