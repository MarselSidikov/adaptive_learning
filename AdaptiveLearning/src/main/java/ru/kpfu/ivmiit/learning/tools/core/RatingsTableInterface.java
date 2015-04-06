package ru.kpfu.ivmiit.learning.tools.core;
import org.apache.spark.mllib.recommendation.Rating;
import ru.kpfu.ivmiit.learning.tools.models.Result;
import java.util.List;

/**
 * Created by Зульфат on 06.04.2015.
 */
public interface RatingsTableInterface {
    public void AddResult (Result result);
    public List<Rating> getABSRatingForSudent(int studentID);
    public List<Rating> getRelRatingForStudent(int studentID);
}
