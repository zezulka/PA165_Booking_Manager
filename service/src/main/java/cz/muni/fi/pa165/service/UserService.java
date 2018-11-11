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
     * @throws IllegalArgumentException {@code user} or {@code password} is null
     * or empty or id of {@code user} is null
     * @return True if registration was successful, false otherwise.
     */
    boolean register(User user, String password);

    /**
     * Get all registered users.
     *
     * @return {@link List} of users. Empty list if there are none.
     */
    List<User> getAll();

    /**
     * Try to authenticate a user. Return true only if the hashed password
     * matches the records.
     *
     * @throws IllegalArgumentException {@code user} or {@code hashedPassword}
     * is null or empty or id of {@code user} is null
     */
    boolean authenticate(User user, String hashedPassword);

    /**
     * Checks if the given user is an administrator.
     *
     * @param candidate user to be checked, must not be null and must exist in
     * the database
     * @throws IllegalArgumentException candidate is null or id of
     * {@code candidate} is null
     */
    boolean isAdmin(User candidate);

    /**
     * Finds user by the database identifier.
     *
     * @param id id of the user, must not be null
     * @throws IllegalArgumentException id is null
     * @return {@link User} instance with the given id or null if there is no
     * such user.
     */
    User findById(Long id);

    /**
     * Finds user by {@code email} which is guaranteed to be unique in the
     * database.
     *
     * @param email email of the user, must not be null
     * @return {@link User} instance with the given email or null if there is no
     * such user.
     */
    User findByEmail(String email);
}
