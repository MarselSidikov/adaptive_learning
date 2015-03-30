package ru.kpfu.ivmiit.learning.tools.core;


import ru.kpfu.ivmiit.learning.tools.models.TestResult;

import java.util.List;

/**
 * @author Sidikov Marsel (Kazan Federal University) and ZulfatMiftakhutdinov (Kazan Federal University)
 *
 */
public interface MaterialsResolver {


    /**
     *
     * @param lessonID lesson id
     * @return returns alternative material(extra) for lesson with lessonID
     */
    String getAlternativeMaterial(int lessonID);


    /**
     *
     * @param result test results for current lesson
     * @return if mistakes in test return those blocks that match to the incorrect questions
     * otherwise returns next lesson main material
     */
    String getNewMaterial(TestResult result);
}
