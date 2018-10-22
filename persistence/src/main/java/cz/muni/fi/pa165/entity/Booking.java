package cz.muni.fi.pa165.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Future;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 *
 *
 */
@Entity
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @NotNull
    private BigDecimal total;

    @NotNull
    @Future
    private LocalDate from;
    
    @NotNull
    @Min(1)
    private Integer durationInDays;
    
    @ManyToOne(optional = false)
    @NotNull
    private Customer customer;
    
    @ManyToMany
    @NotNull
    private List<Room> rooms;

    public Booking() {
    }
}
