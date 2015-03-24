package ru.kpfu.ivmiit.learning.tools;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
/**
 * Created by Зульфат on 23.03.2015.
 */
public class SCSingletone {
    private SparkConf sparkConf;
    private JavaSparkContext sparkContext;
    private static SCSingletone ourInstance;

    public static synchronized SCSingletone getInstance() {
        if (ourInstance==null)
            ourInstance = new SCSingletone();
        return ourInstance;
    }

    private SCSingletone() {
        sparkConf = new SparkConf().setAppName("AdaptiveLearning").setMaster("local");
        sparkContext = new JavaSparkContext(sparkConf);
    }
    public JavaSparkContext getSparkContext() {
        return sparkContext;
    }
    public SparkConf getSparkConf() {
        return sparkConf;
    }

}
