package cz.muni.fi.pa165.service;

import java.util.List;
import org.springframework.stereotype.Service;
import cz.muni.fi.pa165.entity.Customer;

/**
 *
 * @author Miloslav Zezulka
 */
@Service
public interface CustomerService {

    /**
     * Register the given Customer with the given unencrypted password.
     *
     * @return true if registration was successful, false otherwise.
     */
    boolean register(Customer u, String password);

    /**
     * Get all registered Customers.
     *
     * @return {@link List} of customers. Empty list if there are none.
     */
    List<Customer> getAll();

    /**
     * Try to authenticate a Customer. Return true only if the hashed password
     * matches the records.
     */
    boolean authenticate(Customer u, String hashedPassword);

    /**
     * Checks if the given Customer is an administrator.
     *
     * @param candidate Customer to be checked, must not be null
     */
    boolean isAdmin(Customer candidate);

    /**
     * Finds Customer by the database identifier.
     *
     * @param id id of the customer, must not be null
     * @return {@link Customer} instance with the given id or null if there is
     * no such customer.
     */
    Customer findById(Long id);

    /**
     * Finds Customer by {@code email} which is guaranteed to be unique in the
     * database.
     *
     * @param email email of the customer, must not be null
     * @return {@link Customer} instance with the given email or null if there
     * is no such customer.
     */
    Customer findByEmail(String email);
}
