package com.nightlyecommercebackend.repository;

import com.nightlyecommercebackend.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
