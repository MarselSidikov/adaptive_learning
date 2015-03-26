package ru.kpfu.ivmiit.learning.tools.core;

import ru.kpfu.ivmiit.learning.tools.models.Answers;
import ru.kpfu.ivmiit.learning.tools.models.Result;
import ru.kpfu.ivmiit.learning.tools.models.Test;

import java.util.List;

/**
 * @author Marsel Sidikov (Kazan Federal University) and ZulfatMiftakhutdinov (Kazan Federal University)
 */
public interface TestProvider {

    /**
     *
     * @param results users previous test results
     * @param lessonId
     * @throws java.lang.IllegalArgumentException if lessonId is invalid
     * @return test for the current material also based on last test results
     */
    Test getTest(List<Result> results,int lessonId);


    /**
     *
     * @param answers for the test
     * @param userToken
     * @throws java.lang.IllegalArgumentException if userToken or answers are invalid
     * @return estimate of the test
     */
    Result getResult(String userToken,Result answers);
}
