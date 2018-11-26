// @author Martin Palenik

package cz.muni.fi.pa165.api.dto;

import cz.muni.fi.pa165.entity.Hotel;
import cz.muni.fi.pa165.enums.RoomType;

import java.math.BigDecimal;
import java.util.Objects;

public class RoomDTO {
    private Long id;
    private Hotel hotel;
    private Integer number;
    private String description;
    private BigDecimal recommendedPrice;
    private byte[] image;
    private RoomType type;

    public RoomDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getRecommendedPrice() {
        return recommendedPrice;
    }

    public void setRecommendedPrice(BigDecimal recommendedPrice) {
        this.recommendedPrice = recommendedPrice;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public RoomType getType() {
        return type;
    }

    public void setType(RoomType type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RoomDTO)) return false;
        RoomDTO roomDTO = (RoomDTO) o;
        return getHotel().equals(roomDTO.getHotel()) &&
                getNumber().equals(roomDTO.getNumber());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getHotel(), getNumber());
    }
}