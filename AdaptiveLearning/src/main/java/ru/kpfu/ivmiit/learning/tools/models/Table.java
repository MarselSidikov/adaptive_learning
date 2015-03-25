package ru.kpfu.ivmiit.learning.tools.models;


import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.mllib.recommendation.ALS;
import org.apache.spark.mllib.recommendation.MatrixFactorizationModel;
import org.apache.spark.mllib.recommendation.Rating;
import org.apache.spark.storage.StorageLevel;
import ru.kpfu.ivmiit.learning.tools.SCSingletone;
import org.apache.spark.api.java.function.Function;

import java.util.LinkedList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Зульфат on 24.03.2015.
 */
public class Table {
    private JavaRDD<Rating> tableAbsTestReslt;
    private JavaRDD<Rating> tableRelTestResult;
    private MatrixFactorizationModel modelAbsTestResult;
    private MatrixFactorizationModel modelRelTestResult;
    private int rankForAbsTestResult;
    private int numIterationsForAbsTestResult;
    private double alphaForAbsTestResult;
    private int rankForRelTestResult;
    private int numIterationsForRelTestResult;
    private double alphaForRelTestResult;
    private JavaSparkContext javaSparkContext;
    public Table(String path, int rankForAbsTestResult, int numIterationsForAbsTestResult, int rankForRelTestResult, int numIterationsForRelTestResult, double alphaForAbsTestResult, double alphaForRelTestResult) {
        this.rankForAbsTestResult = rankForAbsTestResult;
        this.rankForRelTestResult = rankForRelTestResult;
        this.numIterationsForAbsTestResult = numIterationsForAbsTestResult;
        this.numIterationsForRelTestResult = numIterationsForRelTestResult;
        this.alphaForAbsTestResult = alphaForAbsTestResult;
        this.alphaForRelTestResult = alphaForAbsTestResult;
        javaSparkContext = SCSingletone.getInstance().getSparkContext();
        JavaRDD<String> strData = SCSingletone.getInstance().getSparkContext().textFile(path);
        tableAbsTestReslt = strData.map(new Function<String, Rating>() {
            public Rating call(String s) {
                String[] sarr = s.split(";");
                return new Rating(Integer.parseInt(sarr[0]), Integer.parseInt(sarr[1]), Double.parseDouble(sarr[2]));
            }
        });
        tableRelTestResult = strData.map(new Function<String, Rating>() {
            public Rating call(String s) {
                String[] sarr = s.split(";");
                return new Rating(Integer.parseInt(sarr[0]), Integer.parseInt(sarr[1]), Double.parseDouble(sarr[3]));
            }
        });
        modelAbsTestResult = ALS.train(JavaRDD.toRDD(tableAbsTestReslt), rankForAbsTestResult, numIterationsForAbsTestResult, alphaForAbsTestResult);
        modelRelTestResult = ALS.train(JavaRDD.toRDD(tableRelTestResult), rankForRelTestResult, numIterationsForRelTestResult, alphaForRelTestResult);
    }

    public Table(JavaRDD<Rating> tableAbsTestResult, JavaRDD<Rating> tableRelTestResult, int rankForAbsTestResult, int numIterationsForAbsTestResult, int rankForRelTestResult, int numIterationsForRelTestResult, double alphaForAbsTestResult, double alphaForRelTestResult) {
        this.rankForAbsTestResult = rankForAbsTestResult;
        this.rankForRelTestResult = rankForRelTestResult;
        this.numIterationsForAbsTestResult = numIterationsForAbsTestResult;
        this.numIterationsForRelTestResult = numIterationsForRelTestResult;
        this.alphaForAbsTestResult = alphaForAbsTestResult;
        this.alphaForRelTestResult = alphaForAbsTestResult;
        this.tableAbsTestReslt = tableAbsTestResult;
        this.tableRelTestResult = tableRelTestResult;
        modelAbsTestResult = ALS.train(JavaRDD.toRDD(tableAbsTestReslt), rankForAbsTestResult, numIterationsForAbsTestResult, alphaForAbsTestResult);
        modelRelTestResult = ALS.train(JavaRDD.toRDD(tableRelTestResult), rankForRelTestResult, numIterationsForRelTestResult, alphaForRelTestResult);

    }

    private void recalc() {
        modelAbsTestResult = ALS.train(JavaRDD.toRDD(tableAbsTestReslt), rankForAbsTestResult, numIterationsForAbsTestResult, alphaForAbsTestResult);
        modelRelTestResult = ALS.train(JavaRDD.toRDD(tableRelTestResult), rankForRelTestResult, numIterationsForRelTestResult, alphaForRelTestResult);

    }

    private void AddResult(Result result, int user, int lesson) {
        boolean isCorrect;
        double complexity;
        double totalComplexity = 0;
        double correctComplexity = 0;
        Map<Integer, List<String>> res = result.getResults();
        for (Map.Entry<Integer, List<String>> questionID : res.entrySet()) {
            isCorrect = Boolean.parseBoolean(questionID.getValue().get(0));
            complexity = Double.parseDouble(questionID.getValue().get(1));
            totalComplexity += complexity;
            if (isCorrect)
                correctComplexity += complexity;
        }
        List<Rating> ratAbs = new LinkedList<Rating>();
        ratAbs.add(new Rating(user, lesson, correctComplexity));
        List<Rating> ratRel = new LinkedList<Rating>();
        ratRel.add(new Rating(user, lesson, correctComplexity / totalComplexity));
        JavaRDD<Rating> ratAbsRDD = javaSparkContext.parallelize(ratAbs);
        JavaRDD<Rating>  ratRelRDD = javaSparkContext.parallelize(ratRel);
        tableAbsTestReslt = tableRelTestResult.union(ratAbsRDD);
        tableRelTestResult = tableRelTestResult.union(ratRelRDD);
        tableRelTestResult.persist(StorageLevel.MEMORY_AND_DISK_SER());
        tableAbsTestReslt.persist(StorageLevel.MEMORY_AND_DISK_SER());
        recalc();
    }


    public void getResultForUser(int user) {
        JavaRDD<Rating> userAbsRes = tableAbsTestReslt.filter(new Function<Rating, Boolean>() {
            public Boolean call(Rating rating) throws Exception {
                return rating.user()==user;
            }
        });
        JavaRDD<Rating> userRelRes = tableRelTestResult.filter(new Function<Rating, Boolean>() {
            public Boolean call(Rating rating) throws Exception {
                return rating.user()==user;
            }
        });
        List<Rating> userAbsResults = userAbsRes.collect();
        List<Rating> userRelResults = userRelRes.collect();

    }
}
