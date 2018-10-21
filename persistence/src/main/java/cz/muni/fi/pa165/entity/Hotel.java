package cz.muni.fi.pa165.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

/**
 *
 * 
 */
@Entity
public class Hotel {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    
    @NotNull
    private String address;
    
    @NotNull
    @Column(nullable=false, unique=true)
    private String name;
    
    public Hotel() {
    }
    
}
