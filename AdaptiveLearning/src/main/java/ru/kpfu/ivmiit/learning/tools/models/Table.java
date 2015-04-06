package ru.kpfu.ivmiit.learning.tools.models;


import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.mllib.recommendation.ALS;
import org.apache.spark.mllib.recommendation.MatrixFactorizationModel;
import org.apache.spark.mllib.recommendation.Rating;
import org.apache.spark.storage.StorageLevel;
import ru.kpfu.ivmiit.learning.tools.SCSingletone;
import org.apache.spark.api.java.function.Function;
import ru.kpfu.ivmiit.learning.tools.core.RatingsTableInterface;
import java.util.List;

/**
 * Created by Зульфат on 24.03.2015.
 */
public class Table implements RatingsTableInterface {
    private JavaRDD<Rating> tableAbsTestResult;
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
        tableAbsTestResult = strData.map(new Function<String, Rating>() {
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
        modelAbsTestResult = ALS.train(JavaRDD.toRDD(tableAbsTestResult), rankForAbsTestResult, numIterationsForAbsTestResult, alphaForAbsTestResult);
        modelRelTestResult = ALS.train(JavaRDD.toRDD(tableRelTestResult), rankForRelTestResult, numIterationsForRelTestResult, alphaForRelTestResult);
    }

    public Table(JavaRDD<Rating> tableAbsTestResult, JavaRDD<Rating> tableRelTestResult, int rankForAbsTestResult, int numIterationsForAbsTestResult, int rankForRelTestResult, int numIterationsForRelTestResult, double alphaForAbsTestResult, double alphaForRelTestResult) {
        this.rankForAbsTestResult = rankForAbsTestResult;
        this.rankForRelTestResult = rankForRelTestResult;
        this.numIterationsForAbsTestResult = numIterationsForAbsTestResult;
        this.numIterationsForRelTestResult = numIterationsForRelTestResult;
        this.alphaForAbsTestResult = alphaForAbsTestResult;
        this.alphaForRelTestResult = alphaForAbsTestResult;
        this.tableAbsTestResult = tableAbsTestResult;
        this.tableRelTestResult = tableRelTestResult;
        modelAbsTestResult = ALS.train(JavaRDD.toRDD(this.tableAbsTestResult), rankForAbsTestResult, numIterationsForAbsTestResult, alphaForAbsTestResult);
        modelRelTestResult = ALS.train(JavaRDD.toRDD(tableRelTestResult), rankForRelTestResult, numIterationsForRelTestResult, alphaForRelTestResult);

    }

    private void recalc() {
        modelAbsTestResult = ALS.train(JavaRDD.toRDD(tableAbsTestResult), rankForAbsTestResult, numIterationsForAbsTestResult, alphaForAbsTestResult);
        modelRelTestResult = ALS.train(JavaRDD.toRDD(tableRelTestResult), rankForRelTestResult, numIterationsForRelTestResult, alphaForRelTestResult);

    }

    public void AddResult(Result result) {
        double correctComplexity = result.getAbsComplexity();
        double relComplexity = result.getRelComplexity();
        //making update on tableAbsResult
        tableAbsTestResult = tableAbsTestResult.map(new Function<Rating, Rating>() {
            @Override
            public Rating call(Rating r) throws Exception {
                if (r.product() == result.getLessonId() && r.user() == result.getStudentId())
                    return new Rating(result.getStudentId(), result.getLessonId(), correctComplexity);
                else
                    return r;
            }
        });

        //making update on tableRelResult
        tableRelTestResult = tableRelTestResult.map(new Function< Rating, Rating> () {
            @Override
            public Rating call(Rating r) throws Exception {
                if (r.product()==result.getLessonId() && r.user()==result.getStudentId())
                    return new Rating(result.getStudentId(),result.getLessonId(),relComplexity);
                else
                    return r;
            }
        });
        tableAbsTestResult.cache();
        tableRelTestResult.cache();
        recalc();
    }


    public List<Rating> getABSRatingForSudent(int studentID) {
        JavaRDD<Rating> userAbsRes = tableAbsTestResult.filter(new Function<Rating, Boolean>() {
            public Boolean call(Rating rating) throws Exception {
                return rating.user() == studentID;
            }
        });
        return userAbsRes.collect();
    }
    public List<Rating> getRelRatingForStudent(int studentID) {
        JavaRDD<Rating> studentRelResults = tableRelTestResult.filter(new Function<Rating, Boolean>() {
            public Boolean call(Rating rating) throws Exception {
                return rating.user()==studentID;
            }
        });
        return studentRelResults.collect();
    }
}
