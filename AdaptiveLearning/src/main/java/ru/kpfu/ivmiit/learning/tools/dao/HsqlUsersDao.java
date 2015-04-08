package ru.kpfu.ivmiit.learning.tools.dao;

import ru.kpfu.ivmiit.learning.tools.models.LoginData;
import ru.kpfu.ivmiit.learning.tools.models.TestResult;
import ru.kpfu.ivmiit.learning.tools.models.User;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * @author Sidikov Marsel, Lebedenko Igor, Karpov Oleg (Kazan Federal University)
 *
 */
public class HsqlUsersDao extends NamedParameterJdbcDaoSupport implements UsersDao {

    private static final String HSQL_STUDENT_PASSWHASH_BY_LOGIN = "SELECT passw_hash FROM Student WHERE " +
            "login = :login";

    private static final String HSQL_UPDATE_STUDENT_BY_LOGPAS = "UPDATE Student SET user_token = :userToken WHERE " +
            "(login = :login AND passw_hash = :password)";

    private static final String HSQL_STUDENTS_COUNT_BY_LOGIN = "SELECT COUNT(*) FROM Student WHERE " +
            "login = :login";

    private static final String HSQL_STUDENTS_COUNT_BY_USERTOKEN = "SELECT COUNT(*) FROM Student WHERE " +
            "user_token = :userToken";

    private static final String HSQL_GET_STUDENT_BY_USERTOKEN = "SELECT (*) FROM Student WHERE " +
            "user_token = :userToken";

    private static final String HSQL_UPDATE_STUDENT_BY_USERTOKEN = "UPDATE Student SET user_token = " +
            ":newUserToken WHERE user_token = :userToken";

    private static final String HSQL_CURRENT_URLS_BY_USERTOKEN = "SELECT current_urls FROM Student WHERE " +
            "user_token = :userToken";

    private static final String HSQL_CURRENT_LESSON_BY_USERTOKEN = "SELECT current_lesson FROM Student WHERE " +
            "user_token = :userToken";

    private static final String HSQL_UPDATE_CURRENT_URLS = "UPDATE Student SET current_urls = :URLs WHERE " +
            "user_token = :userToken";

    private static final String HSQL_UPDATE_CURRENT_LESSON = "UPDATE Student SET current_lesson = :lessonID " +
            "WHERE user_token = :userToken";

    private static final String HSQL_UPDATE_CURRENT_LESSON_AND_URLS = "UPDATE Student SET current_lesson = " +
            ":lessonID, current_urls = :currentURLs WHERE user_token = :userToken";

    private static final String HSQL_STUDENT_ID_BY_USERTOKEN = "SELECT id FROM Student WHERE " +
            "user_token = :userToken";

    private static final String HSQL_INSERT_TEST_RESULT = "INSERT INTO TestResult VALUES (:lessonID, :studentID, " +
            ":block , :mark)";

    private static final String HSQL_GET_MARKS_BY_STUDENT_ID = "SELECT mark FROM TestResult WHERE " +
            "student_id = :studentID";

    private static final String HSQL_INSERT_STUDENT = "INSERT INTO Student VALUES (:firstName, :lastName, " +
            ":passwHash , :login, :userToken, :currentLesson, :currentURLs)";

    private static final String HSQL_FIRST_LESSON_ID = "SELECT MIN(id) FROM Lesson";

    private static final String HSQL_MAIN_MAT_URL_BY_LESSON_ID = "SELECT main_mat_url FROM Lesson WHERE " +
            "id = :currentLesson";


    private RowMapper<LoginData> loginDataRowMapper = new RowMapper<LoginData>() {
        @Override
        public LoginData mapRow(ResultSet resultSet, int i) throws SQLException {
            LoginData loginData = new LoginData();
            loginData.setLogin(resultSet.getString("login"));
            loginData.setPassword(resultSet.getString("passw_hash"));
            return loginData;
        }
    };

    private RowMapper<User> userRowMapper = new RowMapper<User>() {
        @Override
        public User mapRow(ResultSet resultSet, int i) throws SQLException {
            User user = new User();
            user.setName(resultSet.getString("first_name"));
            user.setLastName(resultSet.getString("last_name"));
            user.setUserLogin(loginDataRowMapper.mapRow(resultSet, i).getLogin(),
                            loginDataRowMapper.mapRow(resultSet, i).getPassword());
            return user;
        }
    };

    private RowMapper<Integer> marksRowMapper = new RowMapper<Integer>() {
        @Override
        public Integer mapRow(ResultSet resultSet, int i) throws SQLException {
            return resultSet.getInt("mark");
        }
    };

    @Override
    public String login(LoginData data) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("login", data.getLogin());

        String passwHash = getNamedParameterJdbcTemplate().queryForObject(HSQL_STUDENT_PASSWHASH_BY_LOGIN,
                paramMap, String.class);

        if (passwHash != null) {
            if (PasswordHash.validatePassword(data.getPassword(), passwHash)) {
                paramMap.put("password", passwHash);
                String userToken = UsersDaoUtil.generateUserToken();
                paramMap.put("userToken", userToken);
                getNamedParameterJdbcTemplate().update(HSQL_UPDATE_STUDENT_BY_LOGPAS, paramMap);

                return userToken;
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
    public boolean checkLogin(String login) {
        if (login == null) {
            throw new IllegalArgumentException();
        }

        SqlParameterSource namedParameters = new MapSqlParameterSource("login", login);

        int count = getNamedParameterJdbcTemplate().queryForObject(HSQL_STUDENTS_COUNT_BY_LOGIN,
                namedParameters, Integer.class);

        return count == 0;
    }

    @Override
    public void logout(String userToken) {
        if (userToken == null) {
            throw new IllegalArgumentException();
        }

        Map<String, Object> paramMap = new HashMap<String, Object>();
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
        if (user == null || !checkLogin(user.getLoginData().getLogin())) {
            throw new IllegalArgumentException();
        }
        Map<String, Object> paramMap = new HashMap<String, Object>();

        String passwHash = PasswordHash.createHash(user.getLoginData().getPassword());
        String userToken = UsersDaoUtil.generateUserToken();
        Integer currentLesson = getNamedParameterJdbcTemplate().queryForObject(HSQL_FIRST_LESSON_ID,
                paramMap, Integer.class);

        if (currentLesson != null) {
            paramMap.put("currentLesson", currentLesson);
            String currentURLs = getNamedParameterJdbcTemplate().queryForObject(HSQL_MAIN_MAT_URL_BY_LESSON_ID,
                    paramMap, String.class);

            if (currentURLs != null) {
                paramMap.put("firstName", user.getName());
                paramMap.put("lastName", user.getLastname());
                paramMap.put("passwHash", passwHash);
                paramMap.put("login", user.getLoginData().getLogin());
                paramMap.put("userToken", userToken);
                paramMap.put("currentURLs", currentURLs);

                getNamedParameterJdbcTemplate().update(HSQL_INSERT_STUDENT, paramMap);

                return userToken;
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
    public User getProfile(String userToken) {
        SqlParameterSource namedParameters = new MapSqlParameterSource("userToken", userToken);

        User user = getNamedParameterJdbcTemplate().queryForObject(HSQL_GET_STUDENT_BY_USERTOKEN,
                namedParameters, userRowMapper);

        if (user != null) {
            return user;
        }
        else {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public List<Integer> getMaterials(String userToken) {
        SqlParameterSource namedParameters = new MapSqlParameterSource("userToken", userToken);

        int count = getNamedParameterJdbcTemplate().queryForObject(HSQL_STUDENTS_COUNT_BY_USERTOKEN,
                namedParameters, Integer.class);

        if (count == 1) {
            Integer currentLesson = getNamedParameterJdbcTemplate().queryForObject(HSQL_CURRENT_LESSON_BY_USERTOKEN,
                    namedParameters, Integer.class);

            if (currentLesson != null) {
                List<Integer> material = new ArrayList<Integer>();
                material.add(currentLesson);

                return material;
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
    public void changeCurrentMaterial(String userToken, int alternativeMaterialId) {
        if (userToken == null) {
            throw new IllegalArgumentException();
        }

        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("userToken", userToken);

        int count = getNamedParameterJdbcTemplate().queryForObject(HSQL_STUDENTS_COUNT_BY_USERTOKEN,
                paramMap, Integer.class);

        if (count == 1) {
            paramMap.put("lessonID", alternativeMaterialId);
            String currentURLs = getNamedParameterJdbcTemplate().queryForObject(HSQL_MAIN_MAT_URL_BY_LESSON_ID,
                    paramMap, String.class);

            if (currentURLs != null) {
                paramMap.put("currentURLs", currentURLs);
                getNamedParameterJdbcTemplate().update(HSQL_UPDATE_CURRENT_LESSON_AND_URLS, paramMap);
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
        SqlParameterSource namedParameters = new MapSqlParameterSource("userToken", userToken);

        int count = getNamedParameterJdbcTemplate().queryForObject(HSQL_STUDENTS_COUNT_BY_USERTOKEN,
                namedParameters, Integer.class);

        if (count == 1) {
            Integer studentID = getNamedParameterJdbcTemplate().queryForObject(HSQL_STUDENT_ID_BY_USERTOKEN,
                    namedParameters, Integer.class);

            if (studentID != null) {
                List<Integer> result = getNamedParameterJdbcTemplate().query(HSQL_GET_MARKS_BY_STUDENT_ID,
                        namedParameters, marksRowMapper);

                if (result == null) {
                    return Collections.emptyList();
                }
                else {
                    return result;
                }
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
    public void answersSubmit(String userToken, TestResult result) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("userToken", userToken);

        int count = getNamedParameterJdbcTemplate().queryForObject(HSQL_STUDENTS_COUNT_BY_USERTOKEN,
                paramMap, Integer.class);

        if (count == 1) {
            Integer studentID = getNamedParameterJdbcTemplate().queryForObject(HSQL_STUDENT_ID_BY_USERTOKEN,
                    paramMap, Integer.class);
            Integer lessonID = getNamedParameterJdbcTemplate().queryForObject(HSQL_CURRENT_LESSON_BY_USERTOKEN,
                    paramMap, Integer.class);

            if (studentID != null && lessonID != null) {
                paramMap.put("studentID", studentID);
                paramMap.put("lessonID", lessonID);
                paramMap.put("block", result.getBlock());
                paramMap.put("mark", result.getMark());

                getNamedParameterJdbcTemplate().update(HSQL_INSERT_TEST_RESULT, paramMap);
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
    public String getCurrentURLs(String userToken) {
        SqlParameterSource namedParameters = new MapSqlParameterSource("userToken", userToken);

        String current = getNamedParameterJdbcTemplate().queryForObject(HSQL_CURRENT_URLS_BY_USERTOKEN,
                namedParameters, String.class);

        if (current != null) {
            return current;
        }
        else {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public int getCurrentLessonID(String userToken) {
        SqlParameterSource namedParameters = new MapSqlParameterSource("userToken", userToken);

        int count = getNamedParameterJdbcTemplate().queryForObject(HSQL_STUDENTS_COUNT_BY_USERTOKEN,
                namedParameters, Integer.class);

        if (count == 1) {
            Integer currentLessonID = getNamedParameterJdbcTemplate().queryForObject(HSQL_CURRENT_LESSON_BY_USERTOKEN,
                    namedParameters, Integer.class);
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
        Map<String, Object> paramMap = new HashMap<String, Object>();
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
