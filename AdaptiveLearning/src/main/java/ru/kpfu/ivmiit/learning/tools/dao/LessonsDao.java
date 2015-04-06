package ru.kpfu.ivmiit.learning.tools.dao;


import ru.kpfu.ivmiit.learning.tools.models.Lesson;
import java.util.List;
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
    public Lesson getLesson(int id);

    /**
     *
     * @param id
     * @throws java.lang.IllegalArgumentException if id is invalid
     * @return alternativeMaterial for related theme
     */
    public int getAlternativeLesson(int id);
    public List<Integer> getAvailableLessons (List<Integer> learnedLessons);
}
