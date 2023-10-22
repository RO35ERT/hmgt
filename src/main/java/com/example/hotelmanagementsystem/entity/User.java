package com.example.hotelmanagementsystem.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String lastName;

    private String middleName;
    @Column(nullable = false)
    private String residential;
    @Column(nullable = false)
    private String postal;

    @Column(nullable = false)
    private Boolean isEnabled;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(
            name = "branch_id"
    )
    private Branch branch;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "users_roles",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")}
    )
    private List<Role> roles = new ArrayList<>();

    @OneToOne(
            mappedBy = "user",
            cascade = CascadeType.ALL
    )
    private ConfirmationToken token;

    public User(String name, String email, String password, String lastName, String middleName, String residential, String postal, Boolean isEnabled, List<Role> roles) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.lastName = lastName;
        this.middleName = middleName;
        this.residential = residential;
        this.postal = postal;
        this.isEnabled = isEnabled;
        this.roles = roles;
    }
}
