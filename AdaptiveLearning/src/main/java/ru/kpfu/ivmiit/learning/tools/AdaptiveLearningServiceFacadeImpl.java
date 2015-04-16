package ru.kpfu.ivmiit.learning.tools;

import ru.kpfu.ivmiit.learning.tools.core.MaterialsResolver;
import ru.kpfu.ivmiit.learning.tools.core.TestProvider;
import ru.kpfu.ivmiit.learning.tools.dao.QuestionsDAO;
import ru.kpfu.ivmiit.learning.tools.dao.UsersDao;
import ru.kpfu.ivmiit.learning.tools.models.*;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Marsel Sidikov, ZulfatMiftakhutdinov, Igor Lebedenko (Kazan Federal University)
 */
public class AdaptiveLearningServiceFacadeImpl implements AdaptiveLearningServiceFacade {

	UsersDao usersDao;

	private MaterialsResolver materialsResolver;

	private TestProvider testProvider;

    private QuestionsDAO questionsDAO;

    @Autowired
    public void setQuestionsDAO (QuestionsDAO questionsDAO) {
        this.questionsDAO = questionsDAO;
    }

    @Autowired
    public void setUsersDao(UsersDao usersDao) {
        this.usersDao = usersDao;
    }

    @Autowired
    public void setMaterialsResolver(MaterialsResolver materialsResolver) {
        this.materialsResolver = materialsResolver;
    }

    @Autowired
    public void setTestProvider(TestProvider testProvider) {
        this.testProvider = testProvider;
    }

    @Override
	public String login(LoginData data) {
		return usersDao.login(data);
	}

	@Override
	public boolean checkLogin(String login) {
		return usersDao.checkLogin(login);
	}

	@Override
	public void logout(String userToken) {
		usersDao.logout(userToken);
	}

	@Override
	public String signUp(User user) {
		return usersDao.signUp(user);
	}

	@Override
	public User getProfile(String userToken) {
		return usersDao.getProfile(userToken);
	}

    @Override
    public Material getMaterial(String userToken) {
        String currentURLs = usersDao.getCurrentURLs(userToken);
        String [] URLs = currentURLs.split(";");
        List<String> URLsList = new LinkedList<String>();
        for (String url:URLs) {
            URLsList.add(url);
        }
        return new Material(URLsList);
    }

    @Override
    public boolean changeMaterial( String userToken) {
        int currentLessonID = usersDao.getCurrentLessonID(userToken);
        String alternativeMaterialURL = materialsResolver.getAlternativeMaterial(currentLessonID);
        if (alternativeMaterialURL!=null) {
            usersDao.setCurrentURLs( userToken, alternativeMaterialURL);
            return true;
        }
        return false;
    }

    @Override
	public Test getTest(String userToken) {
        List<Integer> userResults = usersDao.getAllResults(userToken);
        int currentMaterialId = usersDao.getMaterials(userToken).get(0);
        return testProvider.getTest(userResults,currentMaterialId);
	}

	@Override
	public void answersSubmit(String userToken, TestResult results) {
        questionsDAO.checkIsCorrect(results);
        String currentURLs = materialsResolver.getNewMaterial(results);
        usersDao.answersSubmit(userToken,results);
        usersDao.setCurrentURLs(userToken,currentURLs);
	}
}
