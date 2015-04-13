package ru.kpfu.ivmiit.learning.tools.models;


import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.mllib.recommendation.ALS;
import org.apache.spark.mllib.recommendation.MatrixFactorizationModel;
import org.apache.spark.mllib.recommendation.Rating;
import ru.kpfu.ivmiit.learning.tools.SCSingletone;
import org.apache.spark.api.java.function.Function;
import ru.kpfu.ivmiit.learning.tools.core.RatingsTableInterface;
import scala.Tuple2;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Зульфат on 24.03.2015.
 */
public class Table implements RatingsTableInterface {
    private static Table instance;
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
    JavaPairRDD<Tuple2<Integer, Integer>, Double> absPredictions;
    JavaPairRDD<Tuple2<Integer, Integer>, Double> relPredictions;
    public static synchronized Table getInstance () {
        if (instance==null) {
            instance = new Table();
        }
        return instance;
    }
    private Table () {

    }
    private Table(String path, int rankForAbsTestResult, int numIterationsForAbsTestResult, int rankForRelTestResult, int numIterationsForRelTestResult, double alphaForAbsTestResult, double alphaForRelTestResult) {
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
        recalc();
    }

    private Table(JavaRDD<Rating> tableAbsTestResult, JavaRDD<Rating> tableRelTestResult, int rankForAbsTestResult, int numIterationsForAbsTestResult, int rankForRelTestResult, int numIterationsForRelTestResult, double alphaForAbsTestResult, double alphaForRelTestResult) {
        this.rankForAbsTestResult = rankForAbsTestResult;
        this.rankForRelTestResult = rankForRelTestResult;
        this.numIterationsForAbsTestResult = numIterationsForAbsTestResult;
        this.numIterationsForRelTestResult = numIterationsForRelTestResult;
        this.alphaForAbsTestResult = alphaForAbsTestResult;
        this.alphaForRelTestResult = alphaForAbsTestResult;
        this.tableAbsTestResult = tableAbsTestResult;
        this.tableRelTestResult = tableRelTestResult;
        recalc();
    }

    private void recalc() {
        modelAbsTestResult = ALS.train(JavaRDD.toRDD(tableAbsTestResult), rankForAbsTestResult, numIterationsForAbsTestResult, alphaForAbsTestResult);
        modelRelTestResult = ALS.train(JavaRDD.toRDD(tableRelTestResult), rankForRelTestResult, numIterationsForRelTestResult, alphaForRelTestResult);
        JavaRDD<Tuple2<Object, Object>> userProducts = tableAbsTestResult.map(
                new Function<Rating, Tuple2<Object, Object>>() {
                    public Tuple2<Object, Object> call(Rating r) {
                        return new Tuple2<Object, Object>(r.user(), r.product());
                    }
                }
        );
        absPredictions = JavaPairRDD.fromJavaRDD(
        modelAbsTestResult.predict(JavaRDD.toRDD(userProducts)).toJavaRDD().map(
                new Function<Rating, Tuple2<Tuple2<Integer, Integer>, Double>>() {
                    public Tuple2<Tuple2<Integer, Integer>, Double> call(Rating r){
                        return new Tuple2<Tuple2<Integer, Integer>, Double>(
                                new Tuple2<Integer, Integer>(r.user(), r.product()), r.rating());
                        }
                    }
        ));
        relPredictions  = JavaPairRDD.fromJavaRDD(
                modelRelTestResult.predict(JavaRDD.toRDD(userProducts)).toJavaRDD().map(
                        new Function<Rating, Tuple2<Tuple2<Integer, Integer>, Double>>() {
                            public Tuple2<Tuple2<Integer, Integer>, Double> call(Rating r){
                                return new Tuple2<Tuple2<Integer, Integer>, Double>(
                                        new Tuple2<Integer, Integer>(r.user(), r.product()), r.rating());
                            }
                        }
                ));

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
    public List<Tuple2<Tuple2<Integer,Integer>,Double>> getRelPredictedRatingsForStudent (int studentID) {
        return relPredictions.filter(new Function<Tuple2<Tuple2<Integer, Integer>, Double>, Boolean>() {
            public Boolean call(Tuple2<Tuple2<Integer, Integer>, Double> tuple2DoubleTuple2) throws Exception {
                return tuple2DoubleTuple2._1()._1()==studentID;
            }
        }).collect();
    }
    public List<Tuple2<Tuple2<Integer,Integer>,Double>> getABSPredictedRatingsForStudent (int studentID) {
        List<Tuple2<Tuple2<Integer,Integer>,Double>> retValue = absPredictions.filter(new Function<Tuple2<Tuple2<Integer, Integer>, Double>, Boolean>() {
            @Override
            public Boolean call(Tuple2<Tuple2<Integer, Integer>, Double> tuple2DoubleTuple2) throws Exception {
                return tuple2DoubleTuple2._1()._1()==studentID;
            }
        }).collect();
        retValue.sort(new Comparator<Tuple2<Tuple2<Integer, Integer>, Double>>() {
            @Override
            public int compare(Tuple2<Tuple2<Integer, Integer>, Double> o1, Tuple2<Tuple2<Integer, Integer>, Double> o2) {
                return Double.compare(o1._2(),o1._2());
            }
        });
        return retValue;
    }
}
