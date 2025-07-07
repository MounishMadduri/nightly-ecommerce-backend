package com.nightlyecommercebackend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false)
    private String username;
    @Column(unique = true, nullable = false)
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private Boolean enabled;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public User(Long id, String username, String email, String password, String firstName, String lastName, Boolean enabled, LocalDateTime createdAt, LocalDateTime updatedAt, Set<Role> roles) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.enabled = enabled;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.roles = roles;
    }

    // Many-to-Many relationship
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles", // name of join table
            joinColumns = @JoinColumn(name = "user_id"), // foreign key from User
            inverseJoinColumns = @JoinColumn(name = "role_id") // foreign key from Role
    )
    private Set<Role> roles = new HashSet<>();

    public User() {

    }


//   | ------------------------------------ | --------------------------------------------------------------------------------------- |
//   | `@ManyToMany`                        | Says: a user can have many roles, and a role can belong to many users.                  |
//   | `fetch = FetchType.EAGER`            | When a user is fetched from DB, also fetch the roles immediately (eagerly).             |
//   | `cascade = CascadeType.ALL`          | Operations like `persist()`, `delete()` on `User` will also be applied to its roles.    |
//   | `@JoinTable(...)`                    | Defines the **join table** `user_roles` with two foreign keys: `user_id` and `role_id`. |
//   | `Set<Role> roles = new HashSet<>();` | Avoids duplicate roles and initializes the collection.                                  |
//   | ------------------------------------ | --------------------------------------------------------------------------------------- |


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public String setPassword(String password) {
        this.password = password;
        return password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

}
