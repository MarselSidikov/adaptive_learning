package ru.kpfu.ivmiit.learning.tools;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import ru.kpfu.ivmiit.learning.tools.dao.StudentsDao;
import ru.kpfu.ivmiit.learning.tools.models.LoginData;
import ru.kpfu.ivmiit.learning.tools.models.Student;


import static junit.framework.Assert.assertTrue;
import static junit.framework.TestCase.assertEquals;
import static org.mockito.Matchers.any;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AdaptiveLearningServiceFacadeImplTest {

	AdaptiveLearningServiceFacadeImpl serviceFacade;

	@Mock
    StudentsDao studentsDao;

    // TODO: refactor, when models was created more correctly
	LoginData testLoginData = new LoginData();
    String testLogin = new String();
    String testUserToken = "userToken";
    Student testSignUp = new Student();
    String testGetProfile = new String();

	@Before
	public void setUp() throws Exception {

        // TODO: refactor for Spring usage
		serviceFacade = new AdaptiveLearningServiceFacadeImpl();
		serviceFacade.setStudentsDao(studentsDao);

        // TODO: refactor with throws
		when(studentsDao.login(testLoginData)).thenReturn("12345");

        when(studentsDao.checkLogin(testLogin)).thenReturn(true);

        when(studentsDao.signUp(testSignUp)).thenReturn("Name");

        when(studentsDao.getProfile(testGetProfile)).thenReturn(null);
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
        verify(studentsDao).logout(testUserToken);
    }
    @Test
    public  void testSignUp() throws Exception {
        String actual = serviceFacade.signUp(testSignUp);
        assertEquals("Name", actual);
    }
    @Test
    public  void testGetProfile() throws Exception {
        Student actual = serviceFacade.getProfile(testGetProfile);
        assertEquals(null, actual);
    }

}