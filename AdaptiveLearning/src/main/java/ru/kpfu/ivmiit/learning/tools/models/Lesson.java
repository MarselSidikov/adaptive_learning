package ru.kpfu.ivmiit.learning.tools.models;

import java.util.List;

/**
 * Created by ������ on 30.03.2015.
 */
public class Lesson {
    private int id;
    private String mainMatUrl;
    private String extraMatUrl;
    private List<String> blockURLs;
    private List<Integer> blockTestsIds;
    private List<Integer> mainTestIds;
    private int nextLesson;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Integer> getMainTestIds() {
        return mainTestIds;
    }

    public void setMainTestIds(List<Integer> mainTestIds) {
        this.mainTestIds = mainTestIds;
    }

    public int getNextLesson() {
        return nextLesson;
    }

    public void setNextLesson(int nextLesson) {
        this.nextLesson = nextLesson;
    }

    public Lesson(String mainMatUrl, String extraMatUrl, List<String> blockURLs, List<Integer> blockTestsIDs) {
        this.mainMatUrl = mainMatUrl;
        this.extraMatUrl = extraMatUrl;
        this.blockURLs = blockURLs;
        this.blockTestsIds = blockTestsIDs;
    }

    public Lesson(String mainMatURL, String extraMatURL) {

        this.mainMatUrl = mainMatURL;
        this.extraMatUrl = extraMatURL;
    }

    public String getMainMatUrl() {
        return mainMatUrl;
    }

    public void setMainMatUrl(String mainMatUrl) {
        this.mainMatUrl = mainMatUrl;
    }

    public List<String> getBlockURLs() {
        return blockURLs;
    }

    public void setBlockURLs(List<String> blockURLs) {
        this.blockURLs = blockURLs;
    }

    public String getExtraMatUrl() {
        return extraMatUrl;
    }

    public void setExtraMatUrl(String extraMatUrl) {
        this.extraMatUrl = extraMatUrl;
    }

    public List<Integer> getBlockTestsIds() {
        return blockTestsIds;
    }

    public void setBlockTestsIds(List<Integer> blockTestsIds) {
        this.blockTestsIds = blockTestsIds;
    }
}
