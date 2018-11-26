package cz.muni.fi.pa165.api.dto;

import java.util.Objects;

/**
 *
 * @author Miloslav Zezulka
 */
public class RoomCreateDTO {
    private HotelDTO hotel;
    private Integer number;

    public HotelDTO getHotel() {
        return hotel;
    }

    public void setHotel(HotelDTO hotel) {
        this.hotel = hotel;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + Objects.hashCode(this.hotel);
        hash = 67 * hash + Objects.hashCode(this.number);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof RoomCreateDTO)) {
            return false;
        }
        final RoomCreateDTO other = (RoomCreateDTO) obj;
        if (!hotel.equals(other.hotel)) {
            return false;
        }
        return number.equals(other.number);
    }
    
    
}
