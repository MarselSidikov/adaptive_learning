package ru.kpfu.ivmiit.learning.tools.models;


/**
 * @author Ilnar Ramazanov (Kazan Federal University),Zulfat Miftahutdinov (Kazan Federal University)
 */
public class Answer {
    private int id;
    private String answerText;
    private int relatedQuestionID;

    public String getAnswerText() {
        return answerText;
    }

    public void setAnswerText(String answerText) {
        this.answerText = answerText;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRelatedQuestionID() {
        return relatedQuestionID;
    }

    public void setRelatedQuestionID(int relatedQuestionID) {
        this.relatedQuestionID = relatedQuestionID;
    }
}
