package ru.kpfu.ivmiit.learning.tools.dao;

import ru.kpfu.ivmiit.learning.tools.models.Test;

import java.util.Collection;

/**
 * @author Marsel Sidikov (Kazan Federal University)
 */
public class HsqlMaterialsDao implements MaterialsDao {
    @Override
    public String getMaterialText() {
        return null;
    }

    @Override
    public Collection<Test> getRelativeTests() {
        return null;
    }

    @Override
    public int getComplexity() {
        return 0;
    }
}
