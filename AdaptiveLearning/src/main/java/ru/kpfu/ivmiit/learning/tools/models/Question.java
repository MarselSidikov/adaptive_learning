package ru.kpfu.ivmiit.learning.tools.models;

/**
 * Created by Зульфат on 30.03.2015.
 */
public class Question {
    private int id;
    private String question;
    private int correctAnswerID;
    private int block;

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

    public void setBlock(int block) {
        this.block = block;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Question(int id, String question, int block, int correctAnswerID) {
        this.id = id;
        this.question = question;
        this.block = block;
        this.correctAnswerID = correctAnswerID;
    }

    public Question(String question, int block) {

        this.question = question;
        this.block = block;
    }
}
