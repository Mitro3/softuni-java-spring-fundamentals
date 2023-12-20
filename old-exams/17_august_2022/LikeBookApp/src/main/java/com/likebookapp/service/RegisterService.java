package com.likebookapp.service;

import com.likebookapp.model.entity.User;
import com.likebookapp.model.dto.UserRegistrationDTO;
import com.likebookapp.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RegisterService {

    private final UserRepository userRepository;

    private final ModelMapper modelMapper;

    public RegisterService(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    public boolean register(UserRegistrationDTO registrationDTO) {
        if (!registrationDTO.getPassword().equals(registrationDTO.getConfirmPassword())) {
            return false;
        }

        Optional<User> byEmail = this.userRepository.findByEmail(registrationDTO.getEmail());

        if (byEmail.isPresent()) {
            return false;
        }

        User newUser = modelMapper.map(registrationDTO, User.class);

        this.userRepository.save(newUser);

        return true;
    }
}
