package ru.kpfu.ivmiit.learning.tools.models;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.mllib.recommendation.ALS;
import org.apache.spark.mllib.recommendation.MatrixFactorizationModel;
import org.apache.spark.mllib.recommendation.Rating;
import ru.kpfu.ivmiit.learning.tools.SCSingletone;
import org.apache.spark.api.java.function.Function;

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
    public Table (String path) {
        JavaRDD<String> strData = SCSingletone.getInstance().getSparkContext().textFile(path);
        tableAbsTestReslt = strData.map(new Function<String,Rating> () {
            public Rating call (String s) {
                String [] sarr = s.split(";");
                return new Rating(Integer.parseInt(sarr[0]),Integer.parseInt(sarr[1]),Double.parseDouble(sarr[2]));
            }
        });
        tableRelTestResult = strData.map(new Function<String,Rating> () {
            public Rating call (String s) {
                String [] sarr = s.split(";");
                return new Rating(Integer.parseInt(sarr[0]),Integer.parseInt(sarr[1]),Double.parseDouble(sarr[3]));
            }
        });
        modelAbsTestResult = ALS.train(JavaRDD.toRDD(tableAbsTestReslt),rankForAbsTestResult,numIterationsForAbsTestResult,alphaForAbsTestResult);
        modelRelTestResult = ALS.train(JavaRDD.toRDD(tableRelTestResult),rankForRelTestResult,numIterationsForRelTestResult,alphaForRelTestResult);
    }
    public Table (JavaRDD<Rating> tableAbsTestReslt, JavaRDD<Rating> tableRelTestResult) {
        this.tableAbsTestReslt = tableAbsTestReslt;
        this.tableRelTestResult = tableRelTestResult;
    }
    public void set (int i,int j, TElement el) {
        //save to file recalc
    }
    public JavaRDD<TElement> getResultsForStudent (int studentID) {
        return null;
    }
    private void recalc () {

    }
}
