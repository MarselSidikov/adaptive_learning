package ru.kpfu.ivmiit.learning.tools;

/**
 * @author Sidikov Marsel (Kazan Federal University)
 *
 */
public class AdaptiveLearningServiceFacade {

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

    String signUp(User user);

    User getProfile(String userToken);

    Test getTest(String userToken);

    void answersSubmit(String userToken, Answers answers);

}