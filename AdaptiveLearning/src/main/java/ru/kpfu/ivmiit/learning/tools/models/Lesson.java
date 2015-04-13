package ru.kpfu.ivmiit.learning.tools.models;

/**
 * @author Ilnar Ramazanov (Kazan Federal University), Zulfat Miftahutdinoz (Kazan Federal University)
 */
public class Lesson {
    private int id;
    private String coveredTopics;
    private String name;
    private int complexity;
    private String lessonsMLB;
    private int lesson;
    private int alternativeLessonIDS;


    public String getCoveredTopics() {
        return coveredTopics;
    }

    public void setCoveredTopics(String coveredTopics) {
        this.coveredTopics = coveredTopics;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getComplexity() {
        return complexity;
    }

    public void setComplexity(int complexity) {
        this.complexity = complexity;
    }

    public String getLessonsMLB() {
        return lessonsMLB;
    }

    public void setLessonsMLB(String lessonsMLB) {
        this.lessonsMLB = lessonsMLB;
    }

    public int getLesson() {
        return lesson;
    }

    public void setLesson(int lesson) {
        this.lesson = lesson;
    }

    public int getAlternativeLessonIDS() {
        return alternativeLessonIDS;
    }

    public void setAlternativeLessonIDS(int alternativeLessonIDS) {
        this.alternativeLessonIDS = alternativeLessonIDS;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
