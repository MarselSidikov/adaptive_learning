package ru.kpfu.ivmiit.learning.tools.models;

/**
 * Created by ������ on 30.03.2015.
 */
public class StudentAnswers {
    private int id;
    private int questionID;
    private int testResultID;
    private boolean isCorrect;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuestionID() {
        return questionID;
    }

    public void setQuestionID(int questionID) {
        this.questionID = questionID;
    }

    public int getTestResultID() {
        return testResultID;
    }

    public void setTestResultID(int testResultID) {
        this.testResultID = testResultID;
    }

    public void setCorrect(boolean isCorrect) {
        this.isCorrect = isCorrect;
    }

    public StudentAnswers(boolean isCorrect) {
        this.isCorrect = isCorrect;
    }

    public boolean isCorrect() {
        return isCorrect;
    }

    public void setIsCorrect(boolean isCorrect) {
        this.isCorrect = isCorrect;
    }

}
