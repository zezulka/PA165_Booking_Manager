package cz.muni.fi.pa165.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import cz.muni.fi.pa165.entity.Booking;
import cz.muni.fi.pa165.entity.Room;

@Repository
public class BookingDaoImpl implements BookingDao {

    @PersistenceContext
    private EntityManager em;
    
    @Override
    public void create(Booking booking) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void remove(Booking booking) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Booking findById(Long id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<Booking> findAll() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<Booking> findByRoom(Room room) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
