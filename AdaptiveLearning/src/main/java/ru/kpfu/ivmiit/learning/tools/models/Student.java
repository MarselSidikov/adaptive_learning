package ru.kpfu.ivmiit.learning.tools.models;

import java.util.List;

/**
 * Created by ������ on 30.03.2015.
 */
public class Student {
    private String firstName;
    private String lastName;
    private int passwHash;
    private String login;
    private String userToken;
    private List<Integer> currentLesson;
    private List<String> currentUrls;

    public List<Integer> getCurrentLesson() {
        return currentLesson;
    }

    public void setCurrentLesson(List<Integer> currentLesson) {
        this.currentLesson = currentLesson;
    }

    public List<String> getCurrentUrls() {
        return currentUrls;
    }

    public void setCurrentUrls(List<String> currentUrls) {
        this.currentUrls = currentUrls;
    }

    public Student(String firstName, String lastName, int passwHash, String login, String userToken) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.passwHash = passwHash;
        this.login = login;
        this.userToken = userToken;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getPasswHash() {
        return passwHash;
    }

    public void setPasswHash(int passwHash) {
        this.passwHash = passwHash;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }

}
