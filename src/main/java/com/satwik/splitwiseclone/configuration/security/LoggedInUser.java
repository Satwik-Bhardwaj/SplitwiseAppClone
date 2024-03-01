package com.satwik.splitwiseclone.configuration.security;

import com.satwik.splitwiseclone.persistence.models.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class LoggedInUser {

    private UUID userId;

}
