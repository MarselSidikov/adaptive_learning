package ru.kpfu.ivmiit.learning.tools.dao;

import ru.kpfu.ivmiit.learning.tools.models.Material;
import ru.kpfu.ivmiit.learning.tools.models.Test;

import java.util.Collection;

/**
 * @author Sidikov Marsel (Kazan Federal University)
 *
 */
public interface MaterialsDao {

    /**
     *
     * @param id
     * @throws java.lang.IllegalArgumentException if id is invalid
     * @return alternativeMaterial for related theme
     */
    String getAlternativeMaterial(int id);
    /**
     *
     * @param lessonID lesson id
     * @return returns document URLs for all blocks
     * @throws java.lang.IllegalArgumentException if id is invalid
     */
    public String getBlockURLs (int lessonID);

    /**
     *
     * @param lessonID lesson id
     * @return returns next lesson for lesson with id = lessonID
     * @throws java.lang.IllegalArgumentException if id is invalid
     */
    public int getNextLesson(int lessonID);

    /**
     *
     * @param lessonID lesson id
     * @return returns main document URL
     * @throws java.lang.IllegalArgumentException if id is invalid
     */
    public String getMainURL (int lessonID);
}
