package ru.kpfu.ivmiit.learning.tools.models;

import java.util.ArrayList;

/**
 * @author Ilnar Ramazanov (Kazan Federal University), Zulfat Miftahutdinov (Kazan Federal University)
 */
public class Student {
    private int id;
    private String firstName;
    private String lastName;
    private String login;
    private String pass_hash;
    private int currentLessonID;
    private String knowledgeVector;
    private double educability;
    private String lessonsPassed;

    public String getLessonsPassed() {
        return lessonsPassed;
    }

    public void setLessonsPassed(String lessonsPassed) {
        this.lessonsPassed = lessonsPassed;
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

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPass_hash() {
        return pass_hash;
    }

    public void setPass_hash(String pass_hash) {
        this.pass_hash = pass_hash;
    }

    public int getCurrentLessonID() {
        return currentLessonID;
    }

    public void setCurrentLessonID(int currentLessonID) {
        this.currentLessonID = currentLessonID;
    }

    public String getKnowledgeVector() {
        return knowledgeVector;
    }

    public void setKnowledgeVector(String knowledgeVector) {
        this.knowledgeVector = knowledgeVector;
    }

    public double getEducability() {
        return educability;
    }

    public void setEducability(double educability) {
        this.educability = educability;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
