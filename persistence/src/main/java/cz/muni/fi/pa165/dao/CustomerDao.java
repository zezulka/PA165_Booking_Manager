package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.entity.Customer;
import java.util.List;

/**
 * @author Petr Valenta
 */
public interface CustomerDao {

    public void create(Customer c);
    
    public void remove(Customer c);

    public void update(Customer c);

    public Customer findById(Long id);

    public Customer findByEmail(String email);

    public List<Customer> findAll();
}
