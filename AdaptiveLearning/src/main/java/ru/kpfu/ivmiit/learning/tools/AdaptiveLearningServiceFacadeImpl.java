package ru.kpfu.ivmiit.learning.tools;

import ru.kpfu.ivmiit.learning.tools.core.MaterialsResolver;
import ru.kpfu.ivmiit.learning.tools.core.TestProvider;
import ru.kpfu.ivmiit.learning.tools.dao.UsersDao;
import ru.kpfu.ivmiit.learning.tools.models.Answers;
import ru.kpfu.ivmiit.learning.tools.models.LoginData;
import ru.kpfu.ivmiit.learning.tools.models.Test;
import ru.kpfu.ivmiit.learning.tools.models.User;

/**
 * @author Marsel Sidikov (Kazan Federal University)
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
		return null;
	}

	@Override
	public Test getTest(String userToken) {
		usersDao.get
		return testProvider.getTest()
	}

	@Override
	public void answersSubmit(String userToken, Answers answers) {

	}
}
