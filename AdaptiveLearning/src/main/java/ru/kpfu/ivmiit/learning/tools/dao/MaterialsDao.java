package ru.kpfu.ivmiit.learning.tools.dao;

import ru.kpfu.ivmiit.learning.tools.models.Test;

import java.util.Collection;

/**
 * @author Sidikov Marsel (Kazan Federal University)
 *
 */
public interface MaterialsDao {
    String getMaterialText();
    Collection<Test> getRelativeTests();
    int getComplexity();
}
