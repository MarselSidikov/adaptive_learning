package ru.kpfu.ivmiit.learning.tools.dao;

import ru.kpfu.ivmiit.learning.tools.models.Question;

/**
 * Created by Зульфат on 30.03.2015.
 */
public interface QuestionsDAO {
    public Question getQuestionWithID (int id);
}
