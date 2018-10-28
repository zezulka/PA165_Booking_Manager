package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.entity.Hotel;
import cz.muni.fi.pa165.entity.Room;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

public class RoomDaoImpl implements RoomDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void create(Room room) {
        if (room == null) {
            throw new IllegalArgumentException("Room cannot be null.");
        }
        if (room.getId() != null) {
            throw new IllegalArgumentException("Room must have a null id when"
                    + "being stored.");
        }
        em.persist(room);
    }

    @Override
    public void remove(Room room) {
        if (room == null) {
            throw new IllegalArgumentException("Cannot remove null room.");
        }
        if (room.getId() == null) {
            throw new IllegalArgumentException("Room id not set.");
        }
        em.remove(findById(room.getId()));
    }

    @Override
    public Room update(Room room) {
        if (room == null) {
            throw new IllegalArgumentException("Cannot update null room.");
        }
        if (room.getId() == null) {
            throw new IllegalArgumentException("Room id not set.");
        }
        return em.merge(room);
    }

    @Override
    public Room findById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Id cannot be null.");
        }
        return em.find(Room.class, id);
    }

    @Override
    public List<Room> findAll() {
        return em
                .createQuery("SELECT r FROM Room r", Room.class)
                .getResultList();
    }

    @Override
    public List<Room> findByHotel(Hotel hotel) {
        if(hotel == null) {
            throw new IllegalArgumentException("Can't find rooms with hotel being null.");
        }
        if(hotel.getId() == null) {
            throw new IllegalArgumentException("Hotel must have its id set.");
        }
        return em
                .createQuery("SELECT r FROM Room r WHERE r.hotel = :hotel",
                        Room.class)
                .setParameter("hotel", hotel)
                .getResultList();
    }

    @Override
    public Room findByNumber(Hotel hotel, Integer number) {
        if(hotel == null || number == null) {
            throw new IllegalArgumentException("None of the arguments shall be null.");
        }
        if(hotel.getId() == null) {
            throw new IllegalArgumentException("Hotel must have its id set.");
        }
        return em
                .createQuery("SELECT r FROM Room r WHERE r.hotel = :hotel",
                        Room.class)
                .setParameter("hotel", hotel)
                .getSingleResult();
    }
}