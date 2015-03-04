package ru.kpfu.ivmiit.learning.tools;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import ru.kpfu.ivmiit.learning.tools.dao.UsersDao;
import ru.kpfu.ivmiit.learning.tools.models.LoginData;


import static junit.framework.TestCase.assertEquals;
import static org.mockito.Matchers.any;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AdaptiveLearningServiceFacadeImplTest {

	// тестируемый объект
	AdaptiveLearningServiceFacadeImpl serviceFacade;

	// подменяемый интерфейс
	@Mock
	UsersDao usersDao;

	// тестовые данные
	LoginData testLoginData = new LoginData();

	@Before
	public void setUp() throws Exception {

		// создала объект, привязала зависимость
		serviceFacade = new AdaptiveLearningServiceFacadeImpl();
		serviceFacade.usersDao = usersDao;

		// пишешь временную реализацию:
		// когда у usersDao вызывается метод login на testLoginData мы должны вернуть 12345
		when(usersDao.login(testLoginData)).thenReturn("12345");
	}

	// тест
	@Test
	public void testLogin() throws Exception {
		// получили данные от фасада
		String actual = serviceFacade.login(testLoginData);

		// проверяем возвращенный результат
		assertEquals("12345", actual);
	}
}