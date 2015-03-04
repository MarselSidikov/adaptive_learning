package ru.kpfu.ivmiit.learning.tools;

import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import ru.kpfu.ivmiit.learning.tools.dao.UsersDao;
import ru.kpfu.ivmiit.learning.tools.models.LoginData;
import ru.kpfu.ivmiit.learning.tools.models.User;


import static junit.framework.TestCase.assertEquals;
import static org.mockito.Matchers.any;

import static org.mockito.Mockito.stubVoid;
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
    String testLogin = new String();
    String testUserToken = new String();
    User testSignUp = new User();
    String testGetProfile = new String();

	@Before
	public void setUp() throws Exception {

		// создала объект, привязала зависимость
		serviceFacade = new AdaptiveLearningServiceFacadeImpl();
		serviceFacade.usersDao = usersDao;

		// пишешь временную реализацию:
		// когда у usersDao вызывается метод login на testLoginData мы должны вернуть 12345
		when(usersDao.login(testLoginData)).thenReturn("12345");
        // когда у usersDao вызывается метод checkLogin на testLogin мы должны вернуть true
        when(usersDao.checkLogin(testLogin)).thenReturn(true);
        // когда у usersDao вызывается метод logout на testUserToken мы должны вернуть id
        when(usersDao.logout(testUserToken)).thenReturn();
        // когда у usersDao вызывается метод signUp на testSignUp мы должны вернуть Name
        when(usersDao.signUp(testSignUp)).thenReturn("Name");
        // когда у usersDao вызывается метод getProfile на testGetProfile мы должны вернуть null
        when(usersDao.getProfile(testGetProfile)).thenReturn(null);
	}

	// тест
	@Test
	public void testLogin() throws Exception {
		// получили данные от фасада
		String actual = serviceFacade.login(testLoginData);

		// проверяем возвращенный результат
		assertEquals("12345", actual);
	}
    @Test
    public  void testCheckLogin() throws Exception {
        Boolean actual = serviceFacade.checkLogin(testLogin);

        assertEquals((Object) true, actual);
    }
    @Test
    public  void testLogOut() throws Exception {
        String actual = serviceFacade.logout(testUserToken);

        assertEquals("12345", actual);
    }
    @Test
    public  void testSignUp() throws Exception {
        String actual = serviceFacade.signUp(testSignUp);

        assertEquals("name", actual);
    }
    @Test
    public  void testGetProfile() throws Exception {
        User actual = serviceFacade.getProfile(testGetProfile);

        assertEquals(null, actual);
    }

}