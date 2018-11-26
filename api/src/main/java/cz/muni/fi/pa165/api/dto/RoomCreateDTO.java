
// @author Martin Palenik

package cz.muni.fi.pa165.api.dto;

import cz.muni.fi.pa165.entity.Hotel;

public class RoomCreateDTO {
    private Hotel hotel;
    private Integer number;

    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }
}