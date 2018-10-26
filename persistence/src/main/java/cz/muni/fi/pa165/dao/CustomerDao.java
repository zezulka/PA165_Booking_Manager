package cz.muni.fi.pa165.dao;

import java.util.List;

import cz.muni.fi.pa165.entity.Customer;

/**
 * Customer entity interface
 *
 * @author Petr Valenta
 */
public interface CustomerDao {
    /**
     * Persists a new customer to the database
     *
     * @param c customer to be persisted.
     * @throws IllegalArgumentException
     *
     */
    public void create(Customer c);

    /**
     * summary
     *
     * @param
     * @return
     * @throws
     */
    public void remove(Customer c);

    /**
     * summary
     *
     * @param
     * @return
     * @throws
     */
    public Customer update(Customer c);

    /**
     * summary
     *
     * @param
     * @return
     * @throws
     */
    public Customer findById(Long id);

    /**
     * summary
     *
     * @param
     * @return
     * @throws
     */
    public Customer findByEmail(String email);

    /**
     * summary
     *
     * @param
     * @return
     * @throws
     */
    public List<Customer> findAll();
}
