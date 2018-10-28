package cz.muni.fi.pa165.entity;

import cz.muni.fi.pa165.enums.RoomType;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

/**
 * @author Martin Páleník
 */
@Entity
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne(optional = false)
    @NotNull
    private Hotel hotel;

    @NotNull
    private Integer number;
    
    @NotNull
    private String description;

    @NotNull
    @DecimalMin("0.0")
    private BigDecimal recommendedPrice;

    @NotNull
    private byte[] image;

    @Enumerated
    private RoomType type;

    public Room() {
    }

    // getters and setters
    // https://www.yegor256.com/2014/09/16/getters-and-setters-are-evil.html
    // JPA [...] rely on the get/set notation

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
        if (!(o instanceof Room)) return false;
        Room room = (Room) o;
        return Objects.equals(hotel, room.hotel) &&
                Objects.equals(number, room.number);
    }

    @Override
    public int hashCode() {
        return Objects.hash(hotel, number);
    }
}
