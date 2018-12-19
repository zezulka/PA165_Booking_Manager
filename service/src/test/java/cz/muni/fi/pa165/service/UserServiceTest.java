package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.dao.UserDao;
import cz.muni.fi.pa165.entity.User;
import cz.muni.fi.pa165.service.config.ServiceConfiguration;
import cz.muni.fi.pa165.utils.Security;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import static org.assertj.core.api.Assertions.*;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 *
 * @author Martin Palenik
 */
@ContextConfiguration(classes = ServiceConfiguration.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class UserServiceTest {

    @Mock
    private UserDao userDao;

    @Autowired
    @InjectMocks
    private UserService userService;

    private User user;
    private User admin;
    private String password, hash;

    private List<User> users;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Before
    public void init() {
        user = new User();
        admin = new User();
        password = "password";
        hash = Security.createHash(password);

        user.setFirstName("Martin");
        user.setSurname("IsAwesome");
        user.setEmail("user@gmail.com");
        user.setAdministrator(false);
        user.setPasswordHash(hash);

        admin.setFirstName("Martin");
        admin.setSurname("IsAwesomeAdmin");
        admin.setEmail("admin@gmail.com");
        admin.setAdministrator(true);
        admin.setPasswordHash(hash);

        users = new ArrayList<>();
        users.add(user);
        users.add(admin);
    }

    @Test
    public void registerHappy() {
        userService.register(user, password);
        verify(userDao).create(user);
    }

    @Test
    public void registerNullUser() {
        assertThatThrownBy(() -> userService.register(null, password))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void registerNullPassword() {
        assertThatThrownBy(() -> userService.register(user, null))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void updateHappy() {
        userService.update(user);
        verify(userDao).update(user);
    }

    @Test
    public void updateUserNull() {
        assertThatThrownBy(() -> userService.update(null))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void getAllHappy() {
        when(userDao.findAll()).thenReturn(users);
        List<User> u = userService.getAll();
        verify(userDao).findAll();
        assertEquals(u, users);
    }

    @Test
    public void getAllEmpty() {
        List<User> u = new ArrayList<>();
        when(userDao.findAll()).thenReturn(u);
        u = userService.getAll();
        verify(userDao).findAll();
        assertEquals(u, Collections.EMPTY_LIST);
    }

    @Test
    public void findByIdHappy() {
        when(userDao.findById(1L)).thenReturn(user);
        User u = userService.findById(1L);
        verify(userDao).findById(1L);
        assertEquals(u, user);
    }

    @Test
    public void findByIdNull() {
        assertThatThrownBy(() -> userService.findById(null))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void findByEmailHappy() {
        when(userDao.findByEmail("user@gmail.com")).thenReturn(user);
        User u = userService.findByEmail("user@gmail.com");
        verify(userDao).findByEmail("user@gmail.com");
        assertEquals(u, user);
    }

    @Test
    public void findByEmailNull() {
        assertThatThrownBy(() -> userService.findByEmail(null))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void isAdmin() {
        when(userDao.findByEmail("admin@gmail.com")).thenReturn(admin);
        User a = userService.findByEmail("admin@gmail.com");
        assertTrue(a.isAdministrator());
    }

    @Test
    public void isNotAdmin() {
        when(userDao.findByEmail("user@gmail.com")).thenReturn(user);
        User u = userService.findByEmail("user@gmail.com");
        assertFalse(u.isAdministrator());
    }

    @Test
    public void authenticateUserNull() {
        assertThatThrownBy(() -> userService.authenticate(null, user.getPasswordHash()))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void authenticatePasswordNull() {
        assertThatThrownBy(() -> userService.authenticate(user, null))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void authenticateHappy() {
        assertTrue(
                userService.authenticate(user, password)
        );
    }

    @Test
    public void authenticateIncorrectPassword() {
        assertFalse(
                userService.authenticate(user, Security.createHash(password + "incorrect"))
        );
    }
}
