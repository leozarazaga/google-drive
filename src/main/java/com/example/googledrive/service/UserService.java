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

/**
 * Service responsible for managing user-related operations.
 */
@Service
public class UserService implements UserDetailsService{

    private final UserRepository userRepository;

    private final PasswordEncoder encoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.encoder = encoder;

    }

    /**
     * Creates a new user based on the provided user details.
     *
     * @param dto The details of the user to be created.
     * @return The newly created user.
     */
    public User createUser(CreateUserDto dto) {
        User user = new User(dto.getUsername(), encoder.encode(dto.getPassword()), dto.getEmail());
        return userRepository.save(user);
    }

    /**
     * Retrieves a list of all users in the system.
     *
     * @return A list of all users.
     */
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    /**
     * Retrieves a user by their unique identifier.
     *
     * @param id The unique identifier of the user.
     * @return The user with the specified identifier.
     * @throws UserNotFoundException if the user is not found.
     */
    public User getUserById(String id) {
        UUID uuid = UUID.fromString(id);
        return userRepository.findById(uuid).orElseThrow(() -> new UserNotFoundException(id));
    }

    /**
     * Updates the details of an existing user.
     *
     * @param id  The unique identifier of the user to be updated.
     * @param dto The updated details of the user.
     * @return The updated user.
     * @throws UserNotFoundException if the user is not found.
     */
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

    /**
     * Deletes a user by their unique identifier.
     *
     * @param id The unique identifier of the user to be deleted.
     * @throws UserNotFoundException if the user is not found.
     */
    public void deleteUser(String id) {
        UUID uuid = UUID.fromString(id);
        User user = userRepository.findById(uuid).orElseThrow(() -> new UserNotFoundException(id));
        userRepository.delete(user);
    }

    /**
     * Loads a user by their username for Spring Security authentication.
     *
     * @param username The username of the user to be loaded.
     * @return The user details.
     * @throws UsernameNotFoundException if the user is not found.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = this.userRepository
                .findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Could not find user '" + username + "'."));
        return user;
    }

}
