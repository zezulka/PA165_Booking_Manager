package cz.muni.fi.pa165.dao;

import java.util.List;

import cz.muni.fi.pa165.entity.Customer;
import javax.validation.ConstraintViolationException;

/**
 * Customer entity interface
 *
 * @author Petr Valenta
 */
public interface CustomerDao {
    /**
     * Persists a new {@link Customer} to the database.
     *
     * @param c customer to be persisted.
     * @throws IllegalArgumentException customer is null
     * @throws ConstraintViolationException column constraint was violated
     */
    public void create(Customer c);

    /**
     * Removes an exeisting {@link Customer} from the database.
     *
     * @param c customer to be removed
     * @throws IllegalArgumentException customer is null
     */
    public void remove(Customer c);

    /**
     * Updates an existing {@link Customer}.
     *
     * @param customer customer to be updated
     * @return instance of the updated customer
     * @throws IllegalArgumentException customer is null
     * @throws ConstraintViolationException column constraint was violated
     **/
    public Customer update(Customer c);

    /**
     * Finds and returns a {@link Customer} with the id {@link id}.
     *
     * @param id non-null id
     * @return customer or null
     * @throws IllegalArgumentException id is null
     */
    public Customer findById(Long id);

    /**
     * Returns a {@link Customer} with matching {@link email} address.
     * Return null if such customer cannot be found.
     *
     * @param email email address String
     * @return customer or null
     * @throws IllegalArgumentException email is null or empty
     */
    public Customer findByEmail(String email);

    /**
     * Returns a {@link List} of all customers in the database.
     *
     * @return A {@link List} of all customers
     */
    public List<Customer> findAll();
}
