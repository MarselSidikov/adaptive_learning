package ru.kpfu.ivmiit.learning.tools;

/**
 * @author Sidikov Marsel (Kazan Federal University)
 *
 */
public class AdaptiveLearningServiceFacade {
    String login(LoginData data);

    boolean checkLogin(String login);

    void logout(String userToken);

    String signUp(User user);

    User getProfile(String userToken);

    Test getTest(String userToken);

    void answersSubmit(String userToken, Answers answers);

}