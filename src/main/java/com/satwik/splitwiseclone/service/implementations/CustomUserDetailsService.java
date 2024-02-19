package com.satwik.splitwiseclone.service.implementations;

import com.satwik.splitwiseclone.persistence.models.User;
import com.satwik.splitwiseclone.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findById(Integer.valueOf(id));
        // TODO : add user not found exception
        if(!user.isPresent()) throw new UsernameNotFoundException("User not found!");

        User fetchedUser = user.get();

        UserDetails userDetails =  org.springframework.security.core.userdetails.User.builder()
                .username(String.valueOf(fetchedUser.getId()))
                .password(fetchedUser.getPassword())
                .roles("USER")
                .build();

        return userDetails;
    }
}
