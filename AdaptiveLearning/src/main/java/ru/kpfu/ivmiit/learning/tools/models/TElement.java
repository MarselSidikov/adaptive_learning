package ru.kpfu.ivmiit.learning.tools.models;

/**
 * Created by Зульфат on 24.03.2015.
 */
public class TElement {
    private int studentID;
    private int lessonID;
    private double absTestResult;
    private double relTestResult;
    public void setStudentID(int studentID) {
        this.studentID = studentID;
    }
    public void setLessonID(int lessonID) {
        this.lessonID = lessonID;
    }
    public void setAbsTestResult (double absTestResult) {
        this.absTestResult = absTestResult;
    }
    public void setRelTestResult (double relTestResult) {
        this.relTestResult = relTestResult;
    }
    public int getStudentID  () {
        return studentID;
    }
    public int getLessonID () {
        return lessonID;
    }
    public double getAbsTestResult () {
        return absTestResult;
    }
    public double getRelTestResult () {
        return relTestResult;
    }
    public TElement(int studentID,int lessonID, double absTestResult, double relTestResult) {
        this.relTestResult = relTestResult;
        this.absTestResult = absTestResult;
        this.lessonID = lessonID;
        this.studentID = studentID;

    }
}
