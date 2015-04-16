package ru.kpfu.ivmiit.learning.tools.dao;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Sidikov Marsel, Lebedenko Igor, Karpov Oleg (Kazan Federal University)
 */
public class HsqlMaterialsDao extends NamedParameterJdbcDaoSupport implements MaterialsDao {

    private static final String HSQL_BLOCK_URLS_BY_LESSON_ID = "SELECT block_urls FROM Lessons WHERE id = :lessonID";

    private static final String HSQL_MAIN_MAT_URL_BY_LESSON_ID = "SELECT mainMatUrl FROM Lessons WHERE id = :lessonID";

    private static final String HSQL_EXTRA_MAT_URL_BY_LESSON_ID = "SELECT extra_material_url FROM Lessons WHERE id = :lessonID";

    private static final String HSQL_NEXT_LESSON_BY_LESSON_ID = "SELECT next_lesson FROM Lessons WHERE id = :lessonID";


    @Override
    public String getAlternativeMaterial(int id) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("lessonID", id);

        String extraMatURL = getNamedParameterJdbcTemplate().queryForObject(HSQL_EXTRA_MAT_URL_BY_LESSON_ID,
                paramMap, String.class);

        if (extraMatURL != null) {
            return extraMatURL;
        }
        else {
            throw new IllegalArgumentException();
        }
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
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("lessonID", lessonID);

        Integer nextLesson = getNamedParameterJdbcTemplate().queryForObject(HSQL_NEXT_LESSON_BY_LESSON_ID,
                paramMap, Integer.class);

        if (nextLesson != null) {
            return nextLesson;
        }
        else {
            throw new IllegalArgumentException();
        }
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
