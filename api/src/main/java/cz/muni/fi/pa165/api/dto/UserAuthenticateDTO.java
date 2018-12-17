package cz.muni.fi.pa165.api.dto;

/**
 *
 * @author Soňa Barteková
 *
 */
public class UserAuthenticateDTO {

    private Long userId;
    private String password;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long usedId) {
        this.userId = usedId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
