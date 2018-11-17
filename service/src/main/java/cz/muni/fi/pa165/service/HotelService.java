package cz.muni.fi.pa165.service;

import org.springframework.stereotype.Service;

import java.util.List;

import cz.muni.fi.pa165.entity.Hotel;
import cz.muni.fi.pa165.entity.Room;

/**
 * @author Petr Valenta
 */
@Service
public interface HotelService {
    Hotel findById(Long id);
    List<Hotel> findAll();
    Hotel createHotel(Hotel h);
    void addRoom(Hotel h, Room r);
    void removeRoom(Hotel h, Room r);
    void changeAddress(Hotel h, String a);
    void deleteHotel(Hotel h);
}
