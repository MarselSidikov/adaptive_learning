package ru.kpfu.ivmiit.learning.tools.dao;

import ru.kpfu.ivmiit.learning.tools.models.Material;
import ru.kpfu.ivmiit.learning.tools.models.Test;

import java.util.Collection;

/**
 * @author Marsel Sidikov (Kazan Federal University)
 */
public class HsqlMaterialsDao implements MaterialsDao {

    @Override
    public Material getMaterial(int id) {
        return null;
    }

    @Override
    public int getAlternativeMaterial(int id) {
        return 0;
    }
}
