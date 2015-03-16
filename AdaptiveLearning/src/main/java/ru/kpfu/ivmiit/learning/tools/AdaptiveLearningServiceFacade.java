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
     * @param student
     * @return token of student
     * @throws java.lang.IllegalArgumentException if some of student-data is not valid
     */
    String signUp(Student student);

    /**
     *
     * @param userToken
     * @return information of user with userToken
     * @throws java.lang.IllegalArgumentException if user with this token is not exist
     */
    Student getProfile(String userToken);

    /**
     *
     * @param id
     * @param userToken
     * @return
     */
    Lesson getMaterial(int id, String userToken);

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