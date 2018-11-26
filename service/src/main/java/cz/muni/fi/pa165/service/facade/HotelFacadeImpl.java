package cz.muni.fi.pa165.service.facade;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cz.muni.fi.pa165.api.dto.HotelCreateDTO;
import cz.muni.fi.pa165.api.dto.HotelDTO;
import cz.muni.fi.pa165.api.facade.HotelFacade;
import cz.muni.fi.pa165.entity.Hotel;
import cz.muni.fi.pa165.service.HotelService;
import cz.muni.fi.pa165.service.auxiliary.BeanMappingService;

/**
 * 
 * @author Soňa Barteková
 *
 */

@Service
@Transactional
public class HotelFacadeImpl implements HotelFacade{
	
    @Autowired
    private BeanMappingService beanMappingService;
    
    @Autowired
    private HotelService hotelService;

	@Override
	public void createHotel(HotelCreateDTO hotel) {
        if (hotel == null) {
            throw new IllegalArgumentException("Hotel cannot be null.");
        }
        Hotel hotelEntity = beanMappingService.mapTo(hotel, Hotel.class);
        hotelEntity.setName(hotel.getName());
        hotelEntity.setAddress(hotel.getAddress());
        hotelService.create(hotelEntity);
	}

	@Override
	public List<HotelDTO> findAll() {
        return beanMappingService.mapTo(hotelService.findAll(), HotelDTO.class);
	}

	@Override
	public HotelDTO findById(Long id) {
        Hotel hotel = hotelService.findById(id);
        return (hotel == null) ? null : beanMappingService.mapTo(hotel, HotelDTO.class);
	}

	@Override
	public HotelDTO findByName(String name) {
        Hotel hotel = hotelService.findByName(name);
        return (hotel == null) ? null : beanMappingService.mapTo(hotel, HotelDTO.class);
	}

}
