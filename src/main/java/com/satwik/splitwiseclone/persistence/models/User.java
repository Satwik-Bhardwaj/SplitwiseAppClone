package com.satwik.splitwiseclone.persistence.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NonNull;
import org.antlr.v4.runtime.misc.NotNull;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Entity
@Table(name = "user")
public class User extends BaseEntity {

    @Column(name = "username", unique = true)
    private String username;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "phone_country_code")
    private String countryCode;

    @Column(name = "phone_number", unique = true)
    private long phoneNumber;

    @Column(name = "password")
    private String password;

    @OneToMany(mappedBy = "owner", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Expense> expenseList;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Group> groupList;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ExpenseShare> userInvolvedInExpenses;

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<GroupMembers> groupMembers = new ArrayList<>();

}
