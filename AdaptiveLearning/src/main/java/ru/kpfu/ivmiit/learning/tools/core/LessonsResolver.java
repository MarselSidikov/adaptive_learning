package ru.kpfu.ivmiit.learning.tools.core;

import ru.kpfu.ivmiit.learning.tools.models.Lesson;
import ru.kpfu.ivmiit.learning.tools.models.Result;

import java.util.List;

/**
 * @author Sidikov Marsel (Kazan Federal University) and Zulfat Miftakhutdinov (Kazan Federal University)
 *
 */
public interface LessonsResolver {

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


    /**
     *
     * @param studentID
     * @return next lesson that best fit for user
     */
    int getNewLessonForStudent(int studentID, List<Integer> learnedLessons);
}
