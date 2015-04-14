package ru.kpfu.ivmiit.learning.tools.models;

import java.util.List;

/**
 * Created by Зульфат on 30.03.2015.
 */
public class Question {
    private int id;
    private String question;
    private int correctAnswerId;
    private int block;
    private Answer correctAnswer;
    private List<Answer> answers;

    public void setCorrectAnswerId(int correctAnswerId) {
        this.correctAnswerId = correctAnswerId;
    }

    public Answer getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(Answer correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }

    public int getBlock() {
        return block;
    }

    public int getCorrectAnswerId() {
        return correctAnswerId;
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

    public Question(int id, String question, int block, int correctAnswerId) {
        this.id = id;
        this.question = question;
        this.block = block;
        this.correctAnswerId = correctAnswerId;
    }

    public Question(String question, int block) {

        this.question = question;
        this.block = block;
    }
}
