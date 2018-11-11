package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.dao.UserDao;
import cz.muni.fi.pa165.entity.User;
import cz.muni.fi.pa165.utils.Security;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

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
        if (user.getId() == null) {
            throw new IllegalArgumentException("User does not have its id set.");
        }
        user.setPasswordHash(Security.createHash(password));
        userDao.create(user);
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
        if (user.getId() == null) {
            throw new IllegalArgumentException("User does not have its id set.");
        }
        return Security.validatePassword(plainPassword, user.getPasswordHash());
    }

    @Override
    public boolean isAdmin(User candidate) {
        if (candidate == null) {
            throw new IllegalArgumentException("User is null");
        }
        return findById(candidate.getId()).isAdmin();
    }

    @Override
    public User findById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Id cannot be null.");
        }
        return userDao.findById(id);
    }

    @Override
    public User findByEmail(String email) {
        if (email == null) {
            throw new IllegalArgumentException("Email cannot be null.");
        }
        return userDao.findByEmail(email);
    }
}
