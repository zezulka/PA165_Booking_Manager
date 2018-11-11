package cz.muni.fi.pa165.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import cz.muni.fi.pa165.PersistenceApplicationContext;
import cz.muni.fi.pa165.entity.User;

/**
 *
 * @author Soňa Barteková
 */
@ContextConfiguration(classes = PersistenceApplicationContext.class)
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@Transactional
public class UserDaoTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private UserDao userDao;

    private User c1;
    private User c2;

    @BeforeMethod
    private void init() {
        c1 = new User();
        c2 = new User();

        c1.setEmail("teriductyl@jurassic.com");
        c1.setFirstName("Teri");
        c1.setSurname("Ductyl");
        c1.setAdmin(false);
        c1.setPasswordHash("IAmDead123");

        c2.setEmail("paige.turner@book.com");
        c2.setFirstName("Paige");
        c2.setSurname("Turner");
        c2.setAdmin(true);
        c2.setPasswordHash("TurnToPage394");

        userDao.create(c1);
        userDao.create(c2);
    }

    @Test
    public void findAllTest() {
        assertThat(userDao.findAll()).hasSize(2).containsExactly(c1, c2);
    }

    @Test
    public void correctFindByIdTest() {
        assertThat(userDao.findById(c1.getId())).isEqualTo(c1);
    }

    @Test
    public void nullFindByIdTest() {
        assertThatThrownBy(() -> userDao.findById(null))
                .isInstanceOf(DataAccessException.class)
                .hasCauseInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void correctFindByEmailTest() {
        assertThat(userDao.findByEmail(c1.getEmail())).isEqualTo(c1);
    }

    @Test
    public void nullFindByEmailTest() {
        assertThatThrownBy(() -> userDao.findByEmail(null))
                .isInstanceOf(DataAccessException.class)
                .hasCauseInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void emptyFindByEmailTest() {
        assertThatThrownBy(() -> userDao.findByEmail(""))
                .isInstanceOf(DataAccessException.class)
                .hasCauseInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void notExistingFindByEmailTest() {
        assertThat(userDao.findByEmail("nickname@mail.com")).isNull();
    }

    @Test
    public void correctCreateTest() {
        User c = new User();

        c.setEmail("Skyeblue@sky.com");
        c.setFirstName("Skye");
        c.setSurname("Blue");
        c.setAdmin(false);
        c.setPasswordHash("SkyIsBlue42");

        userDao.create(c);

        assertThat(c.getId()).isNotNull();
        assertThat(userDao.findById(c.getId())).isEqualTo(c);
        assertThat(userDao.findAll()).hasSize(3);
    }

    @Test
    public void nullCreateTtest() {
        assertThatThrownBy(() -> userDao.create(null))
                .isInstanceOf(DataAccessException.class)
                .hasCauseInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void notNullIdCreateTest() {
        assertThatThrownBy(() -> userDao.create(c1))
                .isInstanceOf(DataAccessException.class)
                .hasCauseInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void correctUpdateTest() {
        User c = userDao.findById(c1.getId());
        assertThat(c.isAdmin()).isFalse();
        c1.setAdmin(true);
        userDao.update(c1);
        c = userDao.findById(c1.getId());
        assertThat(c.isAdmin()).isTrue();
        assertThat(userDao.findAll()).hasSize(2).containsExactly(c1, c2);
    }

    @Test
    public void nullUpdateTest() {
        assertThatThrownBy(() -> userDao.update(null))
                .isInstanceOf(DataAccessException.class)
                .hasCauseInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void nullIdUpdateTest() {
        User c = new User();
        assertThatThrownBy(() -> userDao.update(c))
                .isInstanceOf(DataAccessException.class)
                .hasCauseInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void correctRemoveTest() {
        assertThat(userDao.findAll()).hasSize(2);
        userDao.remove(c1);
        assertThat(userDao.findAll()).hasSize(1).containsExactly(c2);
        userDao.remove(c2);
        assertThat(userDao.findAll()).isEmpty();
    }

    @Test
    public void nullRemoveTest() {
        assertThatThrownBy(() -> userDao.remove(null))
                .isInstanceOf(DataAccessException.class)
                .hasCauseInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void nullIdRemoveTest() {
        User c = new User();
        assertThatThrownBy(() -> userDao.remove(c))
                .isInstanceOf(DataAccessException.class)
                .hasCauseInstanceOf(IllegalArgumentException.class);
    }
}
