package cz.muni.fi.pa165.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import cz.muni.fi.pa165.entity.Hotel;

/**
 * 
 * Class accessing persistence context for CRUD operations for hotel class.
 * It implements HotelDao interface.
 * @author Soňa Barteková
 *
 */

@Repository
public class HotelDaoImpl implements HotelDao {
	
	@PersistenceContext
	private EntityManager em;

	public void create(Hotel h) {
		if(h == null) {
			throw new IllegalArgumentException("Hotel cannot be null.");
		}
		if(h.getId() != null) {
			throw new IllegalArgumentException("Hotel must have a null id when being stored.");
		}
		em.persist(h);
	}

	public void remove(Hotel h) throws IllegalArgumentException{
		em.remove(h);
	}

	public Hotel update(Hotel h) {
        if (findById(h.getId()) == null) {
            throw new IllegalArgumentException("Unknown hotel to update!");
        }
        
		return em.merge(h);
	}

	public Hotel findById(Long id) {
		return em.find(Hotel.class, id);
	}

	public List<Hotel> findAll() {
		return em.createQuery("select h from Hotel h", Hotel.class).getResultList();
	}

	public Hotel findByName(String name) {
        if(name == null) {
            throw new IllegalArgumentException("You try to find hotel by NULL name!");
        }
		try {
			return em.createQuery("select h from Hotel h where name = :name", Hotel.class)
					.setParameter("name", name).getSingleResult();
		}
		catch(NoResultException nre) {
			return null;
		}
	}
}
