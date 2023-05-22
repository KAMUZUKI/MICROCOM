package com.mu;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.eval.IRStatistics;
import org.apache.mahout.cf.taste.eval.RecommenderBuilder;
import org.apache.mahout.cf.taste.eval.RecommenderEvaluator;
import org.apache.mahout.cf.taste.eval.RecommenderIRStatsEvaluator;
import org.apache.mahout.cf.taste.impl.eval.AverageAbsoluteDifferenceRecommenderEvaluator;
import org.apache.mahout.cf.taste.impl.eval.GenericRecommenderIRStatsEvaluator;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericItemBasedRecommender;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.ItemSimilarity;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;
import org.apache.mahout.cf.taste.similarity.precompute.example.GroupLensDataModel;
import org.junit.Test;

import java.io.File;
import java.util.List;

/**
 * @author MUZUKI
 * @Classname BasicTest
 * @Description TODO
 * @Date 2023/5/22 14:11
 */

public class BasicTest {

    private String path = "F:\\Dataset\\small\\ratings.dat";
    /**
     * 基于用户的推荐
     * @throws Exception
     */
    @Test
    public void BaseUserRecommender() throws Exception {
        //准备数据 这里是电影评分数据
        File file = new File(path);
        //将数据加载到内存中，GroupLensDataModel是针对开放电影评论数据的
//        DataModel dataModel = new GroupLensDataModel(file);
        DataModel dataModel = new FileDataModel(file);
//        FileDataModel dataModel = new FileDataModel(new File(path),false,0, ",");
        //计算相似度，相似度算法有很多种，欧几里得、皮尔逊等等。
        UserSimilarity similarity = new PearsonCorrelationSimilarity(dataModel);
        //计算最近邻域，邻居有两种算法，基于固定数量的邻居和基于相似度的邻居，这里使用基于固定数量的邻居
        UserNeighborhood userNeighborhood = new NearestNUserNeighborhood(100, similarity, dataModel);
        //构建推荐器，协同过滤推荐有两种，分别是基于用户的和基于物品的，这里使用基于用户的协同过滤推荐
        Recommender recommender = new GenericUserBasedRecommender(dataModel, userNeighborhood, similarity);
        //给用户ID等于5的用户推荐10部电影
        List<RecommendedItem> recommendedItemList = recommender.recommend(5, 10);
        //打印推荐的结果
        System.out.println("使用基于用户的协同过滤算法");
        System.out.println("为用户5推荐10个商品");
        for (RecommendedItem recommendedItem : recommendedItemList) {
            System.out.println(recommendedItem);
        }
    }

    /**
     * 基于物品的推荐
     * @throws Exception
     */
    @Test
    public void BaseItemRecommender() throws Exception{
        //准备数据 这里是电影评分数据
        File file = new File(path);
        //将数据加载到内存中，GroupLensDataModel是针对开放电影评论数据的
        DataModel dataModel = new GroupLensDataModel(file);
        //计算相似度，相似度算法有很多种，欧几里得、皮尔逊等等。
        ItemSimilarity itemSimilarity = new PearsonCorrelationSimilarity(dataModel);
        //构建推荐器，协同过滤推荐有两种，分别是基于用户的和基于物品的，这里使用基于物品的协同过滤推荐
        GenericItemBasedRecommender recommender = new GenericItemBasedRecommender(dataModel, itemSimilarity);
        //给用户ID等于5的用户推荐10个与2398相似的商品
        List<RecommendedItem> recommendedItemList = recommender.recommendedBecause(5, 2398, 10);
        //打印推荐的结果
        System.out.println("使用基于物品的协同过滤算法");
        System.out.println("根据用户5当前浏览的商品2398，推荐10个相似的商品");
        for (RecommendedItem recommendedItem : recommendedItemList) {
            System.out.println(recommendedItem);
        }
        long start = System.currentTimeMillis();
        recommendedItemList = recommender.recommendedBecause(5, 34, 10);
        //打印推荐的结果
        System.out.println("使用基于物品的协同过滤算法");
        System.out.println("根据用户5当前浏览的商品34，推荐10个相似的商品");
        for (RecommendedItem recommendedItem : recommendedItemList) {
            System.out.println(recommendedItem);
        }
        System.out.println(System.currentTimeMillis() -start);
    }

    /**
     * 评估推荐模型
     * @throws Exception
     */
    @Test
    public void MyEvaluator() throws Exception{
        //准备数据 这里是电影评分数据
        File file = new File(path);
        //将数据加载到内存中，GroupLensDataModel是针对开放电影评论数据的
        DataModel dataModel = new GroupLensDataModel(file);
        //推荐评估，使用均方根
        //RecommenderEvaluator evaluator = new RMSRecommenderEvaluator();
        //推荐评估，使用平均差值
        RecommenderEvaluator evaluator = new AverageAbsoluteDifferenceRecommenderEvaluator();
        RecommenderBuilder builder = new RecommenderBuilder() {
            public Recommender buildRecommender(DataModel dataModel) throws TasteException {
                UserSimilarity similarity = new PearsonCorrelationSimilarity(dataModel);
                UserNeighborhood neighborhood = new NearestNUserNeighborhood(2, similarity, dataModel);
                return new GenericUserBasedRecommender(dataModel, neighborhood, similarity);
            }
        };
        // 用70%的数据用作训练，剩下的30%用来测试
        double score = evaluator.evaluate(builder, null, dataModel, 0.7, 1.0);
        //最后得出的评估值越小，说明推荐结果越好
        System.out.println(score);
    }

    @Test
    public void MyIRStatistics() throws Exception{
        //准备数据 这里是电影评分数据
        File file = new File(path);
        //将数据加载到内存中，GroupLensDataModel是针对开放电影评论数据的
        DataModel dataModel = new GroupLensDataModel(file);
        RecommenderIRStatsEvaluator statsEvaluator = new GenericRecommenderIRStatsEvaluator();
        RecommenderBuilder recommenderBuilder = new RecommenderBuilder() {
            public Recommender buildRecommender(DataModel model) throws TasteException {
                UserSimilarity similarity = new PearsonCorrelationSimilarity(model);
                UserNeighborhood neighborhood = new NearestNUserNeighborhood(4, similarity, model);
                return new GenericUserBasedRecommender(model, neighborhood, similarity);
            }
        };
        // 计算推荐4个结果时的查准率和召回率
        //使用评估器，并设定评估期的参数
        //4表示"precision and recall at 4"即相当于推荐top4，然后在top-4的推荐上计算准确率和召回率
        IRStatistics stats = statsEvaluator.evaluate(recommenderBuilder, null, dataModel, null, 4, GenericRecommenderIRStatsEvaluator.CHOOSE_THRESHOLD, 1.0);
        System.out.println(stats.getPrecision());
        System.out.println(stats.getRecall());
    }
}
