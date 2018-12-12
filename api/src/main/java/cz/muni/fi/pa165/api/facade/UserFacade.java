package cz.muni.fi.pa165.api.facade;

import cz.muni.fi.pa165.api.dto.UserAuthenticateDTO;
import cz.muni.fi.pa165.api.dto.UserDTO;

import java.util.List;

/**
 * 
 * @author Miloslav Zezulka
 */
public interface UserFacade {

    /**
     * Registers the user with the supplied unencrypted password.
     * @param user user to register
     * @param password password to hash
     */
    void register(UserDTO user, String password);

    /**
     * List all users.
     * @return list of users, empty list otherwise
     */
    List<UserDTO> getAll();

    /**
     * Authenticates the user with the supplied credentials.
     * @param auth authentication details
     * @return true if authentication was successful, false otherwise
     */
    boolean authenticate(UserAuthenticateDTO auth);

    /**
     * Check whether {@code candidate} has got privileges of an administrator.
     * 
     * @param candidate candidate whose permissions will be checked
     * @return true if user is an administrator, false otherwise
     */
    boolean isAdmin(UserDTO candidate);

    /**
     * Searches for a user with the given database identifier {@code id}.
     * 
     * @param id id of the user in the database
     * @return user mapped to the id, null if there is no such user
     */
    UserDTO findById(Long id);

    /**
     * Searches for a user with the given email.
     * 
     * @param email email to search with
     * @return user with the given email, null if there is no such user
     */
    UserDTO findByEmail(String email);
    
    /**
     * Updates the given {@code user} in the database.
     * 
     * @param user user to be updated in the database, must not be null
     */
    void update(UserDTO user);
}
