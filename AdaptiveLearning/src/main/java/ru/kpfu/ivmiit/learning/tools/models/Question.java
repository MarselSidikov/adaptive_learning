package ru.kpfu.ivmiit.learning.tools.models;

/**
 * Created by Зульфат on 30.03.2015.
 */
public class Question {
    private int id;
    private String question;
    private int correct_answer_id;
    private int block;

    public int getBlock() {
        return block;
    }

    public int getCorrect_answer_id() {
        return correct_answer_id;
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

    public Question(int id, String question, int block, int correct_answer_id) {
        this.id = id;
        this.question = question;
        this.block = block;
        this.correct_answer_id = correct_answer_id;
    }

    public Question(String question, int block) {

        this.question = question;
        this.block = block;
    }
}
