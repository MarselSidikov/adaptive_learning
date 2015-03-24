package ru.kpfu.ivmiit.learning.tools.dao;

import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.api.java.JavaSQLContext;
import ru.kpfu.ivmiit.learning.tools.SCSingletone;
import ru.kpfu.ivmiit.learning.tools.models.Lesson;

/**
 * @author Marsel Sidikov (Kazan Federal University)
 */
public class HsqlLessonsDao implements LessonsDao {

    @Override
    public Lesson getLesson(int id) {
        return null;
    }

    @Override
    public int getAlternativeLesson(int id) {
        return 0;
    }

    @Override
    public int getLessonWithComplexity(Double complexity) {
        JavaSparkContext sc = SCSingletone.getInstance().getSparkContext();

        JavaSQLContext sqlContext = new JavaSQLContext(sc);
        return 0;
    }
}
