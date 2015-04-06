package ru.kpfu.ivmiit.learning.tools.dao;


import ru.kpfu.ivmiit.learning.tools.models.LoginData;
import ru.kpfu.ivmiit.learning.tools.models.TestResult;
import ru.kpfu.ivmiit.learning.tools.models.User;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Sidikov Marsel, Lebedenko Igor, Karpov Oleg (Kazan Federal University)
 *
 */
public class HsqlUsersDao extends NamedParameterJdbcDaoSupport implements UsersDao {

    private static final String HSQL_GET_STUDENTS_COUNT_BY_LOGPAS = "SELECT COUNT(*) FROM Students WHERE " +
            "(login = :login AND passHash = :password)";

    private static final String HSQL_UPDATE_STUDENT_BY_LOGPAS = "UPDATE Students SET userToken = :userToken WHERE " +
            "(login = :login AND passHash = :password)";

    private static final String HSQL_STUDENTS_COUNT_BY_LOGIN = "SELECT COUNT(*) FROM Students WHERE " +
            "login = :login";

    private static final String HSQL_STUDENTS_COUNT_BY_USERTOKEN = "SELECT COUNT(*) FROM Students WHERE " +
            "userToken = :userToken";

    private static final String HSQL_GET_STUDENT_BY_USERTOKEN = "SELECT (*) FROM Students WHERE " +
            "userToken = :userToken";

    private static final String HSQL_UPDATE_STUDENT_BY_USERTOKEN = "UPDATE Students SET userToken = " +
            ":newUserToken WHERE userToken = :userToken";

    private static final String HSQL_LESSONS_COUNT_BY_ID = "SELECT COUNT(*) FROM Lessons WHERE id = " +
            ":alternativeMaterialId";

    private static final String HSQL_CURRENT_URLS_BY_USERTOKEN = "SELECT currentURLs FROM Students WHERE " +
            "userToken = :userToken";

    private static final String HSQL_CURRENT_LESSON_BY_USERTOKEN = "SELECT currentLesson FROM Students WHERE " +
            "userToken = :userToken";

    private static final String HSQL_UPDATE_CURRENT_URLS = "UPDATE Students SET currentURLs = :URLs WHERE " +
            "userToken = :userToken";

    private static final String HSQL_UPDATE_CURRENT_LESSON = "UPDATE Students SET currentLesson = :lessonID " +
            "WHERE userToken = :userToken";


    private RowMapper<LoginData> loginDataRowMapper = new RowMapper<LoginData>() {
        @Override
        public LoginData mapRow(ResultSet resultSet, int i) throws SQLException {
            LoginData loginData = new LoginData();
            loginData.setLogin(resultSet.getString("login"));
            loginData.setPassword(resultSet.getString("passHash"));
            return loginData;
        }
    };

    private RowMapper<User> userRowMapper = new RowMapper<User>() {
        @Override
        public User mapRow(ResultSet resultSet, int i) throws SQLException {
            User user = new User();
            user.setName(resultSet.getString("firstName"));
            user.setLastName(resultSet.getString("lastName"));
            user.setUserLogin(loginDataRowMapper.mapRow(resultSet, i).getLogin(),
                            loginDataRowMapper.mapRow(resultSet, i).getPassword());
            return user;
        }
    };

    @Override
    public String login(LoginData data) {
        Map<String, String> paramMap = new HashMap<String, String>();

        paramMap.put("login", data.getLogin());
        paramMap.put("password", data.getPassword());

        int count = getNamedParameterJdbcTemplate().queryForObject(HSQL_GET_STUDENTS_COUNT_BY_LOGPAS,
                paramMap, Integer.class);

        if (count == 1) {
            String userToken = UsersDaoUtil.generateUserToken();
            paramMap.put("userToken", userToken);
            getNamedParameterJdbcTemplate().update(HSQL_UPDATE_STUDENT_BY_LOGPAS, paramMap);

            return userToken;
        }
        else {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public boolean checkLogin(String login) {
        if (login == null) {
            throw new IllegalArgumentException();
        }

        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("login", login);

        int count = getNamedParameterJdbcTemplate().queryForObject(HSQL_STUDENTS_COUNT_BY_LOGIN,
                paramMap, Integer.class);

        return count == 0;
    }

    @Override
    public void logout(String userToken) {
        if (userToken == null) {
            throw new IllegalArgumentException();
        }

        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("userToken", userToken);

        int count = getNamedParameterJdbcTemplate().queryForObject(HSQL_STUDENTS_COUNT_BY_USERTOKEN,
                paramMap, Integer.class);

        if (count == 1) {
            paramMap.put("newUserToken", "");
            getNamedParameterJdbcTemplate().update(HSQL_UPDATE_STUDENT_BY_USERTOKEN, paramMap);
        }
        else {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public String signUp(User user) {
        return null;
    }

    @Override
    public User getProfile(String userToken) {
        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("userToken", userToken);

        User user;
        user = getNamedParameterJdbcTemplate().queryForObject(HSQL_GET_STUDENT_BY_USERTOKEN,
                paramMap, userRowMapper);

        if (user != null) {
            return user;
        }
        else {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public List<Integer> getMaterials(String userToken) {
        return null;
    }

    @Override
    public void changeCurrentMaterial(String userToken, int alternativeMaterialId) {
        if (userToken == null) {
            throw new IllegalArgumentException();
        }

        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("lessonID", alternativeMaterialId);

        int count = getNamedParameterJdbcTemplate().queryForObject(HSQL_LESSONS_COUNT_BY_ID,
                paramMap, Integer.class);

        if (count == 1) {
            paramMap.put("userToken", userToken);
            count = getNamedParameterJdbcTemplate().queryForObject(HSQL_STUDENTS_COUNT_BY_USERTOKEN,
                    paramMap, Integer.class);

            if (count == 1) {
                getNamedParameterJdbcTemplate().update(HSQL_UPDATE_CURRENT_LESSON, paramMap);
            }
            else {
                throw new IllegalArgumentException();
            }
        }
        else {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public List<Integer> getAllResults(String userToken) {
        return null;
    }

    @Override
    public void answersSubmit(String userToken, TestResult result) {

    }

    @Override
    public String getCurrentURLs(String userToken) {
        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("userToken", userToken);

        String current = getNamedParameterJdbcTemplate().queryForObject(HSQL_CURRENT_URLS_BY_USERTOKEN,
                paramMap, String.class);

        if (current != null) {
            return current;
        }
        else {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public int getCurrentLessonID(String userToken) {
        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("userToken", userToken);

        int count = getNamedParameterJdbcTemplate().queryForObject(HSQL_STUDENTS_COUNT_BY_USERTOKEN,
                paramMap, Integer.class);

        if (count == 1) {
            Integer currentLessonID = getNamedParameterJdbcTemplate().queryForObject(HSQL_CURRENT_LESSON_BY_USERTOKEN,
                    paramMap, Integer.class);
            if (currentLessonID != null) {
                return currentLessonID;
            }
            else {
                throw new IllegalArgumentException();
            }
        }
        else {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public void setCurrentURLs(String userToken, String URLs) {
        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("userToken", userToken);

        int count = getNamedParameterJdbcTemplate().queryForObject(HSQL_STUDENTS_COUNT_BY_USERTOKEN,
                paramMap, Integer.class);

        if (count == 1) {
            paramMap.put("URLs", URLs);
            getNamedParameterJdbcTemplate().update(HSQL_UPDATE_CURRENT_URLS, paramMap);
        }
        else {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public void setLessonID(String userToken, int lessonID) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("userToken", userToken);

        int count = getNamedParameterJdbcTemplate().queryForObject(HSQL_STUDENTS_COUNT_BY_USERTOKEN,
                paramMap, Integer.class);

        if (count == 1) {
            paramMap.put("lessonID", lessonID);
            getNamedParameterJdbcTemplate().update(HSQL_UPDATE_CURRENT_LESSON, paramMap);
        }
        else {
            throw new IllegalArgumentException();
        }
    }
}
