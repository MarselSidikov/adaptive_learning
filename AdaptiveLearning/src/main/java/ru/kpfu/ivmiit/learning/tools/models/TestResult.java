package ru.kpfu.ivmiit.learning.tools.models;

import java.util.*;

/**
 * Created by Зульфат on 30.03.2015.
 */
public class TestResult {

    private int block;
    private int mark;

    public int getBlock() {
        return block;
    }

    public void setBlock(int block) {
        this.block = block;
    }

    public int getMark() {
        return mark;
    }

    public void setMark(int mark) {
        this.mark = mark;
    }

    public TestResult(int block, int mark) {

        this.block = block;
        this.mark = mark;
    }
}
