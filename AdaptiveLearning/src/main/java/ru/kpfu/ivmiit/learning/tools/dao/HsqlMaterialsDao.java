package ru.kpfu.ivmiit.learning.tools.dao;

import ru.kpfu.ivmiit.learning.tools.models.Material;
import ru.kpfu.ivmiit.learning.tools.models.Test;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Sidikov Marsel, Lebedenko Igor, Karpov Oleg (Kazan Federal University)
 */
public class HsqlMaterialsDao extends NamedParameterJdbcDaoSupport implements MaterialsDao {

    private static final String HSQL_BLOCK_URLS_BY_LESSON_ID = "SELECT blockURLS FROM Lessons WHERE id = :lessonID";

    private static final String HSQL_MAIN_MAT_URL_BY_LESSON_ID = "SELECT mainMatURL FROM Lessons WHERE id = :lessonID";

    @Override
    public String getAlternativeMaterial(int id) {
        return null;
    }

    @Override
    public String getBlockURLs(int lessonID) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("lessonID", lessonID);

        String blocksURLs = getNamedParameterJdbcTemplate().queryForObject(HSQL_BLOCK_URLS_BY_LESSON_ID,
                paramMap, String.class);

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
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("lessonID", lessonID);

        String mainMatURL = getNamedParameterJdbcTemplate().queryForObject(HSQL_MAIN_MAT_URL_BY_LESSON_ID,
                paramMap, String.class);

        if (mainMatURL != null) {
            return mainMatURL;
        }
        else {
            throw new IllegalArgumentException();
        }
    }
}
