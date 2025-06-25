package com.nightlyecommercebackend.service.interfaces;

import com.nightlyecommercebackend.entity.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public interface UserService {
    User createUser(User user);
}
