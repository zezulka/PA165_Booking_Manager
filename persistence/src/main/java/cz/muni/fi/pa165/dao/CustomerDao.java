package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.entity.Customer;
import java.util.List;

/**
 *
 *
 */
public interface CustomerDao {

    public void create(Customer u);
    
    public void remove(Customer u);

    public Customer findById(Long id);

    public Customer findByEmail(String email);

    public List<Customer> findAll();
}
