package com.mu.service.impl;

import cn.hutool.core.collection.CollUtil;
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
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author MUZUKI
 * @Classname EvaluationServiceImpl
 * @Description TODO
 * @Date 2023/5/25 20:46
 */

@Slf4j
@Service
public class EvaluationServiceImpl {
    /**
     * 评估推荐模型
     * @throws TasteException
     */
    public void Evaluator(DataModel model) throws TasteException {
        //推荐评估，使用平均差值
        long start = System.currentTimeMillis();
        RecommenderEvaluator evaluator = new AverageAbsoluteDifferenceRecommenderEvaluator();
        RecommenderBuilder builder = dataModel1 -> {
            UserSimilarity similarity = new PearsonCorrelationSimilarity(dataModel1);
            UserNeighborhood neighborhood = new NearestNUserNeighborhood(2, similarity, dataModel1);
            return new GenericUserBasedRecommender(dataModel1, neighborhood, similarity);
        };
        // 用70%的数据用作训练，剩下的30%用来测试
        double score = evaluator.evaluate(builder, null, model, 0.7, 1.0);
        //最后得出的评估值越小，说明推荐结果越好
        log.info(String.valueOf(score));
        log.info("耗时: " + (System.currentTimeMillis() - start));
    }

    /**
     * 评估推荐模型,Precision准确率,Recall召回率
     * @param model
     * @throws TasteException
     */
    public void IRStatistics(DataModel model) throws TasteException {
        long start = System.currentTimeMillis();
        RecommenderIRStatsEvaluator statsEvaluator = new GenericRecommenderIRStatsEvaluator();
        RecommenderBuilder recommenderBuilder = m -> {
            UserSimilarity similarity = new PearsonCorrelationSimilarity(m);
            UserNeighborhood neighborhood = new NearestNUserNeighborhood(4, similarity, m);
            return new GenericUserBasedRecommender(m, neighborhood, similarity);
        };
        // 计算推荐4个结果时的查准率和召回率
        //使用评估器，并设定评估期的参数
        //4表示"precision and recall at 4"即相当于推荐top4，然后在top-4的推荐上计算准确率和召回率
        IRStatistics stats = statsEvaluator.evaluate(recommenderBuilder, null, model, null, 8, GenericRecommenderIRStatsEvaluator.CHOOSE_THRESHOLD, 1.0);
        //Precision准确率
        System.out.println(stats.getPrecision());
        //Recall召回率
        System.out.println(stats.getRecall());
        System.out.println("耗时: " + (System.currentTimeMillis() - start));
    }

    /**
     * 评估推荐模型,MAE均方根误差,RMSE均方根误差
     * @param model
     * @throws TasteException
     */
    public void MaeRmse(DataModel model) throws TasteException {
        log.warn("=====测试计算开始=====");
        List<List<String>> rows = new ArrayList<>();
        BigDecimal i = new BigDecimal("0.4");
        BigDecimal j = new BigDecimal("0.1");
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
        ExcelWriter writer = ExcelUtil.getWriter("D:\\Gitresp\\MICROCOM\\microcom\\RecommenderSystem\\src\\main\\resources\\static" + new Date() + "report.xlsx");
        writer.passCurrentRow();
        writer.write(rows, true);
        writer.close();
    }
}
