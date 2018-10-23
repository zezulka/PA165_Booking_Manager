package cz.muni.fi.pa165.entity;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Email;

/**
 *
 *
 */
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @NotNull
    @Email
    @Column(nullable=false, unique=true)
    private String email;

    @NotNull
    private String firstName;

    @NotNull
    private String surname;

    public Customer() {
    }

}
