package cz.muni.fi.pa165.api.dto;

import java.math.BigDecimal;
import java.util.Objects;

/**
 *
 * @author Miloslav Zezulka
 */
public class RoomCreateDTO {
    private HotelDTO hotel;
    private Integer number;
    private String description;
    private BigDecimal recommendedPrice;

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
