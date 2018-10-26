package cz.muni.fi.pa165.entity;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Email;

import java.util.Objects;

/**
 * @author Petr Valenta
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

    @NotNull //TODO is this necessary?
    private boolean admin;

    @NotNull
    private String passwordHash;

    public Customer() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) { this.id = id; }

    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email; }

    public String getFirstName() { return firstName; }

    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getSurname() { return surname; }

    public void setSurname(String surname) { this.surname = surname; }

    public boolean isAdmin() { return admin; }

    public void setAdmin(boolean admin) { this.admin = admin; }

    public String getPasswordHash() { return passwordHash; }

    public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final Customer customer = (Customer) o;
        return Objects.equals(email, customer.email) &&
            Objects.equals(firstName, customer.firstName) &&
            Objects.equals(surname, customer.surname);
    }

    @Override
    public int hashCode() {

        return Objects.hash(email, firstName, surname);
    }
}
