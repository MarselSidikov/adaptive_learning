package ru.kpfu.ivmiit.learning.tools.dao;

import ru.kpfu.ivmiit.learning.tools.models.LoginData;
import ru.kpfu.ivmiit.learning.tools.models.User;

import java.util.List;

/**
 * @author Sidikov Marsel (Kazan Federal University)
 *
 */
public class HsqlUsersDao implements UsersDao {
    @Override
    public String login(LoginData data) {
        return null;
    }

    @Override
    public boolean checkLogin(String login) {
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
