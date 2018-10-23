package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.entity.Booking;
import cz.muni.fi.pa165.entity.Room;
import java.util.List;

/**
 *
 *
 */
public interface BookingDao {

    public void create(Booking order);

    public void remove(Booking o) throws IllegalArgumentException;

    public Booking findById(Long id);

    public List<Booking> findAll();

    public List<Booking> findByRoom(Room r);
}
