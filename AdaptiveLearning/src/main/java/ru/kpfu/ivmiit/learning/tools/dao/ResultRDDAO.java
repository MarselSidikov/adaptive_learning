package ru.kpfu.ivmiit.learning.tools.dao;

import org.apache.spark.api.java.JavaRDD;
import ru.kpfu.ivmiit.learning.tools.models.Result;
/**
 * Created by Зульфат on 24.03.2015.
 */
public interface ResultRDDAO {
    public JavaRDD<Result> gfh ();
}
