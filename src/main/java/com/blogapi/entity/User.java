package com.blogapi.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String email;

    private String password;

    private String username;

    @ManyToMany(fetch = FetchType.EAGER) // going to load every thing in memory
    @JoinTable(name = "user_roles",        // this annotation is use to join two table ie user&role
            joinColumns = @JoinColumn(name = "user_id"),//user id is join to  user long id LtoR
            inverseJoinColumns = @JoinColumn(name = "role_id"))//role_id is join to role long id RtoL
    private Set<Role> roles = new HashSet<>(); // set it is; so roles not to be duplicate

    // constructors, getters, and setters

    // ...
}
