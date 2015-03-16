package ru.kpfu.ivmiit.learning.tools.dao;

import ru.kpfu.ivmiit.learning.tools.models.LoginData;
import ru.kpfu.ivmiit.learning.tools.models.Result;
import ru.kpfu.ivmiit.learning.tools.models.Student;

import java.util.List;

/**
 * @author Sidikov Marsel (Kazan Federal University)
 *
 */
public class HsqlStudentsDao implements StudentsDao {

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
    public String signUp(Student student) {
        return null;
    }

    @Override
    public Student getProfile(String userToken) {
        return null;
    }

    @Override
    public List<Integer> getLessons(String userToken) {
        return null;
    }

    @Override
    public void changeCurrentLesson(String userToken, int alternativeMaterialId) {

    }

    @Override
    public List<Result> getAllResults(String userToken) {
        return null;
    }

    @Override
    public void answersSubmit(String userToken, Result result) {

    }

    @Override
    public void addNewLesson(String userToken, int materialId) {

    }
}
