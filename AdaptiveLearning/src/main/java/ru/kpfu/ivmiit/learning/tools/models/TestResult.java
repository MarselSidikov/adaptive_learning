package ru.kpfu.ivmiit.learning.tools.models;

import java.util.*;

/**
 * Created by Зульфат on 30.03.2015.
 */
public class TestResult {
    private int lessonID;
    private int studentID;
    private int mark;
    private List<StudentAnswers> answers;

    public int getLessonID() {
        return lessonID;
    }

    public void setLessonID(int lessonID) {
        this.lessonID = lessonID;
    }

    public int getStudentID() {
        return studentID;
    }

    public void setStudentID(int studentID) {
        this.studentID = studentID;
    }

    public List<StudentAnswers> getAnswers() {
        return answers;
    }

    public void setAnswers(List<StudentAnswers> answers) {
        this.answers = answers;
    }

    public int getMark() {
        return mark;
    }

    public void setMark(int mark) {
        this.mark = mark;
    }

    public TestResult(int block, int mark) {

        this.mark = mark;
    }
}
