package cz.muni.fi.pa165.api.facade;

import cz.muni.fi.pa165.api.dto.UserAuthenticateDTO;
import cz.muni.fi.pa165.api.dto.UserDTO;
import java.util.List;

public interface UserFacade {

    boolean register(UserDTO user, String password);

    List<UserDTO> getAll();

    boolean authenticate(UserAuthenticateDTO auth);

    boolean isAdmin(UserDTO candidate);

    UserDTO findById(Long id);

    UserDTO findByEmail(String email);
}
