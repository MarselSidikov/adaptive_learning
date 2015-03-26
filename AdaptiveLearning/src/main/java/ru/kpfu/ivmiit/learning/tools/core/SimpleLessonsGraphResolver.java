package ru.kpfu.ivmiit.learning.tools.core;

import org.springframework.beans.factory.annotation.Autowired;
import ru.kpfu.ivmiit.learning.tools.dao.LessonsDao;
import ru.kpfu.ivmiit.learning.tools.models.Lesson;
import ru.kpfu.ivmiit.learning.tools.models.Result;

import java.util.List;
import java.util.Map;

/**
 * @author Sidikov Marsel, Igor Lebedenko (Kazan Federal University)
 *
 */
public class SimpleLessonsGraphResolver implements LessonsResolver {
    private double levels[];
    private LessonsDao lessonsDao;

    @Autowired
    public void setLessonsDao(LessonsDao lessonsDao) {
        this.lessonsDao = lessonsDao;
    }

    @Override
    public Lesson getLesson(int id) {
        return lessonsDao.getLesson(id);
    }

    @Override
    public int getAlternativeLesson(int id) {
        return lessonsDao.getAlternativeLesson(id);
    }

    public SimpleLessonsGraphResolver () {
        levels = new double[3];
        levels[0] = 0.55;
        levels[1] = 0.70;
        levels[2] = 0.85;
    }


    public int getNewLesson(List<Result> results) {
        double sumOfCorrectAnswerComplexities=0;
        double testComplexity=0;
        double mark;
        Map<Integer,List<String>> testResDict;
        for (Result testRes:results) {
            testResDict = testRes.getResults();
            for (int questionId:testResDict.keySet()) {
                if (Boolean.parseBoolean(testResDict.get(questionId).get(0)))
                    sumOfCorrectAnswerComplexities += Double.parseDouble(testResDict.get(questionId).get(1));
                testComplexity+=Double.parseDouble(testResDict.get(questionId).get(1));
            }
        }
        mark = sumOfCorrectAnswerComplexities/testComplexity;
        int level=0;
        while(mark>levels[level])
            level++;
        return lessonsDao.getLessonWithComplexity(new Double(level));
    }
}
