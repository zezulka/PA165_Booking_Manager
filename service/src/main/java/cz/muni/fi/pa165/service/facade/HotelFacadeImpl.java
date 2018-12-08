package cz.muni.fi.pa165.service.facade;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

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
public class HotelFacadeImpl implements HotelFacade {

    @Inject
    private BeanMappingService beanMappingService;

    @Inject
    private HotelService hotelService;

    @Override
    public void create(HotelCreateDTO hotel) {
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

    @Override
    public void delete(long id) {
        hotelService.delete(hotelService.findById(id));
    }

}
