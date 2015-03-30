package ru.kpfu.ivmiit.learning.tools.dao;

import ru.kpfu.ivmiit.learning.tools.models.LoginData;
import ru.kpfu.ivmiit.learning.tools.models.TestResult;
import ru.kpfu.ivmiit.learning.tools.models.User;

import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;
import org.springframework.jdbc.core.RowMapper;
import java.security.SecureRandom;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author Sidikov Marsel, Lebedenko Igor, Karpov Oleg (Kazan Federal University)
 *
 */
public class HsqlUsersDao extends SimpleJdbcDaoSupport implements UsersDao {

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

        String sql = "SELECT COUNT(*) FROM Students WHERE (login = :login AND passHash = :password)";
        int count = getSimpleJdbcTemplate().queryForInt(sql, paramMap);

        if (count == 1) {
            String AB = "0123456789abcdefghigklmnopqrstuvwxyz";
            SecureRandom rnd = new SecureRandom();

            StringBuilder sb = new StringBuilder(32);
            for (int i = 0; i < 32; i++) {
                sb.append(AB.charAt(rnd.nextInt(AB.length())));
            }
            String userToken = sb.toString();

            paramMap.put("userToken", userToken);
            sql = "UPDATE Students SET userToken = :userToken WHERE (login = :login AND passHash = :password)";
            getSimpleJdbcTemplate().update(sql, paramMap);

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

        String sql = "SELECT COUNT(*) FROM Students WHERE login = :login";
        int count = getSimpleJdbcTemplate().queryForInt(sql, login);

        return count == 0;
    }

    @Override
    public void logout(String userToken) {
        if (userToken == null) {
            throw new IllegalArgumentException();
        }

        Map<String, String> paramMap = new HashMap<String, String>();

        paramMap.put("userToken", userToken);

        String sql = "SELECT COUNT(*) FROM Students WHERE userToken = :userToken";
        int count = getSimpleJdbcTemplate().queryForInt(sql, paramMap);

        if (count == 1) {
            paramMap.put("newUserToken", "");
            sql = "UPDATE Students SET userToken = :newUserToken WHERE userToken = :userToken";
            getSimpleJdbcTemplate().update(sql, paramMap);
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
        User user;
        String sql = "SELECT (*) FROM Students WHERE userToken = :userToken";
        user = getSimpleJdbcTemplate().queryForObject(sql, userRowMapper, userToken);

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
        String sql = "SELECT currentURLs FROM Students WHERE userToken = :userToken";
        String current = getSimpleJdbcTemplate().queryForObject(sql, String.class, userToken);
        if (current != null) {
            return current;
        }
        else {
            throw new IllegalArgumentException();
        }
    }


    @Override
    public int getCurrentLessonID(String userToken) {
        String sql = "SELECT (*) FROM Students WHERE userToken = :userToken";
        int count = getSimpleJdbcTemplate().queryForInt(sql, userToken);
        if (count == 1) {
            sql = "SELECT currentLesson FROM Students WHERE userToken = :userToken";
            return getSimpleJdbcTemplate().queryForInt(sql, userToken);
                    }
        else {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public void setCurrentURLs(String userToken, String URLs) {
        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("userToken", userToken);
        String sql = "SELECT COUNT(*) FROM Students WHERE userToken = :userToken";
        int count = getSimpleJdbcTemplate().queryForInt(sql, paramMap);
        if (count == 1) {
            paramMap.put("URLs", URLs);
            sql = "UPDATE Students SET currentURLs = :URLs WHERE userToken = :userToken";
            getSimpleJdbcTemplate().update(sql, paramMap);
        }
        else {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public void setLessonID(String userToken, int lessonID) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("userToken", userToken);
        String sql = "SELECT COUNT(*) FROM Students WHERE userToken = :userToken";
        int count = getSimpleJdbcTemplate().queryForInt(sql, paramMap);
        if (count == 1) {
            paramMap.put("lessonID", lessonID);
            sql = "UPDATE Students SET currentLesson = :lessonID WHERE userToken = :userToken";
            getSimpleJdbcTemplate().update(sql, paramMap);
        }
        else {
            throw new IllegalArgumentException();
        }
    }
}
