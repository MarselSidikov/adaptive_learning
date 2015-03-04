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
}
