package ru.kpfu.ivmiit.learning.tools.core;

import ru.kpfu.ivmiit.learning.tools.models.Material;

import java.util.List;

/**
 * @author Sidikov Marsel (Kazan Federal University) and ZulfatMiftakhutdinov (Kazan Federal University)
 *
 */
public interface MaterialsResolver {
    Material getMaterial(int id);
    int getAlternativeMaterial(int id);
    int getNewMaterial(int result, List<Integer> oldResults);
}
