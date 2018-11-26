<<<<<<< HEAD:service/src/test/java/cz.muni.fi.pa165.service/UserServiceTest.java
// @author Martin Palenik

=======
>>>>>>> 4b695a6351cf65ea80b0cb12c8fd9625f7aa4d06:service/src/test/java/cz/muni/fi/pa165/service/UserServiceTest.java
package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.dao.UserDao;
import cz.muni.fi.pa165.entity.User;
import cz.muni.fi.pa165.service.config.ServiceConfiguration;
import cz.muni.fi.pa165.utils.Security;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * 
 * @author Martin Palenik
 */
@ContextConfiguration(classes = ServiceConfiguration.class)
public class UserServiceTest extends AbstractTransactionalTestNGSpringContextTests {

    @Mock
    private UserDao userDao;

    @Autowired
    @InjectMocks
    private UserService userService;

    private User user;
    private User admin;
    private String password, hash;

    private List<User> users;

    @BeforeClass
    private void SetUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @BeforeMethod
    public void init() {
        user = new User();
        admin = new User();
        password = "password";
        hash = Security.createHash(password);

        user.setFirstName("Martin");
        user.setSurname("IsAwesome");
        user.setEmail("user@gmail.com");
        user.setAdmin(false);
        user.setPasswordHash(hash);

        admin.setFirstName("Martin");
        admin.setSurname("IsAwesomeAdmin");
        admin.setEmail("admin@gmail.com");
        admin.setAdmin(true);
        admin.setPasswordHash(hash);

        users = new ArrayList<User>();
        users.add(user);
        users.add(admin);
    }

    @Test
    public void registerHappy() {
        userService.register(user, password);
        verify(userDao).create(user);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void registerNullUser() {
        userService.register(null, password);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void registerNullPassword() {
        userService.register(user, null);
    }

    @Test
    public void updateHappy() {
        userService.update(user);
        verify(userDao).update(user);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void updateUserNull() {
        userService.update(null);
    }

    @Test
    public void getAllHappy() {
        when(userDao.findAll()).thenReturn(users);
        List<User> u = userService.getAll();
        verify(userDao).findAll();
        Assert.assertEquals(u, users);
    }

    @Test(enabled = false)
    public void getAllEmpty() {
        List<User> u = new ArrayList<>();
        when(userDao.findAll()).thenReturn(u);
        u = userService.getAll();
        verify(userDao).findAll();
        Assert.assertEquals(u, users);
    }

    @Test
    public void findByIdHappy() {
        when(userDao.findById(1L)).thenReturn(user);
        User u = userService.findById(1L);
        verify(userDao).findById(1L);
        Assert.assertEquals(u, user);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void findByIdNull() {
        userService.findById(null);
    }

    @Test
    public void findByEmailHappy() {
        when(userDao.findByEmail("user@gmail.com")).thenReturn(user);
        User u = userService.findByEmail("user@gmail.com");
        verify(userDao).findByEmail("user@gmail.com");
        Assert.assertEquals(u, user);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void findByEmailNull() {
        userService.findByEmail(null);
    }

    @Test
    public void isAdmin() {
        when(userDao.findByEmail("admin@gmail.com")).thenReturn(admin);
        User a = userService.findByEmail("admin@gmail.com");
        Assert.assertTrue(a.isAdmin());
    }

    @Test
    public void isNotAdmin() {
        when(userDao.findByEmail("user@gmail.com")).thenReturn(user);
        User u = userService.findByEmail("user@gmail.com");
        Assert.assertFalse(u.isAdmin());
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void authenticateUserNull() {
        userService.authenticate(null, user.getPasswordHash());
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void authenticatePasswordNull() {
        userService.authenticate(user, null);
    }

    @Test
    public void authenticateHappy() {
        Assert.assertTrue(
                userService.authenticate(user, password)
        );
    }

    @Test
    public void authenticateIncorrectPassword() {
        Assert.assertFalse(
                userService.authenticate(user, Security.createHash(password + "incorrect"))
        );
    }
}
