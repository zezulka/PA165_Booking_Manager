package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.entity.Hotel;
import java.util.List;

/**
 *
 *
 */
public interface HotelDao {

    public void create(Hotel p);

    public Hotel findById(Long id);

    public List<Hotel> findAll();

    public void remove(Hotel p) throws IllegalArgumentException;

    public Hotel findByName(String name);
}
