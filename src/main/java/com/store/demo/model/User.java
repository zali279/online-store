package com.store.demo.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Entity
@Data
@RequiredArgsConstructor
@ToString(exclude = {"cart"})  // avoid recursion
@EqualsAndHashCode(exclude = {"cart"}) // avoid recursion
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String name;
    private String email;
    private String password;

    @OneToOne(mappedBy = "user")
    private Cart cart;
}
