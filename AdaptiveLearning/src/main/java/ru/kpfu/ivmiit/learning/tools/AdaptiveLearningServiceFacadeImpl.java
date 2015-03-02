package ru.kpfu.ivmiit.learning.tools;

import ru.kpfu.ivmiit.learning.tools.models.Answers;
import ru.kpfu.ivmiit.learning.tools.models.LoginData;
import ru.kpfu.ivmiit.learning.tools.models.Test;
import ru.kpfu.ivmiit.learning.tools.models.User;

/**
 * @author Marsel Sidikov (Kazan Federal University)
 */
public class AdaptiveLearningServiceFacadeImpl implements AdaptiveLearningServiceFacade {
	@Override
	public String login(LoginData data) {
		return null;
	}

	@Override
	public boolean checkLogin(String login) {
		return false;
	}

	@Override
	public void logout(String userToken) {

	}

	@Override
	public String signUp(User user) {
		return null;
	}

	@Override
	public User getProfile(String userToken) {
		return null;
	}

	@Override
	public Test getTest(String userToken) {
		return null;
	}

	@Override
	public void answersSubmit(String userToken, Answers answers) {

	}
}
