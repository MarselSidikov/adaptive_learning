package ru.kpfu.ivmiit.learning.tools.models;

/**
 * Created by User on 30.03.2015.
 */
public class Answer {
    private int id;
    private int relQuestionID;
    private String answer;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRelQuestionID() {
        return relQuestionID;
    }

    public void setRelQuestionID(int relQuestionID) {
        this.relQuestionID = relQuestionID;
    }



    public Answer(String answer) {
        this.answer = answer;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
