package com.satwik.splitwiseclone.service.implementations;

import com.satwik.splitwiseclone.persistence.dto.user.PhoneDTO;
import com.satwik.splitwiseclone.persistence.dto.user.RegisterUserRequest;
import com.satwik.splitwiseclone.persistence.dto.user.UserDTO;
import com.satwik.splitwiseclone.persistence.models.Group;
import com.satwik.splitwiseclone.persistence.models.User;
import com.satwik.splitwiseclone.repository.GroupRepository;
import com.satwik.splitwiseclone.repository.UserRepository;
import com.satwik.splitwiseclone.service.interfaces.UserService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.nio.file.AccessDeniedException;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    GroupRepository groupRepository;

    @Autowired
    private BCryptPasswordEncoder pwdEncoder;

    // for save and update
    @Override
    @Transactional
    public String saveUser(RegisterUserRequest request) {

        User user = new User();

        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setCountryCode(request.getPhone().getCountryCode());
        user.setPhoneNumber(request.getPhone().getPhoneNumber());

        user.setPassword(pwdEncoder.encode(request.getPassword()));

        // saving the user and getting the saved entity
        user = userRepository.save(user);

        // creating the non group expenses group
        Group group = new Group();
        group.setGroupName("Non Grouped Expenses");
        group.setUser(user);
        group.setDefaultGroup(true);

        // save the group
        groupRepository.save(group);

        return user.getId().toString();
    }


    @Override
    public UserDTO findUserById(UUID userId) throws Exception {

        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        UserDTO userResult = new UserDTO(
                user.getUsername(),
                user.getEmail(),
                new PhoneDTO(user.getCountryCode(), user.getPhoneNumber())
        );

        return userResult;
    }

    @Override
    @Transactional
    public String deleteUser(UUID userId) throws Exception{

        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        userRepository.deleteById(userId);

        return "%s - user deleted.".formatted(userId);
    }

    @Override
    @Transactional
    public String updateUser(UUID userId, RegisterUserRequest request) throws Exception{

        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setCountryCode(request.getPhone().getCountryCode());
        user.setPhoneNumber(request.getPhone().getPhoneNumber());
        user.setPassword(pwdEncoder.encode(request.getPassword()));
        userRepository.save(user);

        return request.getUsername() + " updated successfully.";
    }

}
