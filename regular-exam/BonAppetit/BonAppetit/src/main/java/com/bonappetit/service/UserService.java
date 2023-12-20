package com.bonappetit.service;

import com.bonappetit.model.dtos.RegistrationDTO;
import com.bonappetit.model.entity.User;
import com.bonappetit.repo.UserRepository;
import com.bonappetit.util.LoggedUser;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepo;

    private final LoggedUser loggedUser;

    private final PasswordEncoder encoder;
    private final HttpSession httpSession;

    public UserService(UserRepository userRepo, LoggedUser loggedUser, PasswordEncoder encoder, HttpSession httpSession) {
        this.userRepo = userRepo;
        this.loggedUser = loggedUser;
        this.encoder = encoder;
        this.httpSession = httpSession;
    }

    public User findByEmail(String value) {
        return this.userRepo.findByEmail(value).orElse(null);
    }

    public User findByUsername(String value) {
        return this.userRepo.findByUsername(value).orElse(null);
    }

    public void initAdmin() {
        if (this.userRepo.count() != 0) {
            return;
        }

        User admin = new User();
        admin.setUsername("admin");
        admin.setPassword(encoder.encode("1234"));
        admin.setEmail("admin@abv.bg");
        this.userRepo.save(admin);
    }

    public void initTestUser() {
        if (this.userRepo.count() != 1) {
            return;
        }

        User testUser = new User();
        testUser.setUsername("testUser");
        testUser.setPassword(encoder.encode("1234"));
        testUser.setEmail("test@abv.bg");
        this.userRepo.save(testUser);
    }

    public User findUserById(long id) {
        return this.userRepo.findById(id).orElse(null);
    }

    public void saveUser(User user) {
        this.userRepo.save(user);
    }

    public void register(RegistrationDTO dto) {
        this.userRepo.save(this.mapUser(dto));
        this.login(dto.getUsername());
    }

    public void login(String username) {
        User user = this.userRepo.getByUsername(username).orElse(null);
        this.loggedUser.setId(user.getId());
        this.loggedUser.setUsername(username);
    }

    private User mapUser(RegistrationDTO dto) {
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setPassword(encoder.encode(dto.getPassword()));

        return user;
    }

    public boolean checkCredentials(String username, String password) {
        Optional<User> user = this.userRepo.getByUsername(username);

        if (user.isEmpty()) {
            return false;
        }

        return encoder.matches(password, user.get().getPassword());
    }

    public void logout() {
        this.httpSession.invalidate();
        this.loggedUser.setId(null);
        this.loggedUser.setUsername(null);
    }
}
