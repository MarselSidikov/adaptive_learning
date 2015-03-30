package ru.kpfu.ivmiit.learning.tools.models;

import java.util.*;

/**
 * Created by Зульфат on 30.03.2015.
 */
public class TestResult {

    public int getLessonID() {
        return lessonID;
    }

    public String getUserToken() {
        return userToken;
    }

    int lessonID;
    String userToken;
    public Map<Integer, Boolean> getResult() {
        return result;
    }

    private Map<Integer,Boolean> result;
}
