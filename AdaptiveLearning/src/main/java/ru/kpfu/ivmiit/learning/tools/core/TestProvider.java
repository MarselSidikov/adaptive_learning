package ru.kpfu.ivmiit.learning.tools.core;

import ru.kpfu.ivmiit.learning.tools.models.Answers;
import ru.kpfu.ivmiit.learning.tools.models.Test;

import java.util.List;

/**
 * @author Marsel Sidikov (Kazan Federal University) and ZulfatMiftakhutdinov (Kazan Federal University)
 */
public interface TestProvider {
    Test getTest(List<Integer> results);
    int getResult(String userToken,Answers answers);
}
