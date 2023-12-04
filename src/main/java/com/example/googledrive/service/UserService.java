package com.example.googledrive.service;

import com.example.googledrive.dto.CreateUserDto;
import com.example.googledrive.dto.UpdateUserDto;
import com.example.googledrive.entity.User;
import com.example.googledrive.exception.UserNotFoundException;
import com.example.googledrive.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    private final PasswordEncoder encoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.encoder = encoder;

    }

    /*    - - - - - - - - - - - - - - - - - - -   */

    public User createUser(CreateUserDto dto) {
        User user = new User(dto.getUsername(), encoder.encode(dto.getPassword()), dto.getEmail());
        return userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(String id) {
        UUID uuid = UUID.fromString(id);
        return userRepository.findById(uuid).orElseThrow(() -> new UserNotFoundException(id));
    }


    public User updateUser(String id, UpdateUserDto dto) {
        UUID uuid = UUID.fromString(id);
        User user = userRepository.findById(uuid).orElseThrow(() -> new UserNotFoundException(id));

        if (dto.getUsername().isPresent()) {
            user.setUsername(dto.getUsername().get());
        }

        if (dto.getEmail().isPresent()) {
            user.setEmail(dto.getEmail().get());
        }
        return userRepository.save(user);
    }

    public void deleteUser(String id) {
        UUID uuid = UUID.fromString(id);
        User user = userRepository.findById(uuid).orElseThrow(() -> new UserNotFoundException(id));
        userRepository.delete(user);
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = this.userRepository
                .findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Could not find user '" + username + "'."));
        return user;
    }
}
