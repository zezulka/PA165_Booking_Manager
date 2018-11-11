package cz.muni.fi.pa165.service;

import java.util.List;
import org.springframework.stereotype.Service;
import cz.muni.fi.pa165.entity.User;

/**
 *
 * @author Miloslav Zezulka
 */
@Service
public interface UserService {

    /**
     * Register the given user with the given unencrypted password.
     *
     * @return true if registration was successful, false otherwise.
     */
    boolean register(User u, String password);

    /**
     * Get all registered users.
     *
     * @return {@link List} of users. Empty list if there are none.
     */
    List<User> getAll();

    /**
     * Try to authenticate a user. Return true only if the hashed password
     * matches the records.
     */
    boolean authenticate(User u, String hashedPassword);

    /**
     * Checks if the given user is an administrator.
     *
     * @param candidate user to be checked, must not be null
     */
    boolean isAdmin(User candidate);

    /**
     * Finds user by the database identifier.
     *
     * @param id id of the user, must not be null
     * @return {@link User} instance with the given id or null if there is
     * no such user.
     */
    User findById(Long id);

    /**
     * Finds user by {@code email} which is guaranteed to be unique in the
     * database.
     *
     * @param email email of the user, must not be null
     * @return {@link User} instance with the given email or null if there
     * is no such user.
     */
    User findByEmail(String email);
}
