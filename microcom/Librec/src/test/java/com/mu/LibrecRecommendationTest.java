package com.mu;

import net.librec.conf.Configuration;
import net.librec.data.DataModel;
import net.librec.data.model.TextDataModel;
import net.librec.eval.RecommenderEvaluator;
import net.librec.eval.rating.MAEEvaluator;
import net.librec.filter.GenericRecommendedFilter;
import net.librec.filter.RecommendedFilter;
import net.librec.recommender.Recommender;
import net.librec.recommender.RecommenderContext;
import net.librec.recommender.cf.UserKNNRecommender;
import net.librec.similarity.PCCSimilarity;
import net.librec.similarity.RecommenderSimilarity;

import java.util.List;

/**
 * @author MUZUKI
 * @Classname com.mu.LibrecRecommendationTest
 * @Description TODO
 * @Date 2023/5/22 9:46
 */

public class LibrecRecommendationTest {     // recommender configuration
    // recommender configuration
//    Configuration conf = new Configuration();
//    Resource resource = new Resource("rec/cf/userknn-test.properties");
//	conf.addResource(resource);
//
//    // build data model
//    DataModel dataModel = new TextDataModel(conf);
//	dataModel.buildDataModel();
//
//    // set recommendation context
//    RecommenderContext context = new RecommenderContext(conf, dataModel);
//    RecommenderSimilarity similarity = new PCCSimilarity();
//	similarity.buildSimilarityMatrix(dataModel, true);
//	context.setSimilarity(similarity);
//
//    // training
//    Recommender recommender = new UserKNNRecommender();
//	recommender.recommend(context);
//
//    // evaluation
//    RecommenderEvaluator evaluator = new MAEEvaluator();
//	recommender.evaluate(evaluator);
//
//    // recommendation results
//    List recommendedItemList = recommender.getRecommendedList();
//    RecommendedFilter filter = new GenericRecommendedFilter();
//    recommendedItemList = filter.filter(recommendedItemList);
}
