package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.entity.Booking;
import cz.muni.fi.pa165.entity.Room;
import cz.muni.fi.pa165.entity.User;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

@Repository
public class BookingDaoImpl implements BookingDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void create(Booking booking) {
        if (booking == null) {
            throw new IllegalArgumentException("Booking cannot be null.");
        }
        if (booking.getId() != null) {
            throw new IllegalArgumentException("Booking must have a null id when"
                    + "being stored.");
        }
        em.persist(booking);
    }

    @Override
    public void remove(Booking booking) {
        if (booking == null) {
            throw new IllegalArgumentException("Cannot remove null booking.");
        }
        if (booking.getId() == null) {
            throw new IllegalArgumentException("Booking id not set.");
        }
        em.remove(findById(booking.getId()));
    }

    @Override
    public Booking update(Booking booking) {
        if (booking == null) {
            throw new IllegalArgumentException("Cannot update null booking.");
        }
        if (booking.getId() == null) {
            throw new IllegalArgumentException("Cannot update booking with null id.");
        }
        return em.merge(booking);
    }

    @Override
    public Booking findById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Cannot search with null id.");
        }
        return em.find(Booking.class, id);
    }

    @Override
    public List<Booking> findAll() {
        return em.createQuery("SELECT b FROM Booking b", Booking.class)
                .getResultList();
    }

    @Override
    public List<Booking> findByRoom(Room room) {
        if (room == null) {
            throw new IllegalArgumentException("Room cannot be null.");
        }
        if (room.getId() == null) {
            throw new IllegalArgumentException("Room must have its id set.");
        }
        return em.createQuery("SELECT b FROM Booking b WHERE b.room = :room",
                Booking.class).setParameter("room", room).getResultList();
    }

    @Override
    public List<Booking> findByUser(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null.");
        }
        if (user.getId() == null) {
            throw new IllegalArgumentException("User must have its id set.");
        }
        return em.createQuery("SELECT b FROM Booking b WHERE b.user = :user",
            Booking.class).setParameter("user", user).getResultList();
    }
}
