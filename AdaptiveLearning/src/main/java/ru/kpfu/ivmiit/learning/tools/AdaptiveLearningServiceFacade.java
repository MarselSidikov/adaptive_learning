package ru.kpfu.ivmiit.learning.tools;

import ru.kpfu.ivmiit.learning.tools.models.*;

/**
 * @author Sidikov Marsel, ZulfatMiftakhutdinov (Kazan Federal University)
 */
public interface AdaptiveLearningServiceFacade {

    /**
     *
     * @param data
     * @return token of user
     * @throws java.lang.IllegalArgumentException if user with this LoginData not exist
     */
    String login(LoginData data);

    /**
     *
     * @param login
     * @return true, if login id not exist, false - otherwise
     * @throws java.lang.IllegalArgumentException when login is null
     */

    boolean checkLogin(String login);

    /**
     *
     * @param userToken
     * @throws java.lang.IllegalArgumentException if user with this token not exist, and
     * if userToken is null
     */
    void logout(String userToken);

    /**
     *
     * @param user
     * @return token of user
     * @throws java.lang.IllegalArgumentException if some of user-data is not valid
     */
    String signUp(User user);

    /**
     *
     * @param userToken
     * @return information of user with userToken
     * @throws java.lang.IllegalArgumentException if user with this token is not exist
     */
    User getProfile(String userToken);

    /**
     *
     * @param id
     * @param userToken
     * @return
     */
    Material getMaterial(int id, String userToken);

    /**
     *
     * @param id
     * @param userToken
     * @return true, if alternative material is exist, false - otherwise
     */
    boolean changeMaterial(int id, String userToken);

    /**
     *
     * @param userToken
     * @return test of user
     * @throws java.lang.IllegalArgumentException if user with this token is not exist
     */
    Test getTest(String userToken);

    /**
     *
     * @param userToken
     * @param answers
     * @throws java.lang.IllegalArgumentException if user with this token is not exist
     */
    void answersSubmit(String userToken, Answers answers);
}