package com.satwik.splitwiseclone.persistence.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "group_table")
public class Group extends BaseEntity {

    @Column(name = "group_name")
    private String groupName;

    @Column(name = "default_group")
    private boolean defaultGroup;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

}
