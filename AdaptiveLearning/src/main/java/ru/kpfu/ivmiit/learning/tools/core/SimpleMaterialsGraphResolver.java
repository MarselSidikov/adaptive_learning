package ru.kpfu.ivmiit.learning.tools.core;

import ru.kpfu.ivmiit.learning.tools.dao.MaterialsDao;
import ru.kpfu.ivmiit.learning.tools.models.Material;

import java.util.List;

/**
 * @author Sidikov Marsel (Kazan Federal University)
 *
 */
public class SimpleMaterialsGraphResolver implements MaterialsResolver {
    MaterialsDao materialsDao;

    @Override
    public Material getMaterial(int id) {
        return null;
    }

    @Override
    public int getAlternativeMaterial(int id) {
        return 0;
    }

    @Override
    public int getNewMaterial(int result, List<Integer> oldResults) {
        return 0;
    }
}
