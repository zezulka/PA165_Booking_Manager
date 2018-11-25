package cz.muni.fi.pa165.api.facade;

import cz.muni.fi.pa165.api.dto.HotelCreateDTO;
import cz.muni.fi.pa165.api.dto.HotelDTO;
import java.util.List;

public interface HotelFacade {

    boolean createHotel(HotelCreateDTO room);

    List<HotelDTO> findAll();

    HotelDTO findById(Long id);

    HotelDTO findByName(String name);
}
