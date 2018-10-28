package cz.muni.fi.pa165.entity;

import cz.muni.fi.pa165.enums.RoomType;
import java.math.BigDecimal;
import java.util.List;
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

}
