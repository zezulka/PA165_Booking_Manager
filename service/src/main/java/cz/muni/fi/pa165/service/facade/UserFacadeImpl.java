package cz.muni.fi.pa165.service.facade;

import cz.muni.fi.pa165.api.dto.UserAuthenticateDTO;
import cz.muni.fi.pa165.api.dto.UserDTO;
import cz.muni.fi.pa165.api.facade.UserFacade;
import cz.muni.fi.pa165.service.UserService;
import cz.muni.fi.pa165.service.auxiliary.BeanMappingService;
import cz.muni.fi.pa165.entity.User;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserFacadeImpl implements UserFacade {

    @Autowired
    private UserService userService;

    @Autowired
    private BeanMappingService beanMappingService;

    @Override
    public void register(UserDTO user, String password) {
        if (user == null) {
            throw new IllegalArgumentException("User must not be null.");
        }
        User userEntity = beanMappingService.mapTo(user, User.class);
        userService.register(userEntity, password);
        user.setId(userEntity.getId());
    }

    @Override
    public List<UserDTO> getAll() {
        return beanMappingService.mapTo(userService.getAll(), UserDTO.class);
    }

    @Override
    public boolean authenticate(UserAuthenticateDTO auth) {
        if (auth == null) {
            throw new IllegalArgumentException("Authentication details cannot be null.");
        }
        User user = userService.findByEmail(auth.getEmail());
        return userService.authenticate(user, auth.getPassword());
    }

    @Override
    public boolean isAdmin(UserDTO candidate) {
        return userService.isAdmin(beanMappingService.mapTo(candidate, User.class));
    }

    @Override
    public UserDTO findById(Long id) {
        User user = userService.findById(id);
        return (user == null) ? null : beanMappingService.mapTo(user, UserDTO.class);
    }

    @Override
    public UserDTO findByEmail(String email) {
        User user = userService.findByEmail(email);
        return (user == null) ? null : beanMappingService.mapTo(user, UserDTO.class);
    }

	@Override
	public void update(UserDTO user) {
        User userEntity = beanMappingService.mapTo(user, User.class);
        userService.update(userEntity);
	}

}
