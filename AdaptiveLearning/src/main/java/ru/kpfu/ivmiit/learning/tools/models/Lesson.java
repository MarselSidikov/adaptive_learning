package ru.kpfu.ivmiit.learning.tools.models;

import java.util.List;

/**
 * Created by Ильнар on 30.03.2015.
 */
public class Lesson {
    private String mainMatURL;
    private String extraMatURL;
    private List<String> blockURLs;
    private int[] blockTestsIDs;

    public Lesson(String mainMatURL, String extraMatURL, List<String> blockURLs, int[] blockTestsIDs) {
        this.mainMatURL = mainMatURL;
        this.extraMatURL = extraMatURL;
        this.blockURLs = blockURLs;
        this.blockTestsIDs = blockTestsIDs;
    }

    public Lesson(String mainMatURL, String extraMatURL) {

        this.mainMatURL = mainMatURL;
        this.extraMatURL = extraMatURL;
    }

    public String getMainMatURL() {
        return mainMatURL;
    }

    public void setMainMatURL(String mainMatURL) {
        this.mainMatURL = mainMatURL;
    }

    public List<String> getBlockURLs() {
        return blockURLs;
    }

    public void setBlockURLs(List<String> blockURLs) {
        this.blockURLs = blockURLs;
    }

    public String getExtraMatURL() {
        return extraMatURL;
    }

    public void setExtraMatURL(String extraMatURL) {
        this.extraMatURL = extraMatURL;
    }

    public int[] getBlockTestsIDs() {
        return blockTestsIDs;
    }

    public void setBlockTestsIDs(int[] blockTestsIDs) {
        this.blockTestsIDs = blockTestsIDs;
    }
}
