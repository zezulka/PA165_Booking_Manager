package cz.muni.fi.pa165.api.dto;

/**
 *
 * @author Soňa Barteková
 *
 */
public class UserAuthenticateDTO {

    private String password;
    private String email;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
