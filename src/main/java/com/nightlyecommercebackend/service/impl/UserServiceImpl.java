package com.nightlyecommercebackend.service.impl;

import com.nightlyecommercebackend.entity.User;
import com.nightlyecommercebackend.repository.UserRepository;
import com.nightlyecommercebackend.service.interfaces.UserService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    UserRepository userRepository;
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    private BCryptPasswordEncoder encoder  = new BCryptPasswordEncoder(12);
    @Override
    public User createUser(User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        return userRepository.save(user);
    }
}
