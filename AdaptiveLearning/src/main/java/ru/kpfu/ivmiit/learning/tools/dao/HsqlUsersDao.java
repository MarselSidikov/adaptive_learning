package ru.kpfu.ivmiit.learning.tools.dao;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import ru.kpfu.ivmiit.learning.tools.models.LoginData;
import ru.kpfu.ivmiit.learning.tools.models.Student;
import ru.kpfu.ivmiit.learning.tools.models.TestResult;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * @author Sidikov Marsel, Lebedenko Igor, Karpov Oleg (Kazan Federal University)
 *
 */
public class HsqlUsersDao extends NamedParameterJdbcDaoSupport implements UsersDao {

    private static final String HSQL_STUDENT_PASSWHASH_BY_LOGIN = "SELECT pass_hash FROM Students WHERE " +
            "login = :login";

    private static final String HSQL_UPDATE_STUDENT_BY_LOGPAS = "UPDATE Students SET userToken = :userToken WHERE " +
            "(login = :login AND pass_hash = :password)";

    private static final String HSQL_STUDENTS_COUNT_BY_LOGIN = "SELECT COUNT(*) FROM Students WHERE " +
            "login = :login";

    private static final String HSQL_STUDENTS_COUNT_BY_USERTOKEN = "SELECT COUNT(*) FROM Students WHERE " +
            "userToken = :userToken";

    private static final String HSQL_GET_STUDENT_BY_USERTOKEN = "SELECT (*) FROM Students WHERE " +
            "userToken = :userToken";

    private static final String HSQL_UPDATE_STUDENT_BY_USERTOKEN = "UPDATE Students SET userToken = " +
            ":newUserToken WHERE userToken = :userToken";

    private static final String HSQL_CURRENT_URLS_BY_USERTOKEN = "SELECT current_urls FROM Students WHERE " +
            "userToken = :userToken";

    private static final String HSQL_CURRENT_LESSON_BY_USERTOKEN = "SELECT current_lesson FROM Students WHERE " +
            "userToken = :userToken";

    private static final String HSQL_UPDATE_CURRENT_URLS = "UPDATE Students SET current_urls = :URLs WHERE " +
            "userToken = :userToken";

    private static final String HSQL_UPDATE_CURRENT_LESSON = "UPDATE Students SET current_lesson = :lessonID " +
            "WHERE userToken = :userToken";

    private static final String HSQL_UPDATE_CURRENT_LESSON_AND_URLS = "UPDATE Students SET current_lesson = " +
            ":lessonID, current_urls = :currentURLs WHERE userToken = :userToken";

    private static final String HSQL_UPDATE_CURRENT_LESSON_BY_ID = "UPDATE Students SET current_lesson = " +
            ":lessonID WHERE id = :id";

    private static final String HSQL_STUDENT_ID_BY_USERTOKEN = "SELECT id FROM Students WHERE " +
            "userToken = :userToken";

    private static final String HSQL_INSERT_TEST_RESULT = "INSERT INTO TestResult VALUES (:lessonID, :studentID, " +
            ":mark)";

    private static final String HSQL_GET_MARKS_BY_STUDENT_ID = "SELECT mark FROM TestResult WHERE " +
            "student_id = :studentID";

    private static final String HSQL_INSERT_STUDENT = "INSERT INTO Students VALUES (:firstName, :lastName, " +
            ":login, :passwHash , :currentLesson, :userToken, :currentURLs)";

    private static final String HSQL_MAIN_MAT_URL_BY_LESSON_ID = "SELECT mainMatUrl FROM Lessons WHERE " +
            "id = :currentLesson";


    private RowMapper<Student> studentRowMapper = new RowMapper<Student>() {
        @Override
        public Student mapRow(ResultSet resultSet, int i) throws SQLException {

            String firstName = resultSet.getString("first_name");
            String lastName = resultSet.getString("last_name");
            String login = resultSet.getString("login");
            String passwHash = resultSet.getString("pass_hash");
            String userToken = resultSet.getString("userToken");

            Student student = new Student(firstName, lastName, passwHash, login, userToken);

            List<Integer> currentLessonList = new ArrayList<Integer>();
            currentLessonList.add(resultSet.getInt("current_lesson"));
            student.setCurrentLesson(currentLessonList);

            String[] currentUrlsArray = resultSet.getString("current_urls").split(":");
            List<String> currentUrlsList = new ArrayList<String>();
            Collections.addAll(currentUrlsList, currentUrlsArray);
            student.setCurrentUrls(currentUrlsList);

            return student;
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
    public String signUp(Student student) {
        if (student == null || !checkLogin(student.getLogin())) {
            throw new IllegalArgumentException();
        }
        Map<String, Object> paramMap = new HashMap<String, Object>();

        String passwHash = PasswordHash.createHash(student.getPasswHash());
        String userToken = UsersDaoUtil.generateUserToken();

        if (student.getCurrentLesson() != null && student.getCurrentUrls() != null) {
            paramMap.put("firstName", student.getFirstName());
            paramMap.put("lastName", student.getLastName());
            paramMap.put("passwHash", passwHash);
            paramMap.put("login", student.getLogin());
            paramMap.put("userToken", userToken);
            paramMap.put("currentLesson", student.getCurrentLesson());
            paramMap.put("currentURLs", student.getCurrentUrls());

            getNamedParameterJdbcTemplate().update(HSQL_INSERT_STUDENT, paramMap);

            return userToken;
        }
        else {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public Student getProfile(String userToken) {
        SqlParameterSource namedParameters = new MapSqlParameterSource("userToken", userToken);

        int count = getNamedParameterJdbcTemplate().queryForObject(HSQL_STUDENTS_COUNT_BY_USERTOKEN,
                namedParameters, Integer.class);

        if (count != 1) {
            return getNamedParameterJdbcTemplate().queryForObject(HSQL_GET_STUDENT_BY_USERTOKEN,
                    namedParameters, studentRowMapper);
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
    @Override
    public void setLessonID (int id, int lessonID) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("id", id);
        paramMap.put("lessonID", lessonID);

        getNamedParameterJdbcTemplate().update(HSQL_UPDATE_CURRENT_LESSON_BY_ID, paramMap);
    }
}
