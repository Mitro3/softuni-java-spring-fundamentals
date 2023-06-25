package bg.softuni.mobilele.services;

import bg.softuni.mobilele.dtos.UserLoginDTO;
import bg.softuni.mobilele.dtos.UserRegisterDTO;
import bg.softuni.mobilele.entities.UserEntity;
import bg.softuni.mobilele.repositories.UserRepository;
import bg.softuni.mobilele.user.CurrentUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    private CurrentUser currentUser;

    private Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    public UserService(UserRepository userRepository, CurrentUser currentUser) {
        this.userRepository = userRepository;
        this.currentUser = currentUser;
    }

    public void registerAndLogin(UserRegisterDTO userRegisterDTO) {

        UserEntity newUser = new UserEntity();

        newUser.setActive(true);
        newUser.setEmail(userRegisterDTO.getEmail());
        newUser.setFirstName(userRegisterDTO.getFirstName());
        newUser.setLastName(userRegisterDTO.getLastName());
        newUser.setPassword(userRegisterDTO.getPassword());

        userRepository.save(newUser);

        login(newUser);
    }

    public boolean login(UserLoginDTO userLoginDTO) {
        Optional<UserEntity> useOpt = userRepository.findByEmail(userLoginDTO.getUsername());

        if (useOpt.isEmpty()) {
            LOGGER.debug("User with name [{}] not found.", userLoginDTO.getUsername());
            return false;
        }

        boolean success = useOpt.get().getPassword().equals(userLoginDTO.getPassword());

        if (success) {
            login(useOpt.get());
        } else {
            logout();
        }

        return success;
    }

    public void login(UserEntity user) {
        currentUser.setLoggedIn(true);
        currentUser.setName(user.getFirstName() + " " + user.getLastName());
    }

    public void logout() {
        currentUser.clear();
    }
}
