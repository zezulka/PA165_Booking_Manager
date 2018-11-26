package cz.muni.fi.pa165.api.dto;

import cz.muni.fi.pa165.enums.RoomType;
import java.math.BigDecimal;
import java.util.Objects;

/**
 *
 * @author Miloslav Zezulka
 */
public class RoomDTO {

    private Long id;

    private HotelDTO hotel;

    private Integer number;

    private String description;

    private BigDecimal recommendedPrice;

    private byte[] image;

    private RoomType type;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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
    public int hashCode() {
        int hash = 5;
        hash = 53 * hash + Objects.hashCode(this.hotel);
        hash = 53 * hash + Objects.hashCode(this.number);
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
        if (!(obj instanceof RoomDTO)) {
            return false;
        }
        final RoomDTO other = (RoomDTO) obj;
        if (!hotel.equals(other.hotel)) {
            return false;
        }
        return number.equals(other.number);
    }

}
