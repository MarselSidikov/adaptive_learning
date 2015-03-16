package ru.kpfu.ivmiit.learning.tools.dao;

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
        return 0;
    }
}
