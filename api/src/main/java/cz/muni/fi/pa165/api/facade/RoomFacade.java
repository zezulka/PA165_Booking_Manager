package cz.muni.fi.pa165.api.facade;

import cz.muni.fi.pa165.api.dto.HotelDTO;
import cz.muni.fi.pa165.api.dto.RoomCreateDTO;
import cz.muni.fi.pa165.api.dto.RoomDTO;
import java.util.List;

public interface RoomFacade {

    boolean createRoom(RoomCreateDTO room);

    boolean deleteRoom(Long id);

    List<RoomDTO> findAll();

    RoomDTO findById(Long id);

    List<RoomDTO> findByHotel(HotelDTO hotel);

    RoomDTO findByNumber(HotelDTO hotel, Integer number);
}
