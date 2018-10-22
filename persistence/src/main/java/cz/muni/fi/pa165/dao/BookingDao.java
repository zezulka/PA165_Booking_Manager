package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.entity.Booking;
import cz.muni.fi.pa165.entity.Customer;
import cz.muni.fi.pa165.entity.Room;
import java.time.LocalDate;
import java.util.List;

/**
 *
 *
 */
public interface BookingDao {

    public void create(Booking order);

    public List<Booking> findAll();
    
    public List<Booking> findByRoom(Room r);

    public Booking findById(Long id);

    public void remove(Booking o) throws IllegalArgumentException;

    public List<Booking> getBookingsCreatedBetween(LocalDate from, LocalDate to);
}
