package ru.kpfu.ivmiit.learning.tools.core;

import org.springframework.beans.factory.annotation.Autowired;
import ru.kpfu.ivmiit.learning.tools.dao.LessonsDao;
import ru.kpfu.ivmiit.learning.tools.models.Lesson;
import ru.kpfu.ivmiit.learning.tools.models.Result;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.SparkConf;

import java.util.List;
import java.util.Map;

/**
 * @author Sidikov Marsel, Igor Lebedenko (Kazan Federal University)
 *
 */
public class SimpleLessonsGraphResolver implements LessonsResolver {
    private SparkConf conf;
    private JavaSparkContext sc;
    private double levels[];
    private LessonsDao lessonsDao;

    public SimpleLessonsGraphResolver() {
        conf = new SparkConf().setAppName("AdaptiveLearning").setMaster("local");
        sc = new JavaSparkContext(conf);
    }

    @Autowired
    public void setLessonsDao(LessonsDao lessonsDao) {
        this.lessonsDao = lessonsDao;
    }

    @Override
    public Lesson getMaterial(int id) {
        return null;
    }

    @Override
    public int getAlternativeLesson(int id) {
        return lessonsDao.getAlternativeLesson(id);
    }


    private JavaRDD<Result> getJavaRDDFromResultList (List<Result> result) {
        return sc.parallelize(result);
    }
    public int getNewLesson(List<Result> results) {
        double sumOfCorrectAnswerComplexities=0;
        double testComplexity=0;
        double mark;
        JavaRDD<Result> RDDResult = getJavaRDDFromResultList(results);
        Map<Integer,List<String>> testResDict;
        for (Result testRes:results) {
            testResDict = testRes.getResults();
            for (int questionId:testResDict.keySet()) {
                if (Boolean.parseBoolean(testResDict.get(questionId).get(0)))
                    sumOfCorrectAnswerComplexities += Double.parseDouble(testResDict.get(questionId).get(1));
                testComplexity+=Double.parseDouble(testResDict.get(questionId).get(1));
            }
        }
        mark = sumOfCorrectAnswerComplexities/testComplexity;
        int level=0;
        while(mark>levels[level])
            level++;
        return lessonsDao.getLessonWithComplexity(new Double(level));
    }
}
