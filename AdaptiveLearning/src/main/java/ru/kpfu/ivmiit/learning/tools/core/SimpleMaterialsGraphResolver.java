package ru.kpfu.ivmiit.learning.tools.core;

import ru.kpfu.ivmiit.learning.tools.dao.MaterialsDao;
import ru.kpfu.ivmiit.learning.tools.dao.QuestionsDAO;
import ru.kpfu.ivmiit.learning.tools.dao.UsersDao;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;
import ru.kpfu.ivmiit.learning.tools.models.TestResult;
/**
 * @author Zulfat Miftakhutdinov, Igor Lebedenko (Kazan Federal University)
 *
 */
public class SimpleMaterialsGraphResolver implements MaterialsResolver {
    private MaterialsDao materialsDao;
    private QuestionsDAO questionsDAO;
    private UsersDao usersDao;

    @Autowired
    public void setMaterialsDao(MaterialsDao materialsDao) {
        this.materialsDao = materialsDao;
    }

    @Autowired
    public void setQuestionsDAO(QuestionsDAO questionsDAO) {
        this.questionsDAO = questionsDAO;
    }

    @Autowired
    public void setUsersDao(UsersDao usersDao) {
        this.usersDao = usersDao;
    }

    @Override


    public String getAlternativeMaterial(int lessonID) {
        return materialsDao.getAlternativeMaterial(lessonID);
    }


    public String getNewMaterial(TestResult result) {
        String URLs = new String();
        int lessonID = result.getLessonID();
        String userToken = result.getUserToken();
        String blockURLs = materialsDao.getBlockURLs(lessonID);
        String[] blockURLsList = blockURLs.split(";");
        int questionBlock;
        Map<Integer,Boolean> resultDictionary = result.getResult();
        for (Integer questionId:resultDictionary.keySet()) {
            if (!resultDictionary.get(questionId)) {
                questionBlock = questionsDAO.getQuestionWithID(questionId).getBlock();
                URLs = URLs + blockURLsList[questionBlock] + ';';
            }
        }
        if (URLs!=null)
            return URLs;
        int nextLesson = materialsDao.getNextLesson(lessonID);
        usersDao.setLessonID(userToken,nextLesson);
        return materialsDao.getMainURL(nextLesson);
    }


}
