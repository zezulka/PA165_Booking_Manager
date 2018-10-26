package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.entity.Booking;
import cz.muni.fi.pa165.entity.Room;

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
        if(booking == null) {
            throw new IllegalArgumentException("Booking cannot be null.");
        }
        em.persist(booking);
    }

    @Override
    public void remove(Booking booking) {
        if(booking == null) {
            throw new IllegalArgumentException("Cannot remove null booking.");
        }
        if(booking.getId() == null) {
            throw new IllegalArgumentException("Booking id not set.");
        }
        em.remove(findById(booking.getId()));
    }

    @Override
    public Booking update(Booking booking) {
        if(booking == null) {
            throw new IllegalArgumentException("Cannot update null booking.");
        }
        if(booking.getId() == null) {
            throw new IllegalArgumentException("Booking id not set.");
        }
        return em.merge(booking);
    }

    @Override
    public Booking findById(Long id) {
        if(id == null) {
            throw new IllegalArgumentException("Id cannot be null.");
        }
        return em.find(Booking.class, id);
    }

    @Override
    public List<Booking> findAll() {
        return em
                .createQuery("SELECT b FROM Booking b", Booking.class)
                .getResultList();
    }

    @Override
    public List<Booking> findByRoom(Room room) {
        if(room == null) {
            throw new IllegalArgumentException("Can't find bookings with room "
                    + "as null.");
        }
        if(room.getId() == null) {
            throw new IllegalArgumentException("Room must have its id set.");
        }
        return em
                .createQuery("SELECT b FROM Booking b WHERE b.room = :room",
                        Booking.class)
                .setParameter("room", room)
                .getResultList();
    }
}
