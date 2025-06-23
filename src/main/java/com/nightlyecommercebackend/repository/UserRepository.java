package com.nightlyecommercebackend.repository;

import com.nightlyecommercebackend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
