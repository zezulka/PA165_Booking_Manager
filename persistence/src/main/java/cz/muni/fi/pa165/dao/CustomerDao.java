package cz.muni.fi.pa165.dao;

import java.util.List;

import cz.muni.fi.pa165.entity.Customer;

/**
 * @author Petr Valenta
 */
public interface CustomerDao {
    /**
     *
     * @param c customer to be persisted.
     *          @throws IllegalArgumentException
     */
    public void create(Customer c);
    
    public void remove(Customer c);

    public Customer update(Customer c);

    public Customer findById(Long id);

    public Customer findByEmail(String email);

    public List<Customer> findAll();
}
