package ru.kpfu.ivmiit.learning.tools;

import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import ru.kpfu.ivmiit.learning.tools.dao.UsersDao;
import ru.kpfu.ivmiit.learning.tools.models.LoginData;
import ru.kpfu.ivmiit.learning.tools.models.Material;

import ru.kpfu.ivmiit.learning.tools.models.User;



import static junit.framework.TestCase.assertEquals;
import static org.mockito.Matchers.any;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.times;

@RunWith(MockitoJUnitRunner.class)
public class AdaptiveLearningServiceFacadeImplSequenceTest {
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

    LoginData testLoginDataOnIncorrectData = new LoginData();
    String testLoginOnIncorrectData = new String();
    String testUserTokenOnIncorrectData = new String();
    User testSignUpOnIncorrectData = new User();
    String testGetProfileOnIncorrectData = new String();

    @Before
    public void setUp() throws Exception {
        User user = new User();
        // создала объект, привязала зависимость
        serviceFacade = new AdaptiveLearningServiceFacadeImpl();
        serviceFacade.usersDao = usersDao;

        // когда у usersDao вызывается метод checkLogin на testLogin мы должны вернуть true
        when(usersDao.checkLogin(testLogin)).thenReturn(true);
        // когда у usersDao вызывается метод login на testLoginData мы должны вернуть 12345
        when(usersDao.login(testLoginData)).thenReturn("12345");
        // когда у usersDao вызывается метод logout на testUserToken мы должны вернуть id
        when(usersDao.logout(testUserToken)).thenReturn();
        // когда у usersDao вызывается метод signUp на testSignUp мы должны вернуть Name
        when(usersDao.signUp(testSignUp)).thenReturn("Name");
        // когда у usersDao вызывается метод getProfile на testGetProfile мы должны вернуть null
        when(usersDao.getProfile(testGetProfile)).thenReturn(null);
        //Метод verify проверяет не только, чтобы метод был вызван, а так же может проверить сколько раз он был вызван.
        // А так каждый метод у нас вызывается один раз, то метод verify проверяет, что он вызывается 1 раз,
        // для этого вторым аргументом в него передается количество раз, для этого используется метод times().
        verify(usersDao, times(1)).checkLogin(user);
        verify(usersDao, times(1)).login(user);
        verify(usersDao, times(1)).logout(user);
        verify(usersDao, times(1)).signUp(user);
        verify(usersDao, times(1)).getProfile(user);
    }
    // тест
    @Test
    public void testTrue() {
        User user = new User();

        //Для соблюдения правильной последовательности вызовов методов, нам потребуется объект InOrder фрэймворка mockito
        InOrder inOrder = InOrder(usersDao);
        inOrder.verify(this.usersDao).checkLogin(user);
        inOrder.verify(this.usersDao).login(user);
        inOrder.verify(this.usersDao).logout(user);
        inOrder.verify(this.usersDao).signUp(user);
        inOrder.verify(this.usersDao).getProfile(user);
        assertEquals(5);
    }
}
