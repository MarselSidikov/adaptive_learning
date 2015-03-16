package ru.kpfu.ivmiit.learning.tools.core;

import ru.kpfu.ivmiit.learning.tools.models.Answers;
import ru.kpfu.ivmiit.learning.tools.models.Result;
import ru.kpfu.ivmiit.learning.tools.models.Test;

import java.util.List;

/**
 * @author Marsel Sidikov (Kazan Federal University)
 */
public class TestProviderImpl implements TestProvider {

    @Override
    public Test getTest(List<Result> results, int lessonId) {
        return null;
    }

    @Override
    public Result getResult(String userToken, Answers answers) {
        return null;
    }
}
