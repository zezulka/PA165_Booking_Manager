package cz.muni.fi.pa165.service.facade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import cz.muni.fi.pa165.api.dto.HotelDTO;
import cz.muni.fi.pa165.api.dto.RoomCreateDTO;
import cz.muni.fi.pa165.api.dto.RoomDTO;
import cz.muni.fi.pa165.api.facade.RoomFacade;
import cz.muni.fi.pa165.entity.Hotel;
import cz.muni.fi.pa165.entity.Room;
import cz.muni.fi.pa165.service.RoomService;
import cz.muni.fi.pa165.service.auxiliary.BeanMappingService;

/**
 * @author Petr Valenta
 */
@Service
@Transactional
public class RoomFacadeImpl implements RoomFacade {

    @Autowired
    private RoomService roomService;

    @Autowired
    private BeanMappingService bms;

    @Override
    public void createRoom(final RoomCreateDTO room) {
        roomService.createRoom(bms.mapTo(room, Room.class));
    }

    @Override
    public void deleteRoom(final RoomDTO room) {
        roomService.deleteRoom(bms.mapTo(room, Room.class));
    }

    @Override
    public List<RoomDTO> findAll() {
        return bms.mapTo(roomService.findAll(),RoomDTO.class);
    }

    @Override
    public RoomDTO findById(final Long id) {
        return bms.mapTo(roomService.findById(id),RoomDTO.class);
    }

    @Override
    public List<RoomDTO> findByHotel(final HotelDTO hotel) {
        return bms.mapTo(
            roomService.findByHotel(bms.mapTo(hotel, Hotel.class)),
            RoomDTO.class);
    }

    @Override
    public RoomDTO findByNumber(final HotelDTO hotel, final Integer number) {
        return bms.mapTo(
            roomService.findByNumber(bms.mapTo(hotel, Hotel.class), number),
            RoomDTO.class);
    }
}
