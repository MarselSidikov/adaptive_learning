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


import static junit.framework.Assert.assertTrue;
import static junit.framework.TestCase.assertEquals;
import static org.mockito.Matchers.any;

import static org.mockito.Mockito.stubVoid;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AdaptiveLearningServiceFacadeImplTest {

	AdaptiveLearningServiceFacadeImpl serviceFacade;

	@Mock
	UsersDao usersDao;

    // TODO: refactor, when models was created more correctly
	LoginData testLoginData = new LoginData();
    String testLogin = new String();
    String testUserToken = "userToken";
    User testSignUp = new User();
    String testGetProfile = new String();

	@Before
	public void setUp() throws Exception {

        // TODO: refactor for Spring usage
		serviceFacade = new AdaptiveLearningServiceFacadeImpl();
		serviceFacade.setUsersDao(usersDao);

        // TODO: refactor with throws
		when(usersDao.login(testLoginData)).thenReturn("12345");

        when(usersDao.checkLogin(testLogin)).thenReturn(true);

        when(usersDao.signUp(testSignUp)).thenReturn("Name");

        when(usersDao.getProfile(testGetProfile)).thenReturn(null);
	}


	@Test
	public void testLogin() throws Exception {
		String actual = serviceFacade.login(testLoginData);
		assertEquals("12345", actual);
	}
    @Test
    public  void testCheckLogin() throws Exception {
        assertTrue(serviceFacade.checkLogin(testLogin));
    }
    @Test
    public  void testLogOut() throws Exception {
        serviceFacade.logout(testUserToken);
        verify(usersDao).logout(testUserToken);
    }
    @Test
    public  void testSignUp() throws Exception {
        String actual = serviceFacade.signUp(testSignUp);
        assertEquals("Name", actual);
    }
    @Test
    public  void testGetProfile() throws Exception {
        User actual = serviceFacade.getProfile(testGetProfile);
        assertEquals(null, actual);
    }

}