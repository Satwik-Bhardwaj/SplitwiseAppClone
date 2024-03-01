package com.satwik.splitwiseclone.service.implementations;

import com.satwik.splitwiseclone.configuration.security.LoggedInUser;
import com.satwik.splitwiseclone.persistence.dto.user.PhoneDTO;
import com.satwik.splitwiseclone.persistence.dto.user.RegisterUserRequest;
import com.satwik.splitwiseclone.persistence.dto.user.UserDTO;
import com.satwik.splitwiseclone.persistence.models.Group;
import com.satwik.splitwiseclone.persistence.models.User;
import com.satwik.splitwiseclone.repository.GroupRepository;
import com.satwik.splitwiseclone.repository.UserRepository;
import com.satwik.splitwiseclone.service.interfaces.UserService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    GroupRepository groupRepository;

    @Autowired
    private LoggedInUser loggedInUser;

    @Autowired
    private BCryptPasswordEncoder pwdEncoder;

    User checkUser(UUID userId) throws AccessDeniedException {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        User loggedUser = userRepository.findById(loggedInUser.getUserId()).orElseThrow(() -> new RuntimeException("User not found"));
        if (loggedUser.getId() != user.getId()) throw  new AccessDeniedException("Access Denied");

        return user;
    }

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
        user = userRepository.save(user);

        Group group = new Group();
        group.setGroupName("Non Grouped Expenses");
        group.setUser(user);
        group.setDefaultGroup(true);
        groupRepository.save(group);

        return user.getId().toString();
    }

    @Override
    public UserDTO findUserById(UUID userId) throws Exception {

        User user = checkUser(userId);

        return new UserDTO(
                user.getUsername(),
                user.getEmail(),
                new PhoneDTO(user.getCountryCode(), user.getPhoneNumber())
        );
    }

    @Override
    @Transactional
    public String deleteUser(UUID userId) throws Exception{

        User user = checkUser(userId);
        userRepository.deleteById(user.getId());

        return "%s - user deleted.".formatted(userId);
    }

    @Override
    @Transactional
    public String updateUser(UUID userId, RegisterUserRequest request) throws Exception{

        User user = checkUser(userId);
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setCountryCode(request.getPhone().getCountryCode());
        user.setPhoneNumber(request.getPhone().getPhoneNumber());
        user.setPassword(pwdEncoder.encode(request.getPassword()));
        userRepository.save(user);

        return request.getUsername() + " updated successfully.";
    }

}
