package ru.kpfu.ivmiit.learning.tools.dao;

import ru.kpfu.ivmiit.learning.tools.models.Lesson;

/**
 * @author Sidikov Marsel (Kazan Federal University)
 *
 */
public interface LessonsDao {
    /**
     *
     * @param id material id
     * @throws java.lang.IllegalArgumentException if id is invalid
     * @return material
     */
    Lesson getLesson(int id);

    /**
     *
     * @param id
     * @throws java.lang.IllegalArgumentException if id is invalid
     * @return alternativeMaterial for related theme
     */
    int getAlternativeLesson(int id);
    int getLessonWithComplexity(Double complexity);
}
