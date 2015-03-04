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

	AdaptiveLearningServiceFacadeImpl serviceFacade;

	@Mock
	UsersDao usersDao;

	LoginData testLoginData = new LoginData();

	@Before
	public void setUp() throws Exception {
		serviceFacade = new AdaptiveLearningServiceFacadeImpl();
		serviceFacade.usersDao = usersDao;

		when(usersDao.login(any(LoginData.class))).thenThrow(IllegalArgumentException.class);
				when(usersDao.login(testLoginData)).thenReturn("12345");
	}

	@Test
	public void testLogin() throws Exception {
		String actual = serviceFacade.login(testLoginData);
		verify(usersDao.login(testLoginData));

		assertEquals("12345", actual);
	}
}