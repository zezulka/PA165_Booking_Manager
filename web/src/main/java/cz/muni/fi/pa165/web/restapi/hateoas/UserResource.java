package cz.muni.fi.pa165.web.restapi.hateoas;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.List;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.core.Relation;

import cz.muni.fi.pa165.api.dto.UserDTO;

/**
 * @author Petr Valenta
 */
@Relation(value = "user", collectionRelation = "users")
@JsonPropertyOrder({"id", "firstName", "surname", "email"})
public class UserResource extends ResourceSupport {

    @JsonProperty("id")
    private long dtoId;
    private String firstName;
    private String surname;
    private String email;

    public UserResource(UserDTO user) {
        email = user.getEmail();
        firstName = user.getFirstName();
        surname = user.getSurname();
        dtoId = user.getId();
    }

    public String getEmail() {
        return email;
    }

    public long getDtoId() {
        return dtoId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getSurname() {
        return surname;
    }

}