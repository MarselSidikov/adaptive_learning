package ru.kpfu.ivmiit.learning.tools.dao;

import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;
import ru.kpfu.ivmiit.learning.tools.models.Material;
import ru.kpfu.ivmiit.learning.tools.models.Test;

import java.util.Collection;

/**
 * @author Marsel Sidikov (Kazan Federal University)
 */
public class HsqlMaterialsDao extends SimpleJdbcDaoSupport implements MaterialsDao {

    @Override
    public String getAlternativeMaterial(int id) {
        return null;
    }

    @Override
    public String getBlockURLs(int lessonID) {
        String sql = "SELECT blockURLS FROM Lessons WHERE id = :lessonID";
        String blocksURLs = getSimpleJdbcTemplate().queryForObject(sql, String.class, lessonID);

        if (blocksURLs != null) {
            return blocksURLs;
        }
        else {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public int getNextLesson(int lessonID) {
        return 0;
    }

    @Override
    public String getMainURL(int lessonID) {
        String sql = "SELECT mainMatURL FROM Lessons WHERE id = :lessonID";
        String mainMatURL = getSimpleJdbcTemplate().queryForObject(sql, String.class, lessonID);

        if (mainMatURL != null) {
            return mainMatURL;
        }
        else {
            throw new IllegalArgumentException();
        }
    }
}
