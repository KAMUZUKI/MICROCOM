package com.mu.service.impl;

import com.mu.entity.UserArticleOperation;
import com.mu.utils.MySQLDataSourceUtil;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import lombok.extern.slf4j.Slf4j;
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
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.ItemSimilarity;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author MUZUKI
 * @Classname RecommendServiceImpl
 * @Description TODO
 * @Date 2023/5/22 11:15
 */

@Slf4j
public class RecommendServiceImpl {
    /**
     * 基于用户的协同过滤算法
     * @param userId
     * @param size
     * @return
     * @throws Exception
     */
    public List recommendByUser (Long userId,Integer size) throws Exception{
        long t1=System.currentTimeMillis();
        MysqlDataSource dataSource = MySQLDataSourceUtil.getDataSource();
        DataModel model =new MySQLJDBCDataModel(dataSource,"recommend","uid","iid","val", null);
        UserSimilarity similarity=new PearsonCorrelationSimilarity(model);

        log.info("===============使用基于用户的协同过滤算法===============");
        double d1 = similarity.userSimilarity(userId, 2);
        double d2 = similarity.userSimilarity(userId, 3);
        log.info("用户1和用户2的相似度: " + d1);
        log.info("用户1和用户3的相似度: " + d2);
        UserNeighborhood neighborhood=new NearestNUserNeighborhood(size,similarity,model);
        long[] userIds = neighborhood.getUserNeighborhood(userId);
        log.info("当前用户邻居userIDs: " + Arrays.toString(userIds));
        Recommender recommender=new CachingRecommender(new GenericUserBasedRecommender(model,neighborhood,similarity));
        // the Recommender.recommend() method's arguments: first one is the user id;
        //     the second one is the number recommended
        //推荐
        List<RecommendedItem> recommendations=recommender.recommend(1, 2);
        log.info("done and time spend:"+(System.currentTimeMillis()-t1));

        return recommendations;
    }

    /**
     * 基于物品的协同过滤算法
     * @param userId
     * @param size
     * @return
     * @throws Exception
     */
    public List recommendByItem (Long userId,Integer size) throws Exception{
        MysqlDataSource dataSource = MySQLDataSourceUtil.getDataSource();

        DataModel model =new MySQLJDBCDataModel(dataSource,"recommend","uid","iid","val", null);

        ItemSimilarity itemSimilarity=new PearsonCorrelationSimilarity(model);
        GenericItemBasedRecommender itemRecommender = new GenericItemBasedRecommender(model, itemSimilarity);
        List<RecommendedItem> recommendedItems = itemRecommender.recommendedBecause(userId, 2, size);

        log.info("===============使用基于物品的协同过滤算法===============");
        log.info("根据用户2当前浏览的商品2，推荐2个相似的商品");
        return recommendedItems;
    }

//    public List<Long> recommend( Integer userId) throws TasteException {
//        List<UserArticleOperation> userList = userArticleOperationMapper.getAllUserPreference();
//        //创建数据模型
//        DataModel dataModel = this.createDataModel(userList);
//        //获取用户相似程度
//        UserSimilarity similarity = new UncenteredCosineSimilarity(dataModel);
//        //获取用户邻居
//        UserNeighborhood userNeighborhood = new NearestNUserNeighborhood(2, similarity, dataModel);
//        //构建推荐器
//        Recommender recommender = new GenericUserBasedRecommender(dataModel, userNeighborhood, similarity);
//        //推荐2个
//        List<RecommendedItem> recommendedItems = recommender.recommend(userId, 5);
//        List<Long> itemIds = recommendedItems.stream().map(RecommendedItem::getItemID).collect(Collectors.toList());
//        return itemIds;
//    }
//    private DataModel createDataModel(List<UserArticleOperation> userArticleOperations) {
//        FastByIDMap<PreferenceArray> fastByIdMap = new FastByIDMap<>();
//        Map<Integer, List<UserArticleOperation>> map = userArticleOperations.stream().collect(Collectors.groupingBy(UserArticleOperation::getUserId));
//        Collection<List<UserArticleOperation>> list = map.values();
//        for(List<UserArticleOperation> userPreferences : list){
//            GenericPreference[] array = new GenericPreference[userPreferences.size()];
//            for(int i = 0; i < userPreferences.size(); i++){
//                UserArticleOperation userPreference = userPreferences.get(i);
//                GenericPreference item = new GenericPreference(userPreference.getUserId(), userPreference.getArticleId(), userPreference.getValue());
//                array[i] = item;
//            }
//            fastByIdMap.put(array[0].getUserID(), new GenericUserPreferenceArray(Arrays.asList(array)));
//        }
//        return new GenericDataModel(fastByIdMap);
//    }


}
