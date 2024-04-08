package com.satwik.splitwiseclone.persistence.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "refresh_token")
public class RefreshToken extends BaseEntity {

    @Column(name = "token", unique = true)
    String token;

    @Column(name = "expirationTime")
    Date expirationTime;

    @OneToOne
    @JoinColumn(name = "user_id")
    User user;

}
