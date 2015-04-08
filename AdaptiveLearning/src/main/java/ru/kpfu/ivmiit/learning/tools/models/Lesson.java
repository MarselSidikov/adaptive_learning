package ru.kpfu.ivmiit.learning.tools.models;

import java.util.List;

/**
 * Created by Ильнар on 30.03.2015.
 */
public class Lesson {
    private String main_mat_url;
    private String extra_mat_url;
    private List<String> blockURLs;
    private int[] block_tests_ids;

    public Lesson(String main_mat_url, String extra_mat_url, List<String> blockURLs, int[] blockTestsIDs) {
        this.main_mat_url = main_mat_url;
        this.extra_mat_url = extra_mat_url;
        this.blockURLs = blockURLs;
        this.block_tests_ids = blockTestsIDs;
    }

    public Lesson(String mainMatURL, String extraMatURL) {

        this.main_mat_url = mainMatURL;
        this.extra_mat_url = extraMatURL;
    }

    public String getMain_mat_url() {
        return main_mat_url;
    }

    public void setMain_mat_url(String main_mat_url) {
        this.main_mat_url = main_mat_url;
    }

    public List<String> getBlockURLs() {
        return blockURLs;
    }

    public void setBlockURLs(List<String> blockURLs) {
        this.blockURLs = blockURLs;
    }

    public String getExtra_mat_url() {
        return extra_mat_url;
    }

    public void setExtra_mat_url(String extra_mat_url) {
        this.extra_mat_url = extra_mat_url;
    }

    public int[] getBlock_tests_ids() {
        return block_tests_ids;
    }

    public void setBlock_tests_ids(int[] block_tests_ids) {
        this.block_tests_ids = block_tests_ids;
    }
}
