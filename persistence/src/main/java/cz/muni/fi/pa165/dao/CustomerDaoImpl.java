package cz.muni.fi.pa165.dao;

import org.springframework.stereotype.Repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import cz.muni.fi.pa165.entity.Customer;
/**
 * @author Petr Valenta
 */
@Repository
public class CustomerDaoImpl implements CustomerDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void create(Customer c) {
        if (c == null) {
            throw new IllegalArgumentException("Customer cannot be null.");
        }
        em.persist(c);
    }

    @Override
    public void remove(Customer c) {
        if (c == null) {
            throw new IllegalArgumentException("Cannot remove null customer.");
        }
        em.remove(c);
    }

    @Override
    public Customer update(Customer c) {
        if (c == null) {
            throw new IllegalArgumentException("Cannot update null customer.");
        }
        if (c.getId() == null) {
            throw new IllegalArgumentException("Cannot update customer with null id.");
        }
        return em.merge(c);
    }

    @Override
    public Customer findById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Cannot search for null id.");
        }
        em.find(Customer.class, id);
    }

    @Override
    public Customer findByEmail(String email) {
        if (email == null || email.isEmpty()){
            throw new IllegalArgumentException("Cannot search for null or empty email.");
        }
        try {
            return em.createQuery("SELECT c FROM customer c WHERE email = :email",
                Customer.class).setParameter("email", email).getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }

    @Override
    public List<Customer> findAll() {
        return em.createQuery("SELECT c FROM customer c",
            Customer.class).getResultList();
    }
}
