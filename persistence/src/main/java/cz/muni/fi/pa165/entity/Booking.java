package cz.muni.fi.pa165.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
    @Future
    private LocalDate to;
    
    @ManyToOne(optional = false)
    @NotNull
    private Customer customer;
    
    @ManyToOne
    @NotNull
    private Room room;

    public Booking() {
    }
}
