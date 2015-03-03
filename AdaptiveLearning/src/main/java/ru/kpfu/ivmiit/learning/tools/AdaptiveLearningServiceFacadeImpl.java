package ru.kpfu.ivmiit.learning.tools;

import ru.kpfu.ivmiit.learning.tools.core.MaterialsResolver;
import ru.kpfu.ivmiit.learning.tools.core.TestProvider;
import ru.kpfu.ivmiit.learning.tools.dao.UsersDao;
import ru.kpfu.ivmiit.learning.tools.models.*;

import java.util.List;

/**
 * @author Marsel Sidikov (Kazan Federal University) and ZulfatMiftakhutdinov (Kazan Federal University)
 */
public class AdaptiveLearningServiceFacadeImpl implements AdaptiveLearningServiceFacade {

	UsersDao usersDao;

	MaterialsResolver materialsResolver;

	TestProvider testProvider;

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
    public Material getMaterial(int id, String userToken) {
        List<Integer> materialIds = usersDao.getMaterials(userToken);
        if (materialIds.contains(id)) {
            return materialsResolver.getMaterial(id);
        } else throw new IllegalArgumentException();
    }

    @Override
    public boolean changeMaterial(int id, String userToken) {
        int alternativeMaterialId = materialsResolver.getAlternativeMaterial(id);
        if (alternativeMaterialId != -1) {
            usersDao.changeCurrentMaterial(userToken, alternativeMaterialId);
            return true;
        }
        return false;
    }

    @Override
	public Test getTest(String userToken) {
        List<Integer> userResults = usersDao.getAllResults(userToken);
        return testProvider.getTest(userResults);
	}

	@Override
	public void answersSubmit(String userToken, Answers answers) {
        int result = testProvider.getResult(userToken, answers);
        List<Integer> oldUserResults = usersDao.getAllResults(userToken);
        int newMaterial = materialsResolver.getNewMaterial(result, oldUserResults);
        usersDao.answersSubmit(userToken, result);
        usersDao.addNewMaterial(userToken, newMaterial);
	}
}
