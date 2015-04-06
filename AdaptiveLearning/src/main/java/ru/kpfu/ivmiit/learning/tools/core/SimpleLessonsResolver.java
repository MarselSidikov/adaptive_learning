package ru.kpfu.ivmiit.learning.tools.core;

import breeze.optimize.linear.LinearProgram;
import org.apache.spark.mllib.recommendation.Rating;
import org.springframework.beans.factory.annotation.Autowired;
import ru.kpfu.ivmiit.learning.tools.dao.LessonsDao;
import ru.kpfu.ivmiit.learning.tools.models.Lesson;
import ru.kpfu.ivmiit.learning.tools.models.Result;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Sidikov Marsel, Igor Lebedenko (Kazan Federal University)
 *
 */
public class SimpleLessonsResolver implements LessonsResolver {
    private RatingsTableInterface rti;
    private LessonsDao lessonsDao;

    @Autowired
    public void setLessonsDao(LessonsDao lessonsDao) {
        this.lessonsDao = lessonsDao;
    }


    @Autowired
    public void setRti (RatingsTableInterface rti) {
        this.rti = rti;
    }


    @Override
    public Lesson getLesson(int id) {
        return lessonsDao.getLesson(id);
    }

    @Override
    public int getAlternativeLesson(int id) {
        return lessonsDao.getAlternativeLesson(id);
    }

    public int getNewLessonForStudent(int studentID, List<Integer> learnedLessons) {
        int lessonID=0;
        //first optimizing by relative test results(mark)
        List<Integer> availableLessons = lessonsDao.getAvailableLessons(learnedLessons);
        List<Rating> studentRelResults = rti.getRelRatingForStudent(studentID);
        List<Rating> highMark = new LinkedList<Rating>();
        List<Rating> middleMark = new LinkedList<Rating>();
        List<Rating> lowMark = new LinkedList<Rating>();
        List<Rating> optAbsRatings;
        for (Rating r:studentRelResults) {
            if (r.rating()>=0.9) {
                highMark.add(r);
            }
            if (r.rating()<0.9 && r.rating()>=0.75) {
                middleMark.add(r);
            }
            if (r.rating()<0.75) {
                lowMark.add(r);
            }
        }
        //searching in highMarks
        if (highMark.size()!=0) {
            optAbsRatings = highMark;
        } else if (middleMark.size()!=0) {
            optAbsRatings = middleMark;
        } else {
            optAbsRatings = lowMark;
        }
        return lessonID;
    }
}
