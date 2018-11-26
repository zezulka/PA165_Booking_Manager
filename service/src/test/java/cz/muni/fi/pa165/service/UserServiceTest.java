package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.dao.UserDao;
import cz.muni.fi.pa165.entity.User;
import cz.muni.fi.pa165.service.config.ServiceConfiguration;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.mockito.Mockito.verify;

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
    private String password;

    @BeforeClass
    private void SetUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @BeforeMethod
    public void init() {
        user = new User();
        admin = new User();
        password = "password";

        user.setFirstName("Martin");
        user.setSurname("IsAwesome");
        user.setEmail("user@gmail.com");
        user.setAdmin(false);
        user.setPasswordHash("passwordHash");

        user.setFirstName("Martin");
        user.setSurname("IsAwesomeAdmin");
        user.setEmail("admin@gmail.com");
        user.setAdmin(true);
        user.setPasswordHash("passwordHash");
    }

    @Test
    public void registerHappy() {
        userService.register(user, password);
        verify(userDao).create(user);
    }


}
