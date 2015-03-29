package ru.kpfu.ivmiit.learning.tools.dao;

import ru.kpfu.ivmiit.learning.tools.models.LoginData;
import ru.kpfu.ivmiit.learning.tools.models.User;

import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Sidikov Marsel, Lebedenko Igor, Karpov Oleg (Kazan Federal University)
 *
 */
public class HsqlUsersDao extends SimpleJdbcDaoSupport implements UsersDao {
    @Override
    public String login(LoginData data) {
        Map<String, String> paramMap = new HashMap<String, String>();

        paramMap.put("login", data.getLogin());
        paramMap.put("password", data.getPassword());

        String sql = "SELECT COUNT(*) FROM Student WHERE (login = :login AND passwHash = :password)";
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
            sql = "UPDATE Student SET userToken = :userToken WHERE (login = :login AND passwHash = :password)";
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

        String sql = "SELECT COUNT(*) FROM Student WHERE login = :login";
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

        String sql = "SELECT COUNT(*) FROM Student WHERE userToken = :userToken";
        int count = getSimpleJdbcTemplate().queryForInt(sql, paramMap);

        if (count == 1) {
            paramMap.put("newUserToken", "");
            sql = "UPDATE Student SET userToken = :newUserToken WHERE userToken = :userToken";
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
        return null;
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
    public void answersSubmit(String userToken, int result) {

    }

    @Override
    public void addNewMaterial(String userToken, int materialId) {

    }
}
