package ru.kpfu.ivmiit.learning.tools.models;

/**
 * Created by Ильнар on 30.03.2015.
 */
public class StudentAnswers {
    boolean isCorrect;

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
