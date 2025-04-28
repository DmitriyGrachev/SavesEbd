package org.hrachov.com.filmproject.service.impl;

import org.hrachov.com.filmproject.model.User;
import org.hrachov.com.filmproject.repository.UserRepository;
import org.hrachov.com.filmproject.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private static UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    public User findUserById(long id) {
        //TODO
        return userRepository.findById(id).orElse(null);
    }
}
