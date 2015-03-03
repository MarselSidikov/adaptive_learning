package ru.kpfu.ivmiit.learning.tools.core;

import ru.kpfu.ivmiit.learning.tools.models.Material;

import java.util.List;

/**
 * @author Sidikov Marsel (Kazan Federal University) and ZulfatMiftakhutdinov (Kazan Federal University)
 *
 */
public interface MaterialsResolver {

    /**
     *
     * @param id material id
     * @throws java.lang.IllegalArgumentException if id is invalid
     * @return material
     */
    Material getMaterial(int id);


    /**
     *
     * @param id
     * @throws java.lang.IllegalArgumentException if id is invalid
     * @return alternativeMaterial for related theme
     */
    int getAlternativeMaterial(int id);


    /**
     *
     * @param result result for current material test
     * @param oldResults results for previous tests
     * @return next material that best fit for user
     */
    int getNewMaterial(int result, List<Integer> oldResults);
}
