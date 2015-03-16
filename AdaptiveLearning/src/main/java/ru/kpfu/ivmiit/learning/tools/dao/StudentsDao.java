package ru.kpfu.ivmiit.learning.tools.dao;
import ru.kpfu.ivmiit.learning.tools.models.LoginData;
import ru.kpfu.ivmiit.learning.tools.models.Result;
import ru.kpfu.ivmiit.learning.tools.models.Student;

import java.util.List;

/**
 * @author Sidikov Marsel (Kazan Federal University) and ZulfatMiftakhutdinov (Kazan Federal University)
 *
 */
public interface StudentsDao {

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
    boolean checkLogin (String login);

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
     * @param userToken
     * @throws java.lang.IllegalArgumentException if user with this token is not exist
     * @return user materials id sorted by date. First element is current material, last - first studied
     */
    List<Integer> getLessons(String userToken);


    /**
     * Changes the current material of user with token userToken
     * @param userToken
     * @param alternativeMaterialId
     * @return void
     * @throws java.lang.IllegalArgumentException if user with this token does not exist and
     * if userToken is null
     * @throws java.lang.IllegalArgumentException if alternativeMaterialId is invalid
     */
    void changeCurrentLesson(String userToken, int alternativeMaterialId);


    /**
     *
     * @param userToken
     * @throws java.lang.IllegalArgumentException if userToken is invalid
     * @return list of user test results
     */
    List<Result> getAllResults(String userToken);


    /**
     * Submitting result of the test
     * @param userToken
     * @param result
     * @throws java.lang.IllegalArgumentException if userToken is invalid
     * @return void
     */
    void answersSubmit(String userToken,Result result);


    /**
     * adding new material to user with token = userToken
     * @param materialId
     * @param userToken
     * @throws java.lang.IllegalArgumentException if userToken is invalid
     * @return void
     */
    void addNewLesson(String userToken, int materialId);
}
