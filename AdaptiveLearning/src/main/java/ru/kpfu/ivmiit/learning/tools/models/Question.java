package ru.kpfu.ivmiit.learning.tools.models;

/**
 * Created by Зульфат on 30.03.2015.
 */
public class Question {
    private int id;
    private String question;
    private int correctAnswerID;

    public int getBlock() {
        return block;
    }

    public int getCorrectAnswerID() {
        return correctAnswerID;
    }

    public String getQuestion() {
        return question;
    }

    public int getId() {
        return id;
    }

    private int block;

}
