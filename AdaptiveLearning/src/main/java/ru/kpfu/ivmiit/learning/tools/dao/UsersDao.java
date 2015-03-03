package ru.kpfu.ivmiit.learning.tools.dao;
import java.util.Collection;

/**
 * @author Sidikov Marsel (Kazan Federal University)
 *
 */
public interface UsersDao<TestResults> {
    Collection<TestResults> getTestsResults();
    String getUserName ();
    String getUserSurname();
    void insertTestResults (int testId);
    Collection<MaterialsDao> getCurrentMaterials();
}
