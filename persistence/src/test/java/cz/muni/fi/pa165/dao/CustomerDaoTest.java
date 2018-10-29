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
import cz.muni.fi.pa165.entity.Customer;
import cz.muni.fi.pa165.entity.Room;

/**
*
* @author Soňa Barteková
*/
@ContextConfiguration(classes= PersistenceApplicationContext.class)
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@Transactional
public class CustomerDaoTest extends AbstractTestNGSpringContextTests{
	
	@Autowired
	private CustomerDao customerDao;
	
	private Customer c1;
	private Customer c2;
	
	@BeforeMethod
	private void init(){
		c1 = new Customer();
		c2 = new Customer();
		
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
		
		customerDao.create(c1);
		customerDao.create(c2);
	}
	
    @Test
    public void findAllTest() {
        assertThat(customerDao.findAll()).hasSize(2).containsExactly(c1, c2);
    }

    @Test
    public void correctFindByIdTest() {
        assertThat(customerDao.findById(c1.getId())).isEqualTo(c1);
    }
    
    @Test
    public void nullFindByIdTest() {
        assertThatThrownBy(() -> customerDao.findById(null))
        .isInstanceOf(DataAccessException.class)
        .hasCauseInstanceOf(IllegalArgumentException.class);
    }
    
    @Test
    public void correctFindByEmailTest() {
        assertThat(customerDao.findByEmail(c1.getEmail())).isEqualTo(c1);
    }
    
    @Test
    public void nullFindByEmailTest() {
        assertThatThrownBy(() -> customerDao.findByEmail(null))
        .isInstanceOf(DataAccessException.class)
        .hasCauseInstanceOf(IllegalArgumentException.class);
    }
    
    @Test
    public void emptyFindByEmailTest() {
        assertThatThrownBy(() -> customerDao.findByEmail(""))
        .isInstanceOf(DataAccessException.class)
        .hasCauseInstanceOf(IllegalArgumentException.class);
    }
    
    @Test
    public void notExistingFindByEmailTest() {
        assertThat(customerDao.findByEmail("nickname@mail.com")).isNull();
    }
    
    @Test
    public void correctCreateTest() {
    	Customer c = new Customer();

		c.setEmail("Skyeblue@sky.com");
		c.setFirstName("Skye");
		c.setSurname("Blue");
		c.setAdmin(false);
		c.setPasswordHash("SkyIsBlue42");
		
		customerDao.create(c);
		
        assertThat(c.getId()).isNotNull();
        assertThat(customerDao.findById(c.getId())).isEqualTo(c);
        assertThat(customerDao.findAll()).hasSize(3);
    }
    
    @Test
    public void nullCreateTtest() {
        assertThatThrownBy(() -> customerDao.create(null))
                .isInstanceOf(DataAccessException.class)
                .hasCauseInstanceOf(IllegalArgumentException.class);
    }
    
    @Test
    public void notNullIdCreateTest() {
        assertThatThrownBy(() -> customerDao.create(c1))
        .isInstanceOf(DataAccessException.class)
        .hasCauseInstanceOf(IllegalArgumentException.class);	
    }

    
    @Test
    public void correctUpdateTest() {
        Customer c = customerDao.findById(c1.getId());
        assertThat(c.isAdmin()).isFalse();
        c1.setAdmin(true);
        customerDao.update(c1);
        c = customerDao.findById(c1.getId());
        assertThat(c.isAdmin()).isTrue();
        assertThat(customerDao.findAll()).hasSize(2).containsExactly(c1, c2);
    }
    
    @Test
    public void nullUpdateTest() {
        assertThatThrownBy(() -> customerDao.update(null))
        .isInstanceOf(DataAccessException.class)
        .hasCauseInstanceOf(IllegalArgumentException.class);
    }
    
    @Test
    public void nullIdUpdateTest() {
    	Customer c = new Customer();
        assertThatThrownBy(() -> customerDao.update(c))
        .isInstanceOf(DataAccessException.class)
        .hasCauseInstanceOf(IllegalArgumentException.class);
    }
    
    @Test
    public void correctRemoveTest() {
        assertThat(customerDao.findAll()).hasSize(2);
        customerDao.remove(c1);
        assertThat(customerDao.findAll()).hasSize(1).containsExactly(c2);
        customerDao.remove(c2);
        assertThat(customerDao.findAll()).isEmpty();
    }
    
    @Test
    public void nullRemoveTest() {
        assertThatThrownBy(() -> customerDao.remove(null))
        .isInstanceOf(DataAccessException.class)
        .hasCauseInstanceOf(IllegalArgumentException.class);
    }
        
    @Test
    public void nullIdRemoveTest() {
    	Customer c = new Customer();
        assertThatThrownBy(() -> customerDao.remove(c))
        .isInstanceOf(DataAccessException.class)
        .hasCauseInstanceOf(IllegalArgumentException.class);
    }
}
