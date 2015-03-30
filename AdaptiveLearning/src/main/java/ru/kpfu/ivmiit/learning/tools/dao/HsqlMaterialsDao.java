package ru.kpfu.ivmiit.learning.tools.dao;

import ru.kpfu.ivmiit.learning.tools.models.Material;
import ru.kpfu.ivmiit.learning.tools.models.Test;

import java.util.Collection;

/**
 * @author Marsel Sidikov (Kazan Federal University)
 */
public class HsqlMaterialsDao implements MaterialsDao {

    @Override
    public String getAlternativeMaterial(int id) {
        return null;
    }

    @Override
    public String getBlockURLs(int lessonID) {
        return null;
    }

    @Override
    public int getNextLesson(int lessonID) {
        return 0;
    }

    @Override
    public String getMainURL(int lessonID) {
        return null;
    }
}
