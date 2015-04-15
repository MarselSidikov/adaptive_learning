package ru.kpfu.ivmiit.learning.tools.core;

import ru.kpfu.ivmiit.learning.tools.models.Answer;
import ru.kpfu.ivmiit.learning.tools.models.Test;

import java.util.List;

/**
 * @author Marsel Sidikov (Kazan Federal University) and ZulfatMiftakhutdinov (Kazan Federal University)
 */
public interface TestProvider {

    /**
     *
     * @param results users previous test results
     * @param materialId
     * @throws java.lang.IllegalArgumentException if materialId is invalid
     * @return test for the current material
     */
    Test getTest(List<Integer> results,int materialId);


    /**
     *
     * @param answers for the test
     * @param userToken
     * @throws java.lang.IllegalArgumentException if userToken or answers are invalid
     * @return estimate of the test
     */
    int getResult(String userToken,Answer answers);
}
