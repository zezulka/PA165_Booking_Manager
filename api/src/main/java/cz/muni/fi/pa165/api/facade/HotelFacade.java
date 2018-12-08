package cz.muni.fi.pa165.api.facade;

import cz.muni.fi.pa165.api.dto.HotelCreateDTO;
import cz.muni.fi.pa165.api.dto.HotelDTO;
import java.util.List;

/**
 *
 * @author Soňa Barteková
 *
 */
public interface HotelFacade {

    /**
     * Delete hotel by its database identifier.
     * @param id identifier to search by
     */
    void delete(long id);
    
    /**
     * Create new hotel.
     *
     * @param hotel hotel to create
     */
    void create(HotelCreateDTO hotel);

    /**
     * List all hotels.
     *
     * @return list of hotels
     */
    List<HotelDTO> findAll();

    /**
     * Find hotel by its identifier.
     *
     * @param id given identifier
     * @return hotel with given identifier. Null if there is no such hotel.
     */
    HotelDTO findById(Long id);

    /**
     * Find hotel by its name.
     *
     * @param name given name
     * @return hotel with given name. Null if there is no such hotel.
     */
    HotelDTO findByName(String name);
}
