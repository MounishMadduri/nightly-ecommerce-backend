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
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
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

    // Many-to-Many relationship
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles", // name of join table
            joinColumns = @JoinColumn(name = "user_id"), // foreign key from User
            inverseJoinColumns = @JoinColumn(name = "role_id") // foreign key from Role
    )
    private Set<Role> roles = new HashSet<>();


//   | ------------------------------------ | --------------------------------------------------------------------------------------- |
//   | `@ManyToMany`                        | Says: a user can have many roles, and a role can belong to many users.                  |
//   | `fetch = FetchType.EAGER`            | When a user is fetched from DB, also fetch the roles immediately (eagerly).             |
//   | `cascade = CascadeType.ALL`          | Operations like `persist()`, `delete()` on `User` will also be applied to its roles.    |
//   | `@JoinTable(...)`                    | Defines the **join table** `user_roles` with two foreign keys: `user_id` and `role_id`. |
//   | `Set<Role> roles = new HashSet<>();` | Avoids duplicate roles and initializes the collection.                                  |
//   | ------------------------------------ | --------------------------------------------------------------------------------------- |




}
