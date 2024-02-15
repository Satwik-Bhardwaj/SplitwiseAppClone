package com.satwik.splitwiseclone.persistence.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "group_table")
public class Group {

    @Id
    @Column(name = "group_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "group_name")
    private String groupName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

}
