package com.nightlyecommercebackend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false)
    private String role_name;

    @ManyToMany(mappedBy = "roles")
    private Set<User> users = new HashSet<>();


//  | -------------------- | -------------------------------------------------------------------------------------------------------- |
//  | `mappedBy = "roles"` | Tells JPA: “the other side (User entity) owns the relationship” — this avoids duplication of join table. |
//  | -------------------- | -------------------------------------------------------------------------------------------------------- |

}
