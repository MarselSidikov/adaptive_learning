package ru.kpfu.ivmiit.learning.tools.dao;
import ru.kpfu.ivmiit.learning.tools.models.LoginData;
import ru.kpfu.ivmiit.learning.tools.models.User;

import java.util.Collection;
import java.util.List;

/**
 * @author Sidikov Marsel (Kazan Federal University) and ZulfatMiftakhutdinov (Kazan Federal University)
 *
 */
public interface UsersDao<TestResults> {
    String login(LoginData data);
    boolean checkLogin (String login);
    void logout(String userToken);
    String signUp(User user);
    User getProfile(String userToken);
    List<Integer> getMaterials(String userToken);
    void changeCurrentMaterial(String userToken,int alternativeMaterialId);
    List<Integer> getAllResults(String userToken);
    void answersSubmit(String userToken,int result);
    void addNewMaterial(String userToken,int materialId);
}
