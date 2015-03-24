package ru.kpfu.ivmiit.learning.tools.core;

import org.apache.spark.SparkConf;
import org.apache.spark.SparkContext;
import org.apache.spark.api.java.JavaSparkContext;
import ru.kpfu.ivmiit.learning.tools.SCSingletone;
import ru.kpfu.ivmiit.learning.tools.models.Lesson;
import ru.kpfu.ivmiit.learning.tools.models.Result;

import java.util.List;

/**
 * Created by Зульфат on 24.03.2015.
 */
public class ALSLessonResolver {
    private JavaSparkContext sc;

    public ALSLessonResolver() {
        sc = SCSingletone.getInstance().getSparkContext();
    }
    @Override
    public Lesson getLesson(int id) {
        return null;
    }

    @Override
    public int getAlternativeLesson(int id) {
        return 0;
    }

    @Override
    public int getNewLesson(List<Result> results) {
        return 0;
    }
}
