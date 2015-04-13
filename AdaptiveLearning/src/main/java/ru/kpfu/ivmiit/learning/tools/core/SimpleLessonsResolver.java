package ru.kpfu.ivmiit.learning.tools.core;

import breeze.optimize.linear.LinearProgram;
import org.apache.spark.mllib.recommendation.Rating;
import org.springframework.beans.factory.annotation.Autowired;
import ru.kpfu.ivmiit.learning.tools.dao.LessonsDao;
import ru.kpfu.ivmiit.learning.tools.models.Lesson;
import ru.kpfu.ivmiit.learning.tools.models.Result;
import scala.Tuple2;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Sidikov Marsel, Igor Lebedenko, Zulfat Mifathutdinov (Kazan Federal University)
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
        List<Tuple2<Tuple2<Integer,Integer>,Double>> studentRelResults = rti.getRelPredictedRatingsForStudent(studentID);
        List<Tuple2<Tuple2<Integer,Integer>,Double>> highMark = new LinkedList<Tuple2<Tuple2<Integer,Integer>,Double>>();
        List<Tuple2<Tuple2<Integer,Integer>,Double>> middleMark = new LinkedList<Tuple2<Tuple2<Integer,Integer>,Double>>();
        List<Tuple2<Tuple2<Integer,Integer>,Double>> lowMark = new LinkedList<Tuple2<Tuple2<Integer,Integer>,Double>>();
        List<Tuple2<Tuple2<Integer,Integer>,Double>> optAbsRatings;
        for (Tuple2<Tuple2<Integer,Integer>,Double> r:studentRelResults) {
            if (r._2()>=0.9 && availableLessons.contains(r._1()._2())) {
                highMark.add(r);
            }
            if (r._2()<0.9 && r._2()>=0.75 && availableLessons.contains(r._1()._2())) {
                middleMark.add(r);
            }
            if (r._2()<0.75 && availableLessons.contains(r._1()._2())) {
                lowMark.add(r);
            }
        }
        //choosing one
        if (highMark.size()!=0) {
            optAbsRatings = highMark;
        } else if (middleMark.size()!=0) {
            optAbsRatings = middleMark;
        } else {
            optAbsRatings = lowMark;
        }
        List<Tuple2<Tuple2<Integer,Integer>,Double>> studentAbsResults = rti.getABSPredictedRatingsForStudent(studentID);
        Iterator<Tuple2<Tuple2<Integer,Integer>,Double>> iter = optAbsRatings.iterator();
        Tuple2<Tuple2<Integer,Integer>,Double> element;
        while (lessonID==0 && iter.hasNext()) {
            element = iter.next();
            if (optAbsRatings.contains(element)) {
                lessonID = element._1()._2();
            }
        }
        return lessonID;
    }
}
