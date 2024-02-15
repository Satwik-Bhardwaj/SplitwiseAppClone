package com.satwik.splitwiseclone.persistence.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
@Entity
@Table(name = "user")
public class User {

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "username")
    private String username;

    @Column(name = "email")
    private String email;

    @Column(name = "phone_country_code")
    private String countryCode;

    @Column(name = "phone_number")
    private long phoneNumber;

    @Column(name = "password")
    private String password;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Expense> expenseList;

//    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    private List<Group> groupList;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Group> groups;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<ExpenseShare> userInvolvedInExpenses;

}
