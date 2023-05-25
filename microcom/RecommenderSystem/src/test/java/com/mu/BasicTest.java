package com.mu;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import lombok.extern.slf4j.Slf4j;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.eval.IRStatistics;
import org.apache.mahout.cf.taste.eval.RecommenderBuilder;
import org.apache.mahout.cf.taste.eval.RecommenderEvaluator;
import org.apache.mahout.cf.taste.eval.RecommenderIRStatsEvaluator;
import org.apache.mahout.cf.taste.impl.eval.AverageAbsoluteDifferenceRecommenderEvaluator;
import org.apache.mahout.cf.taste.impl.eval.GenericRecommenderIRStatsEvaluator;
import org.apache.mahout.cf.taste.impl.eval.RMSRecommenderEvaluator;
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
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author MUZUKI
 * @Classname BasicTest
 * @Description TODO
 * @Date 2023/5/22 14:11
 */

@Slf4j
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
        DataModel dataModel = new FileDataModel(file);
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
        DataModel dataModel = new FileDataModel(file);
        //推荐评估，使用均方根
        //RecommenderEvaluator evaluator = new RMSRecommenderEvaluator();
        //推荐评估，使用平均差值
        long start = System.currentTimeMillis();
        RecommenderEvaluator evaluator = new AverageAbsoluteDifferenceRecommenderEvaluator();
        RecommenderBuilder builder = dataModel1 -> {
            UserSimilarity similarity = new PearsonCorrelationSimilarity(dataModel1);
            UserNeighborhood neighborhood = new NearestNUserNeighborhood(2, similarity, dataModel1);
            return new GenericUserBasedRecommender(dataModel1, neighborhood, similarity);
        };
        // 用70%的数据用作训练，剩下的30%用来测试
        double score = evaluator.evaluate(builder, null, dataModel, 0.7, 1.0);
        //最后得出的评估值越小，说明推荐结果越好
        System.out.println(score);
        System.out.println("耗时: " + (System.currentTimeMillis() - start));
    }

    @Test
    public void MyIRStatistics() throws Exception{
        //准备数据 这里是电影评分数据
        File file = new File(path);
        //将数据加载到内存中，GroupLensDataModel是针对开放电影评论数据的
        DataModel dataModel = new FileDataModel(file);
        long start = System.currentTimeMillis();
        RecommenderIRStatsEvaluator statsEvaluator = new GenericRecommenderIRStatsEvaluator();
        RecommenderBuilder recommenderBuilder = model -> {
            UserSimilarity similarity = new PearsonCorrelationSimilarity(model);
            UserNeighborhood neighborhood = new NearestNUserNeighborhood(4, similarity, model);
            return new GenericUserBasedRecommender(model, neighborhood, similarity);
        };
        // 计算推荐4个结果时的查准率和召回率
        //使用评估器，并设定评估期的参数
        //4表示"precision and recall at 4"即相当于推荐top4，然后在top-4的推荐上计算准确率和召回率
        IRStatistics stats = statsEvaluator.evaluate(recommenderBuilder, null, dataModel, null, 8, GenericRecommenderIRStatsEvaluator.CHOOSE_THRESHOLD, 1.0);
        //Precision准确率
        System.out.println(stats.getPrecision());
        //Recall召回率
        System.out.println(stats.getRecall());
        System.out.println("耗时: " + (System.currentTimeMillis() - start));
    }

    @Test
    public void MaeRmse() throws Exception {
        //准备数据 这里是电影评分数据
        File file = new File(path);
        DataModel model = new FileDataModel(file);
        log.warn("=====测试计算开始=====");
        List<List<String>> rows = new ArrayList<>();
        BigDecimal i = new BigDecimal("0.4");
        BigDecimal j = new BigDecimal("0.1");
        BigDecimal one = new BigDecimal("1");
        BigDecimal pointOne = new BigDecimal("0.1");
        BigDecimal upperBound = new BigDecimal("1");
        BigDecimal lowerBound = new BigDecimal("0.5");

        while (i.compareTo(upperBound) < 0) {
            while (j.compareTo(lowerBound) < 0) {
                // 定义推荐算法
                RecommenderBuilder builder = new RecommenderBuilder() {
                    @Override
                    public Recommender buildRecommender(DataModel model) throws TasteException {
                        UserSimilarity similarity = new PearsonCorrelationSimilarity(model);
                        UserNeighborhood neighborhood = new NearestNUserNeighborhood(4, similarity, model);
                        return new GenericUserBasedRecommender(model,neighborhood,similarity);
                    }
                };

                // 定义MAE评估器
                RecommenderEvaluator maeEvaluator = new AverageAbsoluteDifferenceRecommenderEvaluator();
                double mae = maeEvaluator.evaluate(builder, null, model, i.doubleValue(), j.doubleValue());

                // 定义RMSE评估器
                RecommenderEvaluator rmseEvaluator = new RMSRecommenderEvaluator();
                double rmse = rmseEvaluator.evaluate(builder, null, model, i.doubleValue(), j.doubleValue());
                log.warn("trainingPercentage: " + i + ", evaluationPercentage: " + j);
                // 输出MAE和RMSE
                log.warn("MAE: " + mae);
                log.warn("RMSE: " + rmse);
                List<String> row = CollUtil.newArrayList(i.toString(), j.toString(), Double.toString(mae), Double.toString(rmse));
                rows.add(row);
                j = j.add(pointOne);
            }
            i = i.add(pointOne);
            j = new BigDecimal("0.1");
        }

        // 生成Excel
        ExcelWriter writer = ExcelUtil.getWriter("D:\\Gitresp\\MICROCOM\\microcom\\RecommenderSystem\\src\\main\\resources\\static\\report.xlsx");
        writer.passCurrentRow();
        writer.write(rows, true);
        writer.close();
    }

    @Test
    public void ExcelTest(){
        List<String> row1 = CollUtil.newArrayList("aa", "bb", "cc", "dd");
        List<String> row2 = CollUtil.newArrayList("aa1", "bb1", "cc1", "dd1");
        List<String> row3 = CollUtil.newArrayList("aa2", "bb2", "cc2", "dd2");
        List<String> row4 = CollUtil.newArrayList("aa3", "bb3", "cc3", "dd3");
        List<String> row5 = CollUtil.newArrayList("aa4", "bb4", "cc4", "dd4");
        List<List<String>> rows = CollUtil.newArrayList(row1, row2, row3, row4, row5);
        ExcelWriter writer = ExcelUtil.getWriter("static/report.xlsx");
        writer.passCurrentRow();
        writer.write(rows, true);
        writer.close();
    }
}
