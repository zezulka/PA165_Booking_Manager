package cz.muni.fi.pa165.dao;

import java.util.List;

import cz.muni.fi.pa165.entity.User;
import javax.validation.ConstraintViolationException;

/**
 * User entity interface.
 *
 * @author Petr Valenta
 */
public interface UserDao {

    /**
     * Persists a new {@link User} to the database.
     *
     * @param user user to be persisted.
     * @throws IllegalArgumentException user is null or user already
     * exists
     * @throws ConstraintViolationException column constraint was violated
     */
    public void create(User user);

    /**
     * Removes an existing {@link User} from the database.
     *
     * @param user user to be removed
     * @throws IllegalArgumentException user is null or user has null id
     */
    public void remove(User user);

    /**
     * Updates an existing {@link User}.
     *
     * @param user user to be updated
     * @return instance of the updated user
     * @throws IllegalArgumentException user is null
     * @throws ConstraintViolationException column constraint was violated
     *
     */
    public User update(User user);

    /**
     * Finds and returns a {@link User} with the id {@code id}.
     *
     * @param id non-null id
     * @return user or null
     * @throws IllegalArgumentException id is null
     */
    public User findById(Long id);

    /**
     * Returns a {@link User} with matching {@code email} address. Return
     * null if such user cannot be found.
     *
     * @param email email address String
     * @return user or null
     * @throws IllegalArgumentException email is null or empty
     */
    public User findByEmail(String email);

    /**
     * Returns a {@link List} of all users in the database.
     *
     * @return A {@link List} of all users
     */
    public List<User> findAll();
}
