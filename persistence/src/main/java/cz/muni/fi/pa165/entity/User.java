package cz.muni.fi.pa165.entity;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

/**
 * User entity
 *
 * @author Petr Valenta
 */
@Entity
@Table(name="Users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @NotNull
    @Email
    @Column(nullable = false, unique = true)
    private String email;

    @NotNull
    private String firstName;

    @NotNull
    private String surname;

    @NotNull
    private boolean administrator;

    @NotNull
    private String passwordHash;

    public User() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public boolean isAdministrator() {
        return administrator;
    }

    public void setAdministrator(boolean admin) {
        this.administrator = admin;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || !(o instanceof User)) {
            return false;
        }

        final User user = (User) o;

        if (!user.getEmail().equals(getEmail())) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hash(email);
    }
}
