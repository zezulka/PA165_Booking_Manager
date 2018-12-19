package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.dao.UserDao;
import cz.muni.fi.pa165.entity.User;
import cz.muni.fi.pa165.service.exceptions.BookingManagerDataAccessException;
import cz.muni.fi.pa165.utils.Security;
import java.util.List;
import javax.persistence.EntityExistsException;
import javax.persistence.TransactionRequiredException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

/*
 * @author Miloslav Zezulka 
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public boolean register(User user, String password) {
        if (user == null) {
            throw new IllegalArgumentException("User is null.");
        }
        if (password == null || password.isEmpty()) {
            throw new IllegalArgumentException("Password must be nonempty.");
        }
        user.setPasswordHash(Security.createHash(password));
        try {
            userDao.create(user);
        } catch (TransactionRequiredException | EntityExistsException | IllegalArgumentException e) {
            throw new BookingManagerDataAccessException("UserDAO could not create a new user.", e);
        }
        return userDao.findById(user.getId()) != null;
    }

    @Override
    public List<User> getAll() {
        return userDao.findAll();
    }

    @Override
    public boolean authenticate(User user, String plainPassword) {
        if (user == null) {
            throw new IllegalArgumentException("User is null");
        }
        if (plainPassword == null || plainPassword.isEmpty()) {
            throw new IllegalArgumentException("Hashed password must be nonempty.");
        }
        return Security.validatePassword(plainPassword, user.getPasswordHash());
    }

    @Override
    public boolean isAdmin(User candidate) {
        if (candidate == null) {
            throw new IllegalArgumentException("User is null");
        }
        try {
            User u = findById(candidate.getId());
            return u != null && u.isAdministrator();
        } catch (TransactionRequiredException | IllegalArgumentException e) {
            throw new BookingManagerDataAccessException("Could not verify "
                    + "whether the user is an administrator.", e);
        }
    }

    @Override
    public User findById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Id cannot be null.");
        }
        try {
            return userDao.findById(id);
        } catch (IllegalArgumentException e) {
            throw new BookingManagerDataAccessException("Could not find user by id.", e);
        }
    }

    @Override
    public User findByEmail(String email) {
        if (email == null) {
            throw new IllegalArgumentException("Email cannot be null.");
        }
        try {
            return userDao.findByEmail(email);
        } catch (IllegalArgumentException e) {
            throw new BookingManagerDataAccessException("Could not find user by id.", e);
        }
    }

    @Override
    public void update(User user) throws DataAccessException {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null.");
        }
        try {
            userDao.update(user);    
        } catch(TransactionRequiredException | IllegalArgumentException e) {
            throw new BookingManagerDataAccessException("Could not update user.", e);
        }
    }
}
