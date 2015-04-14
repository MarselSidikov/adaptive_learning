package ru.kpfu.ivmiit.learning.tools.dao;

import ru.kpfu.ivmiit.learning.tools.models.Question;
import ru.kpfu.ivmiit.learning.tools.models.TestResult;

/**
 * Created by Зульфат on 30.03.2015.
 */
public interface QuestionsDAO {
    public void checkIsCorrect(TestResult result);
}
