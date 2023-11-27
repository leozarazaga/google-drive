package com.example.googledrive.service;

import com.example.googledrive.dto.CreateUserDto;
import com.example.googledrive.entity.User;
import com.example.googledrive.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /*    - - - - - - - - - - - - - - - - - - -   */

    public User createUser(CreateUserDto dto){
        User user = new User(dto.getUsername(), dto.getEmail());
        return userRepository.save(user);
    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public User getUserById(String id){
        UUID uuid = UUID.fromString(id);
        return userRepository.findById(uuid).orElseThrow();
    }

}
