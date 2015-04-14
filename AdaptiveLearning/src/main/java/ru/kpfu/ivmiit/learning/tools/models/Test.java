package ru.kpfu.ivmiit.learning.tools.models;

import java.util.List;

/**
 * Created by Зульфат on 14.04.2015.
 */
public class Test {
    private int id;
    private List<Question> questions;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }
}
