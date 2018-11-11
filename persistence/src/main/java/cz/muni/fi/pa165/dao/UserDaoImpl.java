package cz.muni.fi.pa165.dao;

import org.springframework.stereotype.Repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import cz.muni.fi.pa165.entity.User;

/**
 * User entity interface implementation
 *
 * @author Petr Valenta
 */
@Repository
public class UserDaoImpl implements UserDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void create(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null.");
        }
        if (user.getId() != null) {
            throw new IllegalArgumentException("Cannot create existing user.");
        }
        em.persist(user);
    }

    @Override
    public void remove(User user) {
        if (user == null) {
            throw new IllegalArgumentException("Cannot remove null user.");
        }
        if (user.getId() == null) {
            throw new IllegalArgumentException("Cannot remove user with null id.");
        }
        em.remove(user);
    }

    @Override
    public User update(User user) {
        if (user == null) {
            throw new IllegalArgumentException("Cannot update null user.");
        }
        if (user.getId() == null) {
            throw new IllegalArgumentException("Cannot update user with null id.");
        }
        return em.merge(user);
    }

    @Override
    public User findById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Cannot search for null id.");
        }
        return em.find(User.class, id);
    }

    @Override
    public User findByEmail(String email) {
        if (email == null || email.isEmpty()) {
            throw new IllegalArgumentException("Cannot search for null or empty email.");
        }
        try {
            return em.createQuery("SELECT u FROM User u WHERE u.email = :email",
                    User.class).setParameter("email", email).getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }

    @Override
    public List<User> findAll() {
        return em.createQuery("SELECT u FROM User u",
                User.class).getResultList();
    }
}
