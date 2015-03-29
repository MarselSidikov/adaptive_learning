package ru.kpfu.ivmiit.learning.tools.dao;

import ru.kpfu.ivmiit.learning.tools.models.LoginData;
import ru.kpfu.ivmiit.learning.tools.models.User;

import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;
import java.util.List;

/**
 * @author Sidikov Marsel, Lebedenko Igor, Karpov Oleg (Kazan Federal University)
 *
 */
public class HsqlUsersDao extends SimpleJdbcDaoSupport implements UsersDao {
    @Override
    public String login(LoginData data) {
        String login = data.getLogin();
        String password = data.getPassword();

        String sql = "SELECT COUNT(*) FROM Student WHERE login = :login AND passwHash = :password";
        int count = getSimpleJdbcTemplate().queryForInt(sql, login, password);

        if (count == 0) {
            throw new IllegalArgumentException();
        }

        return login;
    }

    @Override
    public boolean checkLogin(String login) {
        if (login == null) {
            throw new IllegalArgumentException();
        }

        String sql = "SELECT COUNT(*) FROM Student WHERE login = :login";
        int count = getSimpleJdbcTemplate().queryForInt(sql, login);

        if (count == 0) {
            return true;
        }

        return false;
    }

    @Override
    public void logout(String userToken) {
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
