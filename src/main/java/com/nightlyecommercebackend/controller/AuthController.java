package com.nightlyecommercebackend.controller;

import com.nightlyecommercebackend.entity.Login;
import com.nightlyecommercebackend.entity.Role;
import com.nightlyecommercebackend.entity.User;
import com.nightlyecommercebackend.repository.RoleRepository;
import com.nightlyecommercebackend.repository.UserRepository;
import com.nightlyecommercebackend.security.JWTUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    PasswordEncoder passwordEncoder;
    UserRepository userRepository;
    RoleRepository roleRepository;
    AuthenticationManager authenticationManager;
    JWTUtil jwtUtil;

    public AuthController(PasswordEncoder passwordEncoder, UserRepository userRepository, RoleRepository roleRepository, AuthenticationManager authenticationManager, JWTUtil jwtUtil) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody User userRequest) {
        if(userRepository.existsByUsername(userRequest.getUsername())){
            return ResponseEntity.badRequest().body("Username already exists");
        }
        if (userRepository.existsByEmail(userRequest.getEmail())) {
            return ResponseEntity.badRequest().body("Email is already in use!");
        }
        // Encrypt password
        System.out.println("Raw password: " + userRequest.getPassword());
        String hashed = userRequest.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        System.out.println("Hashed password: " + hashed);

        // Add ROLE_USER
        Optional<Role> userRoleOpt =   roleRepository.findByRoleName("ROLE_USER");
        Role userRole = userRoleOpt.orElseThrow(() -> new RuntimeException("ROLE_USER not found!"));
        userRequest.setRoles(Collections.singleton(userRole));

        userRequest.setEnabled(true);
        userRequest.setCreatedAt(LocalDateTime.now());
        userRequest.setUpdatedAt(LocalDateTime.now());

        userRepository.save(userRequest);

        return ResponseEntity.ok("User registered successfully!");

    }
    @PostMapping("/signup/admin")
    public ResponseEntity<?> registerAdmin(@RequestBody User userRequest) {
        if(userRepository.existsByUsername(userRequest.getUsername())){
            return ResponseEntity.badRequest().body("Username already exists");
        }
        if (userRepository.existsByEmail(userRequest.getEmail())) {
            return ResponseEntity.badRequest().body("Email is already in use!");
        }
        // Encrypt password
        userRequest.setPassword(passwordEncoder.encode(userRequest.getPassword()));

        // Add ROLE_ADMIN
        Optional<Role> adminRoleOpt = roleRepository.findByRoleName("ROLE_ADMIN");
        Role adminRole = adminRoleOpt.orElseThrow(() -> new RuntimeException("ROLE_ADMIN not found!"));
        userRequest.setRoles(Collections.singleton(adminRole));

        userRequest.setEnabled(true);
        userRequest.setCreatedAt(LocalDateTime.now());
        userRequest.setUpdatedAt(LocalDateTime.now());

        userRepository.save(userRequest);

        return ResponseEntity.ok("Admin registered successfully!");

    }

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@RequestBody Login loginRequest) {
        System.out.println("Login API called");
        System.out.println("Trying to authenticate: " + loginRequest.getUsername());
        System.out.println("Raw password: " + loginRequest.getPassword());
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );
        System.out.println("✅ Auth result: " + authentication.isAuthenticated());
        System.out.println("✅ Auth authorities: " + authentication.getAuthorities());
        System.out.println("Authentication successful for user: " + loginRequest.getUsername());
//        String jwt = jwtUtil.generateToken(loginRequest.getUsername());

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String jwt = jwtUtil.generateToken(userDetails);
        System.out.println("Generated JWT: " + jwt);
        return ResponseEntity.ok().body("{\"token\": \"" + jwt + "\"}");

    }

}
