package ru.kpfu.ivmiit.learning.tools;

import ru.kpfu.ivmiit.learning.tools.core.LessonsResolver;
import ru.kpfu.ivmiit.learning.tools.core.TestProvider;
import ru.kpfu.ivmiit.learning.tools.dao.StudentsDao;
import ru.kpfu.ivmiit.learning.tools.models.*;

import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

/**
 * @author Marsel Sidikov, ZulfatMiftakhutdinov, Igor Lebedenko (Kazan Federal University)
 */
public class AdaptiveLearningServiceFacadeImpl implements AdaptiveLearningServiceFacade {

	private StudentsDao studentsDao;

	private LessonsResolver lessonsResolver;

	private TestProvider testProvider;

    @Autowired
    public void setStudentsDao(StudentsDao studentsDao) {
        this.studentsDao = studentsDao;
    }

    @Autowired
    public void setLessonsResolver(LessonsResolver lessonsResolver) {
        this.lessonsResolver = lessonsResolver;
    }

    @Autowired
    public void setTestProvider(TestProvider testProvider) {
        this.testProvider = testProvider;
    }

    @Override
	public String login(LoginData data) {
		return studentsDao.login(data);
	}

	@Override
	public boolean checkLogin(String login) {
		return studentsDao.checkLogin(login);
	}

	@Override
	public void logout(String userToken) {
		studentsDao.logout(userToken);
	}

	@Override
	public String signUp(Student student) {
		return studentsDao.signUp(student);
	}

	@Override
	public Student getProfile(String userToken) {
		return studentsDao.getProfile(userToken);
	}

    @Override
    public Lesson getMaterial(int id, String userToken) {
        List<Integer> materialIds = studentsDao.getLessons(userToken);
        if (materialIds.contains(id)) {
            return lessonsResolver.getMaterial(id);
        } else throw new IllegalArgumentException();
    }

    @Override
    public boolean changeMaterial(int id, String userToken) {
        int alternativeMaterialId = lessonsResolver.getAlternativeLesson(id);
        if (alternativeMaterialId != -1) {
            studentsDao.changeCurrentLesson(userToken, alternativeMaterialId);
            return true;
        }
        return false;
    }

    @Override
	public Test getTest(String userToken) {
        List<Result> userResults = studentsDao.getAllResults(userToken);
        int currentMaterialId = studentsDao.getLessons(userToken).get(0);
        return testProvider.getTest(userResults,currentMaterialId);
	}

	@Override
	public void answersSubmit(String userToken, Answers answers) {
        Result result = testProvider.getResult(userToken, answers);
        List<Result> oldUserResults = studentsDao.getAllResults(userToken);
        int newLesson = lessonsResolver.getNewLesson(oldUserResults);
        studentsDao.answersSubmit(userToken, result);
        studentsDao.addNewLesson(userToken, newLesson);
	}
}
